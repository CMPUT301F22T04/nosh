package com.example.nosh.database;


import com.google.firebase.firestore.CollectionReference;

import java.util.Observable;


public abstract class DBController extends Observable {

    final protected CollectionReference ref;

    DBController(CollectionReference ref) {
        this.ref = ref;
    }

    abstract public void create(Object o);

    abstract public Object retrieve(Object o);

    abstract public void retrieve();

    abstract public void update(Object o);

    abstract public void delete(Object o);
}
