package ru.otus.homework.services.database;

import ru.otus.homework.models.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountService implements DbService<Account, Long> {
    private DbTemplate jdbcTemplate;

    public AccountService(DbTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(Account account) throws SQLException, IllegalAccessException {
        return jdbcTemplate.create(account);
    }

    @Override
    public Account load(Long id) throws SQLException {
        return jdbcTemplate.load(id, Account.class);
    }

    @Override
    public void update(Account account) throws SQLException, IllegalAccessException {
        jdbcTemplate.update(account);
    }

    @Override
    public List<Account> getAll() {
        return null;
    }
}
