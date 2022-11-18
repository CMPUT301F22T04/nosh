package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;


public class Recipe extends MealComponent {

    private double preparationTime;
    private long servings;
    private String category = "";
    private String comments = "";
    private String photographRemote = ""; // Image reference in storage
    private String title = "";

    // TODO : Set this to Map for faster and easier access
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    String hashcode;

    public Recipe() {
        hashcode = Hashing
                .sha256()
                .hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
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
                this.ingredients.add(new Ingredient(ingredient));
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
        ingredients = recipe.getIngredients();
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

        for (Ingredient ingredient : this.ingredients) {
            ingredients.add(new Ingredient(ingredient));
        }

        return ingredients;
    }

    // TODO : a better way to update ingredients instead of replace them entirely
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        if (ingredients != null) {
            this.ingredients = new ArrayList<>();

            for (Ingredient ingredient :
                    ingredients) {
                this.ingredients.add(new Ingredient(ingredient));
            }
        }
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        if (hashcode != null) {
            this.hashcode = hashcode;
        }
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public void scale(int factor) {
        super.scale(factor);
    }
}
