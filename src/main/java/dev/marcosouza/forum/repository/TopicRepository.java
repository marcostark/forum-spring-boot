package dev.marcosouza.forum.repository;

import dev.marcosouza.forum.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByCourseName(String name);
}
