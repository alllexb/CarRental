package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.AbstractDomain;

import java.util.List;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public interface AbstractDAO<T extends AbstractDomain> {

    List<T> getAll();

    T getById(Long id);

    void add(T model);

    void update(T model);

    void remove(T model);
}

