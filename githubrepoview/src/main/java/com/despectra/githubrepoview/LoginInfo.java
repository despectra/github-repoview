package com.despectra.githubrepoview;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.despectra.githubrepoview.models.User;
import com.google.gson.Gson;

/**
 * Class for storing current logged user data in shared preferences
 */
public class LoginInfo {

    public static final String USER_DATA_KEY = "login";
    public static final String BASIC_AUTH_KEY = "authorization";

    /**
     * Writes user info and basic auth string in shared preferences.
     * Call it after successful login
     * @param context context to access shared prefs
     * @param user curently logged user data
     * @param basicAuthorization basic auth string
     */
    public static void persistLoggedUser(Context context, User user, String basicAuthorization) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String userData = gson.toJson(user);
        preferences.edit()
                .putString(USER_DATA_KEY, userData)
                .putString(BASIC_AUTH_KEY, basicAuthorization)
                .apply();
    }

    /**
     * Returns basic auth string of current user.
     * @param context context to access shared prefs
     * @return auth string
     */
    public static String getAuthorization(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(BASIC_AUTH_KEY, null);
    }

    /**
     *Returns currently logged user object.
     * @param context context to access shared prefs
     * @return user object
     */
    public static User getLoggedUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String userData = preferences.getString(USER_DATA_KEY, null);
        if(userData == null) {
            return null;
        }
        return gson.fromJson(userData, User.class);
    }

    /**
     * Removes all data on logged user.
     * Call it after logout
     * @param context context to access shared prefs
     */
    public static void clearLoggedUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit()
                .remove(USER_DATA_KEY)
                .remove(BASIC_AUTH_KEY)
                .apply();
    }

}
