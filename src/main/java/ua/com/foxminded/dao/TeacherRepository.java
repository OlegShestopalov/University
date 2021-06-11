package ua.com.foxminded.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.entity.Teacher;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query(value = "SELECT t FROM Teacher t WHERE CONCAT(t.name, t.surname) LIKE %?1% ORDER BY t.name ASC")
    List<Teacher> findByPersonalData(final String name);
}
