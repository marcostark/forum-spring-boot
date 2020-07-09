package dev.marcosouza.forum.repository;

import dev.marcosouza.forum.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByCourseName(String name, Pageable pageable);
}
