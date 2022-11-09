package com.example.nosh.repository;

import com.example.nosh.database.controller.DBController;
import com.example.nosh.database.controller.IngredientDBController;
import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;

import javax.inject.Inject;


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
public class IngredientRepository extends Repository {

    private final HashMap<String, Ingredient> ingredients;

    @Inject
    public IngredientRepository(IngredientDBController dbController) {
        super(dbController);

        ingredients = new HashMap<>();

        sync();
    }

    public void add(Date bestBeforeDate, double unit, int amount,
                    String category, String description, String location,
                    String name) {

        Ingredient ingredient = new Ingredient(bestBeforeDate, unit, amount,
                category, description, location, name);

        ingredients.put(ingredient.getHashcode(), ingredient);

        super.add(ingredient);
    }

    public ArrayList<Ingredient> retrieve() {
        return new ArrayList<>(ingredients.values());
    }

    public void update(String hashcode, Date bestBeforeDate, double unit, int amount,
                       String category, String description, String location,
                       String name) {

        Ingredient ingredient = Objects.requireNonNull(ingredients.get(hashcode));

        ingredient.setBestBeforeDate(bestBeforeDate);
        ingredient.setUnit(unit);
        ingredient.setAmount(amount);
        ingredient.setCategory(category);
        ingredient.setDescription(description);
        ingredient.setLocation(location);
        ingredient.setName(name);

        ingredients.put(hashcode, ingredient);

        super.update(ingredient);
    }

    public void delete(Ingredient ingredient) {
        ingredients.remove(ingredient.getHashcode());

        super.delete(ingredient);
    }

    /**
     * Appended all the data from database into the collection.
     */
    @Override
    public void update(Observable o, Object arg) {
        assert (arg instanceof Ingredient[]);

        Ingredient[] ingredients = (Ingredient[]) arg;

        for (Ingredient ingredient : ingredients) {
            this.ingredients.put(ingredient.getHashcode(), ingredient);
        }

        notifyObservers();
    }
}
