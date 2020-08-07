package dev.marcosouza.forum.service.impl;

import dev.marcosouza.forum.repository.CourseRepository;
import dev.marcosouza.forum.resources.dto.TopicDetailsDto;
import dev.marcosouza.forum.resources.dto.TopicDto;
import dev.marcosouza.forum.model.Topic;
import dev.marcosouza.forum.repository.TopicRepository;
import dev.marcosouza.forum.resources.form.TopicForm;
import dev.marcosouza.forum.resources.form.TopicFormUpdate;
import dev.marcosouza.forum.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final CourseRepository courseRepository;

    public TopicServiceImpl(
            TopicRepository topicRepository,
            CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<TopicDto> getTopics(String courseName, Pageable pageable) {
        Page<Topic> topics;
        if(courseName == null) {
            topics = this.topicRepository.findAll(pageable);
        } else {
            topics = this.topicRepository.findByCourseName(courseName, pageable);
        }
        return TopicDto.converter(topics);
    }

    @Override
    public TopicDetailsDto getTopic(Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.map(TopicDetailsDto::new).orElse(null);
    }

    @Override
    public Topic createTopic(TopicForm topicForm) {
        Topic topic = topicForm.converter(courseRepository);
        this.topicRepository.save(topic);
        return topic;
    }

    @Override
    public Topic updateTopic(
            Long id,
            TopicFormUpdate topicFormUpdate
    ) {
        Optional<Topic> optionalTopic = this.topicRepository.findById(id);
        if(optionalTopic.isPresent()) {
            Topic topic = topicFormUpdate.update(id, topicRepository);
            return topic;
        }
        return null;
    }

    @Override
    public Topic deleteTopic(Long id) {
        Optional<Topic> optionalTopic = this.topicRepository.findById(id);
        if(optionalTopic.isPresent()){
            topicRepository.deleteById(id);
            return optionalTopic.get();
        }
        return  null;
    }


}
