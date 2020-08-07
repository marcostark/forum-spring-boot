package dev.marcosouza.forum.resources;

import dev.marcosouza.forum.resources.dto.TopicDetailsDto;
import dev.marcosouza.forum.resources.dto.TopicDto;
import dev.marcosouza.forum.resources.form.TopicForm;
import dev.marcosouza.forum.resources.form.TopicFormUpdate;
import dev.marcosouza.forum.model.Topic;
import dev.marcosouza.forum.repository.CourseRepository;
import dev.marcosouza.forum.repository.TopicRepository;
import dev.marcosouza.forum.service.TopicService;
import dev.marcosouza.forum.service.impl.TopicServiceImpl;
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

@RestController
@RequestMapping("/topics")
public class TopicsController {

    private final TopicRepository topicRepository;

    private final CourseRepository courseRepository;

    public final TopicService topicService;

    public TopicsController(
            TopicRepository topicRepository,
            CourseRepository courseRepository,
            TopicServiceImpl topicService) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.topicService = topicService;
    }

    @GetMapping
    @Cacheable(value = "listTopics")
    public Page<TopicDto> getTopics(
            @RequestParam(required = false) String courseName,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10, page = 0) Pageable pageable) {
        return this.topicService.getTopics(courseName, pageable);
    }

    @PostMapping
    @CacheEvict(value = "listTopics", allEntries = true)
    public ResponseEntity<TopicDto> create(
            @RequestBody
            @Valid TopicForm topicForm,
            UriComponentsBuilder uriComponentsBuilder) {
        Topic topic = this.topicService.createTopic(topicForm);
        URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }

    @GetMapping("/{id}")
    @CacheEvict(value = "listTopics", allEntries = true)
    public ResponseEntity<TopicDetailsDto> getTopic(@PathVariable Long id) {
        TopicDetailsDto topic = this.topicService.getTopic(id);
        if(topic != null){
            return ResponseEntity.ok(topic);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listTopics", allEntries = true)
    public ResponseEntity<TopicDto> update(
            @PathVariable Long id,
            @RequestBody @Valid TopicFormUpdate topicFormUpdate) {
        Topic topic = this.topicService.updateTopic(id, topicFormUpdate);
        if(topic != null) {
            return ResponseEntity.ok(new TopicDto(topic));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listTopics", allEntries = true)
    public ResponseEntity<TopicDto> delete(@PathVariable Long id) {
        Topic topic = this.topicService.deleteTopic(id);
        if(topic != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
