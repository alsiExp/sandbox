package ru.alsi.spring.annotations_h2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
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

@Repository("TopicDao")
public class JdbcTopicDaoImpl implements TopicDao {

    private static final Log LOG = LogFactory.getLog(JdbcTopicDaoImpl.class);

    private SelectAllTopics selectAllTopics;
    private SelectTopicByName selectTopicByName;
    private UpdateTopic updateTopic;
    private DataSource dataSource;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.selectAllTopics = new SelectAllTopics(dataSource);
        this.selectTopicByName = new SelectTopicByName(dataSource);
        this.updateTopic = new UpdateTopic(dataSource);
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
    public void insert(Topic topic) {

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
}
