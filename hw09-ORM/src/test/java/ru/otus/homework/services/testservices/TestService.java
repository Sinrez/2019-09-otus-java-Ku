package ru.otus.homework.services.testservices;

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
}
