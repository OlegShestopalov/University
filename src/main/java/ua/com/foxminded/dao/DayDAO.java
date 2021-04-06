package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Day;

import java.util.List;

public interface DayDAO {

    public void add(final Day day);

    public void removeDay(final Long id);

    public List<Day> findTerm(final Long startId, final Long stopId);
}
