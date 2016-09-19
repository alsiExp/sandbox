package ru.alsi.spring.annotations_h2.sql;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import ru.alsi.spring.annotations_h2.Topic;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SelectTopicByName extends MappingSqlQuery<Topic> {

    private static final String SQL_FIND_BY_NAME = "SELECT id, name, creation_time from topics WHERE name = :name";

    public SelectTopicByName(DataSource dataSource) {
        super(dataSource, SQL_FIND_BY_NAME);
        super.declareParameter(new SqlParameter("name", Types.VARCHAR));
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
