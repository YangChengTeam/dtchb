package com.yc.jsdsp.utils;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActionBarHelper {

    public static void setActionBarVisible(AppCompatActivity appCompatActivity, boolean visible) {
        if (appCompatActivity != null) {
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                if (visible) {
                    actionBar.show();
                } else {
                    actionBar.hide();
                }
            }
        }
    }

}
