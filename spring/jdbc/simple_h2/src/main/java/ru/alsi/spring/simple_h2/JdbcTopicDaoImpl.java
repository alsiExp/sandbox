package ru.alsi.spring.simple_h2;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTopicDaoImpl implements TopicDao, InitializingBean {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate npjt;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.npjt = namedParameterJdbcTemplate;
        /// for add our CustomSQLErrorCodesTranslator
        ///
        CustomSQLErrorCodesTranslator errorTranslator = new CustomSQLErrorCodesTranslator();
        errorTranslator.setDataSource(dataSource);
        jdbcTemplate.setExceptionTranslator(errorTranslator);
        ///

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Topic> getAll() {
        String sql = "SELECT id, name, creation_time from topics";
        return npjt.query(sql, (resultSet, rowNum) -> {
            Topic topic = new Topic();
            topic.setId(resultSet.getLong("id"));
            topic.setName(resultSet.getString("name"));
            topic.setCreationTime(resultSet.getTimestamp("creation_time").toLocalDateTime());

            return topic;
        });
    }


    /*
    without lambda, like inner class (set TopicMapper second parameter in npjt.query

    private static final class TopicMapper implements RowMapper<Topic> {
        @Override
        public Topic mapRow(ResultSet resultSet, int i) throws SQLException {
            Topic topic = new Topic();
            topic.setId(resultSet.getLong("id"));
            topic.setName(resultSet.getString("name"));
            topic.setCreationTime(resultSet.getTimestamp("creation_time").toLocalDateTime());

            return topic;
        }
    }

    */

    @Override
    public List<Topic> getAllWithMessages() {
        String sql = "SELECT t.id, t.name, t.creation_time, m.id as message_id, m.topic_id, m.content, m.creation_time " +
                "from topics t left join messages m on t.id = m.topic_id";

        return npjt.query(sql, (ResultSet resultSet) -> {
                    Map<Long, Topic> map = new HashMap<>();
                    Topic topic = null;

                    while (resultSet.next()) {
                        Long id = resultSet.getLong("id");
                        topic = map.get(id);

                        if (topic == null) {
                            topic = new Topic();
                            topic.setId(id);
                            topic.setName(resultSet.getString("name"));
                            topic.setCreationTime(resultSet.getTimestamp("creation_time").toLocalDateTime());
                            topic.setMessageList(new ArrayList<Message>());
                            map.put(id, topic);
                        }
                        Long messageId = resultSet.getLong("message_id");
                        if (messageId > 0) {
                            Message message = new Message();
                            message.setId(messageId);
                            message.setTopic_id(resultSet.getLong("topic_id"));
                            message.setContent(resultSet.getString("content"));
                            message.setCreationTime(resultSet.getTimestamp("creation_time").toLocalDateTime());
                            topic.getMessageList().add(message);
                        }
                    }

                    return new ArrayList<Topic>(map.values());
                }
        );
    }



    @Override
    public String findTopicNameById(Long topicId) {
        return jdbcTemplate.queryForObject(
                "SELECT name from TOPICS WHERE id = ?",
                new Object[]{topicId}, String.class
        );
    }

    @Override
    public String findTopicNameByIdNamed(Long topicId) {
        String sql = "SELECT name from TOPICS WHERE id = :topicId";
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("topicId", topicId);
        return npjt.queryForObject(sql, namedParameters, String.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Must set dataSource on ContactDao");
        }

        if (jdbcTemplate == null) {
            throw new BeanCreationException("Null JdbcTemplate on ContactDao");
        }
    }


}
