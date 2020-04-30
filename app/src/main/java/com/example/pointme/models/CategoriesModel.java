package com.example.pointme.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class CategoriesModel {

//    private String categoryNames;
//
//    // Save car image resource id.
//    private String imageIds;
    private HashMap<String, String> categories;
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> categorieNames = new ArrayList<>();

    public CategoriesModel() {
    }

    public CategoriesModel(HashMap<String, String> categories) {
        this.categories = categories;

    }

    public HashMap<String, String> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, String> categories) {
       this.categories = categories;
    }

    public ArrayList<String> getImageUrls() {
        return images = new ArrayList<>(categories.values());
    }

    public ArrayList<String> getCategoriesNames() {
        return categorieNames = new ArrayList<>(categories.keySet());
    }
}
