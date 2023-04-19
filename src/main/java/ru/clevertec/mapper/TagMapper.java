package ru.clevertec.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.util.Fields.ID;
import static ru.clevertec.util.Fields.NAME;

public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getInt(ID));
        tag.setName(resultSet.getString(NAME));
        return tag;
    }
}
