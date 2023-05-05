package ru.clevertec.ecl.api;

import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.util.Fields;

import java.util.List;

@RestController
@RequestMapping("api/tags")
public interface TagApi {
    @GetMapping
    List<Tag> getTags();

    @GetMapping("/{id}")
    Tag getTag(@PathVariable int id);

    @PostMapping
    Tag add(@RequestBody Tag tag);

    @DeleteMapping("/{id}")
    String delete(@PathVariable int id);

    @GetMapping("/find")
    Tag findTags(@RequestParam(name = Fields.NAME, required = false) String name);
}
