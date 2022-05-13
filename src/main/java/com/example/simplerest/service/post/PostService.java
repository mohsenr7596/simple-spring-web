package com.example.simplerest.service.post;

import com.example.simplerest.domain.post.Post;
import com.example.simplerest.dto.post.PostDto;
import com.example.simplerest.repository.post.PostRepository;
import com.example.simplerest.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public long save(PostDto dto) {
        if (dto.getId() != null) {
            updateById(dto.getId(), dto);
            return dto.getId();
        }
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
}
