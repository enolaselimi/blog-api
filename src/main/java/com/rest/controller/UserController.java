package com.rest.controller;
import com.rest.domain.dto.PostResponse;
import com.rest.domain.dto.UserRequest;
import com.rest.domain.dto.UserResponse;
import com.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId){
        UserResponse userResponse = userService.getUserById(userId);
        return userResponse!=null?ResponseEntity.ok(userResponse):ResponseEntity.notFound().build();
    }

    @PostMapping("/api/users")
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucb){
        var createdUser = userService.createUser(userRequest);
        URI locationOfCreatedUser = ucb
                .path("/api/users/{userId}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(locationOfCreatedUser).build();
    }

    @PutMapping("/api/users/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer userId, @RequestBody UserRequest userRequest){
        var updatedUser = userService.updateUser(userId, userRequest);
        return updatedUser!=null?ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }

    @GetMapping("/api/users/{userId}/posts")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getPostsByUserId(userId));
    }

}
