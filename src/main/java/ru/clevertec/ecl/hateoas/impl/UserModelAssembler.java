package ru.clevertec.ecl.hateoas.impl;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.api.controller.impl.UserController;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.hateoas.AbstractModelAssembler;

@Component
public class UserModelAssembler extends AbstractModelAssembler<User>
        implements RepresentationModelAssembler<User, EntityModel<User>> {

    public UserModelAssembler() {
        super.setControllerClass(UserController.class);
    }
}
