package ua.com.foxminded.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT s FROM Student s WHERE CONCAT(s.name, s.surname, s.group.name) LIKE %?1% ORDER BY s.name ASC")
    List<Student> findByNameOrSurnameOrGroup(final String name);
}
