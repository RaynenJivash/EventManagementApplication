package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fit2081.assignment1.provider.CategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;




public class NewEventCategoryActivity extends AppCompatActivity {



    Switch isActive;

    MyBroadCastReceiver myBroadCastReceiver;

    Gson gson = new Gson();

    ArrayList<EventCategory> data = new ArrayList<>();

    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Tokenize received message here
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            boolean isInt = false;

            try {
                StringTokenizer sT = new StringTokenizer(msg, ":");
                int count = sT.countTokens();
                if(count != 2){
                    throw new Exception("Exception message");
                }


                String tokenOne = sT.nextToken();
                String tokenTwo = sT.nextToken();


                char ch = ';';
                int frequency = 0;

                // If there are more than 2 semicolons throw an exception
                for(int i = 0; i < tokenTwo.length(); i++)
                {
                    if(ch == tokenTwo.charAt(i))
                    {
                        ++frequency;
                    }
                }

                if(frequency != 2){
                    throw new Exception("Exception found");
                }


                String[] tokenSplit = tokenTwo.split(";");

                if (!tokenOne.equals("category")){
                    throw new Exception("Exception message");
                }
                if (tokenSplit.length > 3){
                    throw new Exception("Exception message");
                }
                if(tokenSplit[0].equals("")){
                    throw new Exception("Exception message");
                }

                String categoryName = tokenSplit[0];
                if(tokenSplit.length>1 && !tokenSplit[1].equals("")) {

                    int eventCount = Integer.parseInt(tokenSplit[1]);
                    isInt = true;

                    if(eventCount<1){
                        throw new Exception("Exception message");
                    }

                }
                if(tokenSplit.length>2 && !tokenSplit[2].equals("")) {
                    String isActiveEvent = tokenSplit[2];
                    String isActiveUpper = isActiveEvent.toUpperCase();
                    isActive = findViewById(R.id.switch1);

                    if(!(isActiveUpper.equals("TRUE") || isActiveUpper.equals("FALSE"))){
                        throw new Exception("Exception message");
                    }
                    if (isActiveUpper.equals("TRUE")) {
                        isActive.setChecked(true);
                    } else {
                        isActive.setChecked(false);
                    }

                }

                //Updating UI

                TextView tvCatName = findViewById(R.id.editTextCatName);
                tvCatName.setText(categoryName);

                //If there is an int, set count
                if (isInt) {
                    int eventCount = Integer.parseInt(tokenSplit[1]);
                    TextView tvEventCount = findViewById(R.id.editTextEventCount);
                    tvEventCount.setText(String.valueOf(eventCount));
                }

            }
            catch (Exception e){
                //Error message
                String toastMessage = "Invalid message, not sure how to interpret that";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
            }

        }
        }


    int noCountIndicator = -1;  //Integer used to indicate that Event count input is not present (For saving in shared preferences)
    CategoryViewModel mCustomerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);


        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        myBroadCastReceiver = new MyBroadCastReceiver();
        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);

        retrieveCategoryData();
        mCustomerViewModel = new CategoryViewModel(getApplication());
    }


    public void onSaveCategoryButtonClick(View view){
        TextView tvCategoryId = findViewById(R.id.editTextCategoryId);
        String categoryId = generateCategoryId();

        TextView tvCatName = findViewById(R.id.editTextCatName);
        String catName = tvCatName.getText().toString();

        Switch isActive = findViewById(R.id.switch1);
        boolean isActiveBool = isActive.isChecked();

        TextView tvEventCount = findViewById(R.id.editTextEventCount);
        String eventCountString = tvEventCount.getText().toString();

        TextView tvCatLocation = findViewById(R.id.editTextLocation);
        String catLocationString = tvCatLocation.getText().toString();



        if(catName.equals("")){
            String toastMessage = "Category has to have a name";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        // A new string with all of the spaces of catName removed to perform validation check
        String checkName = catName.replaceAll("\\s", "");
        //Checking if AlphaNumeric
        boolean isAlnum = checkName.matches("[A-Za-z0-9]+");
        //Checking if only contains digits
        boolean isOnlyNum = checkName.matches("\\d+");
        if (!isAlnum || isOnlyNum){
            String toastMessage = "Invalid category name";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }



        else if (eventCountString.length() != 0){
            int eventCountInteger = Integer.parseInt(eventCountString);
            if(eventCountInteger >= 0) {

                EventCategory eventCategory = new EventCategory(categoryId, catName,eventCountInteger, isActiveBool, catLocationString);
                mCustomerViewModel.insert(eventCategory);
//                data.add(eventCategory);
//                saveArrayListAsText();


                String toastMessage = "Category saved successfully: " + categoryId;
                tvCategoryId.setText(categoryId);  //setText
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

                //Redirecting to Dashboard Activity
                finish();
            }
            else{
                String toastMessage = "Invalid Event Count";
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        }
        else if(catName.equals("")){
            String toastMessage = "Category has to have a name";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        }
        else{

            EventCategory eventCategory = new EventCategory(categoryId, catName,0, isActiveBool, catLocationString);
//            data.add(eventCategory);
            mCustomerViewModel.insert(eventCategory);


//            saveDataToSharedPreference(categoryId, catName, noCountIndicator, isActiveBool);
            String toastMessage = "Category saved successfully: " + categoryId;
            tvCategoryId.setText(categoryId);  //setText
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();


            finish();


        }

        System.out.println(data);
    }

    public String generateCategoryId(){
        Random random = new Random();
        String categoryId = "C";
        char randomChar1 = (char) (random.nextInt(26) +65);   //Generate a random uppercase letter
        char randomChar2 = (char) (random.nextInt(26) +65);   //Generate a random uppercase letter
        categoryId += randomChar1;
        categoryId += randomChar2;
        categoryId += "-";

        // Generate 4 random numbers
        for (int i = 0; i < 4; i++) {
            int randomNumber = random.nextInt(10);
            categoryId += randomNumber;
        }

        return categoryId;

    }


    // Called when activity is going on in the background
    @Override
    protected void onPause(){
        super.onPause();

        //Unregistering receiver
        unregisterReceiver(myBroadCastReceiver);
    }

    // Called when activity is visible again
    @Override
    protected void onResume(){
        super.onResume();

        //Registering receiver
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);

    }

    private void saveArrayListAsText() {
//        data.add(eventCategory);
        //Convert ArrayList to string
        String arrayListString = gson.toJson(data);

        //Saving to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("Category_List", MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();


        edit.putString("CATEGORIES", arrayListString);
        edit.apply();


//        Toast.makeText(this, arrayListString, Toast.LENGTH_SHORT).show();

    }


    //Method to update current data arrayList based on sharedPreferences
    public void retrieveCategoryData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Category_List", MODE_PRIVATE);
        String string = sharedPreferences.getString("CATEGORIES", null);
//        System.out.println(string);
        if (string != null) {
            Type type = new TypeToken<ArrayList<EventCategory>>() {
            }.getType();
             data = gson.fromJson(string, type);
        }


    }


}