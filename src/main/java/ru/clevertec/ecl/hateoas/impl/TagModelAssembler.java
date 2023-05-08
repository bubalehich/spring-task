package ru.clevertec.ecl.hateoas.impl;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.api.controller.impl.TagController;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.hateoas.AbstractModelAssembler;

@Component
public class TagModelAssembler extends AbstractModelAssembler<Tag>
        implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {

    public TagModelAssembler() {
        super.setControllerClass(TagController.class);
    }
}
