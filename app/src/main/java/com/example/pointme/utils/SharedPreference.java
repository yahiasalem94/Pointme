package com.example.pointme.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.pointme.models.ServiceProvider;
import com.google.gson.Gson;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<ServiceProvider> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, ServiceProvider info) {
        List<ServiceProvider> favorites = getFavorites(context);
        if (!favorites.contains(info)){
            favorites.add(info);
            saveFavorites(context, favorites);
        }
    }

    public void removeFavorite(Context context, ServiceProvider info) {
        ArrayList<ServiceProvider> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(info);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<ServiceProvider> getFavorites(Context context) {
        SharedPreferences settings;
        List<ServiceProvider> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            ServiceProvider[] favoriteItems = gson.fromJson(jsonFavorites,
                    ServiceProvider[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else {
            return new ArrayList<>();
        }
        return (ArrayList<ServiceProvider>) favorites;
    }
}