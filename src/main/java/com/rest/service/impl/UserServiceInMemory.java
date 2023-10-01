package com.rest.service.impl;

import com.rest.BlogDataSource;
import com.rest.domain.dto.PostRequest;
import com.rest.domain.dto.PostResponse;
import com.rest.domain.dto.UserRequest;
import com.rest.domain.dto.UserResponse;
import com.rest.domain.model.Post;
import com.rest.domain.model.PostCategory;
import com.rest.domain.model.User;
import com.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceInMemory implements UserService {
    @Autowired
    private BlogDataSource dataSource;
    @Override
    public UserResponse getUserById(Integer id) {
        var user = dataSource.getUsers().stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst().orElse(null);
        return user == null ? null : new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public User createUser(UserRequest userRequest) {
        var index = dataSource.getUsers().size()+1;
        var user = new User(index, userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        return dataSource.getUsers().add(user) ? user : null;
    }

    @Override
    public UserResponse updateUser(Integer userId, UserRequest userRequest) {
        var userToUpdate = dataSource.getUsers().stream()
                .filter(u -> u.getId() == userId)
                .findFirst().orElse(null);
        if(userToUpdate != null){
            userToUpdate.setName(userRequest.getName());
            userToUpdate.setEmail(userRequest.getEmail());
            userToUpdate.setPassword(userRequest.getPassword());
            var isAdded = dataSource.getUsers().add(userToUpdate);
            var response = new UserResponse(userToUpdate.getId(), userToUpdate.getName(), userToUpdate.getEmail());
            return isAdded ? response : null;
        }
        return null;
    }

    @Override
    public List<PostResponse> getPostsByUserId(Integer userId) {
        List<Post> userPosts= this.dataSource.getPosts().stream()
                .filter(p -> p.getUser().getId() == userId)
                .collect(Collectors.toList());
        List<PostResponse> postResponses = userPosts.stream()
                .map(r -> new PostResponse(r.getId(), r.getTitle(), r.getBody(), r.getUser()))
                .collect(Collectors.toList());
        return postResponses;
    }

}
