package com.yc.redguess.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FileUtil {

    private static String uuid = "";

    public static void setUuid(String uuid) {
        FileUtil.uuid = uuid;
    }

    ///< 读取输入流
    public static String readString(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) {
            result.append(line + "\n");
        }
        return result.toString();
    }
}
