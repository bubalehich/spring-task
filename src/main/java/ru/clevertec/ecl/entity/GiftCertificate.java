package ru.clevertec.ecl.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {

    private int id;

    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotBlank(message = "Description can't be empty")
    private String description;

    @Positive(message = "Price should be positive")
    private double price;

    private String createDate;

    private String lastUpdateDate;

    @Positive(message = "Duration should be positive")
    private int duration;

    private Set<@NotNull Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificate)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                duration == that.duration &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                createDate.equals(that.createDate) &&
                lastUpdateDate.equals(that.lastUpdateDate) &&
                tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, createDate, lastUpdateDate, duration, tags);
    }
}
