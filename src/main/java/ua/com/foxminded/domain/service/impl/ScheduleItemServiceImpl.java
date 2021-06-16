package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.ScheduleItemRepository;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.service.ScheduleItemService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ScheduleItemServiceImpl implements ScheduleItemService {

    private final ScheduleItemRepository scheduleItemRepository;

    @Autowired
    public ScheduleItemServiceImpl(ScheduleItemRepository scheduleItemRepository) {
        this.scheduleItemRepository = scheduleItemRepository;
    }

    @Override
    public void create(ScheduleItem scheduleItem) {
        scheduleItemRepository.save(scheduleItem);
    }

    @Override
    public void delete(Long id) {
        scheduleItemRepository.deleteById(id);
    }

    @Override
    public ScheduleItem findById(Long id) {
        return scheduleItemRepository.getOne(id);
    }

    @Override
    public List<ScheduleItem> findAll() {
        return scheduleItemRepository.findAll();
    }

    @Override
    public Page<ScheduleItem> findByDay(int pageNumber, LocalDate day) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        return scheduleItemRepository.findByDayDay(pageable, day);

    }

    @Override
    public Page<ScheduleItem> findAll(int pageNumber, LocalDate day) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        if (day != null) {
            return findByDay(pageNumber, day);
        }
        return scheduleItemRepository.findAll(pageable);
    }

    @Override
    public Page<ScheduleItem> findByGroupNameOrDay(int pageNumber, String name, LocalDate day) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        if ((day != null) && (name != "")) {
            return scheduleItemRepository.findByGroupsNameAndDayDay(pageable, name, day);
        }
        if (day != null) {
            return scheduleItemRepository.findByDayDay(pageable, day);
        }
        if (name != "") {
            return scheduleItemRepository.findByGroupsName(pageable, name);
        }
        return scheduleItemRepository.findAll(pageable);
    }

    @Override
    public Page<ScheduleItem> findByTeacherNameOrDay(int pageNumber, String name, LocalDate day) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        if ((day != null) && (name != "")) {
            return scheduleItemRepository.findByTeachersNameAndDayDay(pageable, name, day);
        }
        if (day != null) {
            return scheduleItemRepository.findByDayDay(pageable, day);
        }
        if (name != null) {
            return scheduleItemRepository.findByTeachersName(pageable, name);
        }
        return scheduleItemRepository.findAll(pageable);
    }
}
