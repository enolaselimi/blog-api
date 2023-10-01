package com.rest.service;

import com.rest.domain.dto.PostRequest;
import com.rest.domain.dto.PostResponse;
import com.rest.domain.dto.UserRequest;
import com.rest.domain.dto.UserResponse;
import com.rest.domain.model.Post;
import com.rest.domain.model.User;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Integer id);
    User createUser(UserRequest userRequest);
    UserResponse updateUser(Integer userId, UserRequest userRequest);
    List<PostResponse> getPostsByUserId(Integer userId);




}
