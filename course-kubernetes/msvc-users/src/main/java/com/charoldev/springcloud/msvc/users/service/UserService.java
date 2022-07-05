package com.charoldev.springcloud.msvc.users.service;

import com.charoldev.springcloud.msvc.users.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void delete(Long id);
}
