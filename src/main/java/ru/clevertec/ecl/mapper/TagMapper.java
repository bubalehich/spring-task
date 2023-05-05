package ru.clevertec.ecl.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.util.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getInt(Fields.ID));
        tag.setName(resultSet.getString(Fields.NAME));
        return tag;
    }
}
