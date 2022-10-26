package com.example.nosh.repository;

import com.example.nosh.database.DBController;
import com.example.nosh.entity.ingredient.StoredIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


/**
 * This class is the owner (SSOT) of all the Ingredient objects, which means 
 * this class has the right modify or mutate those objects. The data exposed 
 * this class is immutable. In another word, classes that are other than 
 * this class do not have the ability to modify or mutate Ingredient objects.
 *
 * This class is also responible to request four main operations on database 
 * so that the data in local is in sync with the database
 * 
 * This class also implements Observer Pattern. It's a subject because it needs to 
 * keep the UI layer up to date if there is any change in any of the Ingredient 
 * Object, and it's a observer because there's latency when fetching data from 
 * database. This class do not wait for data return after making a request.
 */
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
    }

    public ArrayList<StoredIngredient> retrieve() {
        return new ArrayList<>(storedIngredients.values());
    }

    public void delete(StoredIngredient storedIngredient) {
        storedIngredients.remove(storedIngredient.getHashcode());
        dbController.delete(storedIngredient);
    }

    /**
     * Only call this method when first time instantiate an instance of this 
     * class. It's for retrieving all data from database when app initially 
     * start since there's no data locally.
     */
    public void sync() {
        dbController.retrieve();
    }

    /**
     * Appended all the data from database into the collection.
     */
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
