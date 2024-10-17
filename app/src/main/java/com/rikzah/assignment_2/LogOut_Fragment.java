package com.rikzah.assignment_2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rikzah.assignment_2.Activities.Login_SignUp_Activity;


public class LogOut_Fragment extends Fragment {


LottieAnimationView animationview555;
FirebaseAuth auth;
Button Logout;
TextView userid;

LottieAnimationView progressbar;
LinearLayout layout1;

    public LogOut_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_log_out_,container,false);

        layout1=root.findViewById(R.id.layout1);
        progressbar =root.findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(2000).setStartDelay(2900);
        progressbar.playAnimation();

        progressbar.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);

        if((FirebaseAuth.getInstance().getCurrentUser())==null){
            //user not logged in
            customToast.showCustomToast(getActivity(), "SignIn to enjoy QuickCuisne experience");
            startActivity(new Intent(getActivity(), Login_SignUp_Activity.class));
            return root;  // Return early if the user is not authenticated
        }


        auth=FirebaseAuth.getInstance();
        Logout=root.findViewById(R.id.logoutButton);
        userid=root.findViewById(R.id.usertext);
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            userid.setText(user.getEmail());

            progressbar.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
        }

        animationview555=root.findViewById(R.id.AnimationView555);
        animationview555.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview555.playAnimation();

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                customToast.showCustomToast(getActivity(), "You have successfully Logged Out!");
                // Close the app
                if (getActivity() != null) {
                    getActivity().finishAffinity();
                }
            }
        });
        return root;

    }

}