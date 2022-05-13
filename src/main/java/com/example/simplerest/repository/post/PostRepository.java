package com.example.simplerest.repository.post;

import com.example.simplerest.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleLikeIgnoreCase(String title);

}