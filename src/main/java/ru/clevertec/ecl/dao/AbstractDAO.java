package ru.clevertec.ecl.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import ru.clevertec.ecl.entity.base.BaseEntity;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.util.Criteria;
import ru.clevertec.ecl.util.Pair;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T extends BaseEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> clazz;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Optional<T> getById(int id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    public Pagination<T> getAll(Pagination<T> pagination, Pair<String, String> sortParams) {
        pagination.setContent(
                entityManager.
                        createQuery("from " + clazz.getName() + " t" +
                                " order by t." + sortParams.key() + " " + sortParams.value()).
                        setFirstResult((pagination.getCurrentPage() - 1) * pagination.getSize()).
                        setMaxResults(pagination.getSize()).
                        getResultList());

        long count = (long) entityManager.createQuery("select count(t) from " + clazz.getName() + " t").getSingleResult();
        pagination.setOverallPages(count);

        return pagination;
    }

    public Pagination<T> getBy(List<Criteria> criteria, int page, int size, String sort, String sortMode) {
        StringBuilder queryBuilder = new StringBuilder("from " + clazz.getName() + " t");
        if (!criteria.isEmpty()) {
            queryBuilder.append(" where ");
            criteria.forEach(c -> queryBuilder.
                    append("t.").
                    append(c.key()).
                    append(" ").append(c.operator().getSign()).append(" ").
                    append(c.value()).
                    append(" and "));
            queryBuilder.delete(queryBuilder.lastIndexOf(" and "), queryBuilder.length());
        }

        queryBuilder.append(" order by ").
                append(" t.").
                append(sort).
                append(" ").
                append(sortMode);

        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setFirstResult(page * size).setMaxResults(size);
        Pagination<T> p = new Pagination<>(size, page, 0);
        p.setContent(query.getResultList());
        queryBuilder.insert(0, "select count(t) ");
        long count = (long) entityManager.createQuery(queryBuilder.toString()).getSingleResult();
        p.setOverallPages(count);

        return p;
    }

    public Optional<T> save(T t) {
        if (t.isNew()) {
            entityManager.persist(t);
            return Optional.of(t);
        }
        return Optional.ofNullable(entityManager.merge(t));
    }

    public void delete(T t) {
        entityManager.remove(t);
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
