package com.rest.service.impl;

import com.rest.BlogDataSource;
import com.rest.domain.dto.PostRequest;
import com.rest.domain.dto.PostResponse;
import com.rest.domain.model.Post;
import com.rest.domain.model.PostCategory;
import com.rest.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceInMemory implements PostService {

    @Autowired
    private BlogDataSource dataSource;

    @Override
    public PostCategory createPostForGivenUser(Integer userId, Integer categoryId, PostRequest postRequest) {
        var index = dataSource.getIdIndex();
        var user = dataSource.getUsers().stream()
                .filter(u -> u.getId() == userId)
                .findFirst().orElse(null);
        var category = dataSource.getCategories().stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst().orElse(null);
        var createdPost = new Post(index, postRequest.getTitle(), postRequest.getBody(), user);
        var postCategory = new PostCategory(createdPost, category);
        var isPostAdded = dataSource.getPosts().add(createdPost);
        boolean isPostCategoryAdded = false;
        if(isPostAdded){
            isPostCategoryAdded = dataSource.getPostCategories().add(postCategory);
        }
        return isPostCategoryAdded ? postCategory : null;
    }

    @Override
    public PostResponse getPostByUserIdAndPostId(Integer userId, Integer postId) {
        var post = this.dataSource.getPosts().stream()
                .filter(p -> p.getUser().getId() == userId && p.getId() == postId)
                .findFirst().orElse(null);
        return post == null ? null : new PostResponse(post.getId(), post.getTitle(), post.getBody(), post.getUser());
    }
}