package com.hexaware.jumbo.controller;

import com.hexaware.jumbo.util.JwtTokenUtil;
import com.hexaware.jumbo.model.JwtTokenDetails;
import com.hexaware.jumbo.model.User;
import com.hexaware.jumbo.service.UserService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
 * swagger annotations.
 */
@Api(tags = "login/authenticate")
public class LoginController {
    /**
     * to get logs.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    /**
     * AuthenticationManager.
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * UserService.
     */
    @Autowired
    private UserService userService;
    /**
     * JwtTokenUtil.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     * @param authenticationRequest authenticationRequest.
     * @return token.
     * @throws Exception exception.
     */
    @PostMapping("/login")
    @ApiOperation(value = "User login/Authentication")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized user"),
        @ApiResponse(code = 201, message = "User got succefully registered"),
        @ApiResponse(code = 400, message = "Exception in registering user"),
        @ApiResponse(code = 404, message = "Method not found")
    })
    public ResponseEntity<JwtTokenDetails> createAuthenticationToken(@ApiParam("User Credentials")
        @RequestBody final User authenticationRequest)
        throws Exception {
        JwtTokenDetails jwtTokenDetails = new JwtTokenDetails();
        try {
            this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
            LOGGER.info(">>>>> userDetails <<<<<" + userDetails.getUsername());
            String jwtAccessToken = jwtTokenUtil.generateAccessToken(userDetails.getUsername());
            jwtTokenDetails.setJwtAccessToken(jwtAccessToken);
            return new ResponseEntity<JwtTokenDetails>(jwtTokenDetails, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Exception in authenticating user");
            return new ResponseEntity<>(jwtTokenDetails, HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * @param username username.
     * @param password password.
     * @throws Exception exception.
     */
    private void authenticate(final String username, final String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
