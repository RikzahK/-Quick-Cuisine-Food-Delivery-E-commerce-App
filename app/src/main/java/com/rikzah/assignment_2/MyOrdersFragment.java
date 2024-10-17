package com.rikzah.assignment_2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rikzah.assignment_2.Activities.Login_SignUp_Activity;
import com.rikzah.assignment_2.adapters.MycartAdapter;
import com.rikzah.assignment_2.adapters.OrderHistoryAdapter;
import com.rikzah.assignment_2.models.MycartModel;
import com.rikzah.assignment_2.models.OrderHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {
    LottieAnimationView animationview9,animationView88;
    RecyclerView recyclerView1;
    OrderHistoryAdapter orderHistoryAdapter;
    List<OrderHistoryModel> orderHistoryModels;
    FirebaseFirestore db;
    FirebaseAuth auth;
    LottieAnimationView progressbar;
  LinearLayout layout2;
    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);
        layout2=root.findViewById(R.id.layout2);
        progressbar =root.findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(88888).setStartDelay(0);
        progressbar.playAnimation();

        progressbar.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);

        if((FirebaseAuth.getInstance().getCurrentUser())==null){
            //user not logged in
            customToast.showCustomToast(getActivity(), "SignIn to enjoy QuickCuisne experience");
            startActivity(new Intent(getActivity(), Login_SignUp_Activity.class));
            return root;  // Return early if the user is not authenticated
        }


        animationview9 = root.findViewById(R.id.AnimationView9);
        animationview9.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview9.playAnimation();

        animationView88 = root.findViewById(R.id.AnimationView88);
        animationView88.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationView88.playAnimation();


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView1 = root.findViewById(R.id.recyclerview1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderHistoryModels = new ArrayList<>();
        orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), orderHistoryModels);
        recyclerView1.setAdapter(orderHistoryAdapter);

            db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                String documentId = documentSnapshot.getId();
                                OrderHistoryModel orderHistoryModel = documentSnapshot.toObject(OrderHistoryModel.class);
                                orderHistoryModel.setDocumentId(documentId);
                                orderHistoryModels.add(orderHistoryModel);
                                orderHistoryAdapter.notifyDataSetChanged();

                                progressbar.setVisibility(View.GONE);
                                layout2.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        return root;
    }
}