package com.example.simplerest.service.post;

import com.example.simplerest.domain.post.Comment;
import com.example.simplerest.dto.post.CommentDto;
import com.example.simplerest.repository.post.CommentRepository;
import com.example.simplerest.repository.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public long save(CommentDto dto) {
        if (dto.getId() != null) {
            updateById(dto.getId(), dto);
            return dto.getId();
        }
        var comment = new Comment();
        fillEntity(comment, dto);
        commentRepository.save(comment);
        return comment.getId();
    }

    public Page<CommentDto> readAll(Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        return commentRepository.findAll(pageable).map(CommentDto::new);
    }


    public CommentDto readById(long id) {
        return commentRepository.findById(id).map(CommentDto::new).orElse(null);
    }

    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    public boolean updateById(long id, CommentDto dto) {
        return commentRepository.findById(id).map(e -> {
            fillEntity(e, dto);
            return true;
        }).orElse(false);
    }

    public void fillEntity(Comment comment, CommentDto dto) {
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        comment.setName(dto.getName());
        comment.setPost(postRepository.getById(dto.getId()));
    }
}
