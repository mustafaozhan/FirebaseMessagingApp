package com.ozhan.mustafa.howl.utils


import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:15 PM.
 */

class SharedPrefUtil(private val mContext: Context) {
    private var mSharedPreferences: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    /**
     * Save a string into shared preference

     * @param key   The name of the preference to modify
     * *
     * @param value The new value for the preference
     */
    fun saveString(key: String, value: String) {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences!!.edit()
        mEditor!!.putString(key, value)
        mEditor!!.commit()
    }

    /**
     * Save a int into shared preference

     * @param key   The name of the preference to modify
     * *
     * @param value The new value for the preference
     */
    fun saveInt(key: String, value: Int) {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences!!.edit()
        mEditor!!.putInt(key, value)
        mEditor!!.commit()
    }

    /**
     * Save a boolean into shared preference

     * @param key   The name of the preference to modify
     * *
     * @param value The new value for the preference
     */
    fun saveBoolean(key: String, value: Boolean) {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences!!.edit()
        mEditor!!.putBoolean(key, value)
        mEditor!!.commit()
    }

    /**
     * Retrieve a String value from the preferences.

     * @param key The name of the preference to retrieve.
     * *
     * @return Returns the preference value if it exists, or null.
     * * Throws ClassCastException if there is a preference with this name that is not a String.
     */
    fun getString(key: String): String {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return mSharedPreferences!!.getString(key, null)
    }

    /**
     * Retrieve a String value from the preferences.

     * @param key          The name of the preference to retrieve.
     * *
     * @param defaultValue Value to return if this preference does not exist.
     * *
     * @return Returns the preference value if it exists, or defaultValue.
     * * Throws ClassCastException if there is a preference with this name that is not a String.
     */
    fun getString(key: String, defaultValue: String): String {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return mSharedPreferences!!.getString(key, defaultValue)
    }

    /**
     * Retrieve a int value from the preferences.

     * @param key The name of the preference to retrieve.
     * *
     * @return Returns the preference value if it exists, or 0.
     * * Throws ClassCastException if there is a preference with this name that is not a int.
     */
    fun getInt(key: String): Int {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return mSharedPreferences!!.getInt(key, 0)
    }

    /**
     * Retrieve a int value from the preferences.

     * @param key          The name of the preference to retrieve.
     * *
     * @param defaultValue Value to return if this preference does not exist.
     * *
     * @return Returns the preference value if it exists, or defaultValue.
     * * Throws ClassCastException if there is a preference with this name that is not a int.
     */
    fun getInt(key: String, defaultValue: Int): Int {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return mSharedPreferences!!.getInt(key, defaultValue)
    }

    /**
     * Retrieve a boolean value from the preferences.

     * @param key The name of the preference to retrieve.
     * *
     * @return Returns the preference value if it exists, or false.
     * * Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    fun getBoolean(key: String): Boolean {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return mSharedPreferences!!.getBoolean(key, false)
    }

    /**
     * Retrieve a boolean value from the preferences.

     * @param key          The name of the preference to retrieve.
     * *
     * @param defaultValue Value to return if this preference does not exist.
     * *
     * @return Returns the preference value if it exists, or defaultValue.
     * * Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return mSharedPreferences!!.getBoolean(key, defaultValue)
    }

    /**
     * Clears the shared preference file
     */
    fun clear() {
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        mSharedPreferences!!.edit().clear().apply()
    }

    companion object {
        /**
         * Name of the preference file
         */
        private val APP_PREFS = "application_preferences"
    }
}
