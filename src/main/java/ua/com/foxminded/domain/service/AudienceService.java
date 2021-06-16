package ua.com.foxminded.domain.service;

import ua.com.foxminded.domain.entity.Audience;

import java.util.List;

public interface AudienceService {

    void create(final Audience audience);

    void delete(final Long id);

    List<Audience> findAll();

    Audience findById(final Long id);
}
