package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.ecl.base.AbstractIT;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.objectmothers.UserObjectMother;
import ru.clevertec.ecl.util.Criteria;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserServiceTest extends AbstractIT {
    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testGetByIdShouldReturnUser() {
        var expected = UserObjectMother.getUser();

        var actual = userService.getById(expected.getId());

        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void testGetByIdShouldThrowAnException() {
        assertThatThrownBy(() -> {
            userService.getById(99999);
        }).isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testGetByShouldThrowAnException() {
        assertThatThrownBy(() -> {
            userService.getBy(new ArrayList<Criteria>(), 1, 1, "asc", "id");
        }).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testCreateShouldThrowException() {
        assertThatThrownBy(() -> {
            userService.create(new User());
        }).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testUpdateThrowException() {
        assertThatThrownBy(() -> {
            userService.update(new User());
        }).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testDeleteShouldThrowAnException() {
        assertThatThrownBy(() -> {
            userService.delete(1);
        }).isInstanceOf(UnsupportedOperationException.class);
    }
}
