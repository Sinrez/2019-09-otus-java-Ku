package ru.otus.homework.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.cache.CacheImpl;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.services.database.DbService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class UserServiceTest {

    private DbService<User, Long> userService;
    private TestService testService;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(AddressDataSet.class)
                .addAnnotatedClass(PhoneDataSet.class)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder()
                .build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        testService = new TestService(this);
        expectedUser = generateUser();
        userService = new UserService(sessionFactory, new CacheImpl<Long, User>());
    }

    private User generateUser() {
        return new User(
                testService.generateString(),
                testService.generateNumeric(),
                new AddressDataSet(testService.generateString()),
                Collections.singleton(
                        new PhoneDataSet(testService.generateString())
                )
        );
    }

    @Test
    void create() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        Assertions.assertEquals(1L, expectedUser.getId());
    }

    @Test
    void load() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        User actualUser = userService.load(expectedUser.getId());
        testService.assertEquals(expectedUser, actualUser);
    }

    @Test
    void update() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        Long id = expectedUser.getId();
        expectedUser = new User(
                "update",
                testService.generateNumeric(),
                new AddressDataSet("update"),
                Collections.singleton(
                        new PhoneDataSet("update")
                )
        );
        expectedUser.setId(id);
        userService.update(expectedUser);
        User actualUser = userService.load(expectedUser.getId());
        testService.assertEquals(expectedUser, actualUser);
    }

    @Test
    void getAll() throws SQLException, IllegalAccessException {
        Map<Long, User> expectedUsers = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            User user = generateUser();
            expectedUsers.put(
                    userService.create(user),
                    user
            );
        }
        Map<Long, User> actualUsers = new HashMap<>();
        for (User user : userService.getAll()) {
            actualUsers.put(user.getId(), user);
        }
        Assertions.assertEquals(expectedUsers,actualUsers);
    }
}