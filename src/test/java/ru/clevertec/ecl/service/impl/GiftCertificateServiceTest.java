package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.ecl.base.AbstractIT;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.objectmothers.GiftCertificateObjectMother;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class GiftCertificateServiceTest extends AbstractIT {
    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testGetByIdShouldReturnGiftCertificate() {
        var expected = GiftCertificateObjectMother.getGiftCertificate();

        var actual = giftCertificateService.getById(expected.getId());

        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void testGetByIdShouldThrowAnException() {
        assertThatThrownBy(() -> {
            giftCertificateService.getById(99999);
        }).isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testCreateGiftCertificateSuccess() {
        var input = GiftCertificateObjectMother.getGiftCertificateWithoutId();

        var actual = giftCertificateService.create(input);

        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void testCreateGiftCertificateShouldThrowException() {
        assertThatThrownBy(() -> {
            giftCertificateService.create(new GiftCertificate());
        }).isInstanceOf(RequestParamsNotValidException.class);
    }

    @Test
    void testUpdateShouldThrowAnException() {
        var giftCertificateWithNonExistingId = GiftCertificateObjectMother.getGiftCertificateWithoutId();
        giftCertificateWithNonExistingId.setId(99999999);

        assertThatThrownBy(() -> {
            giftCertificateService.update(giftCertificateWithNonExistingId);
        }).isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void testUpdateGiftCertificateShouldUpdateSuccess() {
        var input = giftCertificateService.getById(2);
        var randomPrice = new Random().nextDouble();
        input.setPrice(randomPrice);

        var actual = giftCertificateService.update(input);

        assertThat(actual.getPrice()).isEqualTo(randomPrice);
    }

    @Test
    void testUpdateGiftCertificateShouldThrowAnException() {
        var input = giftCertificateService.getById(2);
        var randomPrice = new Random().nextDouble();
        var randomDuration = new Random().nextInt();
        input.setPrice(randomPrice);
        input.setDuration(randomDuration);

        assertThatThrownBy(() -> {
            giftCertificateService.update(input);
        }).isInstanceOf(RequestParamsNotValidException.class);
    }

    @Test
    void testDeleteShouldDeleteSuccess() {
        var id = 1;
        giftCertificateService.delete(id);
        entityManager.flush();

        assertThatThrownBy(() -> {
            giftCertificateService.getById(id);
        }).isInstanceOf(ItemNotFoundException.class);
    }
}
