package com.example.skillshub;

/**
 * Model class for carousel items containing an image resource ID and a title
 */
public class CarouselItem {
    // Private member variables
    private int imageResource;
    private String title;

    /**
     * Constructor for creating a new carousel item
     *
     * @param imageResource The drawable resource ID for the item's image
     * @param title The text title for the item
     */
    public CarouselItem(int imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    /**
     * Get the image resource ID
     *
     * @return The drawable resource ID
     */
    public int getImageResource() {
        return imageResource;
    }

    /**
     * Set the image resource ID
     *
     * @param imageResource The drawable resource ID
     */
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    /**
     * Get the title text
     *
     * @return The title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title text
     *
     * @param title The title string
     */
    public void setTitle(String title) {
        this.title = title;
    }
}