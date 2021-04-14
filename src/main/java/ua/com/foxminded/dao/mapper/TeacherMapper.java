package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.domain.entity.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TeacherMapper implements RowMapper<Teacher> {

    public TeacherMapper() {
    }

    @Override
    public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {
        Teacher teacher = new Teacher();

        teacher.setId(resultSet.getLong("id"));
        teacher.setName(resultSet.getString("name"));
        teacher.setSurname(resultSet.getString("surname"));
        teacher.setEmail(resultSet.getString("email"));

        return teacher;
    }
}
