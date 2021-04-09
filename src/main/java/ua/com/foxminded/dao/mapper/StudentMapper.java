package ua.com.foxminded.dao.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {

    @Autowired
    private GroupDAO groupDAO;

    public StudentMapper() {

    }

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getLong("id"));
        Long groupId = resultSet.getLong("group_id");
        student.setName(resultSet.getString("name"));
        student.setSurname(resultSet.getString("surname"));
        student.setSex(resultSet.getString("sex"));
        student.setAge(resultSet.getInt("age"));
        student.setEmail(resultSet.getString("email"));
        student.setGroup(groupDAO.findById(groupId));

        return student;
    }
}
