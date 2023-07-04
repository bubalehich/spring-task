package ru.clevertec.ecl.objectmothers;

import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;

public class OrderObjectMother {
    public static Order getOrder() {
        return Order.builder()
                .id(3000)
                .cost(4.0)
                .purchaseDate("2023-07-04T15:49:36.0102754")
                .giftCertificate(
                        GiftCertificate.builder()
                                .id(10112)
                                .description("string")
                                .name("string")
                                .createDate("2023-07-04T13:04:59.2804217")
                                .lastUpdateDate("2023-07-04T13:04:59.2804217")
                                .price(4.0)
                                .duration(4)
                                .build())
                .build();
    }
}
