package com.yc.redevenlopes.utils;

import android.os.Environment;

import java.io.File;

public class PathUtils {

    public static String getExternalPicturesPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}
