package ru.clevertec.ecl.objectmothers;

import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.List;

public class GiftCertificateObjectMother {
    public static GiftCertificate getGiftCertificateWithoutId() {
        return GiftCertificate.builder()
                .name("name")
                .description("description")
                .price(4.0)
                .duration(4)
                .build();
    }

    public static GiftCertificate getGiftCertificate() {
        return GiftCertificate.builder()
                .id(1)
                .name("name")
                .description("description")
                .price(4.0)
                .duration(4)
                .build();
    }

    public static List<GiftCertificate> getListOfGiftCertificates() {
        return List.of(
                GiftCertificate.builder()
                        .id(10112)
                        .name("name1")
                        .description("description1")
                        .price(1.0)
                        .duration(1)
                        .build(),
                GiftCertificate.builder()
                        .id(10113)
                        .name("name2")
                        .description("description2")
                        .price(2.0)
                        .duration(2)
                        .build());
    }
}
