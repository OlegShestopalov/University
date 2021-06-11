package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ua.com.foxminded.dao.ScheduleItemRepository;
import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.entity.Faculty;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.entity.Lesson;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.entity.Subject;
import ua.com.foxminded.domain.entity.Teacher;
import ua.com.foxminded.domain.service.impl.ScheduleItemServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScheduleItemServiceTest {

    @Mock
    private ScheduleItemRepository scheduleItemRepository;
    @InjectMocks
    private ScheduleItemServiceImpl scheduleItemService;
    @Mock
    private Lesson lesson;
    @Mock
    private Subject subject;
    @Mock
    private Audience audience;
    @Mock
    private Day day;
//    @Mock
//    private Group group;
//    @Mock
//    private Faculty faculty;
//    @Mock
//    private Course course;
    @Mock
    private Page<ScheduleItem> scheduleItemsPage;
    @Mock
    private Teacher teacher;

    @Test
    void CreateScheduleItem_WhenAddNewScheduleItem_ThenCreatedNewScheduleItemInDB() {
        ScheduleItem scheduleItem = new ScheduleItem(1L, lesson, subject, audience, day);

        scheduleItemService.create(scheduleItem);

        verify(scheduleItemRepository, times(1)).save(scheduleItem);
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void DeleteScheduleItemById_WhenInputId_ThenDeletedScheduleItemFromDB() {
        ScheduleItem scheduleItem = new ScheduleItem(1L, lesson, subject, audience, day);

        scheduleItemService.delete(1L);

        verify(scheduleItemRepository, times(1)).deleteById(scheduleItem.getId());
    }

    @Test
    void FindScheduleItemById_WhenInputId_ThenReturnedScheduleItemById() {
        when(scheduleItemRepository.getOne(anyLong())).thenReturn(new ScheduleItem(1L, lesson, subject, audience, day));

        assertEquals(day, scheduleItemService.findById(1L).getDay());
        assertEquals(subject, scheduleItemService.findById(1L).getSubject());
        assertEquals(audience, scheduleItemService.findById(1L).getAudience());
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void FindAllScheduleItems_WhenFindAll_ThenReturnedAllScheduleItems() {
        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
        List<ScheduleItem> scheduleItems = new ArrayList<>(Collections.singletonList(one));

        when(scheduleItemRepository.findAll()).thenReturn(scheduleItems);

        assertEquals(1, scheduleItemService.findAll().size());
        assertEquals(one, scheduleItemService.findAll().get(0));
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void FindScheduleItemsByDay_WhenInputDay_ThenReturnedPagesWithScheduleItemsByDay() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
        Page<ScheduleItem> scheduleItems = new PageImpl<>(Collections.singletonList(one));

        when(scheduleItemRepository.findByDayDay(pageable, day.getDay())).thenReturn(scheduleItems);

        assertEquals(scheduleItems, scheduleItemService.findByDay(pageNumber, day.getDay()));
        assertEquals(one, scheduleItemService.findByDay(pageNumber, day.getDay()).toList().get(0));
        assertEquals(1, scheduleItemService.findByDay(pageNumber, day.getDay()).getTotalElements());
        assertEquals(1, scheduleItemService.findByDay(pageNumber, day.getDay()).getTotalPages());
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void FindAllScheduleItemsByPages_WhenFindAll_ThenReturnedPagesWithScheduleItems() {
        int pageNumber = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
        Page<ScheduleItem> scheduleItems = new PageImpl<>(Collections.singletonList(one));

        when(scheduleItemRepository.findAll(pageable)).thenReturn(scheduleItems);

        assertEquals(scheduleItems, scheduleItemService.findAll(pageNumber, day.getDay()));
        assertEquals(one, scheduleItemService.findAll(pageNumber, day.getDay()).toList().get(0));
        assertEquals(1, scheduleItemService.findAll(pageNumber, day.getDay()).getTotalElements());
        assertEquals(1, scheduleItemService.findAll(pageNumber, day.getDay()).getTotalPages());
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void FindScheduleItemsByGroupsNameOrDay_WhenInputDayOrGroupsName_ThenReturnedPagesWithScheduleItemsByDay() {
//        int pageNumber = 1;
//        String name = "test";
//        LocalDate date = LocalDate.parse("2020-09-01");
//        Group group = new Group(1L, "test", faculty, course);
//        Day day = new Day(1L, LocalDate.parse("2020-09-01"));
//        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
//        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
//        Page<ScheduleItem> scheduleItems = new PageImpl<>(Collections.singletonList(one));
//
//        assertEquals(scheduleItems, scheduleItemService.findByGroupNameOrDay(pageNumber, name, date));
//        assertEquals(one, scheduleItemService.findAll(pageNumber, day.getDay()).toList().get(0));
//        assertEquals(1, scheduleItemService.findAll(pageNumber, day.getDay()).getTotalElements());
//        assertEquals(1, scheduleItemService.findAll(pageNumber, day.getDay()).getTotalPages());
    }



    @Test
    void FindScheduleItemsByTeacherNameOrDay_WhenInputDay_ThenReturnedPagesWithScheduleItemsByDay() {
//        int pageNumber = 1;
//        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));
//
//        when(scheduleItemRepository.findByTeachersNameAndDayDay(pageable, teacher.getName(), day.getDay())).thenReturn(scheduleItemsPage);
    }
}
