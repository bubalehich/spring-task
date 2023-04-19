package ru.clevertec.dao.tag;

import ru.clevertec.dao.DAOInterface;
import ru.clevertec.entity.Tag;

import java.util.List;

public interface TagDAO extends DAOInterface<Tag> {

    void create(Tag tag);

    List<Tag> findAll();

    List<Tag> findBy(String criteria, String value);

    void delete(Tag tag);
}
