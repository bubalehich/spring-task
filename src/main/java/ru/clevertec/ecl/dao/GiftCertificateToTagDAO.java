package ru.clevertec.ecl.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.exception.DAOException;

@Repository
public class GiftCertificateToTagDAO extends AbstractDAO {

    private static final String DELETE_INDEXES_BY_GIFT_CERTIFICATE_QUERY
            = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id = ?";
    private static final String DELETE_INDEXES_BY_TAG_ID_QUERY = "DELETE FROM gift_certificate_tag WHERE tag_id = ?";
    private static final String CREATE_QUERY
            = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (?, ?)";


    public GiftCertificateToTagDAO(JdbcTemplate jdbcTemplate) {
        super(null, jdbcTemplate);
    }

    public int saveGiftCertificateToTag(int giftCertificateId, int tagId) {
        try {
            return jdbcTemplate.update(CREATE_QUERY, giftCertificateId, tagId);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    public int deleteByGiftCertificateId(int giftCertificateId) {
        try {
            return jdbcTemplate.update(DELETE_INDEXES_BY_GIFT_CERTIFICATE_QUERY, giftCertificateId);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    public int deleteByTagId(int tagId) {
        try {
            return jdbcTemplate.update(DELETE_INDEXES_BY_TAG_ID_QUERY, tagId);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }
}
