package com.example.nosh.database.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;


public class FirebaseStorageController extends Observable {

    // Directory of a current user
    private final StorageReference rootRef;
    private final Context context;

    public FirebaseStorageController(Context context, StorageReference ref) {
        this.context = context;
        this.rootRef = ref;
        this.sync();
    }

    public StorageReference add(Uri image) {

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

        return ref;
    }

    /**
     * Get all URLs of images in Firebase storage for the current user
     */
    public void sync() {
        rootRef.listAll().addOnSuccessListener(listResult -> {
            Log.d("sync", "Sync successfully");
            cacheRemote(listResult.getItems());
        });
    }

//    private void cacheIn(List<StorageReference> refs) throws IOException {
//        File cacheDir = context.getCacheDir();
//        File usrDir = new File(cacheDir, rootRef.getName());
//
//        usrDir.mkdirs();
//
//        File[] cachedImages = new File[refs.size()];
//
//        int i = 0;
//
//        for (StorageReference ref : refs) {
//            // Current file structure : /[uid]/...
//            String[] filename = ref.getPath().split("/")[2].split("\\.");
//            File tempFile = File.createTempFile(filename[0], "." + filename[1], usrDir);
//
//            // Save to cache folder in the internal app storage
//            // Path: /data/usr/0/com.example.nosh/cache
//            ref.getFile(tempFile)
//                    .addOnSuccessListener(
//                            taskSnapshot -> Log.d("Download", "Download Successfully")
//                    )
//                    .addOnFailureListener(
//                            e -> Log.w("Download", e)
//                    );
//
//            cachedImages[i++] = tempFile;
//        }
//
//        notifyObservers(cachedImages);
//    }

    private void cacheRemote(List<StorageReference> refs) {
        setChanged();

        StorageReference[] data = {};

        notifyObservers(refs.toArray(data));
    }
}
