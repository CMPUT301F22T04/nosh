package com.example.nosh.repository;

import android.content.Context;
import android.net.Uri;

import com.example.nosh.database.controller.FirebaseStorageController;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


public class RecipeImageRepository extends Observable implements Observer {

    private final Context context;
    private final FirebaseStorageController storageController;
    private final HashMap<String, Uri> recipeImagesCache;
    private final HashMap<String, StorageReference> recipeImagesRemote;

    public RecipeImageRepository(Context context,
                                 FirebaseStorageController storageController) {
        this.context = context;
        this.storageController = storageController;
        this.storageController.addObserver(this);
        recipeImagesRemote = new HashMap<>();
        recipeImagesCache = new HashMap<>();
    }

    public String add(String photograph) {
       StorageReference ref = storageController.add(Uri.fromFile(new File(photograph)));

       recipeImagesRemote.put(ref.getPath(), ref);

       notifyObservers();

       return ref.getPath();
    }

    public void sync() {
        storageController.sync();
    }

    HashMap<String, Uri> getRecipeImagesCache() {
        return recipeImagesCache;
    }

    public HashMap<String, StorageReference> getRecipeImagesRemote() {
        return recipeImagesRemote;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof File[]) {

            File[] cachedInImages = (File[]) arg;

            for (File image :
                    cachedInImages) {
                String filename = image.getName().replaceAll("\\d", "");

                recipeImagesCache.put(filename, Uri.fromFile(image));
            }
        } else if (arg instanceof StorageReference[]) {
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
