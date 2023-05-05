package ru.clevertec.ecl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.DAOInterface;
import ru.clevertec.ecl.dao.GiftCertificateToTagDAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.DAOException;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.validation.ValidationUtil;

import java.util.*;

@Service
@AllArgsConstructor
public class GiftCertificateService implements ServiceInterface<GiftCertificate> {

    private final DAOInterface<GiftCertificate> giftCertificateDAO;

    private final GiftCertificateToTagDAO giftCertificateToTagDAO;

    private final TagService tagService;

    @Transactional
    @Override
    public List<GiftCertificate> getAll() {
        var certificates = giftCertificateDAO.findAll();

        if (certificates.isEmpty()) {
            return new ArrayList<>();
        }

        var giftCertificates = certificates.get();
        giftCertificates.forEach(certificate -> {
            var tags = tagService.getByCertificateId(certificate.getId());
            certificate.getTags().addAll(tags);
        });

        return giftCertificates;
    }

    @Transactional
    @Override
    public GiftCertificate getById(int id) {
        try {
            var giftCertificate = giftCertificateDAO.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(String.format("Gift certificate with id %d not found!", id)));
            giftCertificate.getTags().addAll(tagService.getByCertificateId(id));

            return giftCertificate;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<GiftCertificate> getBy(Map<String, String> params) {
        try {
            var certificates = giftCertificateDAO.findBy(params);

            if (certificates.isEmpty()) {
                return new ArrayList<>();
            }

            List<GiftCertificate> giftCertificates = certificates.get();
            giftCertificates.forEach(certificate -> {
                var tags = tagService.getByCertificateId(certificate.getId());
                certificate.getTags().addAll(tags);
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
            var oldGiftCertificate = giftCertificateDAO.findById(certificate.getId())
                    .orElseThrow(() -> new ItemNotFoundException(
                            String.format("Update failed. Gift certificate with id %d not found.", certificate.getId())));


            oldGiftCertificate = giftCertificateDAO.update(validateAndUpdateFields(oldGiftCertificate, certificate))
                    .orElseThrow(() -> new ServiceException("Update failed. Try again later."));

            giftCertificateToTagDAO.deleteByGiftCertificateId(certificate.getId());
            if (!certificate.getTags().isEmpty()) {
                mapTagsToGiftCertificates(certificate.getTags(), oldGiftCertificate.getId());
            }

            oldGiftCertificate.setTags(new HashSet<>(tagService.getByCertificateId(oldGiftCertificate.getId())));

            return oldGiftCertificate;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        try {
            var giftCertificate = giftCertificateDAO.findById(id).orElseThrow(()
                    -> new ItemNotFoundException(String.format("Not deleted! GiftCertificate with id %d not found!", id)));

            if (giftCertificateToTagDAO.deleteByGiftCertificateId(giftCertificate.getId()) == 0 && giftCertificateDAO.delete(id) == 0) {
                return false;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public GiftCertificate create(GiftCertificate giftCertificate) {
        try {
            var createdGiftCertificate = giftCertificateDAO.create(giftCertificate).orElseThrow(()
                    -> new ServiceException("Can't create giftCertificate. Try again later."));

            if (!giftCertificate.getTags().isEmpty()) {
                mapTagsToGiftCertificates(giftCertificate.getTags(), createdGiftCertificate.getId());
            }

            createdGiftCertificate.setTags(new HashSet<>(tagService.getByCertificateId(createdGiftCertificate.getId())));

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
        var createdTags = new HashSet<Tag>();
        tags.forEach(t -> {
            var tag = Optional.ofNullable(tagService.getByName(t.getName()));
            if (tag.isEmpty()) {
                var created = tagService.create(Tag.builder().name(t.getName()).build());
                createdTags.add(created);
            }
        });

        createdTags.forEach(tag -> giftCertificateToTagDAO.saveGiftCertificateToTag(giftCertificateId, tag.getId()));
    }
}
