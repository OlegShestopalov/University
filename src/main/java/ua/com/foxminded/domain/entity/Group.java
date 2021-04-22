package ua.com.foxminded.domain.entity;

import java.util.Objects;

public class Group {

    private Long id;
    private String name;
    private Faculty faculty;
    private Course course;

    public Group() {
    }

    public Group(String name, Faculty faculty, Course course) {
        this.name = name;
        this.faculty = faculty;
        this.course = course;
    }

    public Group(Long id, String name, Faculty faculty, Course course) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.course = course;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id.equals(group.id) && name.equals(group.name) && faculty.equals(group.faculty) && course.equals(group.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, faculty, course);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", faculty=" + faculty +
                ", course=" + course +
                '}';
    }
}
