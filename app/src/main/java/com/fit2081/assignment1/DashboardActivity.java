package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment1.provider.CategoryViewModel;
import com.fit2081.assignment1.provider.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    int noCountIndicator = -1;  //Integer used to indicate that Event count input is not present

    Gson gson = new Gson();

    ArrayList<Event> data = new ArrayList<>();

    FragmentManager fragmentManager;
    FragmentListCategory categoryFragment;
    CategoryViewModel mCategoryViewModel;
    EventViewModel mEventViewModel;
    RecyclerView.Adapter adapter;

    private GestureDetector mDetector;
    private ScaleGestureDetector mScaleDetector;

    View touchpad;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);


        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        FloatingActionButton fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());


        fragmentManager = getSupportFragmentManager();
        categoryFragment = new FragmentListCategory();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("my_backstack");
        transaction.commit();

        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        touchpad = findViewById(R.id.view);

        MyGestureListener listener = new MyGestureListener();
        mDetector = new GestureDetector(this, listener);
        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);

                return true;
            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call method to save event
                onSaveEventButtonClick(view);
            }
        });

    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{


        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            onSaveEventButtonClick(touchpad);
            TextView touchPadTv = findViewById(R.id.touchPadTv);
            touchPadTv.setText("OnDoubleTap");
            return true;
        }


        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            // Setting all
            TextView tvEventId = findViewById(R.id.editTextEventId);
            tvEventId.setText("");

            TextView tvEventName = findViewById(R.id.editTextEventName);
            tvEventName.setText("");

            TextView tvCatID = findViewById(R.id.editTextCatId);
            tvCatID.setText("");

            TextView tvTicketQty = findViewById(R.id.editTextTicketQty);
            tvTicketQty.setText("");

            Switch isActive = findViewById(R.id.switch2);
            isActive.setChecked(false);

            TextView touchPadTv = findViewById(R.id.touchPadTv);
            touchPadTv.setText("OnLongPress");
        }
    }


    public void onAddCategoryButtonClick(){
        Intent intent = new Intent(this, NewEventCategoryActivity.class);
        //launch the activity using startActivity method
        startActivity(intent);

    }
    public void onLogoutButtonClick(){
        Intent intent = new Intent(this, LoginActivity.class);
        //launch the activity using startActivity method
        startActivity(intent);
        finish();

    }

    public void onViewAllCategoriesClick(){
        Intent intent = new Intent(this, ListCategoryActivity.class);
        //launch the activity using startActivity method
        startActivity(intent);

    }

    public void onViewAllEventsClick(){
        Intent intent = new Intent(this, ListEventActivity.class);
        //launch the activity using startActivity method
        startActivity(intent);

    }
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.item_id_1) {
                onViewAllCategoriesClick();

            } else if (id == R.id.item_id_2) {
                onAddCategoryButtonClick();
            }
            else if (id == R.id.item_id_3) {
                onViewAllEventsClick();
            }
            else if (id == R.id.item_id_4) {
                onLogoutButtonClick();
            }
            // close the drawer
            drawerlayout.closeDrawers();
            // tell the OS
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.option_item_id_1) {
            //Refreshing

            FragmentListCategory fragmentListCategory = (FragmentListCategory) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView4);
            if (fragmentListCategory != null){
                fragmentListCategory.notifyAdapter();
            }

        } else if (id == R.id.option_item_id_2) {
            // Setting all
            TextView tvEventId = findViewById(R.id.editTextEventId);
            tvEventId.setText("");

            TextView tvEventName = findViewById(R.id.editTextEventName);
            tvEventName.setText("");

            TextView tvCatID = findViewById(R.id.editTextCatId);
            tvCatID.setText("");

            TextView tvTicketQty = findViewById(R.id.editTextTicketQty);
            tvTicketQty.setText("");

            Switch isActive = findViewById(R.id.switch2);
            isActive.setChecked(false);
        }

        else if (id == R.id.option_item_id_3){

//            mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
            mCategoryViewModel.deleteAll();

        }
        else if (id == R.id.option_item_id_4){
             mEventViewModel.deleteAll();
             mCategoryViewModel.resetCount();

        }
        // tell the OS
        return true;
    }




    public String generateEventId(){
        Random random = new Random();
        String categoryId = "E";
        char randomChar1 = (char) (random.nextInt(26) +65);   //Generate a random uppercase letter
        char randomChar2 = (char) (random.nextInt(26) +65);   //Generate a random uppercase letter
        categoryId += randomChar1;
        categoryId += randomChar2;
        categoryId += "-";

        // Generate 4 random numbers
        for (int i = 0; i < 5; i++) {
            int randomNumber = random.nextInt(10);
            categoryId += randomNumber;
        }

        return categoryId;

    }

    private void saveArrayListAsText() {
        //Convert ArrayList to string
        String arrayListString = gson.toJson(data);

        //Saving to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("Event_List", MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putString("EVENTS", arrayListString);
        edit.apply();

//        Toast.makeText(this, arrayListString, Toast.LENGTH_SHORT).show();

    }


    public void onSaveEventButtonClick(View view){
        TextView tvEventId = findViewById(R.id.editTextEventId);
        String eventId = generateEventId();


        TextView tvEventName = findViewById(R.id.editTextEventName);
        String eventName = tvEventName.getText().toString();

        TextView tvCatID = findViewById(R.id.editTextCatId);
        String categoryId = tvCatID.getText().toString();

        Switch isActive = findViewById(R.id.switch2);
        boolean isActiveBool = isActive.isChecked();


        boolean notValid = eventName.equals("") || categoryId.equals("");


        AtomicBoolean noMatch = new AtomicBoolean(true);
        mCategoryViewModel.getAllCategories().observe(this, newData -> {

                for (EventCategory cat : newData){
//                    Toast.makeText(this, "RUNS1", Toast.LENGTH_SHORT).show();
                    if (categoryId.equals(cat.getCatId())){
//                        Toast.makeText(this, "RUNS2", Toast.LENGTH_SHORT).show();
                        noMatch.set(false);
                    }

                }
//            if (noMatch.get()) {
//                String toastMessage = "Category does not exist";
//                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
//                return;
//            }
        });
//




        // If noMatch, the specified category does not exist

        if (noMatch.get()) {
            String toastMessage = "Category does not exist";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            return;
        }



        // A new string with all of the spaces of eventName removed to perform validation check
        String checkName = eventName.replaceAll("\\s", "");
        //Checking if AlphaNumeric
        boolean isAlnum = checkName.matches("[A-Za-z0-9]+");
        //Checking if only contains digits
        boolean isOnlyNum = checkName.matches("\\d+");
        if (!isAlnum || isOnlyNum){
            String toastMessage = "Invalid event name ";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        TextView tvTicketQty = findViewById(R.id.editTextTicketQty);
        String ticketQtyString = tvTicketQty.getText().toString();

        if (ticketQtyString.length() != 0 && !notValid){
            int ticketQtyInteger = Integer.parseInt(ticketQtyString);
            if(ticketQtyInteger >= 0) {
                Event event = new Event(eventId, eventName, categoryId, ticketQtyInteger, isActiveBool);

//                String toastMessage = String.format("Event saved: %s to %s", eventId, categoryId);
                tvEventId.setText(eventId);  //setText
//                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
                mEventViewModel.insert(event);
                mCategoryViewModel.increaseCount(categoryId);

//                data.add(event);
//                saveArrayListAsText();


                //Displaying Snackbar
                Snackbar.make(view, String.format("Event saved: %s to %s", eventId, categoryId), Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new MyUndoListener()).show();

            }
            else{
                String toastMessage = "Invalid Tickets available";
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        }
        else if(eventName.equals("") || categoryId.equals("")){
            String toastMessage = "Event and Category ID cannot be empty";
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        }
        else{
            //If no event count, use pass noCountIndicator as parameter
            Event event = new Event(eventId, eventName, categoryId, 0, isActiveBool);

            String toastMessage = String.format("Event saved: %s to %s", eventId, categoryId);
            tvEventId.setText(eventId);  //setText
//            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            mEventViewModel.insert(event);
            mCategoryViewModel.increaseCount(categoryId);



            //Displaying Snackbar
            Snackbar.make(view, String.format("Event saved: %s to %s", eventId, categoryId), Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new MyUndoListener()).show();
        }
    }


    public class MyUndoListener implements View.OnClickListener {

        //Removing the last item added to the data ArrayList
        @Override
        public void onClick(View v) {

            TextView tvCatID = findViewById(R.id.editTextCatId);
            String categoryId = tvCatID.getText().toString();
            TextView tvEventID = findViewById(R.id.editTextEventId);
            String eventId = tvEventID.getText().toString();

            mEventViewModel.deleteEvent(eventId);
            mCategoryViewModel.decreaseCount(categoryId);

        }
    }
}