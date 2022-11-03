package com.example.nosh.database.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class FirebaseStorageController {

    // Directory of a current user
    private final StorageReference rootRef;
    private final Context context;

    public FirebaseStorageController(Context context, StorageReference ref) {
        this.context = context;
        this.rootRef = ref;
        this.sync();
    }

    public void add(Uri image) {

        StorageReference ref = rootRef.child(image.getLastPathSegment());

        UploadTask uploadTask = ref.putFile(image);

        uploadTask
                .addOnSuccessListener(
                taskSnapshot ->
                        Log.d("Upload Image", "Upload successfully")
        )
                .addOnFailureListener(
                        e ->
                                Log.w("Upload Image", e)
                );
    }

    /**
     * Get all URLs of images in Firebase storage for the current user
     */
    private void sync() {
        rootRef.listAll().addOnSuccessListener(listResult ->
        {
            try {
                cacheIn(listResult.getItems());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void cacheIn(List<StorageReference> refs) throws IOException {
        for (StorageReference ref : refs) {
            // Current file structure : /[uid]/...
            String[] filename = ref.getPath().split("/")[2].split("\\.");
            File tempFile = File.createTempFile(filename[0], filename[1]);

            // Default location seems to be at DCIM folder
            ref.getFile(tempFile)
                    .addOnSuccessListener(
                            taskSnapshot -> Log.d("Download", "Download Successfully")
                    )
                    .addOnFailureListener(
                            e -> Log.w("Download", e)
                    );
        }
    }
}
