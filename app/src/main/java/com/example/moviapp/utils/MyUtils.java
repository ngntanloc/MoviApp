package com.example.moviapp.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class MyUtils {

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidPassword(String password, String confirmPassword) {
        boolean isValid = false;
        if (!password.isEmpty() || !password.equals("") || !confirmPassword.isEmpty() || !confirmPassword.equals("")) {
            if (password.equals(confirmPassword)) {
                isValid = true;
            } else {
                isValid = false;
            }
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isEmptyString(String string) {
        if (string.isEmpty()) {
            return true;
        }
        return false;
    }

}
