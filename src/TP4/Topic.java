package TP4;

import java.sql.*;

public class Topic {
    public static String getTopic(Statement statement, int key) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT topic FROM topic WHERE topicId = '" + key + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("topicId introuvable");
        }
        return resultSet.getString(1);
    }

    public static int getKey(Statement statement, String topic) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
        if (!resultSet.next()) {
            statement.execute("INSERT INTO topic(topic) VALUES (\"" + topic + "\");");
            resultSet = statement.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }
}
