package com.example.simplerest.dto.post;

import com.example.simplerest.domain.post.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Long id;
    private Long postId;
    private String email;
    private String name;
    private String body;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.email = comment.getEmail();
        this.name = comment.getName();
        this.body = comment.getBody();
    }
}
