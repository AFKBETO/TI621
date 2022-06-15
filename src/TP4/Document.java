package TP4;

import java.util.Date;

public class Document {

    private String DocumentName;
    private Date DocumentDate;
    private String StorageAddress;
   public Document(String documentName, Date documentDate, String storageAddress) {
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

    public Date getDocumentDate() {
        return new Date(DocumentDate.getTime());
    }

    public void setDocumentDate(Date documentDate) {
        this.DocumentDate = new Date(documentDate.getTime());
    }

    public String getStorageAddress() {
        return StorageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.StorageAddress = storageAddress;
    }

}
