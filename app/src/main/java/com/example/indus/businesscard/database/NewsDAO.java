package com.example.indus.businesscard.database;

import com.example.indus.businesscard.modeldto.NewsEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface NewsDAO {

    @Query("SELECT * FROM newstables")
    Observable<List<NewsEntity>> getAll();

    @Query("SELECT * FROM newstables WHERE id = :id")
    NewsEntity getNewsById(int id);

    @Query("SELECT * FROM newstables WHERE section LIKE :category")
    Single<List<NewsEntity>> getNewsByCategory(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsEntity> entities);

    @Delete
    void delete (NewsEntity newsEntity);

    @Query("DELETE FROM newstables")
    void deleteAll ();
}
