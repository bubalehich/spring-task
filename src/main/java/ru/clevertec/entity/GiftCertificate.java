package ru.clevertec.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GiftCertificate {

    private long id;

    private String name;

    private String description;

    private double price;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private int duration;

    private Set<Tag> tags;
}
