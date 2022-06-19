package TP4;

import java.sql.*;
import java.util.TreeSet;
import java.util.Set;

public class Document {
    private final int documentID;
    private String documentName;
    private String documentDate;
    private String storageAddress;
    private String category;
    private String topic;
    private final Set<String> tags;
    private static int COMPTEUR = 0;

    public Document(String documentName, String documentDate, String storageAddress) {
        this.documentID = ++COMPTEUR;
        this.documentName = documentName;
        this.documentDate = documentDate;
        this.storageAddress = storageAddress;
        tags = new TreeSet<>();
    }

    public int getDocumentID() {
        return documentID;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void deleteTag(Tag tag) {
        tags.remove(tag);
    }

    public Set<String> getTags() {
        return new TreeSet<String>(tags);
    }

    public void insert(Statement statement) throws SQLException {
        int catKey = Category.getKey(statement, category);
        int topicKey = Topic.getKey(statement, topic);
        statement.execute("INSERT INTO Document(DocumentId, Documentname,DocumentDate,StorageAddress,CategoryId,TopicId) VALUES ('" +
                documentID + "','" + documentName +"','" + documentDate +"','" + storageAddress + "'," + catKey + "," + topicKey +");");
        StringBuilder sqlString = new StringBuilder("INSERT IGNORE INTO Possede(DocumentId, TagId) VALUES ");
        for (String tag: tags) {
            int tagKey = Tag.getKey(statement, tag);
            sqlString.append("(" + documentID + "," + tagKey + "),");
        }
        sqlString.deleteCharAt(sqlString.length() - 1);
        sqlString.append(";");
        statement.execute(sqlString.toString());
    }
}
