package com.charoldev.springcloud.msvc.users.controllers;

import com.charoldev.springcloud.msvc.users.models.entity.User;
import com.charoldev.springcloud.msvc.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "userId") Long id) {
        Optional<User> optional = userService.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        // When the email already exists.
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Existe un usuario con ese correo"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> editUser(@Valid @RequestBody User user, BindingResult result, @PathVariable(name = "userId") Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<User> optional = userService.findById(id);
        if (optional.isPresent()) {
            User userDb = optional.get();
            if (!user.getEmail().equalsIgnoreCase(userDb.getEmail()) &&
                    userService.findByEmail(user.getEmail()).isPresent() &&
                    !user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Existe un usuario con ese correo"));
            }
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long id) {
        Optional<User> optional = userService.findById(id);
        if (optional.isPresent()) {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/users-by-course")
    public ResponseEntity<List<User>> getStudentsByCourse(@RequestParam List<Long> ids){
        return ResponseEntity.ok(userService.findAllById(ids));
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
