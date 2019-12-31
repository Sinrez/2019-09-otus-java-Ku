package ru.otus.homework.services.database;

import java.util.List;

public class ParamService {
    public String getValuesString(List<Param> params) {
        StringBuilder valuesString = new StringBuilder();
        if (params.get(0).getType().equals("String")) {
            valuesString.append("'")
                    .append(params.get(0).getValue())
                    .append("'");
        } else {
            valuesString.append(params.get(0).getValue());
        }
        for (int i = 1; i < params.size(); i++) {
            if (params.get(i).getType().equals("String")) {
                valuesString.append(", ")
                        .append("'")
                        .append(params.get(i).getValue())
                        .append("'");
                continue;
            }
            valuesString.append(", ")
                    .append(params.get(i).getValue());
        }
        return valuesString.toString();
    }

    public String getNamesString(List<Param> params) {
        StringBuilder namesString = new StringBuilder(params.get(0).getName());
        for (int i = 1; i < params.size(); i++) {
            namesString.append(", ")
                    .append(params.get(i).getName());
        }
        return namesString.toString();
    }

    public String getUpdateParamsString(List<Param> params) {
        StringBuilder updateParamsString = new StringBuilder();
        updateParamsString.append(params.get(0).getName())
                .append(" = '")
                .append(params.get(0).getValue())
                .append("'");
        for (int i = 1; i < params.size(); i++) {
            updateParamsString.append(", ")
                    .append(params.get(i).getName())
                    .append(" = '")
                    .append(params.get(i).getValue())
                    .append("'");
        }
        return updateParamsString.toString();
    }
}
