package com.example.course_keeper_capstone.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {

    // Validate Dates
    public static boolean checkDate(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
            Date endD = sdf.parse(endDate);
            Date startD = sdf.parse(startDate);
            assert endD != null;
            return endD.after(startD);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Set date to calender instance
     *
     * @param calendar   Calendar Instance
     * @param dateString date in Stwyertewdring format
     */
    public static void setDate(Calendar calendar, String dateString) {
        if (!TextUtils.isEmpty(dateString) && dateString.length() == Constants.DATE_FORMAT.length()) {
            SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
            try {
                Date date = format.parse(dateString);
                calendar.setTime(date);
            } catch (ParseException ignore) {
            }
        }
    }


    public static class LoginPrefs {
        private static final String IS_LOGGED_IN = "IS_LOGGED_IN_KEY";
        private static final String USER_ID = "USER_ID_KEY";

        public static SharedPreferences getSharedPrefs(Context context) {
            SharedPreferences prefs = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
            return prefs;
        }

        public static void setLoggedOut(Context context) {
            getSharedPrefs(context).edit()
                    .putBoolean(IS_LOGGED_IN, false)
                    .apply();
        }

        public static void setLoggedIn(Context context, boolean isLoggedIn, int userId) {
            getSharedPrefs(context).edit()
                    .putBoolean(IS_LOGGED_IN, isLoggedIn)
                    .putInt(USER_ID, userId)
                    .apply();
        }

        public static boolean isLoggedIn(Context context) {
            return getSharedPrefs(context).getBoolean(IS_LOGGED_IN, false);
        }

        public static int getCurrentUserId(Context context) {
            return getSharedPrefs(context).getInt(USER_ID, -1);
        }

        public static void saveUserId(Context context, int userId) {
            getSharedPrefs(context).edit()
                    .putInt(USER_ID, userId)
                    .apply();
        }
    }


    public static String getHashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(password.getBytes());
            byte[] mdArray = md.digest();
            StringBuilder sb = new StringBuilder(mdArray.length * 2);
            for (byte b : mdArray) {
                int v = b & 0xff;
                if (v < 16)
                    sb.append('0');
                sb.append(Integer.toHexString(v));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }
}
