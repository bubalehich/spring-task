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
    void create() {
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
    void findAll() {
        assertTrue(tagDAO.findAll().isPresent());
        assertEquals(5, tagDAO.findAll().get().size());
    }

    @Test
    void findBy() {
        assertThrows(UnsupportedOperationException.class, () ->
                tagDAO.findBy(new HashMap<>()));
    }

    @Test()
    void update() {
        assertThrows(UnsupportedOperationException.class, () ->
                tagDAO.update(new Tag(1, "Oleg")));
    }

    @Test
    void delete() {
        assertEquals(1, tagDAO.delete(4));
        assertEquals(0, tagDAO.delete(999));
    }

    @Test
    void findByCertificateId() {
        var optionalTags = tagDAO.findByCertificateId(1);

        assertTrue(optionalTags.isPresent());
        assertNotNull(optionalTags);
        assertEquals(2, optionalTags.get().size());
    }

    @Test
    void findById() {
        assertTrue(tagDAO.findById(1).isPresent());
        assertFalse(tagDAO.findById(88).isPresent());
    }

    @Test
    void findByName() {
        assertTrue(tagDAO.findByName("FirstTag").isPresent());
        assertFalse(tagDAO.findByName("NonExistingTag").isPresent());
    }
}
