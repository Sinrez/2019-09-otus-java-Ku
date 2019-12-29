package ru.otus.homework.services.reflection;

import ru.otus.homework.annotations.Id;
import ru.otus.homework.services.database.Param;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionServiceImpl implements ReflectionService {
    @Override
    public <T> String getClassName(T objectData) {
        return objectData.getClass().getSimpleName();
    }

    @Override
    public <T> List<Param> getFieldsExceptIdAsParamsExceptAnnotated(T objectData, Class annotationClass) throws IllegalAccessException {
        List<Param> params = new ArrayList<>();
        for (Field declaredField : objectData.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (declaredField.getAnnotation(annotationClass) == null) {
                params.add(
                        new Param(
                                declaredField.getName(),
                                String.valueOf(declaredField.get(objectData)),
                                declaredField.getType().getSimpleName()
                        )
                );
            }
            declaredField.setAccessible(false);
        }
        return params;
    }

    @Override
    public<T> long getId(T objectData) throws IllegalAccessException {
        long id = 0;
        for (Field declaredField : objectData.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (declaredField.getAnnotation(Id.class) != null) {
                               id = Long.valueOf(String.valueOf(declaredField.get(objectData)));
            }
            declaredField.setAccessible(false);
        }
        return id;
    }

    @Override
    public Constructor getConstructor(Class clazz) throws NoSuchMethodException {
        Field[] declaredFields = clazz.getDeclaredFields();

        Class[] paramTypes = new Class[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            paramTypes[i] = declaredFields[i].getType();
        }

        return clazz.getDeclaredConstructor(paramTypes);
    }
}