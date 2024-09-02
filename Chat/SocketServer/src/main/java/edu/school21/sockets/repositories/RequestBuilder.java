package edu.school21.sockets.repositories;

import org.springframework.stereotype.Component;

import java.util.StringJoiner;

public class RequestBuilder{
    static public String generateColumnNames(String tableName, String[] columnName, String prefix) {
        StringJoiner stringJoiner = new StringJoiner(", ", "","");
        for (String column : columnName) {
            stringJoiner.add(String.format("%s.%s AS %s_%s", tableName, column, prefix, column));
        }
        return stringJoiner.toString();
    }
}
