package com.fit2081.assignment1.provider;


import com.fit2081.assignment1.Event;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Query("select * from events")
    LiveData<List<Event>> getAllCustomer();

    @Query("select * from events where eventName=:name")
    List<Event> getCustomer(String name);

    @Insert
    void addEvent(Event event);

    @Query("delete from events where eventId= :eventId")
    void deleteCustomer(String eventId); //Implement

    @Query("delete FROM events")
    void deleteAllCustomers();
}
