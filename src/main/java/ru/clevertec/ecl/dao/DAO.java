package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.util.Pair;

import java.io.Serializable;
import java.util.Map;

public interface DAO<T extends Serializable> {
    T getById(int id);

    Pagination<T> getAll(Pagination<T> pagination, Pair<String, String> sortParams);

    Pagination<T> getBy(Map<String, Pair<String, String>> filterParams, Pair<String, String> sortParams, Pagination<T> p);

    T save(T t);

    void delete(T t);
}
