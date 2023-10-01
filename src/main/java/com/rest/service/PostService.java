package com.rest.service;

import com.rest.domain.dto.CategoryResponse;
import com.rest.domain.dto.PostRequest;
import com.rest.domain.dto.PostResponse;
import com.rest.domain.model.Post;
import com.rest.domain.model.PostCategory;

public interface PostService {
    PostCategory createPostForGivenUser(Integer userId, Integer categoryId, PostRequest postRequest);
    PostResponse getPostByUserIdAndPostId(Integer userId, Integer postId);
}
