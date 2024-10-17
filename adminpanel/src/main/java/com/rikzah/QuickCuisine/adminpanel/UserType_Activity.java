package com.rikzah.QuickCuisine.adminpanel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class UserType_Activity extends AppCompatActivity {

    LottieAnimationView animationview55;
    Button RestaurantRep;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

                animationview55=findViewById(R.id.AnimationView55);
                animationview55.animate().translationX(0).setDuration(88888).setStartDelay(0);
                animationview55.playAnimation();
                RestaurantRep = (Button)findViewById(R.id.RestaurantRep);
                RestaurantRep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Login= new Intent(UserType_Activity.this, Login_MainActivity.class);
                        startActivity(Login);
                    }});}}