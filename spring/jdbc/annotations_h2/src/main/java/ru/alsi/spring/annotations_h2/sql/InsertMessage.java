package ru.alsi.spring.annotations_h2.sql;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertMessage extends BatchSqlUpdate {
    private static final String SQL_INSERT_MESSAGE = "INSERT INTO messages (topic_id, content, creation_time) values" +
            "(:topic_id, :content, :creation_time)";
    private static final int BATCH_SIZE = 10;

    public InsertMessage(DataSource ds) {
        super(ds, SQL_INSERT_MESSAGE);
        super.declareParameter(new SqlParameter("topic_id", Types.INTEGER));
        super.declareParameter(new SqlParameter("content", Types.VARCHAR));
        super.declareParameter(new SqlParameter("creation_time", Types.TIMESTAMP));
        super.setBatchSize(BATCH_SIZE);

        super.setGeneratedKeysColumnNames(new String[] {"id"});
        super.setReturnGeneratedKeys(true);
    }
}
