package com.charoldev.springcloud.msvc.courses.controllers;

import com.charoldev.springcloud.msvc.courses.models.entity.Course;
import com.charoldev.springcloud.msvc.courses.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable(name = "courseId") Long id) {
        Optional<Course> optional = courseService.findById(id);
        if (optional.isPresent()) {
            Course course = optional.get();
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course saveCourse(@RequestBody Course course) {
        return courseService.save(course);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> editCourse(@RequestBody Course course, @PathVariable(name = "courseId") Long id) {
        Optional<Course> optional = courseService.findById(id);
        if (optional.isPresent()) {
            Course courseDb = optional.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "courseId") Long id){
        Optional<Course> optional = courseService.findById(id);
        if (optional.isPresent()){
            courseService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

}
