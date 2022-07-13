package com.charoldev.springcloud.msvc.courses.services;

import com.charoldev.springcloud.msvc.courses.interfaces.UserClientRest;
import com.charoldev.springcloud.msvc.courses.models.User;
import com.charoldev.springcloud.msvc.courses.models.entity.Course;
import com.charoldev.springcloud.msvc.courses.models.entity.CourseUser;
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
    @Transactional(readOnly = true)
    public Optional<Course> findByIdWithUsers(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()){
            Course course = courseOptional.get();

            if (!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers().stream().map(cu -> cu.getUserId()).toList();

                List<User> users = clientRest.getStudentsByCourse(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
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
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()){
            User userMicroservice = clientRest.getUserById(user.getId());

            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMicroservice.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMicroservice);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()){
            User newUserMicroservice = clientRest.createUser(user);

            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(newUserMicroservice.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(newUserMicroservice);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> unassignUser(User user, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()){
            User userMicroservice = clientRest.getUserById(user.getId());

            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMicroservice.getId());

            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMicroservice);
        }
        return Optional.empty();
    }


}
