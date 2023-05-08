package ru.clevertec.ecl.entity.association;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "GiftCertificate_tag")
@Table(name = "gift_certificate_tag")
public class GiftCertificateToTag {

    @EmbeddedId
    private GiftCertificateToTagId id;

    @ManyToOne
    @MapsId("giftCertificateId")
    private GiftCertificate giftCertificate;

    @ManyToOne
    @MapsId("tagId")
    private Tag tag;

    public GiftCertificateToTag(GiftCertificate giftCertificate, Tag tag) {
        this.giftCertificate = giftCertificate;
        this.tag = tag;
        this.id = new GiftCertificateToTagId(
                giftCertificate.getId(),
                tag.getId()
        );
    }
}
