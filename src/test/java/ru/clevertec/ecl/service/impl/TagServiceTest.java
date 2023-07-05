package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.ecl.base.AbstractIT;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.objectmothers.TagObjectMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TagServiceTest extends AbstractIT {
    @Autowired
    private TagService tagService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testGetByIdShouldReturnTag() {
        var expected = TagObjectMother.getTag();

        var actual = tagService.getById(expected.getId());

        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void testGetByIdShouldThrowAnException() {
        assertThatThrownBy(() -> {
            tagService.getById(99999);
        }).isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testCreateTagSuccess() {
        var input = TagObjectMother.getTagWithoutId();

        var actual = tagService.create(input);

        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void testCreateTagShouldThrowAnException() {
        assertThatThrownBy(() -> {
            tagService.create(new Tag());
        }).isInstanceOf(RequestParamsNotValidException.class);
    }

    @Test
    void testUpdateTagShouldThrowAnException() {
        assertThatThrownBy(() -> {
            tagService.update(new Tag());
        }).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testDeleteTagShouldThrowAnException() {
        assertThatThrownBy(() -> {
            tagService.delete(999999);
        }).isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testDeleteShouldDeleteSuccess() {
        var id = 1;
        tagService.delete(id);
        entityManager.flush();

        assertThatThrownBy(() -> {
            tagService.getById(id);
        }).isInstanceOf(ItemNotFoundException.class);
    }
}
