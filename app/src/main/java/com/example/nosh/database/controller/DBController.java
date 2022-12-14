package com.example.nosh.database.controller;


import com.example.nosh.entity.Hashable;
import com.google.firebase.firestore.CollectionReference;

import java.util.Observable;


/**
 * This abstract class defines four main operations on databases and collections : 
 * create, retrieve, update, delete. Every class that inherits this abstract class 
 * must define the behaviors of four main operations on a collection for specifc 
 * type of data (ingredient, recipe, meal plan, etc.)
 *
 * This class implements the Observer Pattern to resolve the async behavior (latency) of
 * fetching data from the database (might also include realtime update with the 
 * database if time is permitted)
 *
 * @author Dekr0
 */
public abstract class DBController extends Observable {

    // In Firestore, each user will have a set of sub-collections
    // (ingredients, recipes, meal plans). The root collection of these sub-
    // collection is identify using user id generated by Firebase Authentication

    // A CollectionReference to a specific sub-collection a more concrete type of
    // DBController want to operate at.
    // Example:
    // IngredientDBController -> ingredients sub-collection
    // RecipeDBController -> recipes sub-collection

    static final String CREATE = "CREATE";
    static final String RETRIEVE = "RETRIEVE";
    static final String UPDATE = "UPDATE";
    static final String DELETE = "DELETE";

    final protected CollectionReference ref;

    DBController(CollectionReference ref) {
        this.ref = ref;
    }

    abstract public void create(Hashable o);

    public void retrieve(Hashable o) {
        throw new UnsupportedOperationException();
    }

    abstract public void retrieve();

    abstract public void update(Hashable o);

    abstract public void delete(Hashable o);
}
