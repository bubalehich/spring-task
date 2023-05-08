package ru.clevertec.ecl.entity.association;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificateToTagId implements Serializable {

    @Column(name = "gift_certificate_id")
    private Integer giftCertificateId;

    @Column(name = "tag_id")
    private Integer tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateToTagId that)) return false;

        return Objects.equals(getGiftCertificateId(), that.getGiftCertificateId()) &&
                Objects.equals(getTagId(), that.getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGiftCertificateId(), getTagId());
    }
}
