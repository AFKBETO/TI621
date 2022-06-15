package TP4;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SQLInitialiser {
    private List<String> queries = new ArrayList<>();
    private static SQLInitialiser instance = new SQLInitialiser();

    private SQLInitialiser() {
        queries.add("CREATE DATABASE IF NOT EXISTS tp4;");
        queries.add("USE tp4;");
        queries.add("DROP TABLE IF EXISTS Possede, Document, Category, Topic, Tag;");
        queries.add("CREATE TABLE Category(CategoryID INT auto_increment, Name VARCHAR(50), PRIMARY KEY(CategoryID));");
        queries.add("CREATE TABLE Topic(TopicID INT auto_increment, Topic VARCHAR(50), PRIMARY KEY(TopicID));");
        queries.add("CREATE TABLE Tag(TagID INT auto_increment, Tag VARCHAR(50), PRIMARY KEY(TagID));");
        queries.add("CREATE TABLE Document(" +
                "DocumentID INT auto_increment, " +
                "DocumentName VARCHAR(50), " +
                "DocumentDate DATE, " +
                "StorageAddress VARCHAR(50), " +
                "TopicID INT NOT NULL, " +
                "CategoryID INT NOT NULL, " +
                "PRIMARY KEY(DocumentID), " +
                "FOREIGN KEY(TopicID) REFERENCES Topic(TopicID), " +
                "FOREIGN KEY(CategoryID) REFERENCES Category(CategoryID)" +
                ");");
        queries.add("CREATE TABLE possede(" +
                "   DocumentID INT," +
                "   TagID INT," +
                "   PRIMARY KEY(DocumentID, TagID)," +
                "   FOREIGN KEY(DocumentID) REFERENCES Document(DocumentID)," +
                "   FOREIGN KEY(TagID) REFERENCES Tag(TagID)" +
                ");");
    }

    public static boolean run(Statement statement) throws SQLException {
        if (!statement.isClosed()) {
            for (String query : instance.queries) {
                System.out.println("Executer " + query);
                statement.execute(query);
            }
            return true;
        }
        return false;
    }
}
