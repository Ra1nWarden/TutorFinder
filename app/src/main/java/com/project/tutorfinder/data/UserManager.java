package com.project.tutorfinder.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * A helper class for managing user data.
 */
public final class UserManager {

    private static final String TAG = "UserManager";
    private static final String USER_ID_KEY = "userId";
    private static final int NO_LOGGED_IN_USER_ID = -1;
    private static final String RAW_QUERY = "select * from users where _id = ?";
    private SharedPreferences preferences;
    private DatabaseOpenHelper databaseOpenHelper;

    public UserManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    public int getLoggedInUserId() {
        return preferences.getInt(USER_ID_KEY, NO_LOGGED_IN_USER_ID);
    }

    public boolean userLoggedIn() {
        return preferences.getInt(USER_ID_KEY, NO_LOGGED_IN_USER_ID) != NO_LOGGED_IN_USER_ID;
    }

    public boolean login(String userName, String inputPassword) {
        Cursor cursor = databaseOpenHelper.getReadableDatabase().rawQuery("select " +
                "_id, password from users where username = ?", new String[]{userName});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String password = cursor.getString(cursor.getColumnIndex("password"));
            int newId = cursor.getInt(cursor.getColumnIndex("_id"));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(USER_ID_KEY, newId);
            editor.commit();
            return password.equals(inputPassword);
        }
        return false;
    }

    public void logout() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, NO_LOGGED_IN_USER_ID)
                .commit();
    }

    public boolean registerUser(String userName, String password, String realName, String
            phoneNumber, String address, double lat, double lon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", userName);
        contentValues.put("password", password);
        contentValues.put("realname", realName);
        contentValues.put("phone_number", phoneNumber);
        contentValues.put("address", address);
        contentValues.put("latitude", lat);
        contentValues.put("longitude", lon);
        long rowId = databaseOpenHelper.getWritableDatabase().insert("users", null,
                contentValues);
        boolean ret = rowId != -1;
        if (ret) {
            return login(userName, password);
        }
        return ret;
    }

    public String getLoggedInUserField(String fieldName) {
        return getLoggedInUserFieldForId(fieldName, getLoggedInUserId());
    }

    public String getLoggedInUserFieldForId(String fieldName, int userId) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(RAW_QUERY, new String[]{Integer.toString
                (userId)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(fieldName));
        } else {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "No item found in database.");
            }
            return null;
        }
    }

    public Location getLoggedInUserLocation() {
        int userId = getLoggedInUserId();
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(RAW_QUERY, new String[]{Integer.toString(userId)});
        cursor.moveToFirst();
        Location location = new Location("");
        location.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
        location.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
        return location;
    }

    public double getLoggedInUserLatitude() {
        int userId = getLoggedInUserId();
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(RAW_QUERY, new String[]{Integer.toString(userId)});
        cursor.moveToFirst();
        return cursor.getDouble(cursor.getColumnIndex("latitude"));
    }

    public double getLoggedInUserLongitude() {
        int userId = getLoggedInUserId();
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(RAW_QUERY, new String[]{Integer.toString(userId)});
        cursor.moveToFirst();
        return cursor.getDouble(cursor.getColumnIndex("longitude"));
    }

    public String getUsernameForId(int id) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from users where _id = ?", new
                String[]{Integer.toString(id)});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("username"));
    }

}
