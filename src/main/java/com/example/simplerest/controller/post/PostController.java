package com.example.simplerest.controller.post;

import com.example.simplerest.dto.post.CommentDto;
import com.example.simplerest.dto.post.PostDto;
import com.example.simplerest.service.post.CommentService;
import com.example.simplerest.service.post.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;
    private final CommentService commentService;


    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public Page<PostDto> readAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        LOGGER.info("calling read all.");
        return postService.readAll(PageRequest.of(page, size));
    }

    @GetMapping("/posts/{id}")
    public PostDto read(
            @PathVariable("id") long id
    ) {
        LOGGER.info("calling read by id.");
        return postService.readById(id);
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> readAllComments(
            @PathVariable("id") long id
    ) {
        LOGGER.info("calling read comments by post id.");
        return commentService.readByPostId(id);
    }

    @GetMapping("/posts")
    public List<PostDto> search(@RequestParam("title") String title) {
        LOGGER.info("calling find by title.");
        return postService.searchByTitle(title);
    }

    @PostMapping
    public ResponseEntity create(PostDto dto) {
        LOGGER.info("calling create enetity.");
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(dto));
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable("id") long id, PostDto dto) {
        LOGGER.info("calling update enetity.");
        return ResponseEntity.ok(postService.updateById(id, dto));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        LOGGER.info("calling delete enetity.");
        postService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
