package ru.clevertec.ecl.api.controller;

import lombok.AllArgsConstructor;
import ru.clevertec.ecl.api.TagApi;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.util.Fields;
import ru.clevertec.ecl.validation.ValidationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class TagController implements TagApi {
    private final ServiceInterface<Tag> tagService;

    @Override
    public List<Tag> getTags() {
        return tagService.getAll();
    }

    @Override
    public Tag getTag(int id) {
        return tagService.getById(id);
    }

    @Override
    public Tag add(Tag tag) {
        ValidationUtil.validate(tag);

        return tagService.save(tag);
    }

    @Override
    public String delete(int id) {
        tagService.delete(id);

        return String.format("Tag with id %d has been deleted", id);
    }

    @Override
    public Tag findTags(String name) {
        return tagService.getBy(mapParams(name)).get(0);
    }

    private Map<String, String> mapParams(String name) {
        Map<String, String> params = new HashMap<>();
        if (name != null && !name.isEmpty())
            params.put(Fields.NAME, name);

        return params;
    }
}
