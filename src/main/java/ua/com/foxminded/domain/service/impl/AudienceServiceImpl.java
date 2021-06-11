package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.AudienceRepository;
import ua.com.foxminded.domain.entity.Audience;
import ua.com.foxminded.domain.service.AudienceService;

import java.util.List;

@Service
@Transactional
public class AudienceServiceImpl implements AudienceService {

    private final AudienceRepository audienceRepository;

    @Autowired
    public AudienceServiceImpl(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    @Override
    public void create(Audience audience) {
        audienceRepository.save(audience);
    }

    @Override
    public void delete(Long id) {
        audienceRepository.deleteById(id);
    }

    @Override
    public List<Audience> findAll() {
        return audienceRepository.findAll();
    }

    @Override
    public Audience findById(Long id) {
        return audienceRepository.getOne(id);
    }
}
