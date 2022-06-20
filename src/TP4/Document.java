package TP4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.Set;

public class Document {
    private final int documentID;
    private String documentName;
    private String documentDate = null;
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

    public Document(String documentName, String storageAddress) {
        this.documentID = ++COMPTEUR;
        this.documentName = documentName;
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

    public void deleteTag(String tag) {
        tags.remove(tag);
    }

    public Set<String> getTags() {
        return new TreeSet<String>(tags);
    }

    public void sync(Connection con) throws SQLException {
        Statement stm = con.createStatement();
        int catKey = Category.getKey(con, category);
        int topicKey = Topic.getKey(con, topic);
        Set<String> tagList = new TreeSet<>();

        stm.execute(
                "INSERT INTO Document(DocumentId,Documentname,"+ ((documentDate != null) ? "DocumentDate," : " ") +
                "StorageAddress,CategoryId,TopicId)" +
                        "VALUES ('" + documentID + "','" + documentName +
                        ((documentDate != null) ? ("','" + documentDate) : "") +
                        "','" + storageAddress + "'," + catKey + "," + topicKey +
                ") ON DUPLICATE KEY UPDATE " +
                        "Documentname = '" + documentName +
                        ((documentDate != null) ? ("',DocumentDate = '" + documentDate) : "") +
                        "',StorageAddress = '" + storageAddress +
                        "',CategoryId = " + catKey +
                        ",TopicId = " + topicKey + ";");

        ResultSet rS = stm.executeQuery("SELECT Tag FROM Possede JOIN Tag USING (TagId) WHERE DocumentId = " + documentID + ";");
        StringBuilder sqlQuery = new StringBuilder();
        while (rS.next()) {
            String tagDB = rS.getString("Tag");
            if (!tags.contains(tagDB)) {
                sqlQuery.append(tagDB + "', '");
            }
            tagList.add(tagDB);
        }
        if (sqlQuery.length() > 0) {
            sqlQuery.delete(sqlQuery.length() - 4, sqlQuery.length());
            sqlQuery.insert(0, "DELETE FROM Possede WHERE DocumentID = " + documentID + " AND TagID IN (" +
                    "SELECT TagId FROM Tag WHERE Tag IN ('");
            sqlQuery.append("'));");
            stm.execute(sqlQuery.toString());
        }
        sqlQuery = new StringBuilder("INSERT IGNORE INTO Possede(DocumentId, TagId) VALUES ");
        for (String tag: tags) {
            int tagKey = Tag.getKey(con, tag);
            sqlQuery.append("(" + documentID + "," + tagKey + "),");
        }
        sqlQuery.deleteCharAt(sqlQuery.length() - 1);
        sqlQuery.append(";");
        stm.execute(sqlQuery.toString());
    }
}
