package ru.clevertec.ecl.api.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import ru.clevertec.ecl.entity.base.BaseEntity;
import ru.clevertec.ecl.pagination.RestPagination;

import java.util.Map;

public interface RestHateoasController<T extends BaseEntity> {

    EntityModel<T> one(int id);

    EntityModel<RestPagination<T>> all(int page, int size, String sort, String sortMode);

    EntityModel<RestPagination<T>> getBy(Map<String, String> params, int page, int size, String sort, String sortMode);

    ResponseEntity<EntityModel<T>> add(T t);

    ResponseEntity<EntityModel<T>> update(T t);

    ResponseEntity<EntityModel<T>> delete(int id);
}
