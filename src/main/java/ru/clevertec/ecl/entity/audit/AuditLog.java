package ru.clevertec.ecl.entity.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.ecl.entity.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "audit_log")
public class AuditLog extends BaseEntity {

    private String operation;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp;

    private String clazz;

    private Integer entityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditLog auditLog)) return false;
        if (!super.equals(o)) return false;

        return getTimestamp() == auditLog.getTimestamp() &&
                Objects.equals(getOperation(), auditLog.getOperation()) &&
                Objects.equals(getClazz(), auditLog.getClazz()) &&
                Objects.equals(getEntityId(), auditLog.getEntityId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOperation(), getTimestamp(), getClazz(), getEntityId());
    }
}
