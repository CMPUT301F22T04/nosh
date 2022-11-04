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
 * A general guideline to creat more concrete class from this abstract class:
 *  create a concrete class if one wants to access and operate on a collection for 
 * a specific data
 *  Example : one concrete class for Ingredient, one concrete class for Recipe, etc.
 *
 * This class implements the Observer Pattern to resolve the async behavior (latency) of 
 * fetching data from the database (might also include realtime update with the 
 * database if time is permitted)
 */
public abstract class DBController extends Observable {

    final protected CollectionReference ref;

    DBController(CollectionReference ref) {
        this.ref = ref;
    }

    abstract public void create(Hashable o);

    abstract public Object retrieve(Hashable o);

    abstract public void retrieve();

    abstract public void update(Hashable o);

    abstract public void delete(Hashable o);
}
