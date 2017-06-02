package com.example.sloth.slothapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private String jwt;

    public Session (Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("slothapp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean loggedIn){ ///, String jwt) {
        editor.putBoolean("loggedInMode", loggedIn);
        //this.jwt = jwt;
        editor.commit();
    }

    public boolean loggedIn() {
        return sharedPreferences.getBoolean("loggedInMode", false);
    }
}
