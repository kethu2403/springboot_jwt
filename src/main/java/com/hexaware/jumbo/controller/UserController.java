package com.hexaware.jumbo.controller;

import com.hexaware.jumbo.model.User;
import com.hexaware.jumbo.model.UserDetails;
import com.hexaware.jumbo.service.UserService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * swagger annotaions.
 */
@Api(tags = "registration")
public class UserController {
    /**
     * to get logs.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    /**
     * userservice.
     */
    @Autowired
    private UserService userService;
    /**
     * @param user user.
     * @return user details.
     * @throws Exception exception.
     */
    @PostMapping("/register")
    @ApiOperation(value = "User Registration")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized user"),
        @ApiResponse(code = 201, message = "User got succefully authenticated"),
        @ApiResponse(code = 400, message = "Exception in authenticating user"),
        @ApiResponse(code = 404, message = "Method not found")
    })
    public ResponseEntity<UserDetails> registerUser(@ApiParam("Register User") @RequestBody final User user) {
        UserDetails userDetails = userService.save(user);
        LOGGER.info("New User Registered:: " + userDetails.toString());
        return new ResponseEntity<UserDetails>(userDetails, HttpStatus.CREATED);
    }
}
