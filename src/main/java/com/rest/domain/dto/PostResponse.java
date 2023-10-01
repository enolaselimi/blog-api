package com.rest.domain.dto;
import com.rest.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostResponse {
    private Integer postId;
    private String title;
    private String body;
    private User user;
}
