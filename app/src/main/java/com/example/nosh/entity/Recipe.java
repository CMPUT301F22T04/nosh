package com.example.nosh.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;


public class Recipe extends MealComponent implements Iterable<Ingredient> {

    private double preparationTime;
    private long servings;
    private String category = "";
    private String comments = "";
    private String photographRemote = ""; // Image reference in storage
    private String title = "";

    // TODO : Set this to Map for faster and easier access
    private HashMap<String, Ingredient> ingredients = new HashMap<>();

    public Recipe() {
        super();
    }

    public Recipe(double preparationTime, long servings, String category,
                  String comments, String photograph, String title,
                  ArrayList<Ingredient> ingredients) {

        this();
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
        this.photographRemote = photograph;
        this.title = title;

        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                this.ingredients.put(ingredient.hashcode, ingredient);
            }
        }
    }

    public Recipe(Recipe recipe) {
        preparationTime = recipe.getPreparationTime();
        servings = recipe.getServings();
        category = recipe.getCategory();
        comments = recipe.getComments();
        photographRemote = recipe.getPhotographRemote();
        title = recipe.getTitle();
        setIngredients(recipe.getIngredients());
        hashcode = recipe.getHashcode();
    }


    @NonNull
    @Override
    public Object clone() {
        return new Recipe(this);
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }

    public long getServings() {
        return servings;
    }

    public void setServings(long servings) {
        this.servings = servings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category != null) {
            this.category = category;
        }
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        if (category != null) {
            this.comments = comments;
        }
    }

    public String getPhotographRemote() {
        return photographRemote;
    }

    public void setPhotographRemote(String photographRemote) {
        if (photographRemote != null) {
            this.photographRemote = photographRemote;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    // For UIs : return a deep clone of all ingredients and perform different
    // type of operations (add, remove, edit) on top of it
    public ArrayList<Ingredient> getIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        for (Ingredient ingredient : this.ingredients.values()) {
            ingredients.add(new Ingredient(ingredient));
        }

        return ingredients;
    }

    public boolean hasIngredient(String hashcode) {
        return ingredients.containsKey(hashcode);
    }

    public void updateIngredient(Ingredient ingredient) {
        ingredients.replace(ingredient.hashcode, new Ingredient(ingredient));
    }

    // TODO : a better way to update ingredients instead of replace them entirely
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        if (ingredients != null) {
            this.ingredients = new HashMap<>();

            for (Ingredient ingredient :
                    ingredients) {
                this.ingredients.put(ingredient.hashcode, ingredient);
            }
        }
    }

    @Override
    public String getName() {
        return title;
    }

    @NonNull
    @Override
    public Iterator<Ingredient> iterator() {
        return getIngredients().iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super Ingredient> action) {
        Iterable.super.forEach(action);
    }
}
