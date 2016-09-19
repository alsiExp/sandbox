package ru.alsi.spring.annotations_h2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.alsi.spring.annotations_h2.sql.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("TopicDao")
public class JdbcTopicDaoImpl implements TopicDao {

    private static final Log LOG = LogFactory.getLog(JdbcTopicDaoImpl.class);

    private SelectAllTopics selectAllTopics;
    private SelectTopicByName selectTopicByName;
    private UpdateTopic updateTopic;
    private InsertTopic insertTopic;
    private InsertMessage insertMessage;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.selectAllTopics = new SelectAllTopics(dataSource);
        this.selectTopicByName = new SelectTopicByName(dataSource);
        this.updateTopic = new UpdateTopic(dataSource);
        this.insertTopic = new InsertTopic(dataSource);
        this.insertMessage = new InsertMessage(dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<Topic> getAll() {
        return selectAllTopics.execute();
    }

    @Override
    public List<Topic> findTopicByName(String name) {
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("name", name);
        return selectTopicByName.executeByNamedParam(mapParam);
    }

    @Override
    public String findTopicNameById(Long topicId) {
        return null;
    }

    @Override
    public List<Topic> getAllWithMessages() {
        String sql = "SELECT t.id, t.name, t.creation_time, m.id as message_id, m.topic_id, m.content, m.creation_time " +
                "from topics t left join messages m on t.id = m.topic_id";

        return namedParameterJdbcTemplate.query(sql, (ResultSet resultSet) -> {
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
    public void insert(Topic topic) {
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("name", topic.getName());
        mapParam.put("creation_time", Timestamp.valueOf(topic.getCreationTime()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertTopic.updateByNamedParam(mapParam, keyHolder);
        topic.setId(keyHolder.getKey().longValue());

        LOG.info("New topic inserted, id:" + topic.getId());
    }

    @Override
    public void update(Topic topic) {
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("name", topic.getName());
        mapParam.put("creation_time", Timestamp.valueOf(topic.getCreationTime()));
        mapParam.put("id", topic.getId());
        updateTopic.updateByNamedParam(mapParam);

        LOG.info("Existing topic updated, id:" + topic.getId());
    }

    @Override
    public void insertWithMessages(Topic topic) {
        insertMessage = new InsertMessage(dataSource);

        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("name", topic.getName());
        mapParam.put("creation_time", Timestamp.valueOf(topic.getCreationTime()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertTopic.updateByNamedParam(mapParam, keyHolder);
        topic.setId(keyHolder.getKey().longValue());

        LOG.info("New topic inserted, id:" + topic.getId());

        List<Message> messages = topic.getMessageList();

        if(messages != null) {
            for(Message m : messages) {
                mapParam = new HashMap<>();
                mapParam.put("topic_id", topic.getId());
                mapParam.put("content", m.getContent());
                mapParam.put("creation_time",Timestamp.valueOf(m.getCreationTime()));
                KeyHolder messageKeyHolder = new GeneratedKeyHolder();
                insertMessage.updateByNamedParam(mapParam, messageKeyHolder);
                m.setId(messageKeyHolder.getKey().longValue());
            }
        }
        insertMessage.flush();
    }
}
