package ru.alsi.spring.simple_h2;

import java.time.LocalDateTime;

public class Topic {
    Long id;
    String name;
    LocalDateTime creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
