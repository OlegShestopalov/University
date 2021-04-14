package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.domain.entity.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FacultyMapper implements RowMapper<Faculty> {

    public FacultyMapper() {
    }

    @Override
    public Faculty mapRow(ResultSet resultSet, int i) throws SQLException {
        Faculty faculty = new Faculty();

        faculty.setId(resultSet.getLong("id"));
        faculty.setName(resultSet.getString("name"));

        return faculty;
    }
}
