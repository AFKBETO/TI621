package TP4;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Autopopulate {
    private List<String> queries = new ArrayList<>();
    private static Autopopulate autopopulate = new Autopopulate();

    private Autopopulate() {
        queries.add("DROP TABLE IF EXISTS Category, Topic, Tag, Document;");
        queries.add("CREATE TABLE Category(CategoryID INT auto_increment, Name VARCHAR(50), PRIMARY KEY(CategoryID));");
        queries.add("CREATE TABLE Topic(TopicID INT auto_increment, Topic VARCHAR(50), PRIMARY KEY(TopicID));");
        queries.add("CREATE TABLE Tag(TagID INT auto_increment, Tag VARCHAR(50), PRIMARY KEY(TagID));");
        queries.add("CREATE TABLE Document(\n" +
                "   DocumentID INT auto_increment,\n" +
                "   DocumentName VARCHAR(50),\n" +
                "   DocumentDate DATE,\n" +
                "   StorageAddress VARCHAR(50),\n" +
                "   TopicID INT NOT NULL,\n" +
                "   CategoryID INT NOT NULL,\n" +
                "   PRIMARY KEY(DocumentID),\n" +
                "   FOREIGN KEY(TopicID) REFERENCES Topic(TopicID),\n" +
                "   FOREIGN KEY(CategoryID) REFERENCES Category(CategoryID)\n" +
                ");");
    }

    public static boolean run(Statement statement) throws SQLException {
        if (!statement.isClosed()) {
            for (String query : autopopulate.queries) {
                statement.execute(query);
            }
            return true;
        }
        return false;
    }
}
