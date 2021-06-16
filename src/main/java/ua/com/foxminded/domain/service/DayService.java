package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Day;

import java.util.List;

public interface DayService {

    void create(final Day day);

    void delete(final Long id);

    List<Day> findAll();

    Day findById(final Long id);
}
