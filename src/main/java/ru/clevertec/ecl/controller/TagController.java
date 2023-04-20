package ru.clevertec.ecl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.validation.ValidationUtil;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.service.ServiceInterface;
import ru.clevertec.ecl.util.Fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TagController {
    private final ServiceInterface<Tag> tagService;

    @Autowired
    public TagController(ServiceInterface<Tag> tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return tagService.getAll();
    }

    @GetMapping("/tags/{id}")
    public Tag getTag(@PathVariable int id) {
        return tagService.getById(id);
    }

    @PostMapping("/tags")
    public Tag add(@RequestBody Tag tag) {
        ValidationUtil.validate(tag);

        return tagService.save(tag);
    }

    @DeleteMapping("/tags/{id}")
    public String delete(@PathVariable int id) {
        tagService.delete(id);

        return String.format("Tag with id %d has been deleted", id);
    }

    @GetMapping("/tags/find")
    public Tag findTags(@RequestParam(name = Fields.NAME, required = false) String name) {
        return tagService.getBy(mapParams(name)).get(0);
    }

    private Map<String, String> mapParams(String name) {
        Map<String, String> params = new HashMap<>();
        if (name != null && !name.isEmpty())
            params.put(Fields.NAME, name);

        return params;
    }
}
