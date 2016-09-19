package ru.alsi.spring.annotations_h2.sql;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertTopic extends SqlUpdate {

    private static final String SQL_INSERT_TOPIC = "INSERT INTO topics (name, creation_time) values (:name, :creation_time)";

    public InsertTopic(DataSource ds) {
        super(ds, SQL_INSERT_TOPIC);
        super.declareParameter(new SqlParameter("name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("creation_time", Types.TIMESTAMP));
        super.setGeneratedKeysColumnNames(new String[] {"id"});
        super.setReturnGeneratedKeys(true);
    }
}
