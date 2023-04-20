package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.validation.ValidationUtil;
import ru.clevertec.ecl.dao.DAOInterface;
import ru.clevertec.ecl.dao.GiftCertificateToTagDAO;
import ru.clevertec.ecl.dao.tag.TagDAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.DAOException;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.ServiceException;

import java.util.*;

@Service
public class GiftCertificateService implements ServiceInterface<GiftCertificate> {
    private final DAOInterface<GiftCertificate> giftCertificateDAO;

    private final GiftCertificateToTagDAO giftCertificateToTagDAO;

    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateService(DAOInterface<GiftCertificate> giftCertificateDAO, GiftCertificateToTagDAO giftCertificateToTagDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateToTagDAO = giftCertificateToTagDAO;
        this.tagDAO = tagDAO;
    }

    @Transactional
    @Override
    public List<GiftCertificate> getAll() {
        Optional<List<GiftCertificate>> certificates = giftCertificateDAO.findAll();

        if (!certificates.isPresent()) {
            return new ArrayList<>();
        }

        List<GiftCertificate> giftCertificates = certificates.get();
        giftCertificates.forEach(c -> {
            Optional<List<Tag>> tags = tagDAO.findByCertificateId(c.getId());
            c.getTags().addAll(tags.orElseGet(ArrayList::new));
        });

        return giftCertificates;
    }

    @Transactional
    @Override
    public GiftCertificate getById(int id) {
        try {
            GiftCertificate giftCertificate = giftCertificateDAO.findById(id).orElseThrow(()
                    -> new ItemNotFoundException(String.format("Gift certificate with id %d not found!", id)));
            giftCertificate.getTags().addAll(tagDAO.findByCertificateId(id).orElseGet(ArrayList::new));

            return giftCertificate;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<GiftCertificate> getBy(Map<String, String> params) {
        try {
            Optional<List<GiftCertificate>> certificates = giftCertificateDAO.findBy(params);

            if (!certificates.isPresent()) {
                return new ArrayList<>();
            }

            List<GiftCertificate> giftCertificates = certificates.get();
            giftCertificates.forEach(c -> {
                Optional<List<Tag>> tags = tagDAO.findByCertificateId(c.getId());
                c.getTags().addAll(tags.orElseGet(ArrayList::new));
            });

            return giftCertificates;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate certificate) {
        try {
            GiftCertificate oldGiftCertificate = giftCertificateDAO.findById(certificate.getId()).orElseThrow(()
                    -> new ItemNotFoundException(String.format("Update failed. Gift certificate with id %d not found."
                    , certificate.getId())));


            oldGiftCertificate
                    = giftCertificateDAO.update(validateAndUpdateFields(oldGiftCertificate, certificate)).orElseThrow(()
                    -> new ServiceException("Update failed. Try again later."));

            giftCertificateToTagDAO.deleteByGiftCertificateId(certificate.getId());
            if (!certificate.getTags().isEmpty()) {
                mapTagsToGiftCertificates(certificate.getTags(), oldGiftCertificate.getId());
            }

            oldGiftCertificate.setTags(new HashSet<>(tagDAO.findByCertificateId(oldGiftCertificate.getId())
                    .orElse(new ArrayList<>())));

            return oldGiftCertificate;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            GiftCertificate giftCertificate = giftCertificateDAO.findById(id).orElseThrow(()
                    -> new ItemNotFoundException(
                    String.format("Not deleted! GiftCertificate with id %d not found!", id)));

            giftCertificateToTagDAO.deleteByGiftCertificateId(giftCertificate.getId());

            giftCertificateDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public GiftCertificate create(GiftCertificate giftCertificate) {
        try {
            GiftCertificate createdGiftCertificate = giftCertificateDAO.create(giftCertificate).orElseThrow(()
                    -> new ServiceException("Can't create giftCertificate. Try again later."));

            if (!giftCertificate.getTags().isEmpty()) {
                mapTagsToGiftCertificates(giftCertificate.getTags(), createdGiftCertificate.getId());
            }

            createdGiftCertificate
                    .setTags(new HashSet<>(tagDAO.findByCertificateId(createdGiftCertificate.getId())
                            .orElse(new ArrayList<>())));

            return createdGiftCertificate;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        return (giftCertificate.getId() == 0)
                ? create(giftCertificate)
                : update(giftCertificate);
    }

    private GiftCertificate validateAndUpdateFields(GiftCertificate oldGiftCertificate, GiftCertificate newGiftCertificate) {
        oldGiftCertificate.setName(newGiftCertificate.getName() == null
                ? oldGiftCertificate.getName()
                : newGiftCertificate.getName());
        oldGiftCertificate.setDescription(newGiftCertificate.getDescription() == null
                ? oldGiftCertificate.getName()
                : newGiftCertificate.getDescription());
        oldGiftCertificate.setPrice(newGiftCertificate.getPrice() == 0
                ? oldGiftCertificate.getPrice()
                : newGiftCertificate.getPrice());
        oldGiftCertificate.setDuration(newGiftCertificate.getDuration() == 0
                ? oldGiftCertificate.getDuration()
                : newGiftCertificate.getDuration());

        ValidationUtil.validate(oldGiftCertificate);

        return oldGiftCertificate;
    }

    /**
     * Check tags existing, create if tag not found in DB, map tags to gift certificate
     */
    private void mapTagsToGiftCertificates(Set<Tag> tags, int giftCertificateId) {
        Set<Tag> createdTags = new HashSet<>();
        for (Tag t : tags) {
            Optional<Tag> tag = tagDAO.findByName(t.getName());
            if (!tag.isPresent()) {
                tag = tagDAO.create(t);
            }
            createdTags.add(tag.get());
        }

        for (Tag t : createdTags) {
            giftCertificateToTagDAO.mapGiftCertificateToTag(giftCertificateId, t.getId());
        }
    }
}