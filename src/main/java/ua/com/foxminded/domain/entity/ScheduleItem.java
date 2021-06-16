package ua.com.foxminded.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "scheduleitem")
public class ScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Valid
    private Lesson lesson;

    @OneToOne
    @Valid
    private Subject subject;

    @OneToOne
    @Valid
    private Audience audience;

    @OneToOne
    @Valid
    private Day day;

    @ManyToMany(mappedBy = "scheduleItems")
    private Set<Group> groups;

    @ManyToMany(mappedBy = "scheduleItems")
    private Set<Teacher> teachers;

    public ScheduleItem() {
    }

    public ScheduleItem(Lesson lesson, Subject subject, Audience audience, Day day) {
        this.lesson = lesson;
        this.subject = subject;
        this.audience = audience;
        this.day = day;
    }

    public ScheduleItem(Long id, Lesson lesson, Subject subject, Audience audience, Day day) {
        this.id = id;
        this.lesson = lesson;
        this.subject = subject;
        this.audience = audience;
        this.day = day;
    }

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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleItem that = (ScheduleItem) o;
        return id.equals(that.id) && lesson.equals(that.lesson) && subject.equals(that.subject) && audience.equals(that.audience) && day.equals(that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lesson, subject, audience, day);
    }


}
