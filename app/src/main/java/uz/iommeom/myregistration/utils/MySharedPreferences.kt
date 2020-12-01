package uz.iommeom.mycontact.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    private const val UNICAL_KEY = "KEY"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(UNICAL_KEY, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setUser(user: String) {
        editor.putString("user", user)
        editor.commit()
    }

    fun getUser(context: Context): String? {
        sharedPreferences = context.getSharedPreferences(UNICAL_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString("user", "")
    }
}