package ru.alsi.spring.simple_h2;

import java.util.List;

public interface TopicDao {

    String findTopicNameById(Long topicId);
    String findTopicNameByIdNamed(Long topicId);

    List<Topic> getAll();

    List<Topic> getAllWithMessages();


}
