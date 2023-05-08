package ru.clevertec.ecl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.ecl.entity.base.NamedEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gift_certificates")
public class GiftCertificate extends NamedEntity {

    private String description;

    private double price;

    private String createDate;

    private String lastUpdateDate;

    private int duration;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public GiftCertificate(String name, String description, double price, int duration) {
        super(name);
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificate that)) return false;
        if (!super.equals(o)) return false;

        return Double.compare(that.getPrice(), getPrice()) == 0 &&
                getDuration() == that.getDuration() &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCreateDate(), that.getCreateDate()) &&
                Objects.equals(getLastUpdateDate(), that.getLastUpdateDate()) &&
                Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription(), getPrice(), getCreateDate(), getLastUpdateDate(),
                getDuration(), getTags());
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
