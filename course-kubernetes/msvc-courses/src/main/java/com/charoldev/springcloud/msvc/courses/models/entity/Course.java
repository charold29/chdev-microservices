package com.charoldev.springcloud.msvc.courses.models.entity;

import com.charoldev.springcloud.msvc.courses.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;

    @Transient
    private List<User> users;

    public Course(){
        courseUsers = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addCourseUser(CourseUser courseUser){
        courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser){

        courseUsers.remove(courseUser);
    }

}
