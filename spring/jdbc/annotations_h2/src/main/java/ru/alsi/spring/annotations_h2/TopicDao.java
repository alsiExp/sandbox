package ru.alsi.spring.annotations_h2;

import java.util.List;

public interface TopicDao {

    List<Topic> getAll();

    List<Topic> findTopicByName(String name);

    String findTopicNameById(Long topicId);

    List<Topic> getAllWithMessages();

    void insert(Topic topic);

    void update(Topic topic);

    void insertWithMessages(Topic topic);



}
