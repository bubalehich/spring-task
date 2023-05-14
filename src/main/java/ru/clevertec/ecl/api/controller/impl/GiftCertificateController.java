package ru.clevertec.ecl.api.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.api.controller.AbstractRestController;
import ru.clevertec.ecl.api.controller.RestHateoasController;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.hateoas.impl.GiftCertificateModelAssembler;
import ru.clevertec.ecl.service.impl.GiftCertificateService;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController extends AbstractRestController<GiftCertificate>
        implements RestHateoasController<GiftCertificate> {

    @Autowired
    public GiftCertificateController(GiftCertificateService service, GiftCertificateModelAssembler assembler) {
        super(service, assembler);
        super.setControllerClass(GiftCertificateController.class);
    }
}
