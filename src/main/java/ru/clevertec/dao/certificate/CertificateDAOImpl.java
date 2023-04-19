package ru.clevertec.dao.certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.clevertec.entity.GiftCertificate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CertificateDAOImpl implements CertificateDAO {

    private static final String TABLE_NAME = "gift_certificate";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_CREATE_DATE = "create_date";
    private static final String FIELD_LAST_UPDATE_DATE = "last_update_date";
    private static final String FIELD_DURATION = "duration";

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertCertificate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertCertificate
                = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource())).withTableName(TABLE_NAME);
    }

    @Override
    public void create(GiftCertificate certificate) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FIELD_NAME, certificate.getName());
        parameters.put(FIELD_DESCRIPTION, certificate.getDescription());
        parameters.put(FIELD_PRICE, certificate.getPrice());
        parameters.put(FIELD_CREATE_DATE, certificate.getCreateDate());
        parameters.put(FIELD_LAST_UPDATE_DATE, certificate.getLastUpdateDate());
        parameters.put(FIELD_DURATION, certificate.getDuration());

        insertCertificate.execute(parameters);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query("select * from gift_certificate", (resultSet, i) -> {
            GiftCertificate certificate = new GiftCertificate();
            certificate.setId(resultSet.getInt(FIELD_ID));
            certificate.setName(resultSet.getString(FIELD_NAME));
            certificate.setDescription(resultSet.getString(FIELD_DESCRIPTION));
            certificate.setPrice(resultSet.getDouble(FIELD_PRICE));
            certificate.setLastUpdateDate(new Timestamp(resultSet.getTimestamp(FIELD_LAST_UPDATE_DATE)
                    .getTime())
                    .toLocalDateTime());
            certificate.setCreateDate(new Timestamp(resultSet.getTimestamp(FIELD_CREATE_DATE).getTime())
                    .toLocalDateTime());
            certificate.setId(resultSet.getInt(FIELD_ID));

            return certificate;
        });
    }

    @Override
    public List<GiftCertificate> findBy(Map<String, String> params) {
        return null;
    }

    @Override
    public void delete(GiftCertificate certificate) {
        jdbcTemplate.update("delete from gift_certificate where id = ?", certificate.getId());
    }

    @Override
    public void update(GiftCertificate certificate) {
        jdbcTemplate.update("update gift_certificate set name = ?, description = ?, price = ?, create_date = ?, " +
                        "last_update_date = ?,description = ? where id = ?",
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                certificate.getDuration(),
                certificate.getId());
    }
}
