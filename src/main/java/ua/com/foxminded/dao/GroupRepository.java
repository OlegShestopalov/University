package ua.com.foxminded.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.domain.entity.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "SELECT g FROM Group g WHERE CONCAT(g.name, g.faculty.name) LIKE %?1%  ORDER BY g.name ASC")
    List<Group> findByName(final String name);
}
