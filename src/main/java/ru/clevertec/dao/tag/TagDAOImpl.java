package ru.clevertec.dao.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.clevertec.entity.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.clevertec.util.Fields.ID;
import static ru.clevertec.util.Fields.NAME;

@Component
public class TagDAOImpl implements TagDAO {

    private static final String FIND_ALL_QUERY = "select * from tag";
    private static final String DELETE_QUERY = "delete from tag where id = ?";

    private static final String TABLE_NAME = "tag";
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
        parameters.put(NAME, tag.getName());

        insertTag.execute(parameters);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, (resultSet, i) -> {
            Tag tag = new Tag();
            tag.setId(resultSet.getInt(ID));
            tag.setName(resultSet.getString(NAME));
            return tag;
        });
    }

    @Override
    public List<Tag> findBy(String criteria, String value) {
        return null;
    }

    @Override
    public List<Tag> findBy(Map<String, String> params) {
        return null;
    }

    @Override
    public void delete(Tag tag) {
        jdbcTemplate.update(DELETE_QUERY, tag.getId());
    }
}
