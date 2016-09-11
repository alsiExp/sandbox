package ru.alsi.spring.simple_h2;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

public class CustomSQLErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {

    @Override
    protected DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {
        // Error code is fictional, just as sample
        if(sqlEx.getErrorCode() == -1111) {
            return new DeadlockLoserDataAccessException(task, sqlEx);
        }

        return null;
    }
}
