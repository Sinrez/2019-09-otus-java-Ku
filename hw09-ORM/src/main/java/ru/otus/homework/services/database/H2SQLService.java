package ru.otus.homework.services.database;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

import static java.sql.DriverManager.getConnection;

public class H2SQLService implements SQLService {
    private Connection connection;
    private ParamService paramService;

    public H2SQLService() throws SQLException {
        connection = getConnection("jdbc:h2:mem:");
        connection.setAutoCommit(true);
        paramService = new ParamService();
    }

    @Override
    public long insertRow(String tableName, List<Param> params) throws SQLException {
        String names = paramService.getNamesString(params);
        String values = paramService.getValuesString(params);
        String sql = "INSERT INTO " + tableName + "(" + names + ") VALUES (" + values + ")";
        Savepoint savePoint = this.connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.executeUpdate();
            long id;
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                id = rs.getLong(1);
                System.out.println("inserted new row, id = " + id);
            }
            connection.commit();
            return id;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            this.connection.rollback(savePoint);
            throw ex;
        }


    }

    @Override
    public void execute(String sqlExpression) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sqlExpression)) {
            pst.executeUpdate();
        }
    }

    @Override
    public void updateRow(String tableName, List<Param> params, long id) throws SQLException {
        String updateParamsString = paramService.getUpdateParamsString(params);
        String sql = "UPDATE " + tableName + " SET " + updateParamsString + "WHERE ID = ?";
        Savepoint savePoint = this.connection.setSavepoint("savePointName");
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            pst.executeUpdate();
            connection.commit();
            System.out.println("update row, id = " + id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            this.connection.rollback(savePoint);
            throw ex;
        }
    }

    public <T> T selectRow(String tableName, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        String sql = "select * from " + tableName + " where id = ?";
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rsHandler.apply(rs);
            }
        }
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }
}
