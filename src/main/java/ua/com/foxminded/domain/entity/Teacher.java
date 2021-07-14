package ua.com.foxminded.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "{min.id}")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "{not.empty}")
    @Size(min = 2, max = 30, message = "{range.size}")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "{not.empty}")
    @Size(min = 2, max = 30, message = "{range.size}")
    private String surname;

    @Column(name = "email")
    @NotEmpty(message = "{not.empty}")
    @Email(message = "{valid.email}")
    private String email;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "teacher_scheduleitem",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "scheduleitem_id")}
    )
    private Set<ScheduleItem> scheduleItems;

    public Teacher() {
    }

    public Teacher(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Teacher(Long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        Teacher teacher = (Teacher) o;
        return id.equals(teacher.id) && name.equals(teacher.name) && surname.equals(teacher.surname) && email.equals(teacher.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
