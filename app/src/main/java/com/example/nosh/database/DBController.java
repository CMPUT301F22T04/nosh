package com.example.nosh.database;

import com.google.firebase.firestore.CollectionReference;


public abstract class DBController {

    final private CollectionReference ref;

    DBController(CollectionReference ref) {
        this.ref = ref;
    }
}
