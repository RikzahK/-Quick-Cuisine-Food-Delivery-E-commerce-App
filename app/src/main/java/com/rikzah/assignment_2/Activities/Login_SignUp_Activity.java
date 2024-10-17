package com.rikzah.assignment_2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.rikzah.assignment_2.R;

public class Login_SignUp_Activity extends AppCompatActivity {
    Button SignIn, SignUp;
    LottieAnimationView animationview3;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        SignIn=findViewById(R.id.SignwithEmail);
        SignUp=findViewById(R.id.SignUpbutton);

        animationview3 = findViewById(R.id.AnimationView3);
        animationview3.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview3.playAnimation();
        //progressBar=findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.GONE);
        auth=FirebaseAuth.getInstance();
       // if ((auth.getCurrentUser())!=null) {
            //progressBar.setVisibility(View.VISIBLE);
            // User is signed in, set the result to indicate success and finish the activity.
           // Toast.makeText(getApplicationContext(),"Please wait you are logged in", Toast.LENGTH_SHORT).show();
      //  }

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( Login_SignUp_Activity.this, SignUp_Activity.class));

            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( Login_SignUp_Activity.this, Login_Activity.class));

            }
        });
    }
}