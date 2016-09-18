package ru.alsi.spring.annotations_h2;

import java.time.LocalDateTime;

public class Message {

    private Long id;
    private Long topic_id;
    private String content;
    private LocalDateTime creationTime;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", topic_id=" + topic_id +
                ", content='" + content + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
