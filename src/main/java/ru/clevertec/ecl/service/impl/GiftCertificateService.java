package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.GenericDAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.service.ServiceInterface;
import ru.clevertec.ecl.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Transactional
@Service
public class GiftCertificateService implements ServiceInterface<GiftCertificate> {
    private final GenericDAO<GiftCertificate> certificateDAO;

    @Autowired
    public GiftCertificateService(GenericDAO<GiftCertificate> certificateDAO) {
        this.certificateDAO = certificateDAO;
        this.certificateDAO.setClazz(GiftCertificate.class);
    }

    @Override
    public GiftCertificate getById(int id) {
        GiftCertificate certificate = certificateDAO.getById(id);
        if (certificate == null) {
            throw new ItemNotFoundException("Gift certificate (id=" + id + ") not found");
        }

        return certificate;
    }

    @Override
    public Pagination<GiftCertificate> getAll(int page, int size, String sort, String sortMode) {
        return certificateDAO.getAll(new Pagination<>(size, page, 0), new Pair<>(sort, sortMode));
    }

    @Override
    public Pagination<GiftCertificate> getBy(Map<String, Pair<String, String>> filterParams,
                                             Pair<String, String> sortParams, int page, int size) {
        //TODO
        return null;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            throw new RequestParamsNotValidException("Empty body");
        }
        if (giftCertificate.getName() == null || giftCertificate.getName().trim().isEmpty()
                || giftCertificate.getDescription() == null || giftCertificate.getDescription().trim().isEmpty()
                || giftCertificate.getDuration() == 0 || giftCertificate.getPrice() == 0) {
            throw new ServiceException("Name, description, duration, price required");
        }
        giftCertificate.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        giftCertificate.setLastUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return certificateDAO.save(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        if (giftCertificate == null || giftCertificate.getId() == 0) {
            throw new ItemNotFoundException("Given gift certificate not found.");
        }

        GiftCertificate oldCertificate = certificateDAO.getById(giftCertificate.getId());
        if (oldCertificate == null) {
            throw new ItemNotFoundException("Given gift certificate not found.");
        }

        boolean costOrDurationChanged = false;
        if (giftCertificate.getPrice() == 0) {
            giftCertificate.setPrice(oldCertificate.getPrice());
        }
        if (giftCertificate.getPrice() != oldCertificate.getPrice()) {
            costOrDurationChanged = true;
        }
        if (giftCertificate.getDuration() == 0) {
            giftCertificate.setDuration(oldCertificate.getDuration());
        }
        if (giftCertificate.getDuration() != oldCertificate.getDuration() && costOrDurationChanged) {
            throw new ServiceException("Price and duration can't be changed at the same time.");
        }
        if (giftCertificate.getName() == null || giftCertificate.getName().trim().isEmpty()) {
            giftCertificate.setName(oldCertificate.getName());
        }
        if (giftCertificate.getDescription() == null || giftCertificate.getDescription().trim().isEmpty()) {
            giftCertificate.setDescription(oldCertificate.getDescription());
        }

        giftCertificate.setCreateDate(oldCertificate.getCreateDate());
        giftCertificate.setLastUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return certificateDAO.save(giftCertificate);
    }

    @Override
    public void delete(int id) {
        GiftCertificate certificate = certificateDAO.getById(id);
        if (certificate == null) {
            throw new ItemNotFoundException("certificate (id=\" + id + \") not found");
        }
        certificateDAO.delete(certificate);
    }
}
