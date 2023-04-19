package ru.clevertec.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.clevertec.dao.TagDAO;
import ru.clevertec.entity.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class TagDAOImpl implements TagDAO {

    private static final String TABLE_NAME = "tag";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_ID = "id";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertTag;

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertTag
                = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource())).withTableName(TABLE_NAME);
    }

    @Override
    public void create(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FIELD_NAME, tag.getName());

        insertTag.execute(parameters);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("select * from tag", (resultSet, i) -> {
            Tag tag = new Tag();
            tag.setId(resultSet.getInt(FIELD_ID));
            tag.setName(resultSet.getString(FIELD_NAME));
            return tag;
        });
    }

    @Override
    public List<Tag> findBy(String criteria, String value) {
        return null;
    }

    @Override
    public void delete(Tag tag) {
        jdbcTemplate.update("delete from tag where id = ?", tag.getId());
    }
}
