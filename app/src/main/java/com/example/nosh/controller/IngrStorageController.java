package com.example.nosh.controller;

import com.example.nosh.database.DBController;
import com.example.nosh.repository.StoredIngredientRepo;
import com.example.nosh.entity.ingredient.StoredIngredient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;


public class IngrStorageController {

    private final StoredIngredientRepo storedIngredientRepo;

    public IngrStorageController(DBController dbController, Observer o) {
        storedIngredientRepo = new StoredIngredientRepo(dbController);

        storedIngredientRepo.addObserver(o);
        storedIngredientRepo.sync();
    }

    public void add(Date bestBeforeDate, int amount, int unit, String name,
                    String description, String category, String location) {

        StoredIngredient storedIngredient = new StoredIngredient(bestBeforeDate,
                amount, unit, name, description, category, location);

        storedIngredientRepo.add(storedIngredient);
    }

    public ArrayList<StoredIngredient> retrieve() {
        return storedIngredientRepo.retrieve();
    }

    public void delete(StoredIngredient storedIngredient) {
        storedIngredientRepo.delete(storedIngredient);
    }
}
