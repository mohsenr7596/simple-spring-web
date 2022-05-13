package com.example.simplerest.dto.post;

import com.example.simplerest.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto implements Serializable {
    private Long id;
    private String title;
    private String body;
    private Long userId;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.userId = post.getUser().getId();
    }
}
