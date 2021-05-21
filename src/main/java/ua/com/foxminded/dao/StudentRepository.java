package ua.com.foxminded.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
