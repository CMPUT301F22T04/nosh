package com.example.nosh.database.sources;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class IngredientRemoteSource {

    private final CollectionReference source;

    public IngredientRemoteSource(FirebaseFirestore firestore, String path) {
        source = firestore.collection(path);
    }

    public CollectionReference getSource() {
        return source;
    }
}
