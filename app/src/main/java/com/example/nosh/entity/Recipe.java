package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;


public class Recipe implements Hashable {

    double preparationTime;
    int servings;
    String category = "";
    String comments = "";
    String photographRemote = ""; // Image reference in storage
    String photographLocal = "";
    String title = "";

    ArrayList<Ingredient> ingredients = new ArrayList<>();

    String hashcode;

    public Recipe() {

    }

    public Recipe(double preparationTime, int servings, String category,
                  String comments, String photograph, String title,
                  ArrayList<Ingredient> ingredients) {
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
        this.photographRemote = photograph;
        this.title = title;

        this.ingredients = ingredients;

        hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPhotographRemote() {
        return photographRemote;
    }

    public void setPhotographRemote(String photographRemote) {
        this.photographRemote = photographRemote;
    }

    public String getPhotographLocal() {
        return photographLocal;
    }

    public void setPhotographLocal(String photographLocal) {
        this.photographLocal = photographLocal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
