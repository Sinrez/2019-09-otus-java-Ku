package ru.otus.homework.services.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.User;
import ru.otus.homework.services.reflection.ReflectionServiceImpl;
import ru.otus.homework.services.testservices.TestService;

import java.sql.SQLException;

class UserServiceTest {

    private DbService<User,Long> userService;
    private TestService testService;
    private User expectedUser;

    @BeforeEach
    void setUp() throws SQLException {
        SQLService h2SQLService = new H2SQLService();
        h2SQLService.execute("create table user(id long auto_increment, name varchar(50),  age int(3))");
        testService = new TestService(this);
        userService = new UserService(
                new H2JdbcTemplate(
                        new ReflectionServiceImpl(),
                        h2SQLService
                )
        );
        expectedUser = new User(
                1L,
                testService.generateString(),
                testService.generateNumeric()
        );
    }

    @Test
    void create() throws SQLException, IllegalAccessException {
        long actualId = userService.create(expectedUser);
        Assertions.assertEquals(expectedUser.getId(), actualId);
    }

    @Test
    void load() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        User actualUser = userService.load(expectedUser.getId());
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void update() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        expectedUser  = new User(
                expectedUser.getId(),
                testService.generateString(),
                testService.generateNumeric()
        );
        userService.update(
                expectedUser
        );
        User actualUser = userService.load(expectedUser.getId());
        Assertions.assertEquals(expectedUser, actualUser);
    }
}