package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.impl.GenericDAO;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.service.ServiceInterface;
import ru.clevertec.ecl.util.Criteria;
import ru.clevertec.ecl.util.Pair;

import java.util.List;

@Transactional
@Service
public class TagService implements ServiceInterface<Tag> {

    private final GenericDAO<Tag> tagDAO;

    @Autowired
    public TagService(GenericDAO<Tag> tagDAO) {
        this.tagDAO = tagDAO;
        this.tagDAO.setClazz(Tag.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Tag getById(int id) {
        return tagDAO.getById(id).orElseThrow(()
                -> new ItemNotFoundException(String.format("Tag id %d not found", id))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Pagination<Tag> getAll(int page, int size, String sort, String sortMode) {
        return tagDAO.getAll(new Pagination<>(size, page, 0), new Pair<>(sort, sortMode));
    }

    @Transactional(readOnly = true)
    @Override
    public Pagination<Tag> getBy(List<Criteria> criteria, int page, int size, String sort, String sortMode) {
        throw new UnsupportedOperationException("Search operation is not allowed for tag");
    }

    @Override
    public Tag create(Tag tag) {
        if (tag == null) {
            throw new RequestParamsNotValidException("Empty body");
        }
        if (tag.getName() == null || tag.getName().trim().isEmpty()) {
            throw new RequestParamsNotValidException("Name required");
        }

        return tagDAO.save(tag).orElse(null);
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Update operation is not allowed for tag");
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Tag tag = tagDAO.getById(id).orElseThrow(()
                -> new ItemNotFoundException(String.format("Tag id %d not found", id))
        );

        try {
            tagDAO.delete(tag);
        } catch (Exception ex) {
            throw new RequestParamsNotValidException("Tag can't be deleted. Already used in GiftCertificate");
        }

        return true;
    }
}
