package ru.clevertec.ecl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.GenericDAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.service.ServiceInterface;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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

    @Override
    public User getById(int id) {
        User user = userDAO.getById(id);
        if (user == null) {
            throw new ItemNotFoundException("User (id=" + id + ") not found");
        }

        return user;
    }

    @Override
    public Pagination<User> getAll(int page, int size, String sort, String sortMode) {
        return userDAO.getAll(new Pagination<>(size, page, 0), new Pair<>(sort, sortMode));
    }

    @Override
    public Pagination<User> getBy(Map<String, Pair<String, String>> filterParams, Pair<String, String> sortParams, int page, int size) {
        return userDAO.getBy(filterParams, sortParams, new Pagination<>(page, size, 0));
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("Create operation for User is not supported");
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException("Update operation for User is not supported");
    }

    public User addCertificate(GiftCertificate certificate, int id) {
        if (certificate == null || !certificate.equals(certificateDAO.getById(certificate.getId()))) {
            throw new ItemNotFoundException("Given certificate not found");
        }
        User user = userDAO.getById(id);
        if (user == null) {
            throw new ItemNotFoundException("User (id=" + id + ") not found");
        }
        Order order = new Order(user, certificate, certificate.getPrice(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        user.addOrder(order);

        return userDAO.save(user);
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Delete operation for User is not supported.");
    }
}
