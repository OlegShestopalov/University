package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.DayRepository;
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.service.DayService;

import java.util.List;

@Service
@Transactional
public class DayServiceImpl implements DayService {

    private final DayRepository dayRepository;

    @Autowired
    public DayServiceImpl(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public void create(Day day) {
        dayRepository.save(day);
    }

    @Override
    public void delete(Long id) {
        dayRepository.deleteById(id);
    }

    @Override
    public List<Day> findAll() {
        return dayRepository.findAll();
    }

    @Override
    public Day findById(Long id) {
        return dayRepository.getOne(id);
    }
}
