package dev.marcosouza.forum.controller.form;

import dev.marcosouza.forum.controller.CourseRepository;
import dev.marcosouza.forum.model.Course;
import dev.marcosouza.forum.model.Topic;

public class TopicForm {

    private String title;

    private String message;

    private String courseName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Topic converter(CourseRepository courseRepository) {
        Course course = courseRepository.findByName(courseName);
        return new Topic(title, message, course);
    }
}
