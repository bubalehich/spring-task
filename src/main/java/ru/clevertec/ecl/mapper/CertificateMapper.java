package ru.clevertec.ecl.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.util.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(resultSet.getInt(Fields.ID));
        certificate.setName(resultSet.getString(Fields.NAME));
        certificate.setDescription(resultSet.getString(Fields.DESCRIPTION));
        certificate.setPrice(resultSet.getDouble(Fields.PRICE));
        certificate.setLastUpdateDate(new Timestamp(resultSet.getTimestamp(Fields.LAST_UPDATE_DATE)
                .getTime())
                .toLocalDateTime()
                .toString());
        certificate.setCreateDate(new Timestamp(resultSet.getTimestamp(Fields.CREATE_DATE).getTime())
                .toLocalDateTime()
                .toString());
        certificate.setId(resultSet.getInt(Fields.ID));

        return certificate;
    }
}
