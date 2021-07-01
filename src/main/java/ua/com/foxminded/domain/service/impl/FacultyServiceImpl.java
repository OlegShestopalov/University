package ua.com.foxminded.domain.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.FacultyRepository;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.service.FacultyService;
import ua.com.foxminded.exception.AlreadyExistException;

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
    public void create(Faculty faculty) throws AlreadyExistException {
        if (facultyRepository.findById(faculty.getId()) != null) {
            throw new AlreadyExistException("Faculty with the same name already exists");
        }
        facultyRepository.save(faculty);
    }

    @Override
    public void update(Faculty faculty) throws NotFoundException {
        if (facultyRepository.findByName(faculty.getName()) == null) {
            throw new NotFoundException("Faculty with that name does not exists");
        }
        facultyRepository.save(faculty);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Faculty faculty = facultyRepository.findById(id).get();
        if (faculty == null) {
            throw new NotFoundException("Faculty could not be found");
        }
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty findById(Long id) throws NotFoundException {
        Faculty faculty = facultyRepository.findById(id).get();
        if (faculty == null) {
            throw new NotFoundException("Faculty could not be found");
        }
        return faculty;
    }

    @Override
    public Page<Faculty> findAll(int pageNumber) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Page<Faculty> faculties = facultyRepository.findAll(pageable);
        if (faculties == null) {
            throw new NotFoundException("Page with faculties could not be found");
        }
        return faculties;
    }

    @Override
    public List<Faculty> findByName(String name) throws NotFoundException {
        List<Faculty> faculties = facultyRepository.findByName(name);
        if (faculties == null) {
            throw new NotFoundException("Faculties could not be found");
        }
        return faculties;
    }

    @Override
    public List<Faculty> findAll() throws NotFoundException {
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties == null) {
            throw new NotFoundException("Faculties could not be found");
        }
        return facultyRepository.findAll();
    }
}
