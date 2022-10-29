package com.example.nosh.database.Initializer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.example.nosh.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;


public class FirebaseAuthInitializer implements Initializer<FirebaseAuth> {

    @NonNull
    @Override
    public FirebaseAuth create(@NonNull Context context) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        if (BuildConfig.DEBUG) {
            fAuth.useEmulator("10.0.2.2", 9099);
        }

        return fAuth;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
