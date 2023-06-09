package com.svalero.storeapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.storeapp.db.dao.FavouritesDAO;
import com.svalero.storeapp.db.dao.PersistDataDAO;
import com.svalero.storeapp.domain.Favourites;
import com.svalero.storeapp.domain.PersistData;

@Database(entities = {PersistData.class, Favourites.class}, version = 1)
public abstract class StoreAppDatabase extends RoomDatabase {
    public abstract PersistDataDAO getPersistDataDAO();
    public abstract FavouritesDAO getFavouriteDAO();
}
