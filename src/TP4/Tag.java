package TP4;

import java.sql.*;

public class Tag {
    public static String getTag(Statement statement, int key) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT tag FROM tag WHERE tagID = '" + key + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("tagId introuvable");
        }
        return resultSet.getString(1);
    }

    public static int getKey(Statement statement, String tag) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
        if (!resultSet.next()) {
            statement.execute("INSERT INTO tag(tag) VALUES (\"" + tag + "\");");
            resultSet = statement.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }
}
