package ua.com.foxminded.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.domain.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
        Course course = new Course();

        course.setId(resultSet.getLong("id"));
        course.setName(resultSet.getString("name"));

        return course;
    }
}
