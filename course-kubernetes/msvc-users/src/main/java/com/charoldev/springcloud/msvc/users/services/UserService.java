package com.charoldev.springcloud.msvc.users.services;

import com.charoldev.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void delete(Long id);

    Optional<User> findByEmail(String email);
    List<User> findAllById(Iterable<Long> ids);
}
