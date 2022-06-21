package TP4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TreeSet;
import java.util.Set;

public class Document {
    private final int documentID;
    private String documentName;
    private String documentDate = null;
    private String storageAddress = null;
    private String category;
    private String topic;
    private final Set<String> tags;
    private static int compteur = 0;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Create a Document instance with the document name, document date, storage address, category and topic
     * @param documentName Name of the document
     * @param documentDate Date of the document
     * @param storageAddress Storage address of the document
     * @param category Category of the document
     * @param topic Topic of the document
     * @throws ParseException If the date format is not yyyy-MM-dd
     */
    public Document(final String documentName, final String documentDate, final String storageAddress, final String category, final String topic) throws ParseException {
        this.documentID = ++compteur;
        this.documentName = documentName;
        FORMAT.parse(documentDate);
        this.documentDate = documentDate;
        this.storageAddress = storageAddress;
        this.category = category;
        this.topic = topic;
        tags = new TreeSet<>();
    }

    /**
     * Create a blank Document instance with only the document name
     * @param documentName Name of the document
     */
    public Document(final String documentName) {
        this.documentID = ++compteur;
        this.documentName = documentName;
        tags = new TreeSet<>();
    }

    /**
     * Create a document with a given ID. Use only for internal methods that requires creation of Document with specific ID.
     * @param documentID Id of the document
     * @throws Exception
     */
    protected Document(final int documentID) throws Exception {
        if (documentID >= compteur) {
            throw new Exception("Document ID invalide");
        }
        this.documentID = documentID;
        tags = new TreeSet<>();
    }

    /**
     * Get the current document's ID.
     * @return This document's ID
     */
    public int getDocumentID() {
        return documentID;
    }

    /**
     * Get the document's name
     * @return This document's name
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Set the document's name
     * @param documentName The name to be set for this document
     */
    public void setDocumentName(final String documentName) {
        this.documentName = documentName;
    }

    /**
     * Get the document's date
     * @return This document's date
     */
    public String getDocumentDate() {
        return documentDate;
    }

    /**
     * Set a string with format yyyy-MM-dd as document date
     * @param documentDate The date to be set for this document
     * @throws ParseException If the format is not yyyy-MM-dd
     */
    public void setDocumentDate(final String documentDate) throws ParseException {
        FORMAT.parse(documentDate);
        this.documentDate = documentDate;
    }

    /**
     * Get the storage address of this document
     * @return This document's storage address
     */
    public String getStorageAddress() {
        return storageAddress;
    }

    /**
     * Set the storage address of this document
     * @param storageAddress The storage address to be set for this document
     */
    public void setStorageAddress(final String storageAddress) {
        this.storageAddress = storageAddress;
    }

    /**
     * Get this document's category
     * @return This document's category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set this document's category
     * @param category The category to be set for this document
     */
    public void setCategory(final String category) {
        this.category = category;
    }

    /**
     * Get this document's topic
     * @return This document's topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Set this document's topic
     * @param topic The topic to be set for this document
     */
    public void setTopic(final String topic) {
        this.topic = topic;
    }

    /**
     * Add a tag to this document
     * @param tag The tag to be added for this document
     */
    public void addTag(final String tag) {
        tags.add(tag);
    }

    /**
     * Remove a tag from this document
     * @param tag The tag to be removed from this document
     * @return Returns true if the tag exists in the list
     */
    public boolean removeTag(final String tag) {
        return tags.remove(tag);
    }

    /**
     * Get this document's list of tags
     * @return This document's list of tags
     */
    public Set<String> getTags() {
        return new TreeSet<String>(tags);
    }

    /**
     * Synchronize data of the current Document instance into the database
     * @param con Connection to the database
     * @throws SQLException If category and topic are null, or there is any SQL error occurs during the synchronization
     */
    public void sync(final Connection con) throws SQLException {
        if (category == null || topic == null) {
            StringBuilder errorMessage = new StringBuilder("Certains attributs sont nuls : ");
            if (category == null) errorMessage.append("category ");
            if (topic == null) errorMessage.append("topic");
            throw new SQLException(errorMessage.toString());
        }
        Statement stm = con.createStatement();
        int catKey = DatabaseController.getCategoryKey(con, category);
        int topicKey = DatabaseController.getTopicId(con, topic);
        Set<String> tagList = new TreeSet<>();

        // create a document if not exist, else update data in case of key duplicate
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

        // search for tag list of the document, if exists
        ResultSet rS = stm.executeQuery("SELECT Tag FROM Possede JOIN Tag USING (TagId) WHERE DocumentId = " + documentID + ";");
        StringBuilder sqlQuery = new StringBuilder();
        while (rS.next()) {
            String tagDB = rS.getString("Tag");
            if (!tags.contains(tagDB)) {
                sqlQuery.append(tagDB + "', '");
            }
            tagList.add(tagDB);
        }
        // delete tags that are not in document's tags
        if (!sqlQuery.isEmpty()) {
            sqlQuery.delete(sqlQuery.length() - 4, sqlQuery.length());
            sqlQuery.insert(0, "DELETE FROM Possede WHERE DocumentID = " + documentID + " AND TagID IN (" +
                    "SELECT TagId FROM Tag WHERE Tag IN ('");
            sqlQuery.append("'));");
            stm.execute(sqlQuery.toString());
        }
        // add tags
        if(tags.size() > 0) {
            sqlQuery = new StringBuilder("INSERT IGNORE INTO Possede(DocumentId, TagId) VALUES ");
            for (String tag: tags) {
                int tagKey = DatabaseController.getTagId(con, tag);
                sqlQuery.append("(" + documentID + "," + tagKey + "),");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(";");
            stm.execute(sqlQuery.toString());
        }
    }

    /**
     * Search in the database for a document with a given ID, which then generate an instance of Document with its data
     * @param con Connection to the database
     * @param docId Id of the document
     * @return An instance of Document corresponding to the Id
     * @throws ParseException If the document date is not yyyy-MM-dd
     * @throws SQLException If there is any SQL error happening
     * @throws Exception If the document ID is greater than the internal counter
     */
    public static Document fetchDocument(final Connection con, final int docId) throws Exception {
        Statement stm = con.createStatement();
        ResultSet rS = stm.executeQuery("SELECT DocumentName, DocumentDate, StorageAddress, Name as Category, Topic " +
                "FROM Document join Category using(categoryId) join Topic using(TopicId) " +
                "WHERE documentId=" + docId + ";");
        if (rS.next()) {
            Document result = new Document(docId);
            result.setDocumentName(rS.getString("DocumentName"));
            if (rS.getString("DocumentDate") != null) result.setDocumentDate(rS.getString("DocumentDate"));
            if (rS.getString("StorageAddress") != null) result.setStorageAddress(rS.getString("StorageAddress"));
            result.setCategory(rS.getString("Category"));
            result.setTopic(rS.getString("Topic"));

            rS = stm.executeQuery("SELECT Tag FROM Possede join Tag using(tagId) WHERE DocumentId=" + docId + ";");
            while (rS.next()) {
                result.addTag(rS.getString(1));
            }
            return result;
        } else {
            throw new SQLException("Aucun résultat trouvé");
        }
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentName='" + documentName + '\'' +
                ", documentDate='" + documentDate + '\'' +
                ", storageAddress='" + storageAddress + '\'' +
                ", category='" + category + '\'' +
                ", topic='" + topic + '\'' +
                ", tags=" + tags +
                '}';
    }
}
