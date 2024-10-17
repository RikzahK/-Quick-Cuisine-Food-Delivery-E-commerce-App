package com.rikzah.assignment_2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rikzah.assignment_2.Activities.ScreenItem;
import com.rikzah.assignment_2.adapters.OnboardingJavaAdapter;
import com.rikzah.assignment_2.adapters.RestaurantAdapter;
import com.rikzah.assignment_2.adapters.ViewAllAdapter;
import com.rikzah.assignment_2.models.RestaurantModel;
import com.rikzah.assignment_2.models.ViewAllModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RestaurantsFragment extends Fragment {
    LottieAnimationView progressBar;
    ScrollView scrollView;
    RecyclerView restaurantRec;
    FirebaseFirestore db;
    ImageSlider imageSlider;
    EditText searchbox;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewsearch;
    private ViewAllAdapter viewAllAdapter;

    LottieAnimationView animationview4;
    //Restaurants
    List<RestaurantModel> restaurantModelList;
    RestaurantAdapter restaurantAdapter;

    public RestaurantsFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_restaurants,container,false);
        db=FirebaseFirestore.getInstance();
        restaurantRec=root.findViewById(R.id.explorerestaurants_rec);
        scrollView=root.findViewById(R.id.scroll_view);
        progressBar =root.findViewById(R.id.progressbar);
        progressBar.animate().translationX(0).setDuration(88888).setStartDelay(0);
        progressBar.playAnimation();

        progressBar.setVisibility(View.VISIBLE);

        /**animationview4=root.findViewById(R.id.AnimationView4);
        animationview4.animate().translationX(0).setDuration(88888).setStartDelay(0);
         animationview4.playAnimation();**/




        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //Restaurants
        restaurantRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        restaurantModelList= new ArrayList<>();
        restaurantAdapter=new RestaurantAdapter(getActivity(),restaurantModelList);
        restaurantRec.setAdapter(restaurantAdapter);
        db.collection("Restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RestaurantModel restaurantModel = document.toObject(RestaurantModel.class);
                                restaurantModelList.add(restaurantModel);
                                restaurantAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);}
                        }else {
                            customToast.showCustomToast(getActivity(),"Error" + task.getException());
                        }
                    }
                });

        //search view
        recyclerViewsearch=root.findViewById(R.id.search_recrest);
        searchbox=root.findViewById(R.id.search_boxrest);
        viewAllModelList=new ArrayList<>();
        viewAllAdapter=new ViewAllAdapter(getContext(),viewAllModelList);
        recyclerViewsearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewsearch.setAdapter(viewAllAdapter);
        recyclerViewsearch.setHasFixedSize(true);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }else{
                    searchProduct(s.toString());
                }

            }
        });


        return root;
    }
    private void searchProduct(String Restaurant) {
        if (!(Restaurant.isEmpty())) {
            db.collection("AllProducts").whereEqualTo("Restaurant", Restaurant).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();

                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    });
        }
    }
}