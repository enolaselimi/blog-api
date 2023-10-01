package com.rest.domain.dto;

import com.rest.domain.model.Category;
import com.rest.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequest {
    private String title;
    private String body;
}
