package ua.com.foxminded.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "group1")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "{min.id}")
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "{range.size}")
    private String name;

    @OneToOne
    @Valid
    private Faculty faculty;

    @OneToOne
    @Valid
    private Course course;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "group_scheduleitem",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "scheduleitem_id")}
    )
    private Set<ScheduleItem> scheduleItems;

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

    public Set<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public void setScheduleItems(Set<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
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
