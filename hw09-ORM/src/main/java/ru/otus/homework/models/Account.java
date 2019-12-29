package ru.otus.homework.models;

import ru.otus.homework.annotations.Id;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    @Id
    private Long id;
    private String name;
    private BigDecimal rest;

    public Account(Long id, String name, BigDecimal rest) {
        this.id = id;
        this.name = name;
        this.rest = rest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(name, account.name) &&
                Objects.equals(rest, account.rest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rest);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rest=" + rest +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRest() {
        return rest;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }
}
