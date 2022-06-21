package TP4;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseController {
    /**
     * initialize: initialise the database with the SQL scripts in initialiser.sql
     * @param con Connection to the database
     * @return true if connection is open, else false
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static boolean initialize(Connection con) throws SQLException, FileNotFoundException {
        if (!con.isClosed()) {
            Statement stm = con.createStatement();
            File file = new File("src/TP4/initialiser.sql");
            Scanner sc = new Scanner(file);
            sc.useDelimiter(";|--|$");

            while (sc.hasNext()) {
                String query = sc.next().trim() + ";";
                if (query.startsWith("# ") || query.length() == 1) {
                    continue;
                }
                System.out.println("Executer " + query);
                stm.execute(query);
            }
            return true;
        }
        return false;
    }

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
     * getTopicId: search for id of a given topic in the database
     * @param con Connection to the database
     * @param topic topic name
     * @return key int
     * @throws SQLException
     */
    public static int getTopicId(final Connection con, final String topic) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
        if (!resultSet.next()) {
            stm.execute("INSERT INTO topic(topic) VALUES (\"" + topic + "\");");
            resultSet = stm.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }

    /**
     * getTag: search for tag with a given id in the database
     * @param con Connection to the database
     * @param key id of the tag
     * @return tag String
     * @throws SQLException
     */
    public static String getTag(final Connection con, final int key) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT tag FROM tag WHERE tagID = '" + key + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("tagId introuvable");
        }
        return resultSet.getString(1);
    }

    /**
     * getTagId: search for id of a given tag in the database
     * @param con Connection to the database
     * @param tag tag name
     * @return id int
     * @throws SQLException
     */
    public static int getTagId(final Connection con, final String tag) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
        if (!resultSet.next()) {
            stm.execute("INSERT INTO tag(tag) VALUES (\"" + tag + "\");");
            resultSet = stm.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }

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
     * getCategoryKey: search for id of a given category in the database
     * @param con Connection to the database
     * @param name category name
     * @return key int
     * @throws SQLException
     */
    public static int getCategoryKey(final Connection con, final String name) throws SQLException {
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
