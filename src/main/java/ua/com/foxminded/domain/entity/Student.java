package ua.com.foxminded.domain.entity;

public class Student {

    private Long id;
    private Long group;
    private String name;
    private String surname;
    private String sex;
    private int age;
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

    public Student(Long group, String name, String surname, String sex, int age, String email) {
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.age = age;
        this.email = email;
    }

    public Student(Long id, Long group, String name, String surname, String sex, int age, String email) {
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

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
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
