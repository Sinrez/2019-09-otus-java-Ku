package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import ru.otus.homework.model.User;

import java.math.BigDecimal;

public class TestService {
    private Object testClass;

    public TestService(Object testClass) {
        this.testClass = testClass;
    }

    public String generateString() {
        return testClass.getClass().getSimpleName() + generateDigit();
    }

    public int generateNumeric() {
        return generateDigit();
    }

    private int generateDigit() {
        return generateInt();
    }

    private int generateInt() {
        return (int) (Math.random() * 10000);
    }

    public BigDecimal generateBigDecimal() {
        return BigDecimal.valueOf(generateInt());
    }

    public void assertEquals(User expectedUser, User actualUser) {
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getPhoneDataSet(), actualUser.getPhoneDataSet());
        Assertions.assertEquals(expectedUser.getAddressDataSet().getId(), actualUser.getAddressDataSet().getId());
        Assertions.assertEquals(expectedUser.getAddressDataSet().getStreet(), actualUser.getAddressDataSet().getStreet());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
    }
}
