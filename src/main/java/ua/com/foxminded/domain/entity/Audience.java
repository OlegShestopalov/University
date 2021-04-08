package ua.com.foxminded.domain.entity;

public class Audience {

    private Long id;
    private int number;
    private int desk;

    public Audience() {
    }

    public Audience(int number, int desk) {
        this.number = number;
        this.desk = desk;
    }

    public Audience(Long id, int number, int desk) {
        this.id = id;
        this.number = number;
        this.desk = desk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDesk() {
        return desk;
    }

    public void setDesk(int desk) {
        this.desk = desk;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                ", number=" + number +
                ", desk=" + desk +
                '}';
    }
}
