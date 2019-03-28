package com.example.pointme.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.pointme.models.ProvidersInfo;
import com.google.gson.Gson;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<ProvidersInfo> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, ProvidersInfo info) {
        List<ProvidersInfo> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<ProvidersInfo>();
        favorites.add(info);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, ProvidersInfo info) {
        ArrayList<ProvidersInfo> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(info);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<ProvidersInfo> getFavorites(Context context) {
        SharedPreferences settings;
        List<ProvidersInfo> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            ProvidersInfo[] favoriteItems = gson.fromJson(jsonFavorites,
                    ProvidersInfo[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<ProvidersInfo>(favorites);
        } else
            return null;

        return (ArrayList<ProvidersInfo>) favorites;
    }
}