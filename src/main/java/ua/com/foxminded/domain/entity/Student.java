package ua.com.foxminded.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Valid
    private Group group;

    @Column(name = "name")
    @NotBlank(message = "{not.empty}")
    @Size(min = 2, max = 30, message = "{range.size}")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "{not.empty}")
    @Size(min = 2, max = 30, message = "{range.size}")
    private String surname;

    @Column(name = "sex")
    @NotBlank(message = "{not.empty}")
    @Size(min = 2, max = 30, message = "{range.size}")
    private String sex;

    @Column(name = "age")
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 70, message = "Age should not be greater than 70")
    private int age;

    @Column(name = "email")
    @NotEmpty(message = "{not.empty}")
    @Email(message = "{valid.email}")
    private String email;

    public Student() {
    }

    public Student(String name, String surname, String sex, int age, String email) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.age = age;
        this.email = email;
    }

    public Student(Group group, String name, String surname, String sex, int age, String email) {
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.age = age;
        this.email = email;
    }

    public Student(Long id, Group group, String name, String surname, String sex, int age, String email) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && id.equals(student.id) && group.equals(student.group) && name.equals(student.name) && surname.equals(student.surname) && sex.equals(student.sex) && email.equals(student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, surname, sex, age, email);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
