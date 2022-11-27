package com.example.nosh.repository;

import android.net.Uri;

import com.example.nosh.database.controller.FirebaseStorageController;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RecipeImageRepository extends Observable implements Observer {

    private final FirebaseStorageController storageController;
    private final HashMap<String, StorageReference> recipeImagesRemote;

    @Inject
    public RecipeImageRepository(FirebaseStorageController storageController) {
        this.storageController = storageController;
        this.storageController.addObserver(this);

        recipeImagesRemote = new HashMap<>();

        sync();
    }

    public String add(Uri photoUri) {
       StorageReference ref = storageController.add(photoUri);

       recipeImagesRemote.put(ref.getPath(), ref);

       notifyObservers();

       return ref.getPath();
    }

    public void sync() {
        storageController.sync();
    }


    public HashMap<String, StorageReference> getRecipeImagesRemote() {
        return recipeImagesRemote;
    }

    public StorageReference getRecipeImageRemote(String remoteLocation) {
        return recipeImagesRemote.get(remoteLocation);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof StorageReference[]) {
            StorageReference[] refs = (StorageReference[]) arg;

            for (StorageReference ref :
                    refs) {
                recipeImagesRemote.put(ref.getPath(), ref);
            }
        } else {
            throw new IllegalArgumentException("Error when retrieving images from Firebase storage");
        }

        setChanged();

        notifyObservers();
    }
}
