package TP4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tag {
    public static String getTag(final Connection con, final int key) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT tag FROM tag WHERE tagID = '" + key + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("tagId introuvable");
        }
        return resultSet.getString(1);
    }

    public static int getKey(final Connection con, final String tag) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
        if (!resultSet.next()) {
            stm.execute("INSERT INTO tag(tag) VALUES (\"" + tag + "\");");
            resultSet = stm.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }
}
