package TP4;

import java.sql.*;

public class Category {
    public static String getName(final Statement statement, final int key) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT name FROM category WHERE categoryID = '" + key + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("categoryId introuvable");
        }
        return resultSet.getString(1);
    }

    public static int getKey(final Statement statement, final String name) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
        if (!resultSet.next()) {
            statement.execute("INSERT INTO Category(name) VALUES (\"" + name + "\");");
            resultSet = statement.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }
}
