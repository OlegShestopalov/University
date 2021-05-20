package ua.com.foxminded.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.GroupRepository;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.service.GroupService;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void create(Group group) {
        groupRepository.save(group);
    }

    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.getOne(id);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
