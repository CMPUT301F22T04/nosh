package com.example.nosh.repository;

import com.example.nosh.database.DBController;
import com.example.nosh.entity.ingredient.StoredIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


public class StoredIngredientRepo extends Observable implements Observer {

    private final DBController dbController;
    private final HashMap<String, StoredIngredient> storedIngredients;

    public StoredIngredientRepo(DBController dbController) {
        this.dbController = dbController;
        storedIngredients = new HashMap<>();

        this.dbController.addObserver(this);
    }

    public void add(StoredIngredient storedIngredient) {
        storedIngredients.put(storedIngredient.getHashcode(), storedIngredient);
        dbController.create(storedIngredient);

        notifyObservers();
    }

    public ArrayList<StoredIngredient> retrieve() {
        return new ArrayList<>(storedIngredients.values());
    }

    public void delete(StoredIngredient storedIngredient) {
        storedIngredients.remove(storedIngredient.getHashcode());
        dbController.delete(storedIngredient);
    }

    public void sync() {
        dbController.retrieve();
    }

    @Override
    public void update(Observable o, Object arg) {
        assert (arg instanceof StoredIngredient[]);

        StoredIngredient[] storedIngredients = (StoredIngredient[]) arg;

        for (StoredIngredient storedIngredient : storedIngredients) {
            add(storedIngredient);
        }

        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        setChanged();

        super.notifyObservers();
    }
}
