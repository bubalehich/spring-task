package ru.clevertec.ecl.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@MappedSuperclass
public abstract class Person extends BaseEntity {

    private String firstName;

    private String lastName;

    protected Person(Integer id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        if (!super.equals(o)) return false;

        return getFirstName().equals(person.getFirstName()) &&
                getLastName().equals(person.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName());
    }
}
