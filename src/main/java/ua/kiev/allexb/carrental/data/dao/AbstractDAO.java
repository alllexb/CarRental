package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.AbstractDomain;

import java.sql.SQLException;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public interface AbstractDAO<T extends AbstractDomain> {

    int ONE = 1;
    int ALL = Integer.MAX_VALUE;

    List<T> getAll() throws SQLException;

    T getById(Long id) throws SQLException;

    void add(T model) throws SQLException;

    void update(T model) throws SQLException;

    void remove(T model) throws SQLException;
}

