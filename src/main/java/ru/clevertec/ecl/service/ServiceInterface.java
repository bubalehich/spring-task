package ru.clevertec.ecl.service;

import java.util.List;
import java.util.Map;

public interface ServiceInterface<T> {
    List<T> getAll();

    T getById(int id);

    List<T> getBy(Map<String, String> params);

    T update(T t);

    boolean delete(int id);

    T create(T t);

    T save(T t);
}
