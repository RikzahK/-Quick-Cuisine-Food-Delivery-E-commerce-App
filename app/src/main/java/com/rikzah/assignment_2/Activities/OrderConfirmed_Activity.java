package com.rikzah.assignment_2.Activities;

import static com.rikzah.assignment_2.R.*;
import static com.rikzah.assignment_2.R.id.fragment_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rikzah.assignment_2.MainActivity;
import com.rikzah.assignment_2.MyCartFragment;
import com.rikzah.assignment_2.ui.home.HomeFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderConfirmed_Activity extends AppCompatActivity {
    LottieAnimationView animationview12;
    // Use androidx.fragment.app.FragmentManager





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_order_confirmed);


        animationview12 = findViewById(id.AnimationView12);
        // Start the translation animation
        animationview12.animate().translationX(0).setDuration(5000).setStartDelay(0);
        // Play the animation
        animationview12.playAnimation();
        // Delay the start of the next activity after 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity here using an Intent
                Intent intent= new Intent(OrderConfirmed_Activity.this, MainActivity.class);
                startActivity(intent);
                // Finish the current activity if needed
                finish();
            }
        }, 5000); // 5000 milliseconds (5 seconds) delay



    }
}