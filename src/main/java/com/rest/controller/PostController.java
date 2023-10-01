package com.rest.controller;
import com.rest.domain.dto.PostRequest;
import com.rest.domain.dto.PostResponse;
import com.rest.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/api/users/{userId}/posts/{categoryId}")
    public ResponseEntity<Void> createPostForGivenUser(@RequestBody PostRequest postRequest,
                                                       UriComponentsBuilder ucb,
                                                       @PathVariable Integer userId,
                                                       @PathVariable Integer categoryId){
        var createdPost = postService.createPostForGivenUser(userId, categoryId, postRequest);
        URI locationOfCreatedPost = ucb
                .path("/api/users/{userId}/posts/{categoryId}")
                .buildAndExpand(createdPost.getPost().getId(), createdPost.getCategory().getId())
                .toUri();
        return ResponseEntity.created(locationOfCreatedPost).build();
    }

    @GetMapping("/api/users/{userId}/posts/{postId}")
    public ResponseEntity<PostResponse> getPostByUserIdAndPostId(@PathVariable Integer userId, @PathVariable Integer postId){
        PostResponse postResponse = postService.getPostByUserIdAndPostId(userId, postId);
        return postResponse!=null?ResponseEntity.ok(postResponse):ResponseEntity.notFound().build();
    }
}
