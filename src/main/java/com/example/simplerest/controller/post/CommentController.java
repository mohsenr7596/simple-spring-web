package com.example.simplerest.controller.post;

import com.example.simplerest.dto.post.CommentDto;
import com.example.simplerest.service.post.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public Page<CommentDto> readAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return commentService.readAll(PageRequest.of(page, size));
    }

    @GetMapping("/comments")
    public List<CommentDto> readAllByPostId(
            @RequestParam("postId") long id
    ) {
        return commentService.readByPostId(id);
    }

    @PostMapping
    public ResponseEntity create(CommentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(dto));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity update(@PathVariable("id") long id, CommentDto dto) {
        return ResponseEntity.ok(commentService.updateById(id, dto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        commentService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
