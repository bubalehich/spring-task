package ru.clevertec.ecl.api.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.api.controller.AbstractRestController;
import ru.clevertec.ecl.api.controller.RestHateoasController;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.hateoas.impl.UserModelAssembler;
import ru.clevertec.ecl.service.impl.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractRestController<User> implements RestHateoasController<User> {

    @Autowired
    public UserController(UserService service, UserModelAssembler assembler) {
        super(service, assembler);
        super.setControllerClass(UserController.class);
    }

    @PutMapping("/{userId}/{giftCertificateId}")
    public ResponseEntity<?> update(@PathVariable int userId, @PathVariable int giftCertificateId) {
        EntityModel<User> em = assembler.toModel(((UserService) service).addCertificate(userId, giftCertificateId));

        return ResponseEntity.created(em.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(em);
    }
}