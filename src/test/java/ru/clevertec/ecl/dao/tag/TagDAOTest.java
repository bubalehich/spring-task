package ru.clevertec.ecl.dao.tag;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.clevertec.ecl.entity.Tag;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TagDAOTest {
    private EmbeddedDatabase embeddedDatabase;
    private JdbcTemplate jdbcTemplate;
    private TagDAO tagDAO;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        tagDAO = new TagDAO(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void testCreate() {
        var tagName = "SixthTag";
        var tag = new Tag();
        tag.setName(tagName);

        tagDAO.create(tag);
        var optionalCreatedTag = tagDAO.findByName(tagName);

        assertTrue(optionalCreatedTag.isPresent());
        var createdTag = optionalCreatedTag.get();
        assertNotNull(tag);
        assertNotEquals(0, createdTag.getId());
        assertEquals(tagName, createdTag.getName());
    }

    @Test
    void testFindAll() {
        var actual = tagDAO.findAll();

        assertTrue(actual.isPresent());
        assertEquals(5, actual.get().size());
    }

    @Test()
    void testFindByShouldThrowException() {
        assertThrowsExactly(UnsupportedOperationException.class, () ->
                tagDAO.findBy(new HashMap<>()));
    }

    @Test()
    void testUpdateShouldThrowException() {
        assertThrowsExactly(UnsupportedOperationException.class, () ->
                tagDAO.update(new Tag(1, "Oleg")));
    }

    @Test
    void testDeleteByExistingId() {
        var result = tagDAO.delete(4);

        assertEquals(1, result);
    }

    @Test
    void testDeleteByNonExistingId() {
        var result = tagDAO.delete(999);

        assertEquals(0, result);
    }

    @Test
    void findByCertificateId() {
        var optionalTags = tagDAO.findByCertificateId(1);

        assertTrue(optionalTags.isPresent());
        assertNotNull(optionalTags);
        assertEquals(2, optionalTags.get().size());
    }

    @Test
    void testFindByExistingId() {
        var certificate = tagDAO.findById(1);

        assertTrue(certificate.isPresent());
    }

    @Test
    void testFindByNonExistingId() {
        var certificate = tagDAO.findById(88);

        assertFalse(certificate.isPresent());
    }

    @Test
    void testFindByExistingName() {
        var actual = tagDAO.findByName("FirstTag");

        assertTrue(actual.isPresent());
    }

    @Test
    void testFindByNonExistingName() {
        var actual = tagDAO.findByName("NonExistingTag");

        assertFalse(actual.isPresent());
    }
}
