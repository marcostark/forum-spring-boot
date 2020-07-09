package dev.marcosouza.forum.controller;

import dev.marcosouza.forum.controller.dto.TopicDetailsDto;
import dev.marcosouza.forum.controller.dto.TopicDto;
import dev.marcosouza.forum.controller.form.TopicForm;
import dev.marcosouza.forum.controller.form.TopicFormUpdate;
import dev.marcosouza.forum.model.Topic;
import dev.marcosouza.forum.repository.TopicRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicsController {

    private final TopicRepository topicRepository;

    private final CourseRepository courseRepository;

    public TopicsController(
            TopicRepository topicRepository,
            CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<TopicDto> getTopics(String courseName) {
        List<Topic> topics;
        if (courseName == null) {
            topics = topicRepository.findAll();
        } else {
            topics = topicRepository.findByCourseName(courseName);
        }
        return TopicDto.converter(topics);
    }

    @PostMapping
    public ResponseEntity<TopicDto> create(
            @RequestBody
            @Valid TopicForm topicForm,
            UriComponentsBuilder uriComponentsBuilder) {
        Topic topic = topicForm.converter(courseRepository);
        this.topicRepository.save(topic);
        URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDto> getTopic(@PathVariable Long id) {
        Optional<Topic> topic = this.topicRepository.findById(id);
        if(topic.isPresent()) {
            return ResponseEntity.ok(new TopicDetailsDto(topic.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDto> update(
            @PathVariable Long id,
            @RequestBody @Valid TopicFormUpdate topicFormUpdate) {

        Optional<Topic> optionalTopic = this.topicRepository.findById(id);
        if(optionalTopic.isPresent()) {
            Topic topic = topicFormUpdate.update(id, topicRepository);
            return ResponseEntity.ok(new TopicDto(topic));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TopicDto> delete(@PathVariable Long id) {
        Optional<Topic> optionalTopic = this.topicRepository.findById(id);
        if(optionalTopic.isPresent()) {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
