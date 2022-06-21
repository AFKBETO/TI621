package TP4;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseController {
    private Connection con = null;
    private static final DatabaseController instance = new DatabaseController();

    private DatabaseController() {}

    /**
     * Get the controller instance using the Connection
     * @param con Connection to the database
     * @return Singleton instance of DatabaseController
     */
    public static DatabaseController getInstance(Connection con) {
        instance.con = con;
        return instance;
    }

    /**
     * Get the controller instance, only usable if Connection has been specified
     * @return Singleton instance of DatabaseController
     * @throws Exception If getInstance(Connection con) has not been used before
     */
    public static DatabaseController getInstance() throws Exception {
        if (instance.con == null) {
            throw new Exception("Connection n'est pas specifiee");
        }
        return instance;
    }

    /**
     * Initialize the database with the SQL scripts in initialiser.sql
     * @return Returns true if connection is open and the SQL is proceeded successfully
     * @throws SQLException If there is any SQL error happening
     * @throws FileNotFoundException If the file is missing
     */
    public boolean initialize() throws SQLException, FileNotFoundException {
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
     * Search for topic with a given id in the database
     * @param topicId Id of the topic
     * @return The topic's name
     * @throws SQLException If there is any SQL errors happening
     */
    public String getTopic(final int topicId) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT topic FROM topic WHERE topicId = '" + topicId + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("topicId introuvable");
        }
        return resultSet.getString(1);
    }

    /**
     * Search for id of a given topic in the database
     * @param topic Topic name
     * @return The topic's Id
     * @throws SQLException If there is any SQL errors happening
     */
    public int getTopicId(final String topic) throws SQLException {
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
     * Search for tag with a given id in the database
     * @param tagId Id of the tag
     * @return The tag's name
     * @throws SQLException If there is any SQL errors happening
     */
    public String getTag(final int tagId) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT tag FROM tag WHERE tagID = '" + tagId + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("tagId introuvable");
        }
        return resultSet.getString(1);
    }

    /**
     * Search for id of a given tag in the database
     * @param tag Tag name
     * @return The tag's Id
     * @throws SQLException If there is any SQL errors happening
     */
    public int getTagId(final String tag) throws SQLException {
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
     * Search for category with a given id in the database
     * @param categoryId Id of the category
     * @return The category's name
     * @throws SQLException If there is any SQL errors happening
     */
    public String getName(final int categoryId) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT name FROM category WHERE categoryID = '" + categoryId + "' LIMIT 1");
        if (!resultSet.next()) {
            throw new SQLException("categoryId introuvable");
        }
        return resultSet.getString(1);
    }

    /**
     * Search for id of a given category in the database
     * @param name Category name
     * @return The category's Id
     * @throws SQLException If there is any SQL errors happening
     */
    public int getCategoryKey(final String name) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
        if (!resultSet.next()) {
            stm.execute("INSERT INTO Category(name) VALUES (\"" + name + "\");");
            resultSet = stm.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }

    /**
     * Print the document with the given docId. This is different from printing a Document instance
     * @param docId Id of the document
     * @throws SQLException If there is any SQL errors happening
     */
    public void printDocument(final int docId) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet rS = stm.executeQuery("SELECT documentName, documentDate, storageAddress, Name AS Category, Topic FROM Document " +
                "JOIN Category USING(categoryId) JOIN Topic USING(topicId) " +
                "WHERE DocumentId = " + docId + ";");
        if(rS.next()){
            System.out.printf("%-25s %-12s %-35s %-10s %-30s %-10s\n", "DocumentName", "Date", "StorageAddress", "Category", "Topic", "Tags");
            System.out.printf("%-25s %-12s %-35s %-10s %-30s", rS.getString("documentName"), rS.getString("documentDate"), rS.getString("storageAddress"), rS.getString("Category"), rS.getString("Topic"));
            rS = stm.executeQuery("SELECT Tag FROM Possede JOIN Tag USING(TagId) WHERE DocumentId = " + docId + ";");
            while (rS.next()) {
                System.out.print(rS.getString(1) + " ");
            }
            System.out.println();
        }
    }
}
