package ru.clevertec.ecl.dao.certificate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.HashMap;
import java.util.Optional;

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
    void create() {
        var giftCertificate = new GiftCertificate();
        giftCertificate.setName("ZhZh");
        giftCertificate.setDescription("zhu zhu");
        giftCertificate.setPrice(3);
        giftCertificate.setDuration(6);
        giftCertificateDAO.create(giftCertificate);

        var giftCertificate1 = giftCertificateDAO.findByName("ZhZh");

        Assertions.assertTrue(giftCertificate1.isPresent());
        giftCertificate = giftCertificate1.get();
        assertNotNull(giftCertificate);
        assertNotEquals(0, giftCertificate.getId());
        assertEquals("ZhZh", giftCertificate.getName());
        assertEquals("zhu zhu", giftCertificate.getDescription());
        assertEquals(3, giftCertificate.getPrice());
        assertEquals(6, giftCertificate.getDuration());
    }

    @Test
    void findAll() {
        assertTrue(giftCertificateDAO.findAll().isPresent());
        assertEquals(3, giftCertificateDAO.findAll().get().size());
    }

    @Test
    void update() {
        Optional<GiftCertificate> certificate = giftCertificateDAO.findById(1);

        assertTrue(certificate.isPresent());
        var giftCertificate = certificate.get();
        giftCertificate.setName("Zazazazaz");
        giftCertificate.setDescription("Eheheheh");
        giftCertificate.setPrice(2);
        giftCertificate.setDuration(4);
        giftCertificateDAO.update(giftCertificate);

        var optionalChanged = giftCertificateDAO.findById(1);

        assertTrue(optionalChanged.isPresent());
        var changed = optionalChanged.get();
        assertNotNull(changed);
        assertEquals("Zazazazaz", changed.getName());
        assertEquals("Eheheheh", changed.getDescription());
        assertEquals(2, changed.getPrice());
        assertEquals(4, changed.getDuration());
    }

    @Test
    void delete() {
        assertEquals(1, giftCertificateDAO.delete(1));
        assertEquals(0, giftCertificateDAO.delete(999));
    }

    @Test
    void findById() {
        assertTrue(giftCertificateDAO.findById(1).isPresent());
        assertFalse(giftCertificateDAO.findById(88).isPresent());
    }

    @Test
    void findByName() {
        assertTrue(giftCertificateDAO.findByName("Cerf1").isPresent());
        assertFalse(giftCertificateDAO.findByName("NonExistingCertificate").isPresent());
    }

    @Test
    void findBySortByName() {
        var params = new HashMap<String, String>();
        params.put("SORT", "create_date");
        params.put("ORDER", "ASC");
        params.put("NAME", "Cerf");
        params.put("DESCRIPTION", "desc");

        var optionalGiftCertificates = giftCertificateDAO.findBy(params);

        assertTrue(optionalGiftCertificates.isPresent());
        var certificates = optionalGiftCertificates.get();
        assertNotNull(certificates);
        assertEquals(3, certificates.size());
        assertEquals("Cerf1", certificates.get(0).getName());
        assertEquals("desc1", certificates.get(0).getDescription());
        assertEquals(12.50, certificates.get(0).getPrice());
        assertEquals(1, certificates.get(0).getDuration());
    }
}
