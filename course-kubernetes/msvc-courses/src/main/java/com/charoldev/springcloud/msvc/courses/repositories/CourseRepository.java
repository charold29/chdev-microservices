package com.charoldev.springcloud.msvc.courses.repositories;

import com.charoldev.springcloud.msvc.courses.models.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
