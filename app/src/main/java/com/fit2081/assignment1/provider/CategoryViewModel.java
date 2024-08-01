package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.EventCategory;

import java.util.List;
public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository mRepository;
    private LiveData<List<EventCategory>> mAllCustomers;

    public CategoryViewModel(@NonNull Application application) {
        super(application);   //Previously was super(application) but error said it was redundant
        mRepository = new CategoryRepository(application);
        mAllCustomers = mRepository.getAllCategories();
    }

    public LiveData<List<EventCategory>> getAllCategories() {
        return mAllCustomers;
    }

    public void insert(EventCategory category) {
        mRepository.insert(category);
    }
    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void increaseCount(String categoryId){
        mRepository.increaseCount(categoryId);
    }

    public void decreaseCount(String categoryId){
        mRepository.decreaseCount(categoryId);
    }

    public void resetCount(){
        mRepository.resetCount();
    }

}
