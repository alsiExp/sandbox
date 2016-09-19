package ru.alsi.spring.annotations_h2;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class JdbcTopicImplSample {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/spring/app-context-xml.xml");
        ctx.refresh();

        String[] beanNames =  ctx.getBeanDefinitionNames();

        TopicDao dao = ctx.getBean("TopicDao", TopicDao.class);

        System.out.println(dao.findTopicByName("First topic"));

        Topic t = new Topic();
        t.setId(1L);
        t.setName("Updated first topic");
        t.setCreationTime(LocalDateTime.now());
        dao.update(t);

        Topic t2 = new Topic();
        t2.setName("New inserted Topic");
        t2.setCreationTime(LocalDateTime.now());
        dao.insert(t2);

        Topic t3 = new Topic("New topic with messages", LocalDateTime.now(), new ArrayList<>(Arrays.asList(
                new Message("Content #1", LocalDateTime.now()),
                new Message("Content #2", LocalDateTime.now())
        )));
        dao.insertWithMessages(t3);

        ArrayList<Topic> arr = new ArrayList<>(dao.getAllWithMessages());
        for(Topic topic: arr) {
            System.out.println(topic.toString());
        }
    }
}
