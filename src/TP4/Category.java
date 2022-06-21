package TP4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Category {
    /**
     * getCategory: search for category with a given id in the database
     * @param con Connection to the database
     * @param key id of the category
     * @return category String
     * @throws SQLException
     */
    public static String getName(final Connection con, final int key) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT name FROM category WHERE categoryID = '" + key + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("categoryId introuvable");
        }
        return resultSet.getString(1);
    }
    /**
     * getKey: search for id of a given category in the database
     * @param con Connection to the database
     * @param name category name
     * @return key int
     * @throws SQLException
     */
    public static int getKey(final Connection con, final String name) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
        if (!resultSet.next()) {
            stm.execute("INSERT INTO Category(name) VALUES (\"" + name + "\");");
            resultSet = stm.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }
}
