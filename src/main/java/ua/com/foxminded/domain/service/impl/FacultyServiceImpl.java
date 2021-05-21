package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.FacultyRepository;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.service.FacultyService;

import java.util.List;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public void create(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty findById(Long id) {
        return facultyRepository.getOne(id);
    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }
}
