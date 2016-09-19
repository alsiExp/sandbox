package ru.alsi.spring.annotations_h2.sql;

import org.springframework.jdbc.object.MappingSqlQuery;
import ru.alsi.spring.annotations_h2.Topic;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectAllTopics extends MappingSqlQuery<Topic> {
    private static final String SQL_SELECT_ALL_TOPIC = "SELECT id, name, creation_time from topics";

    public SelectAllTopics(DataSource dataSource) {
        super(dataSource, SQL_SELECT_ALL_TOPIC);
    }

    @Override
    protected Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        Topic topic = new Topic();
        topic.setId(rs.getLong("id"));
        topic.setName(rs.getString("name"));
        topic.setCreationTime(rs.getTimestamp("creation_time").toLocalDateTime());
        return topic;
    }
}
