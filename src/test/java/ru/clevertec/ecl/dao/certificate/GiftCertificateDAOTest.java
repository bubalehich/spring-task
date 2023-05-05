package ru.clevertec.ecl.dao.certificate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateDAOTest {

    private EmbeddedDatabase embeddedDatabase;

    private JdbcTemplate jdbcTemplate;

    private GiftCertificateDAO giftCertificateDAO;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScript("schema.sql")
                .addScript("data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        giftCertificateDAO = new GiftCertificateDAO(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void testCreate() {
        var giftCertificate = GiftCertificate.builder()
                .name("ZhZh")
                .description("zhu zhu")
                .price(3)
                .duration(6)
                .build();

        giftCertificateDAO.create(giftCertificate);
        var optionalGiftCertificate = giftCertificateDAO.findByName(giftCertificate.getName());

        assertTrue(optionalGiftCertificate.isPresent());
        var createdGiftCertificate = optionalGiftCertificate.get();
        assertEquals(5, createdGiftCertificate.getId());
        assertEquals(giftCertificate.getName(), createdGiftCertificate.getName());
        assertEquals(giftCertificate.getPrice(), createdGiftCertificate.getPrice());
        assertNotNull(createdGiftCertificate.getCreateDate());
        assertNotNull(createdGiftCertificate.getLastUpdateDate());
    }

    @Test
    void testFindAll() {
        var actual = giftCertificateDAO.findAll();

        assertTrue(actual.isPresent());
        assertEquals(4, actual.get().size());
    }

    @Test
    void testUpdate() {
        var optionalExistingCertificate = giftCertificateDAO.findById(1);
        assertTrue(optionalExistingCertificate.isPresent());
        var existingCertificate = optionalExistingCertificate.get();
        existingCertificate.setName("Changed");
        existingCertificate.setDescription("Changed");
        existingCertificate.setPrice(2);

        giftCertificateDAO.update(existingCertificate);

        var optionalUpdatedCertificate = giftCertificateDAO.findById(1);
        assertTrue(optionalUpdatedCertificate.isPresent());
        var updatedCertificate = optionalUpdatedCertificate.get();
        assertNotNull(updatedCertificate);
        assertEquals("Changed", updatedCertificate.getName());
        assertEquals("Changed", updatedCertificate.getDescription());
        assertEquals(2, updatedCertificate.getPrice());
    }

    @Test
    void testDeleteByExistingId() {
        var result = giftCertificateDAO.delete(4);

        assertEquals(1, result);
    }

    @Test
    void testDeleteByNonExistingId() {
        var result = giftCertificateDAO.delete(999);

        assertEquals(0, result);
    }

    @Test
    void testFindByExistingId() {
        var certificate = giftCertificateDAO.findById(1);

        assertTrue(certificate.isPresent());
    }

    @Test
    void testFindByNonExistingId() {
        var certificate = giftCertificateDAO.findById(88);

        assertFalse(certificate.isPresent());
    }

    @Test
    void testFindByExistingName() {
        var actual = giftCertificateDAO.findByName("Cerf1");

        assertTrue(actual.isPresent());
    }

    @Test
    void testFindByNonExistingName() {
        var actual = giftCertificateDAO.findByName("NonExistingCertificate");

        assertFalse(actual.isPresent());
    }

    @Test
    void testFindByWithParams() {
        var params = new HashMap<String, String>();
        params.put("SORT", "create_date");
        params.put("ORDER", "ASC");
        params.put("NAME", "Cerf");
        params.put("DESCRIPTION", "desc");

        var optionalGiftCertificates = giftCertificateDAO.findBy(params);

        assertTrue(optionalGiftCertificates.isPresent());
        var certificates = optionalGiftCertificates.get();
        assertNotNull(certificates);
        assertEquals(4, certificates.size());
        assertEquals("Cerf1", certificates.get(0).getName());
        assertEquals("desc1", certificates.get(0).getDescription());
        assertEquals(13.00, certificates.get(0).getPrice());
    }
}
