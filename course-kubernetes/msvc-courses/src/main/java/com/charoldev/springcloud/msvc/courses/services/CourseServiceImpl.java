package com.charoldev.springcloud.msvc.courses.services;

import com.charoldev.springcloud.msvc.courses.interfaces.UserClientRest;
import com.charoldev.springcloud.msvc.courses.models.User;
import com.charoldev.springcloud.msvc.courses.models.entity.Course;
import com.charoldev.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private UserClientRest clientRest;

    @Override
    @Transactional
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<User> assignUser(User user, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> createUser(User user, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> unassignUser(User user, Long courseId) {
        return Optional.empty();
    }


}
