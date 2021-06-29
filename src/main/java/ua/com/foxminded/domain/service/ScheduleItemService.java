package ua.com.foxminded.domain.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.exception.AlreadyExistException;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleItemService {

    void create(ScheduleItem scheduleItem) throws AlreadyExistException;

    void update(ScheduleItem scheduleItem) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    ScheduleItem findById(Long id) throws NotFoundException;

    List<ScheduleItem> findAll() throws NotFoundException;

    Page<ScheduleItem> findAll(int pageNumber, LocalDate day) throws NotFoundException;

    Page<ScheduleItem> findByDay(int pageNumber, LocalDate day) throws NotFoundException;

    Page<ScheduleItem> findByGroupNameOrDay(int pageNumber, String name, LocalDate day) throws NotFoundException;

    Page<ScheduleItem> findByTeacherNameOrDay(int pageNumber, String name, LocalDate day) throws NotFoundException;
}
