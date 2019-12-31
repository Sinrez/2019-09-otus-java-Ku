package ru.otus.homework.services.database;

import ru.otus.homework.annotations.Id;
import ru.otus.homework.services.reflection.ReflectionService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class H2JdbcTemplate implements DbTemplate {

    private ReflectionService reflectionService;
    private SQLService sqlService;

    public H2JdbcTemplate(ReflectionService reflectionService, SQLService sqlService) throws SQLException {
        this.reflectionService = reflectionService;
        this.SQLService = sqlService;
    }

    public <T> long create(T objectData) throws SQLException, IllegalAccessException {
        String tableName = reflectionService.getClassName(objectData);
        List<Param> params = reflectionService.getFieldsExceptIdAsParamsExceptAnnotated(objectData, Id.class);
        return SQLService.insertRow(tableName, params);
    }

    public <T> void update(T objectData) throws IllegalAccessException, SQLException {
        String tableName = reflectionService.getClassName(objectData);
        List<Param> params = reflectionService.getFieldsExceptIdAsParamsExceptAnnotated(objectData, Id.class);
        long id = reflectionService.getId(objectData);
        SQLService.updateRow(tableName, params, id);
    }

    public <T> T load(long id, Class<T> clazz) throws SQLException {
        T result = SQLService.selectRow(clazz.getSimpleName(), id, resultSet -> {
                    try {
                        if (resultSet.next()) {
                            Constructor constructor = reflectionService.getConstructor(clazz);
                            Object[] constructorArgs = new Object[clazz.getDeclaredFields().length];

                            Field[] declaredFields = clazz.getDeclaredFields();
                            for (int i = 0; i < constructorArgs.length; i++) {
                                Field field = declaredFields[i];
                                field.setAccessible(true);
                                constructorArgs[i] = resultSet.getObject(field.getName());
                                field.setAccessible(false);
                            }
                            return (T) constructor.newInstance(constructorArgs);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        System.out.println("result:" + result);
        return result;
    }
}
