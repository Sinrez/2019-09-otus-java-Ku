package ru.otus.homework.services.database;

import ru.otus.homework.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserService implements DbService<User, Long> {
    private DbTemplate jdbcTemplate;

    public UserService(DbTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(User user) throws SQLException, IllegalAccessException {
        return jdbcTemplate.create(user);
    }

    @Override
    public User load(Long id) throws SQLException {
        return jdbcTemplate.load(id, User.class);
    }

    @Override
    public void update(User user) throws SQLException, IllegalAccessException {
        jdbcTemplate.update(user);
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
