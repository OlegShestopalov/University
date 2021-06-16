package ua.com.foxminded.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.entity.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query(value = "SELECT f FROM Faculty f WHERE f.name LIKE %?1%  ORDER BY f.name ASC")
    List<Faculty> findByName(final String name);
}
