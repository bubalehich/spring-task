package ru.clevertec.ecl.objectmother;

import ru.clevertec.ecl.entity.Tag;

import java.util.List;
import java.util.Map;

public class TagObjectMother {
    public static List<Tag> getTags() {
        return List.of(
                Tag.builder()
                        .name("Tag")
                        .build()
        );
    }

    public static Tag getTag() {
        return Tag.builder()
                .id(1)
                .name("Tag")
                .build();
    }

    public static Map<String, String> getSearchParams() {
        return Map.of("name", "Tag");
    }
}
