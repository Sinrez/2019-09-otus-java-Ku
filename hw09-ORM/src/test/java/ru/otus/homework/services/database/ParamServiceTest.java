package ru.otus.homework.services.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ParamServiceTest {
    private ParamService paramService = new ParamService();
    private List<Param> testParams;

    @BeforeEach
    void setUp() {
        testParams = new ArrayList<>();
        testParams.add(new Param("param1","name1" , "String"));
        testParams.add(new Param("param2","name2" , "String"));
        testParams.add(new Param("param3", "1", "number"));
        testParams.add(new Param("param4", "3", "number"));
    }

    @Test
    void getValuesString() {
        String expectedValuesString = "'name1', 'name2', 1, 3";
        String actualValuesString = paramService.getValuesString(testParams);
        Assertions.assertEquals(expectedValuesString, actualValuesString);
    }

    @Test
    void getNamesString() {
        String expectedNamesString = "param1, param2, param3, param4";
        String actualTypesString = paramService.getNamesString(testParams);
        Assertions.assertEquals(expectedNamesString, actualTypesString);
    }

    @Test
    void getUpdateParamsString() {
        String expectedUpdateParamsString = "param1 = 'name1', param2 = 'name2', param3 = '1', param4 = '3'";
        String actualUpdateParamsString = paramService.getUpdateParamsString(testParams);
        Assertions.assertEquals(expectedUpdateParamsString, actualUpdateParamsString);
    }
}