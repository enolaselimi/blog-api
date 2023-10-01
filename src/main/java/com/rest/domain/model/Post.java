package com.rest.domain.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Post extends BaseDomain{
    private String title;
    private String body;
    private User user;

    public Post(Integer id, String title, String body, User user) {
        super(id);
        this.title = title;
        this.body = body;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(title, post.title) && Objects.equals(body, post.body) && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, user);
    }
}
