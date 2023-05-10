package ru.clevertec.ecl.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class NamedEntity extends BaseEntity {

    @Column(unique = true)
    private String name;

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedEntity that)) return false;
        if (!super.equals(o)) return false;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
