package ua.com.foxminded.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day1 = (Day) o;
        return id.equals(day1.id) && day.equals(day1.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day);
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", day=" + day +
                '}';
    }
}
