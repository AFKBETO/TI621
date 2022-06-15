package TP4;

public class Document {

    private String DocumentName;
    private String DocumentDate;
    private String StorageAddress;

    public Document(String documentName, String documentDate, String storageAddress) {
        this.DocumentName = documentName;
        this.DocumentDate = documentDate;
        this.StorageAddress = storageAddress;
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

    public String toSQL() {
        return "INSERT INTO Document(DocumentName, DocumentDate, StorageAddress) VALUES ('"
                + this.DocumentName + "','" + this.DocumentDate + "','" + this.StorageAddress + "');";
    }

}
