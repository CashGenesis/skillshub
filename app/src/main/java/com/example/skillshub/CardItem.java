package com.example.skillshub;

import java.util.List;
import java.util.ArrayList;

public class CardItem {
    private String title;
    private String subtitle;
    private String description;
    private String price;
    private float rating;
    private int iconResource;
    private List<String> tags;

    public CardItem(String title, String subtitle, String description,
                    String price, float rating, int iconResource, List<String> tags) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.iconResource = iconResource;
        this.tags = tags;
    }


    // Example factory method to create sample cards
    public static List<CardItem> createSampleCards() {
        List<CardItem> cards = new ArrayList<>();

        // First card
        List<String> tags1 = new ArrayList<>();
        tags1.add("Design");
        tags1.add("UI/UX");
        tags1.add("Remote");
        cards.add(new CardItem(
                "Design Master",
                "UI/UX Program",
                "Find freelance jobs and grow your career from college itself.",
                "₹ 22,000",
                4.9f,
                R.drawable.star_caro,
                tags1
        ));

        // Second card
        List<String> tags2 = new ArrayList<>();
        tags2.add("Coding");
        tags2.add("Web");
        tags2.add("Java");
        cards.add(new CardItem(
                "Code Academy",
                "Full Stack Dev",
                "Find freelance jobs and grow your career from college itself.",
                "₹ 18,000",
                4.7f,
                R.drawable.star_caro,
                tags2
        ));

        // Third card
        List<String> tags3 = new ArrayList<>();
        tags3.add("Marketing");
        tags3.add("Digital");
        tags3.add("SEO");
        cards.add(new CardItem(
                "Digital Marketing",
                "Marketing Pro",
                "Find freelance jobs and grow your career from college itself.",
                "₹ 15,000",
                4.8f,
                R.drawable.star_caro,
                tags3
        ));

        return cards;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}