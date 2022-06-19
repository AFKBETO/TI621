package TP4;

import java.sql.*;
import java.util.TreeSet;
import java.util.Set;

public class Document {
    private final int documentID;
    private String documentName;
    private String documentDate;
    private String storageAddress;
    private Category category;
    private Topic topic;
    private final Set<Tag> tags;
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

    public Category getCategory() {
        return new Category(category.getName());
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Topic getTopic() {
        return new Topic(topic.getTopic());
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void deleteTag(Tag tag) {
        tags.remove(tag);
    }

    public Set<Tag> getTags() {
        return new TreeSet<>(tags);
    }

    public void insert(Statement statement) throws SQLException {
        int catKey = category.getKey(statement);
        int topicKey = topic.getKey(statement);
        statement.execute("INSERT INTO Document(DocumentId, Documentname,DocumentDate,StorageAddress,CategoryId,TopicId) VALUES ('" +
                documentID + "','" + documentName +"','" + documentDate +"','" + storageAddress + "'," + catKey + "," + topicKey +");");
        StringBuilder sqlString = new StringBuilder("INSERT IGNORE INTO Possede(DocumentId, TagId) VALUES ");
        for (Tag tag: tags) {
            int tagKey = tag.getKey(statement);
            sqlString.append("(" + documentID + "," + tagKey + "),");
        }
        sqlString.deleteCharAt(sqlString.length() - 1);
        sqlString.append(";");
        statement.execute(sqlString.toString());
    }
}
