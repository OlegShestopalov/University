package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.FacultyDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.service.FacultyService;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyDAO facultyDAO;
    private final GroupDAO groupDAO;
    private final StudentDAO studentDAO;

    @Autowired
    public FacultyServiceImpl(FacultyDAO facultyDAO, GroupDAO groupDAO, StudentDAO studentDAO) {
        this.facultyDAO = facultyDAO;
        this.groupDAO = groupDAO;
        this.studentDAO = studentDAO;
    }

    @Override
    public void calculateFacultyFullness(final Long id) {
        List<Group> groups = groupDAO.findAllGroupsInFaculty(id);
        int amount = 0;
        for (Group group : groups) {
            amount += studentDAO.findAllStudentsInGroup(group.getId()).size();
        }
    }

    @Override
    public void add(Faculty faculty) {
        facultyDAO.add(faculty);
    }

    @Override
    public void remove(Long id) {
        facultyDAO.removeFaculty(id);
    }

    @Override
    public void update(Long id, Faculty faculty) {
        facultyDAO.update(id, faculty);
    }

    @Override
    public Faculty find(Long id) {
        return facultyDAO.findFaculty(id);
    }

    @Override
    public List<Faculty> findFacultiesBySubjectId(Long id) {
        return facultyDAO.findAllFacultiesBySubjectId(id);
    }

    @Override
    public List<Faculty> findFacultiesByTeacherId(Long id) {
        return facultyDAO.findAllFacultiesByTeacherId(id);
    }
}
