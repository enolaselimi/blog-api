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
public class PostCategory {
    private Post post;
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostCategory that = (PostCategory) o;
        return Objects.equals(post, that.post) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, category);
    }
}
