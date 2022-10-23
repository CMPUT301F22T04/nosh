package com.example.nosh.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.example.nosh.BuildConfig;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;


public class FirestoreInitializer implements Initializer<FirebaseFirestore> {

    @NonNull
    @Override
    public FirebaseFirestore create(@NonNull Context context) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        if (BuildConfig.DEBUG) {
            fStore.useEmulator("10.0.2.2", 8080);
        }

        return fStore;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
