package ua.com.foxminded.domain.model;

import ua.com.foxminded.domain.entity.Course;
import ua.com.foxminded.domain.entity.Faculty;

public class GroupDto {

    private Long id;
    private String name;
    private Faculty faculty;
    private Course course;

    public GroupDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
