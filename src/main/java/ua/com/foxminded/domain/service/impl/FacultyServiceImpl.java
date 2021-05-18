package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.FacultyRepository;
import ua.com.foxminded.dao.GroupRepository;
import ua.com.foxminded.dao.StudentRepository;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.service.FacultyService;

import java.util.List;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository, GroupRepository groupRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void create(Faculty faculty) {
        facultyRepository.create(faculty);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.delete(id);
    }

    @Override
    public void update(Long id, Faculty faculty) {
        facultyRepository.update(faculty);
    }

    @Override
    public Faculty findById(Long id) {
        return facultyRepository.findById(id);
    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    @Override
    public void calculateFacultyFullness(final Long id) {
        List<Group> groups = groupRepository.findAllGroupsInFaculty(id);
        int amount = 0;
        for (Group group : groups) {
            amount += studentRepository.findAllStudentsInGroup(group.getId()).size();
        }
    }

    @Override
    public List<Faculty> findFacultiesBySubjectId(Long id) {
        return facultyRepository.findAllFacultiesBySubjectId(id);
    }

    @Override
    public List<Faculty> findFacultiesByTeacherId(Long id) {
        return facultyRepository.findAllFacultiesByTeacherId(id);
    }
}
