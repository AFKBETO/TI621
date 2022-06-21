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
    private static int COMPTEUR = 0;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Public Constructor, creating a Document instance with the document name, document date and storage address
     * @param documentName name of the document
     * @param documentDate date of the document
     * @param storageAddress storage address
     * @throws ParseException
     */
    public Document(final String documentName, final String documentDate, final String storageAddress) throws ParseException {
        this.documentID = ++COMPTEUR;
        this.documentName = documentName;
        format.parse(documentDate);
        this.documentDate = documentDate;
        this.storageAddress = storageAddress;
        tags = new TreeSet<>();
    }

    /**
     * Public Constructor, creating a blank Document instance with only the document name
     * @param documentName name of the document
     */
    public Document(final String documentName) {
        this.documentID = ++COMPTEUR;
        this.documentName = documentName;
        tags = new TreeSet<>();
    }

    /**
     * Private Constructor to create a document with a given ID
     * @param documentID id of the document
     */
    private Document(final int documentID){
        this.documentID = documentID;
        tags = new TreeSet<>();
    }

    public int getDocumentID() {
        return documentID;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(final String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    /**
     * setDocumentDate: set a string with format yyyy-MM-dd as document date
     * @param documentDate date of the document
     * @throws ParseException
     */
    public void setDocumentDate(final String documentDate) throws ParseException {
        format.parse(documentDate);
        this.documentDate = documentDate;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(final String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(final String topic) {
        this.topic = topic;
    }

    public void addTag(final String tag) {
        tags.add(tag);
    }

    public boolean deleteTag(final String tag) {
        return tags.remove(tag);
    }

    public Set<String> getTags() {
        return new TreeSet<String>(tags);
    }

    /**
     * sync: synchronize data of the current Document instance into the database
     * @param con Connection to the database
     * @throws SQLException
     */
    public void sync(final Connection con) throws SQLException {
        Statement stm = con.createStatement();
        int catKey = Category.getKey(con, category);
        int topicKey = Topic.getKey(con, topic);
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
                int tagKey = Tag.getKey(con, tag);
                sqlQuery.append("(" + documentID + "," + tagKey + "),");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(";");
            stm.execute(sqlQuery.toString());
        }
    }

    /**
     * fetchDocument: search for a document with a given ID, which then generate an instance of Document with its data
     * @param con Connection to the database
     * @param docId id of the document
     * @return document instance
     * @throws SQLException
     * @throws ParseException
     */
    public static Document fetchDocument(final Connection con, final int docId) throws SQLException, ParseException {
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
