package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class EventCategory {
    @ColumnInfo(name = "categoryId")
    private String catId;
    @ColumnInfo(name = "categoryName")
    private String name;
    @ColumnInfo(name = "eventCount")
    private int count;
    @ColumnInfo(name = "isActive")
    private boolean isActive;

    private int finalCount;
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    private String catLocation;
    public EventCategory(String catId, String name, int count, boolean isActive, String catLocation) {
        this.catId = catId;
        this.name = name;
        this.count = count;
        this.isActive = isActive;
        this.finalCount = count;
        this.catLocation = catLocation;
    }

    public String getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }
    public int getCount() { return count; }

    public void setCount(int newCount){
        this.count = newCount;
    }
    public void resetCount(){
        this.count = finalCount;
    }
    public boolean getIsActive() {
        return isActive;
    }

    public int getId(){ return id;}
    public void setId(@NonNull int id) {
        this.id = id;
    }
    public String getCatLocation(){ return catLocation; }
    public void setCatLocation(String newLocation){
        this.catLocation = newLocation;
    }

    public int getFinalCount(){
        return finalCount;
    }
    public void setFinalCount(int newFinalCount){
        finalCount = newFinalCount;
    }
}
