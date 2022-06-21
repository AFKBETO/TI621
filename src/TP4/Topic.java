package TP4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Topic {
    /**
     * getTopic: search for topic with a given id in the database
     * @param con Connection to the database
     * @param key id of the topic
     * @return topic String
     * @throws SQLException
     */
    public static String getTopic(final Connection con, final int key) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT topic FROM topic WHERE topicId = '" + key + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("topicId introuvable");
        }
        return resultSet.getString(1);
    }
    /**
     * getKey: search for id of a given topic in the database
     * @param con Connection to the database
     * @param topic topic name
     * @return key int
     * @throws SQLException
     */
    public static int getKey(final Connection con, final String topic) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
        if (!resultSet.next()) {
            stm.execute("INSERT INTO topic(topic) VALUES (\"" + topic + "\");");
            resultSet = stm.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }
}
