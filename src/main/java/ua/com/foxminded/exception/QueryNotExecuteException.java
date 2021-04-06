package ua.com.foxminded.exception;

import org.springframework.dao.DataAccessException;

public class QueryNotExecuteException extends RuntimeException {

    public QueryNotExecuteException(String message) {
        super(message);
    }

    public QueryNotExecuteException(String message, DataAccessException e) {
        super(message, e);
    }
}
