package dev.marcosouza.forum.service;

import dev.marcosouza.forum.model.Topic;
import dev.marcosouza.forum.resources.dto.TopicDetailsDto;
import dev.marcosouza.forum.resources.dto.TopicDto;
import dev.marcosouza.forum.resources.form.TopicForm;
import dev.marcosouza.forum.resources.form.TopicFormUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TopicService {

    Page<TopicDto> getTopics(String courseName, Pageable pageable);

    TopicDetailsDto getTopic(Long id);

    Topic createTopic(TopicForm topicForm);

    Topic updateTopic(Long id, TopicFormUpdate topicFormUpdate);

    Topic deleteTopic(Long id);
}
