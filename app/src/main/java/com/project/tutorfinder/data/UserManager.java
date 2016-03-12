package com.project.tutorfinder.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * A helper class for managing user data.
 */
public final class UserManager {

    private static final String USER_ID_KEY = "userId";
    private static final int NO_LOGGED_IN_USER_ID = -1;
    private SharedPreferences preferences;

    public UserManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getLoggedInUserId() {
        return preferences.getInt(USER_ID_KEY, NO_LOGGED_IN_USER_ID);
    }

    public boolean userLoggedIn() {
        return preferences.getInt(USER_ID_KEY, NO_LOGGED_IN_USER_ID) != NO_LOGGED_IN_USER_ID;
    }

    public boolean login(String userName, String password) {
        return true;
    }

    public void logout() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, NO_LOGGED_IN_USER_ID)
                .commit();
    }

    public boolean registerUser(String userName, String password, String phoneNumber, String
            address, double lat, double lon) {
        return true;
    }

}
