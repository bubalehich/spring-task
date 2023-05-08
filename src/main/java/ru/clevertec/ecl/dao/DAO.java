package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entity.base.BaseEntity;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.util.Criteria;
import ru.clevertec.ecl.util.Pair;

import java.util.List;
import java.util.Optional;

public interface DAO<T extends BaseEntity> {

    Optional<T> getById(int id);

    Pagination<T> getAll(Pagination<T> pagination, Pair<String, String> sortParams);

    Pagination<T> getBy(List<Criteria> criteria, int page, int size, String sort, String sortMode);

    Optional<T> save(T t);

    void delete(T t);
}
