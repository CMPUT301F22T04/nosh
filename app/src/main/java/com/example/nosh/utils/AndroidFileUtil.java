package com.example.nosh.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;


public class AndroidFileUtil {

    public static void cacheIn(final File cache, final String path) {
        File preCacheFile = new File(path);
        File cacheFile = new File(cache, preCacheFile.getName());

        try {
            FileUtils.copy(new FileInputStream(preCacheFile), new FileOutputStream(cacheFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createSubfolder(final File rootFolder, String subfolderName) {
        File subfolder = new File(rootFolder, subfolderName);

        return subfolder.mkdirs();
    }

    public static String getFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    public static String resolvePath(final Context context, final Uri uri) {

        // Ensure Android SDK using is above 19

        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final String[] split = id.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
}
