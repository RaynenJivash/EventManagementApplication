package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import java.util.Random;
import java.util.StringTokenizer;

public class NewEventActivity extends AppCompatActivity {

    Switch isActive;
    MyBroadCastReceiver myBroadCastReceiver;

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
                // If there aren't 3 semicolons throw an exception
                for(int i = 0; i < tokenTwo.length(); i++)
                {
                    if(ch == tokenTwo.charAt(i))
                    {
                        ++frequency;
                    }}

                if(frequency != 3){
                    throw new Exception("Exception found");
                }




                String[] tokenSplit = tokenTwo.split(";");

                if (!tokenOne.equals("event")){
                    throw new Exception("Exception message");
                }
                if (tokenSplit.length < 2 || tokenSplit.length > 4){
                    throw new Exception("Exception message");
                }

                String eventName = tokenSplit[0];
                if(eventName.equals("")){
                    throw new Exception("Exception message");
                }


                if(tokenSplit.length>2 && !tokenSplit[2].equals("")) {

                    int eventCount = Integer.parseInt(tokenSplit[2]);
                    isInt = true;

                    if(eventCount<1){
                        throw new Exception("Exception message");
                    }

                }
                if(tokenSplit.length>3 && !tokenSplit[3].equals("")) {
                    String isActiveEvent = tokenSplit[3];
                    String isActiveUpper = isActiveEvent.toUpperCase();
                    isActive = findViewById(R.id.switch2);

                    if(!(isActiveUpper.equals("TRUE") || isActiveUpper.equals("FALSE"))){
                        throw new Exception("Exception message");
                    }
                    if (isActiveUpper.equals("TRUE")) {
                        isActive.setChecked(true);
                    } else {
                        isActive.setChecked(false);
                    }

                }

                TextView tvEventName = findViewById(R.id.editTextEventName);
                tvEventName.setText(eventName);
                TextView tvCatId = findViewById(R.id.editTextCatId);
                tvCatId.setText(tokenSplit[1]);


                //If there is an integer, set ticket quantity
                if (isInt) {
                    int eventCount = Integer.parseInt(tokenSplit[2]);
                    TextView tvTicketQty = findViewById(R.id.editTextTicketQty);
                    tvTicketQty.setText(String.valueOf(eventCount));
                }



            }
            catch (Exception e){
                //Error message
                String toastMessage = "Invalid message, not sure how to interpret that";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }


    int noCountIndicator = -1;  //Integer used to indicate that Event count input is not present

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        myBroadCastReceiver = new NewEventActivity.MyBroadCastReceiver();
        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);
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

        TextView tvTicketQty = findViewById(R.id.editTextTicketQty);
        String ticketQtyString = tvTicketQty.getText().toString();
        if (ticketQtyString.length() != 0 && !notValid){
            int ticketQtyInteger = Integer.parseInt(ticketQtyString);
            if(ticketQtyInteger >= 1) {
                saveDataToSharedPreference(eventId, eventName, categoryId, ticketQtyInteger, isActiveBool);
                String toastMessage = String.format("Event saved: %s to %s", eventId, categoryId);
                tvEventId.setText(eventId);  //setText
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            }
            else{
                String toastMessage = "Available tickets has to be greater than 0";
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        }
        else if(eventName.equals("") || categoryId.equals("")){
            String toastMessage = "Event and Category ID cannot be empty";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        }
        else{
            //If no event count, use pass noCoundIndicator as argument
            saveDataToSharedPreference(eventId, eventName, categoryId, noCountIndicator, isActiveBool);
            String toastMessage = String.format("Event saved: %s to %s", eventId, categoryId);
            tvEventId.setText(eventId);  //setText
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }

    }



    private void saveDataToSharedPreference(String eventId, String eventName, String categoryId, int ticketQty, boolean isActive){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        // use .edit function to access file using Editor variable
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save key-value pairs to the shared preference file
        editor.putString("KEY_EVENT_ID", eventId);
        editor.putString("KEY_EVENT_NAME", eventName);
        editor.putString("KEY_CATEGORY_ID", categoryId);
//        editor.putInt("KEY_TICKETS_AVAIL", ticketQty);
        editor.putBoolean("KEY_IS_ACTIVE", isActive);


        if(ticketQty != noCountIndicator){  //To allow no inputs given for Event count
            editor.putInt("KEY_TICKETS_AVAIL", ticketQty);
        }


        // use editor.apply() to save data to the file asynchronously (in background without freezing the UI)
        // doing in background is very common practice for any File Input/Output operations
        editor.apply();

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

        //registering receiver
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);

    }

}