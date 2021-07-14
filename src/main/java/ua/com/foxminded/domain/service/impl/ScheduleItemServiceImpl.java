package ua.com.foxminded.domain.service.impl;

import javassist.NotFoundException;
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
import ua.com.foxminded.exception.AlreadyExistException;

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
    public void create(ScheduleItem scheduleItem) throws AlreadyExistException {
        scheduleItemRepository.save(scheduleItem);
    }

    @Override
    public void update(ScheduleItem scheduleItem) throws NotFoundException {
        scheduleItemRepository.save(scheduleItem);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        ScheduleItem scheduleItem = scheduleItemRepository.findById(id).get();
        if (scheduleItem == null) {
            throw new NotFoundException("ScheduleItem could not be found");
        }
        scheduleItemRepository.deleteById(id);
    }

    @Override
    public ScheduleItem findById(Long id) throws NotFoundException {
        ScheduleItem scheduleItem = scheduleItemRepository.findById(id).get();
        if (scheduleItem == null) {
            throw new NotFoundException("Teacher could not be found");
        }
        return scheduleItem;
    }

    @Override
    public List<ScheduleItem> findAll() throws NotFoundException {
        List<ScheduleItem> scheduleItems = scheduleItemRepository.findAll();
        if (scheduleItems == null) {
            throw new NotFoundException("Teachers could not be  found");
        }
        return scheduleItems;
    }

    @Override
    public Page<ScheduleItem> findByDay(int pageNumber, LocalDate day) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        Page<ScheduleItem> scheduleItems = scheduleItemRepository.findByDayDay(pageable, day);
        if (scheduleItems == null) {
            throw new NotFoundException("Page with scheduleItems by day could not be found");
        }
        return scheduleItems;

    }

    @Override
    public Page<ScheduleItem> findAll(int pageNumber, LocalDate day) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        Page<ScheduleItem> scheduleItems = scheduleItemRepository.findAll(pageable);
        if (scheduleItems == null) {
            throw new NotFoundException("Page with scheduleItems could not be found");
        }
        if (day != null) {
            return findByDay(pageNumber, day);
        }
        return scheduleItems;
    }

    @Override
    public Page<ScheduleItem> findByGroupNameOrDay(int pageNumber, String name, LocalDate day) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        if ((day != null) && (name != "")) {
            return scheduleItemRepository.findByGroupsNameAndDayDay(pageable, name, day);
        }
        if (day != null) {
            return findByDay(pageNumber, day);
        }
        if (name != "") {
            return scheduleItemRepository.findByGroupsName(pageable, name);
        }
        return findAll(pageNumber, day);
    }

    @Override
    public Page<ScheduleItem> findByTeacherNameOrDay(int pageNumber, String name, LocalDate day) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        if ((day != null) && (name != "")) {
            return scheduleItemRepository.findByTeachersNameAndDayDay(pageable, name, day);
        }
        if (day != null) {
            return findByDay(pageNumber, day);
        }
        if (name != null) {
            return scheduleItemRepository.findByTeachersName(pageable, name);
        }
        return findAll(pageNumber, day);
    }
}
