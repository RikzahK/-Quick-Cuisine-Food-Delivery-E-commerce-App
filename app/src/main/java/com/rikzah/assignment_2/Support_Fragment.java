package com.rikzah.assignment_2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.rikzah.assignment_2.adapters.SupportAdapter;
import com.rikzah.assignment_2.models.SupportModelClass;

import java.util.ArrayList;
import java.util.List;

public class Support_Fragment extends Fragment {
    RecyclerView support_rec;
    List<SupportModelClass> supportModelClassList;
    LottieAnimationView animationview300;

    public Support_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_support_,container,false);

        animationview300=root.findViewById(R.id.AnimationView300);
        animationview300.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview300.playAnimation();

        support_rec=root.findViewById(R.id.support_rec);
        support_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        supportModelClassList=new ArrayList<>();
        SupportAdapter supportAdapter= new SupportAdapter(supportModelClassList);
        support_rec.setAdapter(supportAdapter);
        support_rec.setHasFixedSize(true);
        supportModelClassList.add(new SupportModelClass("What is Quick Cuisine?"," Quick Cuisine is a food delivery app developed by Rikzah Khattal. It allows users to order a variety of dishes from different restaurants using their mobile devices."));
        supportModelClassList.add(new SupportModelClass("What does Quick Cuisine offer?","Quick Cuisine offers a diverse range of cuisines, including main course, diet, seafood, fast food, Italian, dessert, and more. Users can explore a selection of dishes from five different restaurants through the app."));
        supportModelClassList.add(new SupportModelClass(" How does Quick Cuisine work?","Users can browse through various categories, choose dishes from different restaurants, and place orders through the app. Quick Cuisine ensures convenient and reliable food delivery across all seven emirates of the UAE."));
        supportModelClassList.add(new SupportModelClass(" Is there an admin panel for Quick Cuisine?","Yes, Quick Cuisine features an admin panel that allows for the management of items, including the addition and removal of dishes. This panel enhances the overall functionality of the app."));
        supportModelClassList.add(new SupportModelClass("What are the payment options on Quick Cuisine?","Quick Cuisine provides users with the flexibility to pay using both cash and card payment methods, making the ordering process convenient for a wide range of users."));
        supportModelClassList.add(new SupportModelClass("What are the future development plans for Quick Cuisine?"," Quick Cuisine is actively working on integrating a customized AI chatbot into the app. The chatbot, powered by the Brainshop API, is currently undergoing testing and aims to enhance the user experience with intelligent interactions."));
        supportModelClassList.add(new SupportModelClass("Can I get more details about the AI chatbot in Quick Cuisine?","Certainly! While the AI chatbot is still in the testing phase, evidence of the work can be found in the project report. The chatbot is designed to provide users with a smart and interactive experience, although the final implementation is pending due to time constraints."));


        return root;
    }

}