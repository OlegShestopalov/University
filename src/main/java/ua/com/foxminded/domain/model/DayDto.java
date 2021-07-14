package ua.com.foxminded.domain.model;

import java.time.LocalDate;

public class DayDto {

    private Long id;
    private LocalDate day;

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
}
