package com.charoldev.springcloud.msvc.courses.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "course_users")
@Getter
@Setter
public class CourseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof CourseUser)){
            return false;
        }
        CourseUser courseUser = (CourseUser) obj;
        return this.userId != null && this.userId.equals(courseUser.userId);
    }

}
