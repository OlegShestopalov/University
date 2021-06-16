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
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.entity.Lesson;
import ua.com.foxminded.domain.entity.ScheduleItem;
import ua.com.foxminded.domain.entity.Subject;
import ua.com.foxminded.domain.service.impl.ScheduleItemServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Mock
    private Page<ScheduleItem> scheduleItemsPage;

    @Test
    void shouldCreateNewScheduleItemInDBWhenAddNewScheduleItem() {
        ScheduleItem scheduleItem = new ScheduleItem(1L, lesson, subject, audience, day);

        scheduleItemService.create(scheduleItem);

        verify(scheduleItemRepository, times(1)).save(scheduleItem);
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void shouldDeleteScheduleItemFromDBWhenInputId() {
        ScheduleItem scheduleItem = new ScheduleItem(1L, lesson, subject, audience, day);

        scheduleItemService.delete(1L);

        verify(scheduleItemRepository, times(1)).deleteById(scheduleItem.getId());
    }

    @Test
    void shouldReturnScheduleItemByIdWhenInputId() {
        when(scheduleItemRepository.getOne(anyLong())).thenReturn(new ScheduleItem(1L, lesson, subject, audience, day));

        assertEquals(day, scheduleItemService.findById(1L).getDay());
        assertEquals(subject, scheduleItemService.findById(1L).getSubject());
        assertEquals(audience, scheduleItemService.findById(1L).getAudience());
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void shouldReturnAllScheduleItemsWhenFindAll() {
        ScheduleItem one = new ScheduleItem(1L, lesson, subject, audience, day);
        List<ScheduleItem> scheduleItems = new ArrayList<>(Collections.singletonList(one));

        when(scheduleItemRepository.findAll()).thenReturn(scheduleItems);

        assertEquals(1, scheduleItemService.findAll().size());
        assertEquals(one, scheduleItemService.findAll().get(0));
        verifyNoMoreInteractions(scheduleItemRepository);
    }

    @Test
    void shouldReturnPagesWithScheduleItemsByDayWhenInputDay() {
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
    void shouldReturnPagesWithScheduleItemsWhenFindAll() {
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
    void shouldReturnPagesWithScheduleItemsByDayWhenInputDayOrGroupsName() {
        int pageNumber = 1;
        String name = "test";
        LocalDate date = LocalDate.parse("2020-09-01");
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));

        when(scheduleItemRepository.findByGroupsNameAndDayDay(pageable, name, date)).thenReturn(scheduleItemsPage);

        assertEquals(scheduleItemsPage, scheduleItemService.findByGroupNameOrDay(pageNumber, name, date));
    }


    @Test
    void shouldReturnPagesWithScheduleItemsByDayWhenInputDayOrTeacherName() {
        int pageNumber = 1;
        String name = "test";
        LocalDate date = LocalDate.parse("2020-09-01");
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("day"));

        when(scheduleItemRepository.findByTeachersNameAndDayDay(pageable, name, date)).thenReturn(scheduleItemsPage);

        assertEquals(scheduleItemsPage, scheduleItemService.findByTeacherNameOrDay(pageNumber, name, date));
    }
}
