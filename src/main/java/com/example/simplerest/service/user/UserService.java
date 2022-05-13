package com.example.simplerest.service.user;

import com.example.simplerest.domain.user.User;
import com.example.simplerest.dto.user.UserDto;
import com.example.simplerest.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long save(UserDto dto) {
        if (dto.getId() != null) {
            updateById(dto.getId(), dto);
            return dto.getId();
        }
        var user = new User();
        fillEntity(user, dto);
        userRepository.save(user);
        return user.getId();
    }

    public Page<UserDto> readAll(Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        return userRepository.findAll(pageable).map(UserDto::new);
    }


    public UserDto readById(long id) {
        return userRepository.findById(id).map(UserDto::new).orElse(null);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public boolean updateById(long id, UserDto dto) {
        return userRepository.findById(id).map(e -> {
            fillEntity(e, dto);
            return true;
        }).orElse(false);
    }

    public void fillEntity(User user, UserDto dto) {
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
    }
}
