package com.charoldev.springcloud.msvc.courses.interfaces;

import com.charoldev.springcloud.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-users", url ="localhost:8001/api/users")
public interface UserClientRest {

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);

    @PostMapping("/")
    User createUser(@RequestBody User user);

}
