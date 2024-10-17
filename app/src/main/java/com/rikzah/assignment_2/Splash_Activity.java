package com.rikzah.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.rikzah.assignment_2.Activities.Intro_Activity;

public class Splash_Activity extends AppCompatActivity {
    LottieAnimationView animationView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();}

        animationView2 = findViewById(R.id.AnimationView102);
        animationView2.animate().translationX(0).setDuration(2000).setStartDelay(2900);
        animationView2.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Go to the next activity after the second animation
                Intent i = new Intent(getApplicationContext(), Intro_Activity.class);
                startActivity(i);
                finish();
            }
        }, 5000); // Set the duration of the animation


    }
}









