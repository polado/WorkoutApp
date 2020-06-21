package com.example.workoutapp.utils;

import com.example.workoutapp.base.BaseView;

public class Utils {
    public static boolean isNotEmpty(String text, String msg, BaseView view) {
        if (text.trim().isEmpty()) {
            view.showErrorMsg(msg);
            return false;
        }
        return true;
    }
}
