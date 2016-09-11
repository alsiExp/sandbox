package ru.alsi.spring.simple_h2;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.ArrayList;

public class JdbcTopicImplSample {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/spring/app-context-xml.xml");
        ctx.refresh();

        TopicDao dao = ctx.getBean("TopicDAO", TopicDao.class);

        System.out.println(dao.findTopicNameById(1L));
        System.out.println(dao.findTopicNameByIdNamed(2L));

        ArrayList<Topic> arr = new ArrayList<>(dao.getAll());
        for(Topic t: arr) {
            System.out.println(t.toString());
        }


    }
}
