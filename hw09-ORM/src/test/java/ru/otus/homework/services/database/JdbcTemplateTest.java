package ru.otus.homework.services.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Account;
import ru.otus.homework.models.User;
import ru.otus.homework.services.reflection.ReflectionServiceImpl;
import ru.otus.homework.services.testservices.TestService;

import java.sql.SQLException;

class JdbcTemplateTest {
    private DbTemplate jdbcTemplate;
    private TestService testService;
    private User expectedUser;
    private Account expectedAccount;

    @BeforeEach
    void setUp() throws SQLException {
        testService = new TestService(this);
        SQLService h2SQLService = new H2SQLService();
        h2SQLService.execute("create table user(id long auto_increment, name varchar(50),  age int(3))");
        h2SQLService.execute(
                "create table account(id bigint(20) not null auto_increment, name varchar(255),  rest number)"
        );
        jdbcTemplate = new H2JdbcTemplate(
                new ReflectionServiceImpl(),
                h2SQLService
        );
        expectedUser = new User(
                1L,
                testService.generateString(),
                testService.generateNumeric()
        );
        expectedAccount = new Account(
                1L,
                testService.generateString(),
                testService.generateBigDecimal()
        );
    }

    @Test
    void createUser() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expectedUser);
        Assertions.assertEquals(expectedUser.getId(), id);
    }

    @Test
    void updateUser() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expectedUser);
        Assertions.assertEquals(expectedUser.getId(), id);

        User actualUser = jdbcTemplate.load(expectedUser.getId(), User.class);
        Assertions.assertEquals(expectedUser, actualUser);
        expectedUser = new User(
                actualUser.getId(),
                testService.generateString(),
                testService.generateNumeric()
        );
        jdbcTemplate.update(expectedUser);
        actualUser = jdbcTemplate.load(expectedUser.getId(), User.class);
        Assertions.assertEquals(expectedUser, actualUser);

    }

    @Test
    void loadUser() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expectedUser);
        Assertions.assertEquals(expectedUser.getId(), id);

        User actualUser = jdbcTemplate.load(1, User.class);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void loadAccount() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expectedAccount);
        Assertions.assertEquals(expectedAccount.getId(), id);

        Account actualAccount = jdbcTemplate.load(1, Account.class);
        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void createAccount() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expectedAccount);
        Assertions.assertEquals(expectedAccount.getId(), id);
    }

    @Test
    void updateAccount() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expectedAccount);
        Assertions.assertEquals(expectedAccount.getId(), id);

        Account actualAccount = jdbcTemplate.load(expectedAccount.getId(), Account.class);
        Assertions.assertEquals(expectedAccount, actualAccount);
        expectedAccount = new Account(
                actualAccount.getId(),
                testService.generateString(),
                testService.generateBigDecimal()
        );
        jdbcTemplate.update(expectedAccount);
        actualAccount = jdbcTemplate.load(expectedAccount.getId(), Account.class);
        Assertions.assertEquals(expectedAccount, actualAccount);
    }
}