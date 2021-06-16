package ua.com.foxminded.domain.service;

import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

public interface FacultyService {

    void create(final Faculty faculty);

    void delete(final Long id);

    Faculty findById(final Long id);

    Page<Faculty> findAll(final int pageNumber);

    List<Faculty> findByName(final String name);
}
