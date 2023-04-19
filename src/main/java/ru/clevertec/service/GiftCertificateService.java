package ru.clevertec.service;

import ru.clevertec.dao.DAOInterface;
import ru.clevertec.dao.certificate.CertificateDAO;
import ru.clevertec.dao.tag.TagDAO;
import ru.clevertec.entity.GiftCertificate;
import ru.clevertec.entity.Tag;
import ru.clevertec.exception.TagNotFoundException;
import ru.clevertec.util.Fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftCertificateService implements ServiceInterface<GiftCertificate> {
    private final DAOInterface<GiftCertificate> certificateDao;
    private final DAOInterface<Tag> tagDao;

    public GiftCertificateService(CertificateDAO CertificateDao, TagDAO tagDAO) {
        this.certificateDao = CertificateDao;
        this.tagDao = tagDAO;
    }

    @Override
    public List<GiftCertificate> getAll() {
        return certificateDao.findAll();
    }

    @Override
    public GiftCertificate getById(int id) {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.ID, String.valueOf(id));
        return certificateDao.findBy(params).get(0);
    }

    @Override
    public List<GiftCertificate> getBy(Map<String, String> params) {
        return certificateDao.findBy(params);
    }

    @Override
    public void update(GiftCertificate certificate) {
        certificateDao.update(certificate);
    }

    @Override
    public void delete(int id) {
        GiftCertificate tag = this.getById(id);
        if (tag == null) throw new TagNotFoundException(
                String.format("Gift certificate with id %d was not found", id));
        certificateDao.delete(id);
    }

    @Override
    public void create(GiftCertificate certificate) {

    }
}