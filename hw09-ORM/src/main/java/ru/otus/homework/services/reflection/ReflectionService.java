package ru.otus.homework.services.reflection;

import ru.otus.homework.services.database.Param;

import java.lang.reflect.Constructor;
import java.util.List;

public interface ReflectionService {
    <T> String getClassName(T objectData);

    <T> List<Param> getFieldsExceptIdAsParamsExceptAnnotated(T objectData, Class annotationClass) throws IllegalAccessException;

    <T> long getId(T objectData) throws IllegalAccessException;

    Constructor getConstructor(Class clazz) throws NoSuchMethodException;
}
