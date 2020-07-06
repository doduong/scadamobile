package com.cntt.cnhp.scadamobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by DUONG on 29/11/2018
 */
public class SharedPref {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean autoCommit = true;

    public SharedPref(Context activity) {
        this.autoCommit = true;
        pref = activity.getSharedPreferences("SharePref", Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SharedPref(Context activity, String name) {
        this.autoCommit = true;
        pref = activity.getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SharedPref(Context activity, boolean autoCommit) {
        this.autoCommit = autoCommit;
        pref = activity.getSharedPreferences("SharePref", Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    // String----------------------------------------------------------------//
    public void putString(String key, String value) {
        editor.putString(key, value);
        if (autoCommit) {
            commit();
        }

    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    // Int------------------------------------------------------------------//
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        if (autoCommit) {
            commit();
        }
    }
    public void remove(String key) {
        editor.remove(key);
        if (autoCommit) {
            commit();
        }
    }

    public int getInt(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    // Long-----------------------------------------------------------------//
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public long getLong(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    // Float------------------------------------------------------------------//
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        if (autoCommit) {
            commit();
        }
    }

    public float getFloat(String key, float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }

    // Boolean-------------------------------------------------------------//
    public boolean getBoolean(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        if (autoCommit) {
            commit();
        }
    }

    // ListString----------------------------------------------------------------//
    public int putListString(String key, ArrayList<String> listString) {
        for (int i = 0; i < listString.size(); i++)
            editor.putString("List#String" + i + key, listString.get(i));
        if (autoCommit)
            commit();
        return listString.size();
    }

    public ArrayList<String> getListString(String key, int sizeList,
                                           String defaultValue) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < sizeList; i++)
            result.add(pref.getString("List#String" + i + key, defaultValue));
        return result;
    }

    // ListInt----------------------------------------------------------------//
    public int putListInt(String key, int[] listInt) {
        for (int i = 0; i < listInt.length; i++)
            editor.putInt("List#Int" + i + key, listInt[i]);
        if (autoCommit)
            commit();
        return listInt.length;
    }

    public int[] getListInt(String key, int sizeList, int defaultValue) {
        int[] result = new int[sizeList];
        for (int i = 0; i < sizeList; i++)
            result[i] = pref.getInt("List#Int" + i + key, defaultValue);
        return result;
    }

    // Commnit-------------------------------------------------------------//
    public void commit() {
        editor.commit();
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }
    // ---------------------------------------------------------------------//

}