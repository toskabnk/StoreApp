package com.svalero.storeapp.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.svalero.storeapp.domain.Favourites;

import java.util.List;

@Dao
public interface FavouritesDAO {
    @Query("SELECT * FROM favourites WHERE idProduct=:id AND username=:username")
    Favourites getFavourite(long id, String username);

    @Query("SELECT * FROM favourites")
    List<Favourites> getAllFavourites();

    @Query("SELECT * FROM favourites WHERE username=:username")
    List<Favourites> getFavouritesByUsername(String username);

    @Insert
    void insert(Favourites favourites);

    @Delete
    void delete(Favourites favourites);
}
