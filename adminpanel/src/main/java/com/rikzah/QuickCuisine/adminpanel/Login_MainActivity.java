package com.rikzah.QuickCuisine.adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_MainActivity extends AppCompatActivity {
    Button SignIN;
    EditText email, password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        SignIN=findViewById(R.id.loginButton);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        SignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();}});}
    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            customToast.showCustomToast(getApplicationContext(), "Email is Empty!");
            return;
        }if (TextUtils.isEmpty(userPassword)) {
            customToast.showCustomToast(getApplicationContext(), "Password is Empty!");
            return;
        }if (userPassword.length() < 6){
            customToast.showCustomToast(getApplicationContext(), "Password Length must be greater than 6 letter");}
        // Check if the entered credentials match the authorized credentials
        if (userEmail.equals("admin@quickcuisine.com") && userPassword.equals("admin@quickcuisine@12345")) {
            customToast.showCustomToast(getApplicationContext(), "Login Successful!");
            startActivity(new Intent( Login_MainActivity.this,RestrntRep_MainActivity.class));
            // Add your logic for authorized access here
        } else {
            // Unauthorized login, show a toast message
            // Unauthorized login, show a toast message
            customToast.showCustomToast(getApplicationContext(), "You are not authorized!");}}}