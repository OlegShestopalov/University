package ua.com.foxminded.domain.service;

import org.springframework.data.domain.Page;
import ua.com.foxminded.domain.entity.ScheduleItem;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleItemService {

    void create(ScheduleItem scheduleItem);

    void delete(Long id);

    ScheduleItem findById(Long id);

    List<ScheduleItem> findAll();

    Page<ScheduleItem> findAll(int pageNumber, LocalDate day);

    Page<ScheduleItem> findByDay(int pageNumber, LocalDate day);

    Page<ScheduleItem> findByGroupNameOrDay(int pageNumber, String name, LocalDate day);

    Page<ScheduleItem> findByTeacherNameOrDay(int pageNumber, String name, LocalDate day);
}
