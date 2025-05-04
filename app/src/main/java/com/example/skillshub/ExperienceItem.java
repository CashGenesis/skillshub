package com.example.skillshub;

public class ExperienceItem {
    private String title;
    private String description;
    private String certificateLink;

    public ExperienceItem() {
        // Default constructor
    }

    public ExperienceItem(String title, String description, String certificateLink) {
        this.title = title;
        this.description = description;
        this.certificateLink = certificateLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertificateLink() {
        return certificateLink;
    }

    public void setCertificateLink(String certificateLink) {
        this.certificateLink = certificateLink;
    }
}
