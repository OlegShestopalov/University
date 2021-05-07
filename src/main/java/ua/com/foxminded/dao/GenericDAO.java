package ua.com.foxminded.dao;

import java.util.List;

public interface GenericDAO<T> {

    void create(T t);

    void delete(Long id);

    void update(T t);

    List<T> findAll();

    T findById(Long id);
}
