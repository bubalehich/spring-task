package ru.clevertec.dao;

import ru.clevertec.entity.Tag;

import java.util.List;

public interface TagDAO {
    void create(Tag tag);

    List<Tag> findAll();

    List<Tag> findBy(String criteria, String value);

    void delete(Tag tag);
}
