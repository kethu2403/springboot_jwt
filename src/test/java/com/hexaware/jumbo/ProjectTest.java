package com.hexaware.jumbo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hexaware.jumbo.controller.ProjectController;
import com.hexaware.jumbo.model.Project;
import com.hexaware.jumbo.repo.ProjectRepository;
import com.hexaware.jumbo.util.CommonUtil;
import com.hexaware.jumbo.util.JwtTokenUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

// @SpringBootTest
// @RunWith(MockitoJUnitRunner.class)
public class ProjectTest {

    final private String testToken = "";

    @InjectMocks
    ProjectController projectController;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    CommonUtil commonUtil;

    @InjectMocks
    JwtTokenUtil jwtTokenUtil;

    @Mock
    JwtTokenUtil jwtTokens;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(jwtTokenUtil, "jwtAccessTokenExpiryTime", "1");
        ReflectionTestUtils.setField(jwtTokenUtil, "jwtSecret", "jumbosecret");
    }

    @Test
    public void testGetProjects() throws Exception {
        List<Project> project = new ArrayList<Project>();
        project.add(new Project(1, "test1", "user1", new Date()));
        project.add(new Project(2, "test2", "user2", new Date()));
        
        when(projectRepository.findAll()).thenReturn(project);

        ResponseEntity<List<Project>> prj = projectController.getProjects();
        assertEquals(200, prj.getStatusCodeValue());
        assertEquals(2, prj.getBody().size());

    }

    @Test
    public void testCreateProject() throws Exception {
        Project project = new Project(1, "test1", "user", new Date());
        
        String token = createToken();
        String authHeader = "Bearer " + token;

        when(jwtTokens.getUsernameFromToken(token)).thenReturn("user");
        when(projectRepository.save(project)).thenReturn(project);

        ResponseEntity<Project> prj = projectController.createProject(project, authHeader);
        assertEquals(201, prj.getStatusCodeValue());
    }

    public String createToken() {
        String username = "user";
        return jwtTokenUtil.generateAccessToken(username);
    }
}