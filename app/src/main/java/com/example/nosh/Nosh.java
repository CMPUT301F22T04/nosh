package com.example.nosh;

import android.app.Application;

import androidx.startup.AppInitializer;

import com.example.nosh.database.Initializer.FirebaseAuthInitializer;
import com.example.nosh.injection.ApplicationComponent;
import com.example.nosh.injection.DaggerApplicationComponent;


public class Nosh extends Application {

    // appComponent lives in the Application class to share its lifecycle
    ApplicationComponent appComponent = DaggerApplicationComponent.create();

    @Override
    public void onCreate() {
        AppInitializer.getInstance(this).initializeComponent(FirebaseAuthInitializer.class);

        super.onCreate();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
