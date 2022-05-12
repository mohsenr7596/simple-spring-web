package com.example.simplerest.repository.post;

import com.example.simplerest.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}