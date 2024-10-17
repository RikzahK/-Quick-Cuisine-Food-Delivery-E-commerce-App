package com.rikzah.assignment_2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.adapters.RestaurantAdapter;
import com.rikzah.assignment_2.adapters.ViewAllAdapter;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.databinding.ActivityMainBinding;
import com.rikzah.assignment_2.models.RecommendedModel;
import com.rikzah.assignment_2.models.RestaurantModel;
import com.rikzah.assignment_2.models.ViewAllModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ViewAll_Activity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    RecyclerView recyclerView1;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;
    ImageView rest_img;
    LottieAnimationView progressbar;
    ScrollView layout2;
    TextView restName, restDes, restDelivFee,restDelivTime, RestRating;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        String name = getIntent().getStringExtra("popular");
        String type = getIntent().getStringExtra("type");
        recyclerView1 = findViewById(R.id.view_all_recy);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelList);
        recyclerView1.setAdapter(viewAllAdapter);
        layout2=findViewById(R.id.layout1);
        progressbar =findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(88888).setStartDelay(0);
        progressbar.playAnimation();

        progressbar.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);



        //Getting all popular item dishes
        if (name != null && name.equalsIgnoreCase("PopularItemDisplay")) {
            firestore.collection("PopularItems").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Getting Chinese Haven dishes
        if (name != null && name.equalsIgnoreCase("Chinese Haven")) {
            firestore.collection("AllProducts").whereEqualTo("Restaurant", "Chinese Haven").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Getting Trattoria Bella Vita dishes
        if (name != null && name.equalsIgnoreCase("Trattoria Bella Vita")) {
            firestore.collection("AllProducts").whereEqualTo("Restaurant", "Trattoria Bella Vita").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);

                    }
                }
            });
        }

        //Getting SpiceCraft Indian Bites dishes
        if (name != null && name.equalsIgnoreCase("SpiceCraft Indian Bites")) {
            firestore.collection("AllProducts").whereEqualTo("Restaurant", "SpiceCraft Indian Bites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }


        //Getting Lean Cuisine Delights dishes
        if (name != null && name.equalsIgnoreCase("Lean Cuisine Delights")) {
            firestore.collection("AllProducts").whereEqualTo("Restaurant", "Lean Cuisine Delights").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Getting SliceHub Pizzeria dishes
        if (name != null && name.equalsIgnoreCase("SliceHub Pizzeria")) {
            firestore.collection("AllProducts").whereEqualTo("Restaurant", "SliceHub Pizzeria").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Getting SliceHub Pizzeria dishes
        if (name != null && name.equalsIgnoreCase("Seafood House")) {
            firestore.collection("AllProducts").whereEqualTo("Restaurant", "Seafood House").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Category dishes

        //Getting Diet dishes
        if (type != null && type.equalsIgnoreCase("Diet")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Diet").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Getting Dessert dishes
        if (type != null && type.equalsIgnoreCase("Dessert")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Dessert").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Getting Main Course dishes
        if (type != null && type.equalsIgnoreCase("Main Course")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Main Course").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        //Getting Fast Food dishes
        if (type != null && type.equalsIgnoreCase("Fast Food")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Fast Food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        //Getting Sea Food dishes
        if (type != null && type.equalsIgnoreCase("Sea Food")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Sea Food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //Getting Italian dishes
        if (type != null && type.equalsIgnoreCase("Italian")) {
            firestore.collection("AllProducts").whereEqualTo("type", "Italian").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();


                        progressbar.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                }
            });
        }


    }

    public void AddedToCart(ViewAllModel clickedItem) {
        int totalQuantity = 1;
        int totalPrice = 0;

        // Ensure clickedItem is not null before accessing its properties
        if (auth.getCurrentUser() != null && clickedItem != null) {
            // Calculate total price based on quantity and item price
            totalPrice = Integer.valueOf(clickedItem.getPrice()) * totalQuantity;

            // Get current date and time
            String saveCurrentDate, saveCurrentTime;
            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            // Create a HashMap to store cart details
            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", clickedItem.getName());
            cartMap.put("productPrice", clickedItem.getPrice());
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", String.valueOf(totalQuantity));
            cartMap.put("totalprice", totalPrice);
            cartMap.put("productImage", clickedItem.getImg_url());

            // Add to Firestore
            firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                    .collection("AddtoCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            customToast.showCustomToast(getApplicationContext(), "Added to Cart");
                        }
                    });
        } else {
            // User is not logged in or clickedItem is null
            if (auth.getCurrentUser() == null) {
                startActivity(new Intent(ViewAll_Activity.this, Login_SignUp_Activity.class));
            }
        }
    }
}


//scrollView = findViewById(R.id.scroll_view);

//Restaurant description view all
/**scrollView.setVisibility(View.GONE);
 if (name != null){
 scrollView.setVisibility(View.VISIBLE);
 final Object object = getIntent().getSerializableExtra("detail");
 if (object instanceof RestaurantModel) {
 restaurantModel = (RestaurantModel) object;
 }
 rest_img = findViewById(R.id.rest_img);
 restName = findViewById(R.id.rest_name1);
 restDes = findViewById(R.id.rest_des1);
 restDelivFee = findViewById(R.id.rest_fee1);
 restDelivTime = findViewById(R.id.rest_time1);
 RestRating = findViewById(R.id.rest_rating5);

 if (restaurantModel != null) {
 Glide.with(getApplicationContext()).load(restaurantModel.getImg_url()).into(rest_img);
 restName.setText(restaurantModel.getName());
 restDes.setText(restaurantModel.getDescription());
 restDelivFee.setText(restaurantModel.getDelivery_fee());
 restDelivTime.setText(restaurantModel.getDelivery_time());
 RestRating.setText(restaurantModel.getRating());
 }
 }**/