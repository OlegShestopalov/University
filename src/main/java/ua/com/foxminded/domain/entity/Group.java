package ua.com.foxminded.domain.entity;

public class Group {

    private Long id;
    private String name;
    private int faculty;
    private int course;

    public Group() {
    }

    public Group(String name, int faculty, int course) {
        this.name = name;
        this.faculty = faculty;
        this.course = course;
    }

    public Group(Long id, String name, int faculty, int course) {
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

    public int getFaculty() {
        return faculty;
    }

    public void setFaculty(int faculty) {
        this.faculty = faculty;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
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
