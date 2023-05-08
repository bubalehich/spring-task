package ru.clevertec.ecl.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dao.impl.GenericDAO;
import ru.clevertec.ecl.entity.audit.AuditLog;
import ru.clevertec.ecl.entity.base.BaseEntity;

import java.time.LocalDateTime;

@Component
public class AuditListener extends BaseEntity {

    private static final String DELETE_ACTION = "DELETE";
    private static final String UPDATE_ACTION = "UPDATE";
    private static final String PERSIST_ACTION = "PERSIST";
    private final GenericDAO<AuditLog> auditDAO;

    @Autowired
    public AuditListener(GenericDAO<AuditLog> auditDAO) {
        this.auditDAO = auditDAO;
        auditDAO.setClazz(AuditLog.class);
    }

    @PostPersist
    private void beforePersist(Object o) {
        auditDAO.save(new AuditLog(PERSIST_ACTION, LocalDateTime.now(), o.getClass().getSimpleName(), ((BaseEntity) o).getId()));
    }

    @PostUpdate
    private void beforeUpdate(Object o) {
        auditDAO.save(new AuditLog(UPDATE_ACTION, LocalDateTime.now(), o.getClass().getSimpleName(), ((BaseEntity) o).getId()));
    }

    @PreRemove
    private void beforeRemove(Object o) {
        auditDAO.save(new AuditLog(DELETE_ACTION, LocalDateTime.now(), o.getClass().getSimpleName(), ((BaseEntity) o).getId()));
    }
}
