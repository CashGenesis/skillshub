package com.example.skillshub;

import java.util.ArrayList;
import java.util.List;

public class SkillItem {
    private String title;
    private String description;
    private int illustrationResourceId;
    private List<Integer> appIconResourceIds;

    public SkillItem(String title, String description, int illustrationResourceId) {
        this.title = title;
        this.description = description;
        this.illustrationResourceId = illustrationResourceId;
        this.appIconResourceIds = new ArrayList<>();
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

    public int getIllustrationResourceId() {
        return illustrationResourceId;
    }

    public void setIllustrationResourceId(int illustrationResourceId) {
        this.illustrationResourceId = illustrationResourceId;
    }

    public List<Integer> getAppIconResourceIds() {
        return appIconResourceIds;
    }

    public void addAppIcon(int resourceId) {
        this.appIconResourceIds.add(resourceId);
    }
}