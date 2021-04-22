package ua.com.foxminded.domain.entity;

public class Class {

    private Long id;
    private String name;

    public Class() {
    }

    public Class(String name) {
        this.name = name;
    }

    public Class(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Couple{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
