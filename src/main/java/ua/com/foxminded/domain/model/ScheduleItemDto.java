package ua.com.foxminded.domain.model;

import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.domain.entity.Day;
import ua.com.foxminded.domain.entity.Lesson;
import ua.com.foxminded.domain.entity.Subject;

public class ScheduleItemDto {

    private Long id;
    private Lesson lesson;
    private Subject subject;
    private Audience audience;
    private Day day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
