package com.example.objectbox;

import android.app.Application;

import com.example.objectbox.util.ObjectBox;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
        instance = this;
    }
    public static App getInstance(){
        return instance;
    }
}
