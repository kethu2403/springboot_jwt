package com.hexaware.jumbo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hexaware.jumbo.exception.ErrorDetails;
import com.hexaware.jumbo.model.Project;
import com.hexaware.jumbo.repo.ProjectRepository;
import com.hexaware.jumbo.util.CommonUtil;
import com.hexaware.jumbo.util.JwtTokenUtil;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * RestController annotation.
 */
@RestController
/**
 * Cross Origin.
 */
@CrossOrigin
/**
 * RequestMapping.
 */
@RequestMapping("/jumbo/api/v1")
/**
 * SampleController
 */
/**
 * Swagger Annotations.
 */
@Api(tags = "projects")
public class ProjectController {
    /**
     * to get logs.
   */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
    /**
     * ProjectRepository.
     */
    @Autowired
    private ProjectRepository prjRepo;
    /**
     * CommonUtil.
     */
    @Autowired
    private CommonUtil commonUtil;
    /**
     * JwtTokenUtil.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private ProjectController() {

    }

    /**
    * @return projects.
    * @param authHeader authHeader.
    * @param prj prj.
    * @throws Exception exception.
    */
    @PostMapping("/projects")
    @ApiOperation(value = "creating projects")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized user"),
        @ApiResponse(code = 201, message = "project got succefully created"),
        @ApiResponse(code = 400, message = "Exception in creating project"),
        @ApiResponse(code = 404, message = "Method not found")
    })
    public ResponseEntity<Project> createProject(@ApiParam("project Info") @RequestBody final Project prj,
        @ApiParam("authHeader") @RequestHeader(value = "authorization") final String authHeader) throws Exception {
        Project project = new Project();
        try {
            String token = commonUtil.getTokenUsingHeader(authHeader);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (username == null) {
                throw new IllegalArgumentException("Invalid username");
            }
            prj.setCreatedBy(username);
            prj.setCreatedDate(new Date());
            Project result = prjRepo.save(prj);
            LOGGER.info("Project Created Successfully ::" + result.toString());
            return new ResponseEntity<Project>(prj, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.info("Exception in creating Project");
            return new ResponseEntity<>(project, HttpStatus.BAD_REQUEST);
        }
    }

      /**
      * @return sample string.
      * @throws Exception excetion
      */
    @GetMapping("/projects")
    @ApiOperation(value = "reading projects")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized user"),
        @ApiResponse(code = 200, message = "projects got retrevied"),
        @ApiResponse(code = 400, message = "Exception in retreving project"),
        @ApiResponse(code = 404, message = "Method not found")
    })
      public ResponseEntity<List<Project>> getProjects() throws Exception {
        List<Project> project = new ArrayList<>();
        try {
            List<Project> projects = prjRepo.findAll();
            return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Exception in getting projects");
            return new ResponseEntity<>(project, HttpStatus.BAD_REQUEST);
        }
    }

      /**
      * It will handle IllegalArgumentException for this controller.
      *
      * @param ex - Exception
      * @param rq - Web Request
      * @return Error Details
      */
    @ExceptionHandler(IllegalArgumentException.class)
      public final ResponseEntity<ErrorDetails> exception(final IllegalArgumentException ex, final WebRequest rq) {
        LOGGER.error("Exception : {}", ex.getMessage());
        ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
