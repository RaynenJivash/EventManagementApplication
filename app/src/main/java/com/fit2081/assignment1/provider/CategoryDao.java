package com.fit2081.assignment1.provider;


import com.fit2081.assignment1.EventCategory;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("select * from categories")
    LiveData<List<EventCategory>> getAllCategories();

    @Query("select * from categories where categoryName=:name")
    List<EventCategory> getCategory(String name);

    @Insert
    void addCategory(EventCategory category);

    @Query("delete from categories where categoryName= :name")
    void deleteCategory(String name);

    @Query("delete FROM categories")
    void deleteAllCategories();

    @Query("UPDATE categories SET eventCount = eventCount + 1 WHERE categoryId = :categoryId")
    void incrementEventCount(String categoryId);

    @Query("UPDATE categories SET eventCount = eventCount - 1 WHERE categoryId = :categoryId")
    void decrementEventCount(String categoryId);
    @Query("UPDATE categories SET eventCount = finalCount")
    void resetEventCount();
}
