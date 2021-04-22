package ua.com.foxminded.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.FacultyDAO;
import ua.com.foxminded.domain.entity.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupMapper implements RowMapper<Group> {

    private final FacultyDAO facultyDAO;
    private final CourseDAO courseDAO;

    @Autowired
    public GroupMapper(FacultyDAO facultyDAO, CourseDAO courseDAO) {
        this.facultyDAO = facultyDAO;
        this.courseDAO = courseDAO;
    }

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group();

        group.setId(resultSet.getLong("id"));
        group.setName(resultSet.getString("name"));
        Long facultyId = resultSet.getLong("faculty_id");
        group.setFaculty(facultyDAO.findById(facultyId));
        Long courseId = resultSet.getLong("course_id");
        group.setCourse(courseDAO.findById(courseId));

        return group;
    }
}
