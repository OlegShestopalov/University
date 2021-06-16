package ua.com.foxminded.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "audience")
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "{not.null}")
    @Min(value = 1, message = "{min.id}")
    private Long id;

    @Column(name = "number")
    private int number;

    @Column(name = "desk")
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audience audience = (Audience) o;
        return number == audience.number && desk == audience.desk && id.equals(audience.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, desk);
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
