package ru.clevertec.ecl.dao.impl;

import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.dao.AbstractDAO;
import ru.clevertec.ecl.dao.DAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.association.GiftCertificateToTag;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.util.Criteria;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDAO extends AbstractDAO<GiftCertificate> implements DAO<GiftCertificate> {

    public Pagination<GiftCertificate> getBy(List<Criteria> criteria, int page, int size, String sort, String sortMode) {
        StringBuilder queryBuilder = new StringBuilder("from " + getClazz().getName() + " t");
        if (!criteria.isEmpty()) {
            queryBuilder.append(" where ");
            Optional<Criteria> tagNameCriteria = Optional.ofNullable(criteria.stream()
                    .reduce(null, (acc, c) -> c.key().equalsIgnoreCase("tag_name") ? c : acc));
            tagNameCriteria.ifPresent(criteria::remove);

            criteria.forEach(c -> queryBuilder
                    .append("t.")
                    .append(c.key())
                    .append(" ").append(c.operator().getSign()).append(" ")
                    .append("'").append(c.value()).append("'")
                    .append(" and "));

            tagNameCriteria.ifPresent(c ->
                    queryBuilder
                            .append(String.format(" exists " +
                                    "(" +
                                    "    select gct.tag.id " +
                                    "    from %s gct " +
                                    "    join gct.tag gctt " +
                                    "    where t.id = gct.giftCertificate.id and (gctt.name = '%s')" +
                                    ") and ", GiftCertificateToTag.class.getName(), c.value())));
            queryBuilder.delete(queryBuilder.lastIndexOf(" and "), queryBuilder.length());
        }

        queryBuilder
                .append(" order by ")
                .append(" t.")
                .append(sort)
                .append(" ")
                .append(sortMode);

        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setFirstResult(page * size).setMaxResults(size);

        Pagination<GiftCertificate> pagination = new Pagination<>(size, page, 0);
        pagination.setContent(query.getResultList());
        queryBuilder.insert(0, "select count(t) ");
        long count = (long) entityManager.createQuery(queryBuilder.toString()).getSingleResult();
        pagination.setOverallPages(count);

        return pagination;
    }
}
