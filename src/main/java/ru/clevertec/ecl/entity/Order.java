package ru.clevertec.ecl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.ecl.entity.base.BaseEntity;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate giftCertificate;

    private double cost;

    private String purchaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        if (!super.equals(o)) return false;

        return Double.compare(order.getCost(), getCost()) == 0 &&
                Objects.equals(getUser(), order.getUser()) &&
                Objects.equals(getGiftCertificate(), order.getGiftCertificate()) &&
                Objects.equals(getPurchaseDate(), order.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUser().getId(), getGiftCertificate().getId(), getCost(), getPurchaseDate());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                "user=" + user +
                ", giftCertificate=" + giftCertificate +
                ", cost=" + cost +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
