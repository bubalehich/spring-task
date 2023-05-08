package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.GenericDAO;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.service.ServiceInterface;
import ru.clevertec.ecl.util.Pair;

import java.util.Map;

@Transactional
@Service
public class TagService implements ServiceInterface<Tag> {
    private final GenericDAO<Tag> tagDAO;

    @Autowired
    public TagService(GenericDAO<Tag> tagDAO) {
        this.tagDAO = tagDAO;
        this.tagDAO.setClazz(Tag.class);
    }

    @Override
    public Tag getById(int id) {
        Tag tag = tagDAO.getById(id);
        if (tag == null) {
            throw new ItemNotFoundException("Tag (id=" + id + ") not found");
        }

        return tag;
    }

    @Override
    public Pagination<Tag> getAll(int size, int page, String sort, String sortMode) {
        return tagDAO.getAll(new Pagination<>(size, page, 0), new Pair<>(sort, sortMode));
    }

    @Override
    public Pagination<Tag> getBy(Map<String, Pair<String, String>> filterParams, Pair<String, String> sortParams, int page, int size) {
        //TODO search logic

        return tagDAO.getBy(filterParams, sortParams, new Pagination<>(page, size, 0));
    }

    @Override
    public Tag create(Tag tag) {
        if (tag == null) {
            throw new RequestParamsNotValidException("Empty body");
        }
        if (tag.getName() == null || tag.getName().trim().isEmpty()) {
            throw new ServiceException("Name required");
        }

        return tagDAO.save(tag);
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Update operation is not allowed for tag");
    }

    @Override
    public void delete(int id) {
        Tag tag = tagDAO.getById(id);
        if (tag == null) {
            throw new ItemNotFoundException("Tag (id=\" + id + \") not found");
        }
        tagDAO.delete(tag);
    }
}
