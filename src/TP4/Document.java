package TP4;

import java.util.TreeSet;
import java.util.Set;

public class Document {

    private String DocumentName;
    private String DocumentDate;
    private String StorageAddress;
    private Category category;
    private Topic topic;
    private final Set<Tag> tags;

    public Document(String documentName, String documentDate, String storageAddress) {
        this.DocumentName = documentName;
        this.DocumentDate = documentDate;
        this.StorageAddress = storageAddress;
        tags = new TreeSet<>();
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        this.DocumentName = documentName;
    }

    public String getDocumentDate() {
        return DocumentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.DocumentDate = documentDate;
    }

    public String getStorageAddress() {
        return StorageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.StorageAddress = storageAddress;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public Set<Tag> getTags() {
        return new TreeSet<>(tags);
    }
    public String toSql(){
        return "INSERT INTO document(Documentname,DocumentDate,StorageAddress,TopicID,CategoryID) VALUES ('" +this.DocumentName+"','"+this.DocumentDate+"','"+this.StorageAddress+"',1,1);";
    }
}
