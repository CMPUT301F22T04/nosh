package com.example.nosh.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;


public class DatabaseAccessFactoryInitializer implements Initializer<DatabaseAccessFactory> {

    @NonNull
    @Override
    public DatabaseAccessFactory create(@NonNull Context context) {
        return new DatabaseAccessFactory(FirebaseFirestore.getInstance());
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.singletonList(FirestoreInitializer.class);
    }
}
