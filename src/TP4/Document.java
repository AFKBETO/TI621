package TP4;

import java.util.TreeSet;
import java.util.Set;

public class Document {

    private String documentName;
    private String documentDate;
    private String storageAddress;
    private Category category;
    private Topic topic;
    private final Set<Tag> tags;

    public Document(String documentName, String documentDate, String storageAddress) {
        this.documentName = documentName;
        this.documentDate = documentDate;
        this.storageAddress = storageAddress;
        tags = new TreeSet<>();
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
        return "INSERT INTO document(Documentname,DocumentDate,StorageAddress) VALUES ('" +this.documentName +"','"+this.documentDate +"','"+this.storageAddress +"');";
    }
}
