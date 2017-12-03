package com.androidacademy.persistenceshowcase.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.androidacademy.persistenceshowcase.Models.Film;

import java.util.List;

@Dao
public interface FilmDao {

    @Query("SELECT * FROM film")
    List<Film> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Film... films);

    @Delete
    void delete(Film film);

    @Query("DELETE FROM film")
    void deleteAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<Film> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM film WHERE title LIKE :title LIMIT 1")
    Film findByName(String title);
}
