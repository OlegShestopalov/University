package ua.com.foxminded.domain.service;

import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

public interface TeacherService {

    void create(final Teacher teacher);

    void delete(final Long id);

    Teacher findById(final Long id);

    Page<Teacher> findAll(final int pageNumber);

    List<Teacher> findByPersonalData(final String name);
}
