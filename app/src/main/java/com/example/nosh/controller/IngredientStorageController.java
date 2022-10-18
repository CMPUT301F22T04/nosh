package com.example.nosh.controller;

import com.example.nosh.entity.StoredIngredient;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class IngredientStorageController implements Observer {

    final private ArrayList<StoredIngredient> ingredients;

    public IngredientStorageController() {
        ingredients = new ArrayList<>();

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
