package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Lesson;

import java.util.List;

public interface LessonService {

    void create(final Lesson lesson);

    void delete(final Long id);

    List<Lesson> findAll();

    Lesson findById(final Long id);
}
