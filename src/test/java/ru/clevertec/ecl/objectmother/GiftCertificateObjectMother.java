package ru.clevertec.ecl.objectmother;

import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.List;

public class GiftCertificateObjectMother {

    public static GiftCertificate getGiftCertificate() {
        return GiftCertificate.builder()
                .id(1)
                .name("name")
                .price(5.5)
                .description("description")
                .build();
    }

    public static List<GiftCertificate> getGiftCertificates() {
        return List.of(
                getGiftCertificate()
        );
    }
}
