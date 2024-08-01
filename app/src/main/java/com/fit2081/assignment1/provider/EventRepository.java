package com.fit2081.assignment1.provider;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;

import java.util.List;
public class EventRepository {

    private EventDao mCustomerDao;
    private LiveData<List<Event>> mAllCustomers;

    EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        mCustomerDao = db.eventDao();
        mAllCustomers = mCustomerDao.getAllCustomer();
    }
    LiveData<List<Event>> getAllEvents() {
        return mAllCustomers;
    }
    void insert(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> mCustomerDao.addEvent(event));
    }

    void deleteAll(){
        EventDatabase.databaseWriteExecutor.execute(()->{
            mCustomerDao.deleteAllCustomers();
        });
    }

    void deleteEvent(String eventId){
        EventDatabase.databaseWriteExecutor.execute(() -> mCustomerDao.deleteCustomer(eventId));
    }

}
