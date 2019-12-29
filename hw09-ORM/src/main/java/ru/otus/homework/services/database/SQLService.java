package ru.otus.homework.services.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public interface SQLService {

    long insertRow(String tableName, List<Param> params) throws SQLException;

    void execute(String sqlExpression) throws SQLException;

    void updateRow(String tableName, List<Param> fields, long id) throws SQLException;

    <T> T selectRow(String tableName, long id, Function<ResultSet, T> rsHandler) throws SQLException;

    void close() throws SQLException;
}
