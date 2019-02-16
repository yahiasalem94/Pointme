package com.example.pointme.adapters;

public class CategoriesItem {

    // Save car name.
    private String categoryName;

    // Save car image resource id.
    private int imageId;

    public CategoriesItem(String categoryName, int imageId) {
        this.categoryName = categoryName;
        this.imageId = imageId;
    }

    public String getName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
