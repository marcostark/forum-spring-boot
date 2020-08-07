package dev.marcosouza.forum.resources.dto;

import dev.marcosouza.forum.model.Topic;
import dev.marcosouza.forum.model.TopicStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopicDetailsDto {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime createDate;
    private String author;
    private TopicStatus status;
    private List<AnswerDto> answerList;

    public TopicDetailsDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.createDate = topic.getCreateDate();
        this.author = topic.getAuthor().getName();
        this.status = topic.getStatus();
        this.answerList = new ArrayList<>();
        this.answerList.addAll(topic.getAnswers().stream().map(AnswerDto::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getAuthor() {
        return author;
    }

    public TopicStatus getStatus() {
        return status;
    }

    public List<AnswerDto> getAnswerList() {
        return answerList;
    }
}
