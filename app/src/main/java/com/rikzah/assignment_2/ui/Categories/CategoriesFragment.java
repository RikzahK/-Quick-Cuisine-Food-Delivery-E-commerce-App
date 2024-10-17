package com.rikzah.assignment_2.ui.Categories;



import static androidx.recyclerview.widget.GridLayoutManager.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.adapters.HomeAdapters;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.models.HomeCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    ScrollView scrollView;
    RecyclerView recyclerView;
    List<HomeCategory> navCategoryList;
    HomeAdapters navhomeAdapters;
    FirebaseFirestore db;
    LottieAnimationView animationview5;
    LottieAnimationView progressbar;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories,container,false);
        db= FirebaseFirestore.getInstance();
        recyclerView=root.findViewById(R.id.navcategory_rec);
        scrollView=root.findViewById(R.id.layout1);
        animationview5=root.findViewById(R.id.AnimationView5);
        animationview5.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview5.playAnimation();
        progressbar =root.findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(2000).setStartDelay(2900);
        progressbar.playAnimation();
        progressbar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        // Home Category
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        navCategoryList= new ArrayList<>();
        navhomeAdapters=new HomeAdapters(getActivity(),navCategoryList);
        recyclerView.setAdapter(navhomeAdapters);
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                               navCategoryList.add(homeCategory);
                                navhomeAdapters.notifyDataSetChanged();
                                progressbar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);}
                        }else {
                            customToast.showCustomToast(getActivity(), "Error" + task.getException());
                        }}});
        return root;}}