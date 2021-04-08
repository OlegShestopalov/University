package ua.com.foxminded.domain.entity;

import java.time.LocalDate;

public class Day {

    private Long id;
    private LocalDate day;

    public Day() {
    }

    public Day(LocalDate day) {
        this.day = day;
    }

    public Day(Long id, LocalDate day) {
        this.id = id;
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", day=" + day +
                '}';
    }
}
