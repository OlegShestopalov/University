package ua.com.foxminded.domain.model;

public class ScheduleItemDto {

    private Long id;
    private LessonDto lesson;
    private SubjectDto subject;
    private AudienceDto audience;
    private DayDto day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LessonDto getLesson() {
        return lesson;
    }

    public void setLesson(LessonDto lesson) {
        this.lesson = lesson;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public AudienceDto getAudience() {
        return audience;
    }

    public void setAudience(AudienceDto audience) {
        this.audience = audience;
    }

    public DayDto getDay() {
        return day;
    }

    public void setDay(DayDto day) {
        this.day = day;
    }
}
