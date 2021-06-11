package ua.com.foxminded.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.domain.entity.Audience;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scripts/schema.sql", "/scripts/data.sql"})
@ActiveProfiles("test")
public class AudienceRepositoryTest {

    private final AudienceRepository audienceRepository;

    @Autowired
    public AudienceRepositoryTest(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    @Test
    void createAudience() {
        Audience audience = new Audience(1L, 1, 1);
        audienceRepository.save(audience);
        Audience createdAudience = audienceRepository.getOne(audience.getId());

        assertEquals(audience, createdAudience);
    }

    @Test
    void deleteAudience() {
        Audience audienceToBeDeleted = audienceRepository.getOne(1L);
        audienceRepository.deleteById(audienceToBeDeleted.getId());

        assertEquals(2, audienceRepository.findAll().size());
    }

    @Test
    void updateAudience() {
        Audience newAudience = new Audience(1L, 2, 2);
        audienceRepository.save(newAudience);
        Audience updatedAudience = audienceRepository.getOne(1L);

        assertEquals(newAudience, updatedAudience);
    }

    @Test
    void findAllAudiences() {
        List<Audience> audiences = audienceRepository.findAll();

        assertEquals(3, audiences.size());
    }

    @Test
    void findAudienceById() {
        Audience audience = new Audience(1L, 1, 50);
        Audience audienceInDB = audienceRepository.getOne(1L);

        assertEquals(audience, Hibernate.unproxy(audienceInDB));
    }
}
