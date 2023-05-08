package ru.clevertec.ecl.service;

import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.util.Pair;

import java.io.Serializable;
import java.util.Map;

public interface ServiceInterface<T extends Serializable> {
    T getById(int id);

    Pagination<T> getAll(int page, int size, String sort, String sortMode);

    Pagination<T> getBy(Map<String, Pair<String, String>> filterParams, Pair<String, String> sortParams, int page, int size);

    T create(T t);

    T update(T t);

    void delete(int id);
}
