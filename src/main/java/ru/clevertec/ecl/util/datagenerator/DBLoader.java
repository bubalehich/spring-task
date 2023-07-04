package ru.clevertec.ecl.util.datagenerator;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.impl.GenericDAO;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.entity.User;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("pop_db")
@Transactional
public class DBLoader implements ApplicationRunner {

    private final GenericDAO<Tag> tagDAO;
    private final GenericDAO<User> userDAO;
    private final GenericDAO<GiftCertificate> giftCertificateDAO;
    private final Faker faker = new Faker();
    private static final int USERS = 1000;
    private static final int TAGS = 1000;
    private static final int TAGS_PER_GC = 10;
    private static final int ORDERS_PER_USER = 3;
    private static final int GCS = TAGS * TAGS_PER_GC;

    @Autowired
    public DBLoader(GenericDAO<Tag> tagDAO, GenericDAO<User> userDAO, GenericDAO<GiftCertificate> giftCertificateDAO) {
        this.tagDAO = tagDAO;
        this.userDAO = userDAO;
        this.giftCertificateDAO = giftCertificateDAO;
        tagDAO.setClazz(Tag.class);
        userDAO.setClazz(User.class);
        giftCertificateDAO.setClazz(GiftCertificate.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        List<Tag> tags = loadTags();
//        List<GiftCertificate> gcs = loadGCs(tags);
//        List<User> users = loadUsers(gcs);
    }

    public List<Tag> loadTags() {
        List<Tag> tags = new ArrayList<>(TAGS);
        for (int i = 0; i < TAGS; i++) {
            Tag tag = new Tag(faker.internet().slug());
            tags.add(tag);
            tagDAO.save(tag);
        }
        return tags;
    }

    public List<GiftCertificate> loadGCs(List<Tag> tags) {
        List<GiftCertificate> gcs = new ArrayList<>(GCS);

        for (int i = 0; i < GCS; i++) {
            GiftCertificate gc = new GiftCertificate(
                    faker.funnyName().name(),
                    faker.yoda().quote(),
                    faker.number().randomDouble(5, 1, 1000),
                    faker.number().numberBetween(1, 100));
            gcs.add(gc);
        }
        for (GiftCertificate gc : gcs) {
            for (int i = 0; i < TAGS_PER_GC; i++) {
                gc.addTag(tags.get(faker.number().numberBetween(0, tags.size() - 1)));
                giftCertificateDAO.save(gc);
            }
        }
        return gcs;
    }

    public List<User> loadUsers(List<GiftCertificate> gcs) {
        List<User> users = new ArrayList<>(USERS);
        for (int i = 0; i < USERS; i++) {
            users.add(new User(faker.name().firstName(), faker.name().lastName()));
        }
        for (User user : users) {
            for (int i = 0; i < ORDERS_PER_USER; i++) {
                user.addOrder(new Order(user, gcs.get(faker.number().numberBetween(0, gcs.size() - 1))));
            }
            userDAO.save(user);
        }
        return users;
    }
}
