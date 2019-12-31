package ru.otus.homework.services.database;

import java.sql.SQLException;
import java.util.List;

public interface DbService<T, K> {
    long create(T target) throws SQLException, IllegalAccessException;

    T load(K id) throws SQLException;

    void update(T target) throws SQLException, IllegalAccessException;

    List<T> getAll();
}
