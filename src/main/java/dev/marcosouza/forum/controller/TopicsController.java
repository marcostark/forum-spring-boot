package dev.marcosouza.forum.controller;

import dev.marcosouza.forum.controller.dto.TopicDetailsDto;
import dev.marcosouza.forum.controller.dto.TopicDto;
import dev.marcosouza.forum.controller.form.TopicForm;
import dev.marcosouza.forum.controller.form.TopicFormUpdate;
import dev.marcosouza.forum.model.Topic;
import dev.marcosouza.forum.repository.TopicRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
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
    @Cacheable(value = "listTopics")
    public Page<TopicDto> getTopics(
            @RequestParam(required = false) String courseName,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10, page = 0) Pageable pageable) {

        // Forma "manual"
        //Pageable page = PageRequest.of(size, per_page)

        if (courseName == null) {
            Page<Topic> topics = topicRepository.findAll(pageable);
            return TopicDto.converter(topics);

        } else {
            Page<Topic> topics = topicRepository.findByCourseName(courseName, pageable);
            return TopicDto.converter(topics);
        }
    }

    @PostMapping
    @CacheEvict(value = "listTopics", allEntries = true)
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
    @CacheEvict(value = "listTopics", allEntries = true)
    public ResponseEntity<TopicDetailsDto> getTopic(@PathVariable Long id) {
        Optional<Topic> topic = this.topicRepository.findById(id);
        if(topic.isPresent()) {
            return ResponseEntity.ok(new TopicDetailsDto(topic.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listTopics", allEntries = true)
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
    @CacheEvict(value = "listTopics", allEntries = true)
    public ResponseEntity<TopicDto> delete(@PathVariable Long id) {
        Optional<Topic> optionalTopic = this.topicRepository.findById(id);
        if(optionalTopic.isPresent()) {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
