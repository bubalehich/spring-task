package ru.clevertec.ecl.objectmothers;

import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.pagination.Pagination;

import java.util.List;

public class TagObjectMother {
    public static Tag getTag() {
        return Tag.builder()
                .id(1)
                .name("Tag name")
                .build();
    }

    public static List<Tag> getTagList() {
        return List.of(
                Tag.builder()
                        .id(1138)
                        .name("Tag name")
                        .build(),
                Tag.builder()
                        .id(1119)
                        .name("Tag name too")
                        .build());
    }

    public static Tag getTagWithoutId() {
        return Tag.builder()
                .name("Tag name")
                .build();
    }

    public static Pagination<Tag> getTagPagination() {
        return new Pagination<Tag>(2, 1, 1);
    }
}
