package com.example.nosh.database;


import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;


public abstract class DatabaseAccessController {

    final protected CollectionReference ref;

    DatabaseAccessController(CollectionReference ref) {
        this.ref = ref;
    }

    abstract public void create(Object o);

    abstract public void retrieve(Object o);

    abstract public ArrayList<Object> retrieve();

    abstract public void update(Object o);

    abstract public void remove(Object o);
}
