package ua.com.foxminded.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.entity.ScheduleItem;

import java.time.LocalDate;

@Repository
public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {

    Page<ScheduleItem> findAll(Pageable pageable);

    Page<ScheduleItem> findByDayDay(Pageable pageable, LocalDate day);

    Page<ScheduleItem> findByGroupsName(Pageable pageable, final String name);

    Page<ScheduleItem> findByTeachersName(Pageable pageable, final String name);

    Page<ScheduleItem> findByGroupsNameAndDayDay(Pageable pageable, final String name, LocalDate day);

    Page<ScheduleItem> findByTeachersNameAndDayDay(Pageable pageable, final String name, LocalDate day);
}
