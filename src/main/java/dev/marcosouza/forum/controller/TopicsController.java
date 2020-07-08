package dev.marcosouza.forum.controller;

import dev.marcosouza.forum.controller.dto.TopicDto;
import dev.marcosouza.forum.model.Topic;
import dev.marcosouza.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicsController {

    @Autowired
    private TopicRepository topicRepository;

    @RequestMapping("/topics")
    public List<TopicDto> getTopics() {
        List<Topic> topics = topicRepository.findAll();
        return TopicDto.converter(topics);
    }

}
