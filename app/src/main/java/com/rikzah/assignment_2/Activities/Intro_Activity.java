package com.rikzah.assignment_2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.rikzah.assignment_2.MainActivity;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.adapters.OnboardingJavaAdapter;

import java.util.ArrayList;
import java.util.List;


public class Intro_Activity extends AppCompatActivity {
    private ViewPager screenPager;
    OnboardingJavaAdapter javaAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted,btn_skip;
    Animation btnAnim ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();}

        //when this activity is about to be launched, check if it's opened before or not
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();}



                setContentView(R.layout.activity_intro);


                // ini views
                btnNext = findViewById(R.id.btn_next);
                btnGetStarted = findViewById(R.id.btn_get_started);
                tabIndicator = findViewById(R.id.tab_indicator);
                btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
                btn_skip = findViewById(R.id.btn_skip);

                // fill list screen
                final List<ScreenItem> mList = new ArrayList<>();
                mList.add(new ScreenItem("Select a Restaurant","Browse through our extensive list of restaurants and dishes.",R.drawable.restaurantintro));
                mList.add(new ScreenItem("Order Food you like","When you are ready to order, simply add your desired items to your cart and checkout. Its that easy!",R.drawable.foodintro));
                mList.add(new ScreenItem("Deliver to your Home","Get your favorite meals delivered to your doorstep for free with our online food delivery app.",R.drawable.deliveryintro));

                // setup viewpager
                screenPager =findViewById(R.id.screen_viewpager);
                javaAdapter = new OnboardingJavaAdapter(this,mList);
                screenPager.setAdapter(javaAdapter);


                // setup tablayout with viewpager
                tabIndicator.setupWithViewPager(screenPager);

                // next button click Listner
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position = screenPager.getCurrentItem();
                        if (position < mList.size()) {
                            position++;
                            screenPager.setCurrentItem(position);}

                        if (position == mList.size()-1) { // when we rech to the last screen
                            // TODO : show the GETSTARTED Button and hide the indicator and the next button
                            loaddLastScreen();

                        }}});
                // tablayout add change listener
                tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getPosition() == mList.size()-1) loaddLastScreen();
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }});

                // Get Started button click listener
                btnGetStarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //open main activity
                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                        // also we need to save a boolean value to storage so next time when the user run the app
                        // we could know that he is already checked the intro screen activity
                        // i'm going to use shared preferences to that process
                        savePrefsData();
                        finish();
                    }});

                // skip button click listener
                btn_skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        screenPager.setCurrentItem(mList.size());
                    }});}
            private boolean restorePrefData() {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
                return  isIntroActivityOpnendBefore;}
            private void savePrefsData() {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isIntroOpnend",true);
                editor.commit();}

            // show the GETSTARTED Button and hide the indicator and the next button
            private void loaddLastScreen() {
                if (btnNext != null) {
                    btnNext.setVisibility(View.INVISIBLE);}
                if (btnGetStarted != null) {
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnGetStarted.setAnimation(btnAnim);}
                if (btn_skip != null) {
                    btn_skip.setVisibility(View.INVISIBLE);}
                if (tabIndicator != null) {
                    tabIndicator.setVisibility(View.INVISIBLE);}
    }
}