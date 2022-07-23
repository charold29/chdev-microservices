package com.charoldev.springcloud.msvc.courses.client;

import com.charoldev.springcloud.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-users", url ="localhost:3000/api/users")
public interface UserClientRest {

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);

    @PostMapping("/")
    User createUser(@RequestBody User user);

    @GetMapping("/users-by-course")
    List<User> getStudentsByCourse(@RequestParam Iterable<Long> ids);

}
