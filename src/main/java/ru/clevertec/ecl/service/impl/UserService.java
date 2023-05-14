package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.impl.GenericDAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.service.ServiceInterface;
import ru.clevertec.ecl.util.Criteria;
import ru.clevertec.ecl.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Service
public class UserService implements ServiceInterface<User> {

    private final GenericDAO<User> userDAO;

    private final GenericDAO<GiftCertificate> certificateDAO;

    @Autowired
    public UserService(GenericDAO<User> userDAO, GenericDAO<GiftCertificate> certificateDAO) {
        this.userDAO = userDAO;
        this.certificateDAO = certificateDAO;
        this.userDAO.setClazz(User.class);
        this.certificateDAO.setClazz(GiftCertificate.class);
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(int id) {
        return userDAO.getById(id).orElseThrow(()
                -> new ItemNotFoundException(String.format("User id %d not found", id))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Pagination<User> getAll(int page, int size, String sort, String sortMode) {
        return userDAO.getAll(new Pagination<>(size, page, 0), new Pair<>(sort, sortMode));
    }

    @Transactional(readOnly = true)
    @Override
    public Pagination<User> getBy(List<Criteria> criteria, int page, int size, String sort, String sortMode) {
        throw new UnsupportedOperationException("Search operation is not allowed for user");
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("Create operation for User is not supported");
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException("Update operation for User is not supported");
    }

    public User addCertificate(int userId, int giftCertificateId) {
        User user = userDAO.getById(userId).orElseThrow(()
                -> new ItemNotFoundException(String.format("User with id %d not found", userId))
        );

        GiftCertificate giftCertificate = certificateDAO.getById(giftCertificateId).orElseThrow(()
                -> new ItemNotFoundException(String.format("GiftCertificate with id %d not found", userId))
        );

        Order order = new Order(user, giftCertificate, giftCertificate.getPrice(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        user.addOrder(order);

        return userDAO.save(user).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Delete operation for User is not supported.");
    }
}
