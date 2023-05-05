package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.DAOInterface;
import ru.clevertec.ecl.dao.GiftCertificateToTagDAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.objectmother.GiftCertificateObjectMother;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @InjectMocks
    GiftCertificateService service;

    @Mock
    DAOInterface<GiftCertificate> giftCertificateDao;

    @Mock
    GiftCertificateToTagDAO giftCertificateToTagDao;

    @Mock
    TagService tagService;


    @Test
    void testGetAllFindNothing() {
        var expected = new ArrayList<GiftCertificate>();
        when(giftCertificateDao.findAll()).thenReturn(Optional.empty());

        var actual = service.getAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testGetAll() {
        var expected = GiftCertificateObjectMother.getGiftCertificates();
        when(giftCertificateDao.findAll()).thenReturn(Optional.of(GiftCertificateObjectMother.getGiftCertificates()));
        when(tagService.getByCertificateId(anyInt())).thenReturn(new ArrayList<>());

        var actual = service.getAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getById() {
    }

    @Test
    void getBy() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
    }

    @Test
    void save() {
    }
}