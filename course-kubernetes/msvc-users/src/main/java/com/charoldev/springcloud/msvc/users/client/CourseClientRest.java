package com.charoldev.springcloud.msvc.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url ="localhost:8002/api/courses")
public interface CourseClientRest {

    @DeleteMapping("/delete-user/{userId}")
    void deleteCourseUserById(@PathVariable Long userId);

}
