package com.charoldev.springcloud.msvc.users.repositories;

import com.charoldev.springcloud.msvc.users.models.entity.User;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    /*Find By Email Query Method:

      @Query("select u from User u where u.email=?1")
      Optional<User> findByEmail(String email);
     */

}