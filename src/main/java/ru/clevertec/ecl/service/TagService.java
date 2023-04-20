package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.DAOInterface;
import ru.clevertec.ecl.dao.GiftCertificateToTagDAO;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.DAOException;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.exception.ServiceException;

import java.util.*;

@Service
public class TagService implements ServiceInterface<Tag> {
    private final DAOInterface<Tag> tagDao;

    private final GiftCertificateToTagDAO giftCertificateToTagDAO;

    @Autowired
    public TagService(DAOInterface<Tag> tagDao, GiftCertificateToTagDAO giftCertificateToTagDAO) {
        this.tagDao = tagDao;
        this.giftCertificateToTagDAO = giftCertificateToTagDAO;
    }

    @Override
    @Transactional
    public List<Tag> getAll() {
        try {
            return tagDao.findAll().orElse(new ArrayList<>());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Tag getById(int id) {
        try {
            return tagDao.findById(id).orElseThrow(()
                    -> new ItemNotFoundException(String.format("Tag with id %d not found.", id))
            );
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Update operation not supported for Tag");
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            Tag tag = tagDao.findById(id).orElseThrow(()
                    -> new ItemNotFoundException(String.format("Tag with id %d not found.", id))
            );
            giftCertificateToTagDAO.deleteByTagId(tag.getId());
            tagDao.delete(tag.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tag create(Tag tag) {
        try {
            return tagDao.create(tag).orElseThrow(()
                    -> new ServiceException("Error during creating tag.")
            );
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Tag save(Tag tag) {
        try {
            return (tag.getId() == 0)
                    ? create(tag)
                    : update(tag);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tag> getBy(Map<String, String> params) {
        try {
            String name = Optional.of(params.get("name")).orElseThrow(()
                    -> new RequestParamsNotValidException("Invalid tag name."));

            Tag tag = tagDao.findByName(params.get("name")).orElseThrow(()
                    -> new ItemNotFoundException(String.format("Tag with name %s not found.", name))
            );

            return new ArrayList<>(Collections.singletonList(tag));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
