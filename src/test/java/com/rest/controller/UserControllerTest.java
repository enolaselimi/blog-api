package com.rest.controller;

import com.rest.RestBlogApplication;
import com.rest.domain.dto.PostResponse;
import com.rest.domain.dto.UserRequest;
import com.rest.domain.dto.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {RestBlogApplication.class}
)
public class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void test_shouldReturnUserResponseWhenUserIsFound(){
        ResponseEntity<UserResponse> response = restTemplate
                .getForEntity("/api/users/{userId}",UserResponse.class,1);
        Assertions.assertEquals(response.getStatusCode(),HttpStatus.OK);
        var userObj = response.getBody();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, userObj.getId()),
                ()-> Assertions.assertEquals("enola",userObj.getName()),
                ()->Assertions.assertEquals("enola@test.com",userObj.getEmail())
        );
    }

    @Test
    void test_shouldReturn201WhenUserAdded(){
        var userRequest = new UserRequest("alban","alban@test.com", "alban");
        ResponseEntity<Void> userResponse = restTemplate
                .postForEntity("/api/users", userRequest, Void.class);
        URI locationOfUser = userResponse.getHeaders().getLocation();
        ResponseEntity<UserResponse> getResponse = restTemplate
                .getForEntity(locationOfUser, UserResponse.class);
        var addedUser = getResponse.getBody();
        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.CREATED, userResponse.getStatusCode()),
                () -> Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode()),
                () -> Assertions.assertEquals("alban", addedUser.getName()),
                () -> Assertions.assertEquals("alban@test.com", addedUser.getEmail())
        );
    }

    @Test
    void test_shouldReturn204WhenUserIsUpdated(){
        var playerRequest = new UserRequest("name to update", "email to update", "password to update");
        var requestBody = new HttpEntity<>(playerRequest);

        ResponseEntity<Void> putResponse = restTemplate
                .exchange("/api/users/{userId}", HttpMethod.PUT, requestBody, Void.class, 1);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, putResponse.getStatusCode());

        ResponseEntity<UserResponse> getResponse = restTemplate
                .getForEntity("/api/users/{userId}",UserResponse.class,1);
        var responseBody = getResponse.getBody();

        Assertions.assertAll(
                () -> Assertions.assertEquals("name to update", responseBody.getName()),
                () -> Assertions.assertEquals("email to update", responseBody.getEmail())
        );
    }

    @Test
    void test_shouldReturnPostsForAGivenUser(){
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/users/{userId}/posts",String.class, 1);
        Assertions.assertNotNull(getResponse.getBody());
    }

    @Test
    void test_shouldReturnPostsForAGivenUserAndPostId(){
        ResponseEntity<PostResponse> getResponse = restTemplate
                .getForEntity("/api/users/{userId}/posts/{postId}", PostResponse.class,1,1);
        Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, getResponse.getBody().getPostId()),
                () -> Assertions.assertEquals(1, getResponse.getBody().getUser().getId()),
                () -> Assertions.assertEquals("A", getResponse.getBody().getTitle()),
                () -> Assertions.assertEquals("aaaa", getResponse.getBody().getBody())
        );
    }
}
