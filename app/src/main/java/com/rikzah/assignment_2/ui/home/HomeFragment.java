package com.rikzah.assignment_2.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rikzah.assignment_2.Activities.ViewAll_Activity;
import com.rikzah.assignment_2.MainActivity;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.RestaurantsFragment;
import com.rikzah.assignment_2.adapters.HomeAdapters;
import com.rikzah.assignment_2.adapters.PopularAdapters;
import com.rikzah.assignment_2.adapters.RestaurantAdapter;
import com.rikzah.assignment_2.adapters.ViewAllAdapter;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.databinding.FragmentHomeBinding;
import com.rikzah.assignment_2.models.HomeCategory;
import com.rikzah.assignment_2.models.PopularModel;
import com.rikzah.assignment_2.models.RestaurantModel;
import com.rikzah.assignment_2.models.ViewAllModel;
import com.rikzah.assignment_2.ui.Categories.CategoriesFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    ScrollView scrollView;
    RecyclerView popularRec,homeCatRec;

    EditText searchbox;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewsearch;
    private ViewAllAdapter viewAllAdapter;

    FirebaseFirestore db;
    //popular items
    List <PopularModel> popularModelList;
    PopularAdapters popularAdapters;
    TextView viewAllCategories, viewllpopularitems;
    //Home category
    List <HomeCategory> categoryList;
    HomeAdapters homeAdapters;
    ImageSlider imageSlider;
    LottieAnimationView progressbar;
    ScrollView layout1;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);

        db=FirebaseFirestore.getInstance();
        popularRec=root.findViewById(R.id.popular_rec);
        homeCatRec=root.findViewById(R.id.Categories_rec);
      // viewAllCategories=root.findViewById(R.id.view_all_Categories);
        viewllpopularitems=root.findViewById(R.id.view_all_popularitems);


        layout1=root.findViewById(R.id.scroll_view);
        progressbar =root.findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(88888).setStartDelay(0);
        progressbar.playAnimation();

        progressbar.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);


        imageSlider = root.findViewById(R.id.imageslider);
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.ofr66, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.offr55, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ofr44, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.offr55, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ofr22, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ofr44, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


        //popular items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList= new ArrayList<>();
        popularAdapters=new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);
        db.collection("PopularItems")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    PopularModel popularModel = document.toObject(PopularModel.class);
                                    popularModelList.add(popularModel);
                                    popularAdapters.notifyDataSetChanged();

                                    progressbar.setVisibility(View.GONE);
                                    layout1.setVisibility(View.VISIBLE);

                                }
                            }else {
                                customToast.showCustomToast(getActivity(),"Error" + task.getException());

                            }
                        }
                });

        //Home Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList= new ArrayList<>();
        homeAdapters=new HomeAdapters(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapters);
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapters.notifyDataSetChanged();
                            }
                        }else {
                            customToast.showCustomToast(getActivity(), "Error" + task.getException());

                        }
                    }
                });

     /**  viewAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the CategoriesFragment
                Intent intent = new Intent(getContext(),CategoriesFragment.class);
                // Start the activity
                startActivity(intent);
            }
        });**/

        viewllpopularitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ViewAll_Activity.class);
                            intent.putExtra("name", "PopularItemDisplay");
                            startActivity(intent);
            }
        });


        //search view
       recyclerViewsearch=root.findViewById(R.id.search_rec1);
       searchbox=root.findViewById(R.id.search_box1);
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

    private void searchProduct(String type) {
        if(!(type.isEmpty())){
            db.collection("AllProducts").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() && task.getResult()!=null){
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();

                                for(DocumentSnapshot doc : task.getResult().getDocuments()){
                                    ViewAllModel viewAllModel=doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    });
        }
    }
}