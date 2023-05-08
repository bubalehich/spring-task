package ru.clevertec.ecl.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.ecl.entity.base.Person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Person {

    public User(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public User(Integer id, String firstName, String lastName, Set<Order> orders) {
        super(id, firstName, lastName);
        this.orders = orders;
    }

    @JsonManagedReference
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public boolean removeOrder(Order order) {
        return this.orders.remove(order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(getOrders(), user.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrders());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                '}';
    }
}
