package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;

import java.util.List;
public class EventViewModel extends AndroidViewModel {
    private EventRepository mRepository;
    private LiveData<List<Event>> mAllCustomers;

    public EventViewModel(@NonNull Application application) {
        super(application);   //Previously was super(application) but error said it was redundant
        mRepository = new EventRepository(application);
        mAllCustomers = mRepository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return mAllCustomers;
    }

    public void insert(Event event) {
        mRepository.insert(event);
    }
    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteEvent(String eventId){
        mRepository.deleteEvent(eventId);
    }

}
