package ua.com.foxminded.domain.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.dao.GroupRepository;
import ua.com.foxminded.domain.entity.Group;
import ua.com.foxminded.domain.service.GroupService;
import ua.com.foxminded.exception.AlreadyExistException;

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
    public void create(Group group) throws AlreadyExistException {
        if (groupRepository.findByNameOrFaculty(group.getName()) != null) {
            throw new AlreadyExistException("Group with the same name already exists");
        }
        groupRepository.save(group);
    }

    @Override
    public void update(Group group) throws NotFoundException {
        if (groupRepository.findByNameOrFaculty(group.getName()) == null) {
            throw new NotFoundException("Group with that name does not exists");
        }
        groupRepository.save(group);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Group group = groupRepository.findById(id).get();
        if (group == null) {
            throw new NotFoundException("Group could not be found");
        }
        groupRepository.deleteById(id);
    }

    @Override
    public Group findById(Long id) throws NotFoundException {
        Group group = groupRepository.findById(id).get();
        if (group == null) {
            throw new NotFoundException("Group could not be found");
        }
        return group;
    }

    @Override
    public Page<Group> findAll(int pageNumber) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        Page<Group> groups = groupRepository.findAll(pageable);
        if (groups == null) {
            throw new NotFoundException("Page with groups could not be found");
        }
        return groups;
    }

    @Override
    public List<Group> findByName(String name) throws NotFoundException {
        List<Group> groups = groupRepository.findByNameOrFaculty(name);
        if (groups == null) {
            throw new NotFoundException("Groups could not be found");
        }
        return groups;
    }

    @Override
    public List<Group> findAll() throws NotFoundException {
        List<Group> groups = groupRepository.findAll();
        if (groups == null) {
            throw new NotFoundException("Groups could not be found");
        }
        return groups;
    }
}
