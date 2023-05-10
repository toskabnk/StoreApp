package com.svalero.storeapp.util;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import android.app.Application;

import androidx.room.Room;

import com.svalero.storeapp.db.StoreAppDatabase;


public class Database extends Application {

    private StoreAppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), StoreAppDatabase.class, DATABASE_NAME).build();
    }

    public StoreAppDatabase getDatabase(){
        return database;
    }
}
