package ru.clevertec.ecl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.ecl.entity.base.NamedEntity;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag extends NamedEntity {

    public Tag(String name) {
        super(name);
    }

    public Tag(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                "}";
    }
}
