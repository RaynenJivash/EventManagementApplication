package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        drawerlayout = findViewById(R.id.drawer_layout);
//        toolbar = findViewById(R.id.toolbar);

    }



    public void onRegisterButtonClick(View view){
        // get reference to the UI elements
        // findViewById method looks for elements by the Id we set on elements
        // and search for them on current Activity's UI
        TextView tvUsername = findViewById(R.id.editTextUsername);
        TextView tvPassword1 = findViewById(R.id.editTextPassword);
        TextView tvPassword2 = findViewById(R.id.editTextPassword2);

        // using the referenced UI elements we extract values into plain text format
        String usernameString = tvUsername.getText().toString();
        String passwordString1 = tvPassword1.getText().toString();
        String passwordString2 = tvPassword2.getText().toString();


        String message;
        // If password matches, login successful and data stored
        if(passwordString1.equals(passwordString2) && passwordString1.trim().length() != 0 && usernameString.trim().length() != 0 ){
            message = "Registration successful";
            saveDataToSharedPreference(usernameString, passwordString1);

            Intent intent = new Intent(this, LoginActivity.class);

            intent.putExtra("username", usernameString);
            // finally launch the activity using startActivity method
            startActivity(intent);

        }
        else{
            message = "Registration failed";
        }

        // format message to show retrieved student name and Id
        String toastMessage = String.format("%s", message);
        // display a Toast message using makeText method, with three parameters explained below
        // 1. context – The context to use. Usually your android.app.Application or android.app.Activity object.
        // 2. text – The text to show. Can be formatted text.
        // 3. duration – How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();


    }


    private void saveDataToSharedPreference(String username, String password){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        // use .edit function to access file using Editor variable
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save key-value pairs to the shared preference file
        editor.putString("KEY_USERNAME", username);
        editor.putString("KEY_PASSWORD", password);

        // use editor.apply() to save data to the file asynchronously (in background without freezing the UI)
        // doing in background is very common practice for any File Input/Output operations
        editor.apply();

    }

    public void onLoginButtonClick(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        //launch the activity using startActivity method
        startActivity(intent);

    }

//    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            // get the id of the selected item
//            int id = item.getItemId();
//
//            if (id == R.id.item_id_1) {
//                // Do something
//            } else if (id == R.id.item_id_2) {
//                // Do something
//            }
//            // close the drawer
//            drawerlayout.closeDrawers();
//            // tell the OS
//            return true;
//        }
//    }
    
}