package ru.clevertec.dao.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.dao.AbstractDAO;
import ru.clevertec.entity.Tag;
import ru.clevertec.mapper.TagMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.clevertec.util.Fields.*;

@Repository
public class TagDAO extends AbstractDAO<Tag> {
    @Autowired
    public TagDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super("select * from tag",
                "delete from tag where id = ?",
                null,
                namedParameterJdbcTemplate,
                new SimpleJdbcInsert(Objects.requireNonNull(namedParameterJdbcTemplate.getJdbcTemplate().getDataSource()))
                        .withTableName(TAG_TABLE),
                new TagMapper());
    }

    @Override
    public void create(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(NAME, tag.getName());
        simpleJdbcInsert.execute(parameters);
    }

    @Override
    public void update(Tag tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Map<String, MapSqlParameterSource> buildQuery(Map<String, String> params) {
        StringBuilder query = new StringBuilder("SELECT * FROM tag");
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        String sortField = params.remove(SORT);
        String sortType = params.get(SORT_TYPE) == null ? SORT_ASC : params.remove(SORT_TYPE);
        String name = params.get(NAME) == null ? "" : params.remove(NAME);
        query.append(String.format(" WHERE %s LIKE :tag_name ", NAME));
        queryParams.addValue("tag_name", "%" + name + "%");
        if (sortField != null) {
            query.append(String.format(" ORDER BY :%s :%s", SORT, SORT_TYPE));
            queryParams.addValue(SORT, sortField).addValue(SORT_TYPE, sortType);
        }
        query.append(";");
        Map<String, MapSqlParameterSource> map = new HashMap<>();
        map.put(query.toString(), queryParams);
        return map;
    }
}