package ru.clevertec.dao;

import java.util.List;
import java.util.Map;

public interface DAOInterface<T> {
    void create(T t);

    List<T> findAll();

    List<T> findBy(Map<String, String> params);

    void delete(T t);
}
