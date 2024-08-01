package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onRestoreButtonClick();

    }


    public void onRegisterButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //launch the activity using startActivity method
        startActivity(intent);


    }

    public void onLoginButtonClick(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        String usernameRestored = sharedPreferences.getString("KEY_USERNAME", "DEFAULT VALUE");
        String passwordRestored = sharedPreferences.getString("KEY_PASSWORD", "DEFAULT VALUE");


        TextView tvUsername = findViewById(R.id.editTextLoginUsername);
        TextView tvPassword = findViewById(R.id.editTextTextPassword);

        String usernameString = tvUsername.getText().toString();
        String passwordString = tvPassword.getText().toString();


        if (usernameRestored.equals(usernameString) && passwordRestored.equals(passwordString)){
            Intent intent = new Intent(this, DashboardActivity.class);
            //launch the activity using startActivity method
            startActivity(intent);
        }
        else{
            String toastMessage = "Authentication failure: Username or Password incorrect";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }

    }

    public void onRestoreButtonClick(){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        // save key-value pairs to the shared preference file
        String usernameRestored = sharedPreferences.getString("KEY_USERNAME", "Username");


        // update the UI using retrieved values
        TextView tvName = findViewById(R.id.editTextLoginUsername);
        tvName.setText(usernameRestored);
    }
}