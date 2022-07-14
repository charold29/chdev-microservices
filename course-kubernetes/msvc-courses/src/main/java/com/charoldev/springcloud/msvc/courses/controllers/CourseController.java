package com.charoldev.springcloud.msvc.courses.controllers;

import com.charoldev.springcloud.msvc.courses.models.User;
import com.charoldev.springcloud.msvc.courses.models.entity.Course;
import com.charoldev.springcloud.msvc.courses.services.CourseService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

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
        Optional<Course> optional = courseService.findByIdWithUsers(id); //courseService.findById(id);
        if (optional.isPresent()) {
            Course course = optional.get();
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveCourse(@Valid @RequestBody Course course, BindingResult result) {

        if (result.hasErrors()) {
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> editCourse(@Valid @RequestBody Course course, BindingResult result, @PathVariable(name = "courseId") Long id) {

        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<Course> optional = courseService.findById(id);
        if (optional.isPresent()) {
            Course courseDb = optional.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "courseId") Long id) {
        Optional<Course> optional = courseService.findById(id);
        if (optional.isPresent()) {
            courseService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userOptional;

        try {
            userOptional = courseService.assignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicación: " + e.getMessage()));
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userOptional;

        try {
            userOptional = courseService.createUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario" +
                            "o error en la comunicación: " + e.getMessage()));
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/unassign-user/{courseId}")
    public ResponseEntity<?> unassignUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userOptional;

        try {
            userOptional = courseService.unassignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicación: " + e.getMessage()));
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteCourseUserById(@PathVariable Long userId){
        courseService.deleteCourseUserById(userId);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
