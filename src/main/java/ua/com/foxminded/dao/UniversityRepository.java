package ua.com.foxminded.dao;

import ua.com.foxminded.domain.entity.Audience;

import java.util.List;

public interface UniversityRepository {

    public List<Audience> findAllAudiences();
}
