package ru.clevertec.ecl.objectmothers;

import ru.clevertec.ecl.entity.User;

public class UserObjectMother {
    public static User getUser() {
        return User.builder()
                .id(1)
                .firstName("Riley")
                .lastName("Stokes")
                .build();
    }
}
