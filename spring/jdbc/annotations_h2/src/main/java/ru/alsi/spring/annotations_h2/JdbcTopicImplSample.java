package ru.alsi.spring.annotations_h2;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

        ArrayList<Topic> arr = new ArrayList<>(dao.getAll());
        for(Topic topic: arr) {
            System.out.println(topic.toString());
        }
    }
}
