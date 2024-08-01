package com.fit2081.assignment1.provider;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;
import com.fit2081.assignment1.EventCategory;

import java.util.List;
public class CategoryRepository {

    private CategoryDao mCustomerDao;
    private LiveData<List<EventCategory>> mAllCustomers;

    CategoryRepository(Application application) {
        CategoryDatabase db = CategoryDatabase.getDatabase(application);
        mCustomerDao = db.categoryDao();
        mAllCustomers = mCustomerDao.getAllCategories();
    }
    LiveData<List<EventCategory>> getAllCategories() {
        return mAllCustomers;
    }
    void insert(EventCategory category) {
        CategoryDatabase.databaseWriteExecutor.execute(() -> mCustomerDao.addCategory(category));
    }

    void deleteAll(){
        CategoryDatabase.databaseWriteExecutor.execute(()->{
            mCustomerDao.deleteAllCategories();
        });
    }

    void increaseCount(String categoryId){
        CategoryDatabase.databaseWriteExecutor.execute(() -> mCustomerDao.incrementEventCount(categoryId));
    }
    void decreaseCount(String categoryId){
        CategoryDatabase.databaseWriteExecutor.execute(() -> mCustomerDao.decrementEventCount(categoryId));
    }

    void resetCount(){
        CategoryDatabase.databaseWriteExecutor.execute(() -> mCustomerDao.resetEventCount());
    }

}
