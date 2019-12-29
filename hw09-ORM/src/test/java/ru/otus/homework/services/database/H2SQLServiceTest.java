package ru.otus.homework.services.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.annotations.Id;
import ru.otus.homework.models.User;
import ru.otus.homework.services.reflection.ReflectionService;
import ru.otus.homework.services.reflection.ReflectionServiceImpl;
import ru.otus.homework.services.testservices.TestService;

import java.sql.SQLException;

class H2SQLServiceTest {
    private H2SQLService h2DbService;
    private ReflectionService reflectionService;
    private TestService testService;
    private User expected;

    @AfterEach
    void tearDown() throws SQLException {
        h2DbService.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        testService = new TestService(this);
        h2DbService = new H2SQLService();
        reflectionService = new ReflectionServiceImpl();
        expected = new User(
                1L,
                testService.generateString(),
                testService.generateNumeric()
        );
        h2DbService.execute("create table user(id long auto_increment, name varchar(50),  age int(3))");
    }

    @Test
    void insertRow() throws IllegalAccessException, SQLException {
        long userId = h2DbService.insertRow("User", reflectionService.getFieldsExceptIdAsParamsExceptAnnotated(expected, Id.class));
        Assertions.assertEquals(1, userId);
    }


    @Test
    void selectRow() throws SQLException, IllegalAccessException {
        long userId = h2DbService.insertRow("User", reflectionService.getFieldsExceptIdAsParamsExceptAnnotated(expected, Id.class));
        User actual = h2DbService.selectRow("User", userId, resultSet -> {
                    try {
                        if (resultSet.next()) {
                            return new User(
                                    resultSet.getLong("id"),
                                    resultSet.getString("name"),
                                    resultSet.getInt("age")
                            );
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateRow() throws SQLException, IllegalAccessException {
        long userId = h2DbService.insertRow("User", reflectionService.getFieldsExceptIdAsParamsExceptAnnotated(expected, Id.class));
        expected.setAge((int) Math.random());
        expected.setName(String.valueOf(Math.random()));
        h2DbService.updateRow("User", reflectionService.getFieldsExceptIdAsParamsExceptAnnotated(expected, Id.class), expected.getId());
        User actual = h2DbService.selectRow("User", userId, resultSet -> {
                    try {
                        if (resultSet.next()) {
                            return new User(
                                    resultSet.getLong("id"),
                                    resultSet.getString("name"),
                                    resultSet.getInt("age")
                            );
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        Assertions.assertEquals(expected, actual);
    }
}