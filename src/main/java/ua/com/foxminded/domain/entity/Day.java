package ua.com.foxminded.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "day1")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "{min.id}")
    private Long id;

    @Column(name = "day")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
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
