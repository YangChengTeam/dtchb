package com.yc.majiaredgrab.updata;

import android.content.Context;

import java.io.File;

public class PathUtils {
    public static String createDir(Context context, String dirName) {
        String baseDir = makeBaseDir(context);
        File dir = new File(baseDir + dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getAbsolutePath();
    }

    private static String makeBaseDir(Context context) {
        File dir = new File(context.getExternalFilesDir(null) + "/" + context.getPackageName());
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getAbsolutePath();
    }
}
