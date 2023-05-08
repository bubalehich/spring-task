package ru.clevertec.ecl.api.controller;

import lombok.AllArgsConstructor;
import ru.clevertec.ecl.api.GiftCertificateApi;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.util.Fields;
import ru.clevertec.ecl.validation.ValidationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GiftCertificateController implements GiftCertificateApi {

    private final ServiceInterface<GiftCertificate> service;

    @Override
    public List<GiftCertificate> getGiftCertificates() {
        return service.getAll();
    }

    @Override
    public GiftCertificate getById(int id) {
        return service.getById(id);
    }

    @Override
    public GiftCertificate add(GiftCertificate certificate) {
        ValidationUtil.validate(certificate);
        return service.save(certificate);
    }

    @Override
    public GiftCertificate update(int id, GiftCertificate certificate) {
        return service.update(certificate);
    }

    @Override
    public String delete(int id) {
        service.delete(id);

        return String.format("Gift certificate with id %d has been deleted", id);
    }

    @Override
    public List<GiftCertificate> findTags(String name, String sort, String sortType, String description, String order, String tagName) {
        return service.getBy(mapParams(name, sort, sortType, description, order, tagName));
    }

    private Map<String, String> mapParams(String name, String sort, String sortType, String description, String order, String tagName) {
        Map<String, String> params = new HashMap<>();
        if (name != null && !name.isEmpty())
            params.put(Fields.NAME, name);
        if (sort != null && !sort.isEmpty())
            params.put(Fields.SORT, sort);
        if (sortType != null && !sortType.isEmpty())
            params.put(Fields.SORT_TYPE, sortType);
        if (description != null && !description.isEmpty())
            params.put(Fields.DESCRIPTION, description);
        if (order != null && !order.isEmpty())
            params.put(Fields.ORDER, order);
        if (tagName != null && !tagName.isEmpty())
            params.put(Fields.TAG_NAME, tagName);

        return params;
    }
}
