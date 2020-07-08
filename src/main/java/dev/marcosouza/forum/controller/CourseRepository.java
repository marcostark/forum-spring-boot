package dev.marcosouza.forum.controller;

import dev.marcosouza.forum.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String courseName);
}
