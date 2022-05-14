package com.example.simplerest.service.post;

import com.example.simplerest.domain.post.Post;
import com.example.simplerest.dto.post.PostDto;
import com.example.simplerest.repository.post.PostRepository;
import com.example.simplerest.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public long create(PostDto dto) {
        var post = new Post();
        fillEntity(post, dto);
        postRepository.save(post);
        return post.getId();
    }

    public Page<PostDto> readAll(Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        return postRepository.findAll(pageable).map(PostDto::new);
    }


    public PostDto readById(long id) {
        return postRepository.findById(id).map(PostDto::new).orElse(null);
    }

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }

    public boolean updateById(long id, PostDto dto) {
        return postRepository.findById(id).map(e -> {
            fillEntity(e, dto);
            return true;
        }).orElse(false);
    }

    public void fillEntity(Post post, PostDto dto) {
        post.setBody(dto.getBody());
        post.setTitle(dto.getTitle());
        post.setUser(userRepository.getById(dto.getId()));
    }

    public List<PostDto> searchByTitle(String title) {
        return postRepository.findByTitleLikeIgnoreCase(title).stream().map(PostDto::new).collect(Collectors.toList());
    }
}
