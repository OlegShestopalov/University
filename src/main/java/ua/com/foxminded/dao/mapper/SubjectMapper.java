package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.domain.entity.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubjectMapper implements RowMapper<Subject> {

    @Override
    public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
        Subject subject = new Subject();

        subject.setId(resultSet.getLong("id"));
        subject.setName(resultSet.getString("name"));
        subject.setDescription(resultSet.getString("description"));

        return subject;
    }
}
