package ru.clevertec.dao.certificate;

import ru.clevertec.dao.DAOInterface;
import ru.clevertec.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface CertificateDAO extends DAOInterface<GiftCertificate> {

    void create(GiftCertificate certificate);

    List<GiftCertificate> findAll();

    List<GiftCertificate> findBy(Map<String, String> params);

    void delete(GiftCertificate certificate);

    void update(GiftCertificate certificate);
}
