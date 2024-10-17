package com.rikzah.QuickCuisine.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import java.io.Serializable;

public class RestrntRep_MainActivity extends AppCompatActivity {
    Button adddish,removedish;
    LottieAnimationView animationview65, addproductanimation,remoevproductanimation;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrnt_rep_main);

        animationview65=findViewById(R.id.AnimationView65);
        animationview65.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview65.playAnimation();

        addproductanimation=findViewById(R.id.AnimationViewadd);
        addproductanimation.animate().translationX(0).setDuration(88888).setStartDelay(0);
        addproductanimation.playAnimation();

        remoevproductanimation=findViewById(R.id.AnimationViewremove);
        remoevproductanimation.animate().translationX(0).setDuration(88888).setStartDelay(0);
        remoevproductanimation.playAnimation();

        adddish=findViewById(R.id.Adddish);
        removedish=findViewById(R.id.removedish);
        adddish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RestrntRep_MainActivity.this, RestrntRepEdit_Activity.class);
                intent.putExtra("function","add dish");
                startActivity(intent);
            }
        });

       removedish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RestrntRep_MainActivity.this, RestrntRepEdit_Activity.class);
                intent.putExtra("function","remove dish");
                startActivity(intent);
            }
        });


    }
}