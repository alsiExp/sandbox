package ru.alsi.spring.annotations_h2;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class UpdateTopic extends SqlUpdate {
private static final String SQL_UPDATE_CONTACT = "UPDATE topics SET name = :name, creation_time = :creation_time WHERE id = :id";

    public UpdateTopic(DataSource ds) {
        super(ds, SQL_UPDATE_CONTACT);
        super.declareParameter(new SqlParameter("name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("creation_time", Types.TIMESTAMP));
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
    }
}
