package com.hon.librarytest.paging;

import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Frank_Hon on 9/21/2018.
 * E-mail: v-shhong@microsoft.com
 */

@Dao
public interface CheeseDao {

//    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
//    LivePagedListProvider<Integer,Cheese> allCheeseByName();

    @Insert
    void insert(List<Cheese> cheeses);

    @Insert
    void insert(Cheese cheese);

    @Insert
    void delete(Cheese cheese);
}
