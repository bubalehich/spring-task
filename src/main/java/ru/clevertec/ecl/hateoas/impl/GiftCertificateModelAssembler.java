package ru.clevertec.ecl.hateoas.impl;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.api.controller.impl.GiftCertificateController;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.hateoas.AbstractModelAssembler;

@Component
public class GiftCertificateModelAssembler
        extends AbstractModelAssembler<GiftCertificate>
        implements RepresentationModelAssembler<GiftCertificate, EntityModel<GiftCertificate>> {

    public GiftCertificateModelAssembler() {
        super.setControllerClass(GiftCertificateController.class);
    }
}
