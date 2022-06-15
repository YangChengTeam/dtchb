package com.yc.jsdps.utils;


import android.util.Log;

import androidx.appcompat.view.menu.MenuAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MenuHelper {

    public static final String TAG = MenuHelper.class.getSimpleName();
    public static void updatePopupMenuItemLayout(int layoutResID) {
        try {
            setFinalStatic(MenuAdapter.class.getDeclaredField("ITEM_LAYOUT"), layoutResID);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        try {
            /* for java */
            /*Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);*/
            /* for android */
            Field modifiersField = Field.class.getDeclaredField("accessFlags");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        field.set(null, newValue);
    }

}
