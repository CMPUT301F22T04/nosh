package com.example.nosh.injection;

import com.example.nosh.database.controller.FirebaseStorageController;

import dagger.Component;


@Component
public interface ApplicationComponent {

    void inject(FirebaseStorageController storageController);
}
