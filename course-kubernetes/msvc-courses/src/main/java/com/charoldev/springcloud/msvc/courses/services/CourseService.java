package com.charoldev.springcloud.msvc.courses.services;

import com.charoldev.springcloud.msvc.courses.models.User;
import com.charoldev.springcloud.msvc.courses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void delete(Long id);
    Optional<Course> findByIdWithUsers(Long id);

    Optional<User> assignUser(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> unassignUser(User user, Long courseId);

}
