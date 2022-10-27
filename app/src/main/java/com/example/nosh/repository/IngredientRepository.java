package com.example.nosh.repository;

import com.example.nosh.database.DBController;
import com.example.nosh.entity.Ingredient;

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
public class IngredientRepository extends Observable implements Observer {

    private final DBController dbController;
    private final HashMap<String, Ingredient> ingredients;

    public IngredientRepository(DBController dbController) {
        this.dbController = dbController;
        ingredients = new HashMap<>();

        this.dbController.addObserver(this);
    }

    public void add(Ingredient ingredient) {
        ingredients.put(ingredient.getHashcode(), ingredient);
        dbController.create(ingredient);

        notifyObservers();
    }

    public ArrayList<Ingredient> retrieve() {
        return new ArrayList<>(ingredients.values());
    }

    public void delete(Ingredient ingredient) {
        ingredients.remove(ingredient.getHashcode());
        dbController.delete(ingredient);
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
        assert (arg instanceof Ingredient[]);

        Ingredient[] ingredients = (Ingredient[]) arg;

        for (Ingredient ingredient : ingredients) {
            add(ingredient);
        }

        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        setChanged();

        super.notifyObservers();
    }
}
