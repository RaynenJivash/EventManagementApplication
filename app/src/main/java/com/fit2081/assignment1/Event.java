package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {

    @ColumnInfo(name = "eventId")
    private String eventId;

    @ColumnInfo(name = "eventName")
    private String name;
    @ColumnInfo(name = "catID")
    private String catId;
    @ColumnInfo(name = "eventCount")
    private int count;
    @ColumnInfo(name = "isActive")
    private boolean isActive;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    public Event(String eventId, String name, String catId, int count, boolean isActive) {
        this.eventId = eventId;
        this.name = name;
        this.catId = catId;
        this.count = count;
        this.isActive = isActive;
    }

    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }
    public String getCatId() {
        return catId;
    }
    public int getCount() { return count; }
    public boolean getIsActive() {
        return isActive;
    }

    public int getId(){ return id; }
    public void setId(@NonNull int id) {
        this.id = id;
    }
}
