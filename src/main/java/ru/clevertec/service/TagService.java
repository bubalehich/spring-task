package ru.clevertec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.dao.DAOInterface;
import ru.clevertec.dao.tag.TagDAO;
import ru.clevertec.entity.Tag;
import ru.clevertec.exception.TagNotFoundException;
import ru.clevertec.util.Fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagService implements ServiceInterface<Tag> {
    private final DAOInterface<Tag> dao;

    public TagService(TagDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Tag> getAll() {
        return dao.findAll();
    }

    @Override
    public Tag getById(int id) {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.ID, String.valueOf(id));
        return dao.findBy(params).get(0);
    }

    @Override
    public List<Tag> getBy(Map<String, String> params) {
        return dao.findBy(params);
    }

    @Override
    public void update(Tag tag) {
        dao.update(tag);
    }

    @Override
    public void delete(int id) {
        Tag tag = this.getById(id);
        if (tag == null) throw new TagNotFoundException(
                String.format("Tag with id %d was not found", id));
        dao.delete(id);
    }

    @Override
    public void create(Tag tag) {
        dao.create(tag);
    }
}