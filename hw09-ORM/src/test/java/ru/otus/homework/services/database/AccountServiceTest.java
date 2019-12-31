package ru.otus.homework.services.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Account;
import ru.otus.homework.services.reflection.ReflectionServiceImpl;
import ru.otus.homework.services.testservices.TestService;

import java.math.BigDecimal;
import java.sql.SQLException;

class AccountServiceTest {
    private DbService<Account, Long> accountService;
    private TestService testService;
    private Account expectedAccount;


    @BeforeEach
    void setUp() throws SQLException {
        testService = new TestService(this);
        H2SQLService h2SQLService = new H2SQLService();
        h2SQLService
                .execute(
                        "create table account(id bigint(20) not null auto_increment, name varchar(255),  rest number)"
                );
        accountService = new AccountService(new H2JdbcTemplate(new ReflectionServiceImpl(), h2SQLService));
        expectedAccount = new Account(
                1L,
                testService.generateString(),
                BigDecimal.valueOf(testService.generateNumeric())
        );
    }

    @Test
    void create() throws SQLException, IllegalAccessException {
        long actualId = accountService.create(expectedAccount);
        Assertions.assertEquals(expectedAccount.getId(), actualId);
    }

    @Test
    void load() throws SQLException, IllegalAccessException {
        accountService.create(expectedAccount);
        Account actualAccount = accountService.load(expectedAccount.getId());
        Assertions.assertEquals(expectedAccount, actualAccount);

    }

    @Test
    void update() throws SQLException, IllegalAccessException {
        accountService.create(expectedAccount);
        expectedAccount = new Account(
                expectedAccount.getId(),
                testService.generateString(),
                BigDecimal.valueOf(testService.generateNumeric())
        );
        accountService.update(expectedAccount);
        Account actualAccount = accountService.load(expectedAccount.getId());
        Assertions.assertEquals(expectedAccount, actualAccount);

    }
}