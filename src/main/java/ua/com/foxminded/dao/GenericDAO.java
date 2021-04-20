package ua.com.foxminded.dao;

import java.util.List;

public interface GenericDAO<T> {

    T findById(Long id);

    List<T> findAll();

    void create(T t);

    void delete(Long id);

    void update(Long id, T t);
}
