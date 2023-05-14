package ru.clevertec.ecl.service;

import ru.clevertec.ecl.entity.base.BaseEntity;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.util.Criteria;

import java.util.List;

public interface ServiceInterface<T extends BaseEntity> {

    T getById(int id);

    Pagination<T> getAll(int page, int size, String sort, String sortMode);

    Pagination<T> getBy(List<Criteria> criteria, int page, int size, String sort, String sortMode);

    T create(T t);

    T update(T t);

    boolean delete(int id);
}
