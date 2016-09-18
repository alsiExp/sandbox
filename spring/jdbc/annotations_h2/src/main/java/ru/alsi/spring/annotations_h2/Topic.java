package ru.alsi.spring.annotations_h2;

import java.time.LocalDateTime;
import java.util.List;

public class Topic {
    private Long id;
    private String name;
    private LocalDateTime creationTime;
    private List<Message> messageList;

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

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        String p = "";
        if (messageList != null) {
            for (Message m : messageList) {
                sb.append(p);
                sb.append(m.toString());
                p = ", ";
            }
        }
        sb.append("]");
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationTime=" + creationTime +
                ", messageList=" + sb.toString() +
                '}';
    }
}
