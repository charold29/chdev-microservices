package com.charoldev.springcloud.msvc.users.repository;

import com.charoldev.springcloud.msvc.users.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    
}
