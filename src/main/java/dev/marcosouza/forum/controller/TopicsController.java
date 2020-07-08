package dev.marcosouza.forum.controller;

import dev.marcosouza.forum.controller.dto.TopicDto;
import dev.marcosouza.forum.model.Course;
import dev.marcosouza.forum.model.Topic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@RestController
public class TopicsController {

    @RequestMapping("/topics")
    public List<TopicDto> getTopics() {
        Topic topic = new Topic("Dúvida", "Dúvida com spring boot", new Course("Spring boot", "Programação"));
        return TopicDto.converter(Arrays.asList(topic,topic,topic));
    }

}
