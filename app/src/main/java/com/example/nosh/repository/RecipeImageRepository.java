package com.example.nosh.repository;

import android.content.Context;

import com.example.nosh.database.controller.FirebaseStorageController;


public class RecipeImageRepository {

    private final Context context;
    private final FirebaseStorageController storageController;

    public RecipeImageRepository(Context context,
                                 FirebaseStorageController storageController) {
        this.context = context;
        this.storageController = storageController;
    }

    public void add(String photograph) {

    }
}
