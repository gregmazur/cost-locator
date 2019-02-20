
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("documentOf")
    @Expose
    private String documentOf;
    @SerializedName("datePublished")
    @Expose
    private String datePublished;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("dateModified")
    @Expose
    private String dateModified;
    @SerializedName("id")
    @Expose
    private String prozzorroId;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentOf() {
        return documentOf;
    }

    public void setDocumentOf(String documentOf) {
        this.documentOf = documentOf;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getProzzorroId() {
        return prozzorroId;
    }

    public void setProzzorroId(String prozzorroId) {
        this.prozzorroId = prozzorroId;
    }
}
