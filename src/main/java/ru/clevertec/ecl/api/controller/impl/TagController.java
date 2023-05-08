package ru.clevertec.ecl.api.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.api.controller.AbstractRestController;
import ru.clevertec.ecl.api.controller.RestHateoasController;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.hateoas.impl.TagModelAssembler;
import ru.clevertec.ecl.service.impl.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController extends AbstractRestController<Tag>
        implements RestHateoasController<Tag> {

    @Autowired
    public TagController(TagService service, TagModelAssembler assembler) {
        super(service, assembler);
        super.setControllerClass(TagController.class);
    }
}
