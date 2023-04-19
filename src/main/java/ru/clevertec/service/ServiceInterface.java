package ru.clevertec.service;

import java.util.List;
import java.util.Map;

public interface ServiceInterface<T> {
    List<T> getAll();

    T getById(int id);

    List<T> getBy(Map<String, String> params);

    void update (T t);

    void delete(int id);

    void create(T t);
}
