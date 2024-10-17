package com.rikzah.assignment_2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.adapters.RecommendedAdapter;
import com.rikzah.assignment_2.adapters.RestaurantAdapter;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.models.PopularModel;
import com.rikzah.assignment_2.models.RecommendedModel;
import com.rikzah.assignment_2.models.RestaurantModel;
import com.rikzah.assignment_2.models.ViewAllModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Detailed_Activity extends AppCompatActivity {
    ImageView detailedImg, additem, removeitem;
    TextView name, price, description,quantity;
    int totalQuantity=1;
    int totalPrice=0;
    Button addtoccart;
    ViewAllModel viewAllModel =null;
    PopularModel popularModel=null;
    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView recommendedRec;
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);



        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recommendedRec=findViewById(R.id.recommended_rec);
        recommendedRec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recommendedModelList= new ArrayList<>();
        recommendedAdapter=new RecommendedAdapter(this,recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);
        db.collection("QuickCuisineRecommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();



                            }
                        }else {
                            customToast.showCustomToast(getApplicationContext(), "Error" + task.getException());

                        }
                    }
                });

        final Object object=getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel){
            viewAllModel=(ViewAllModel)  object;
        }
        else if (object instanceof PopularModel){
            popularModel=(PopularModel)  object;
        }

        quantity=findViewById(R.id.quantity) ;
        detailedImg=findViewById(R.id.destaileddishimg);
        additem=findViewById(R.id.add_item);
        removeitem=findViewById(R.id.remove_item);
        name=findViewById(R.id.detaileddish_name);
        price=findViewById(R.id.detaileddish_price);
        description=findViewById(R.id.detaileddish_des);
        addtoccart=findViewById(R.id.add_to_cart);

        addtoccart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User is not logged in or clickedItem is null
                if (auth.getCurrentUser() == null) {
                    customToast.showCustomToast(getApplicationContext(),"SignIn to enjoy QuickCuisne experience");
                    startActivity(new Intent(Detailed_Activity.this, Login_SignUp_Activity.class));
                }
                addedToCart();
                // Check if the user is signed in
                /**FirebaseUser firebaseUser=auth.getCurrentUser();
                if (firebaseUser!= null) {
                    // User is already signed in, perform the desired action (e.g., add to cart)
                    addedToCart();}
                else{
                    // User is not signed in, start the Login_SignUp_Activity
                    startActivity(new Intent(Detailed_Activity.this, Login_SignUp_Activity.class));
                }**/

            }
        });
        if(viewAllModel!=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            name.setText(viewAllModel.getName());
            description.setText(viewAllModel.getDescription());
            price.setText(viewAllModel.getPrice());
            totalPrice=Integer.valueOf(viewAllModel.getPrice())*totalQuantity;
        }
        else if(popularModel!=null){
            Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(detailedImg);
            name.setText(popularModel.getName());
            description.setText(popularModel.getDescription());
            price.setText(popularModel.getPrice());
            totalPrice=Integer.valueOf(popularModel.getPrice())*totalQuantity;
        }
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity<10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if (viewAllModel != null) {
                        totalPrice = Integer.valueOf(viewAllModel.getPrice()) * totalQuantity;
                    } else if (popularModel != null) {
                        totalPrice = Integer.valueOf(popularModel.getPrice()) * totalQuantity;
                    }
                }else if (totalQuantity>10) {
                    customToast.showCustomToast(getApplicationContext(),"Can't order more than 10 packages at a time");
                }

            }
        });
        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity>0) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    if (viewAllModel != null) {
                        totalPrice = Integer.valueOf(viewAllModel.getPrice()) * totalQuantity;
                    } else if (popularModel != null) {
                        totalPrice = Integer.valueOf(popularModel.getPrice()) * totalQuantity;
                    }
                }else if (totalQuantity<=0) {
                    customToast.showCustomToast(getApplicationContext(),"No items in the cart");
                }
            }
        });




    }
//public so that it can be accessed from adapter class as well
    public void addedToCart() {
        if (auth.getCurrentUser() != null) {
            String saveCurrentDate, saveCurrentTime;
            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat(  "MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat( "HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());
            if (viewAllModel != null) {
            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", viewAllModel.getName());
            cartMap.put("productPrice",price.getText().toString());
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalprice", totalPrice);
            cartMap.put("productImage",viewAllModel.getImg_url());

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddtoCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                customToast.showCustomToast(getApplicationContext(),"Added to Cart");
                                //finish();
                            }
                        });

            } else if (popularModel != null) {
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", popularModel.getName());
                cartMap.put("productPrice",price.getText().toString());
                cartMap.put("currentDate", saveCurrentDate);
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("totalQuantity", quantity.getText().toString());
                cartMap.put("totalprice", totalPrice);
                cartMap.put("productImage",popularModel.getImg_url());
                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddtoCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                customToast.showCustomToast(getApplicationContext(),"Added to Cart");
                                //finish();
                            }
                        });
            }

           }
        else {
            // User is not logged in, redirect to login/signup page
            startActivity(new Intent(Detailed_Activity.this, Login_SignUp_Activity.class));
        }

    }





    public void addedToCart(RecommendedModel clickedItem) {
        auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null && clickedItem != null) {
            String saveCurrentDate, saveCurrentTime;
            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", clickedItem.getName());
            cartMap.put("productPrice", price.getText().toString());
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalprice", totalPrice);
            cartMap.put("productImage", clickedItem.getImg_url());

            db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                    .collection("AddtoCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            customToast.showCustomToast(getApplicationContext(), "Added to Cart");
                        }
                    });
        } else {
            // clickedItem is null
            customToast.showCustomToast(getApplicationContext(), "Error");


        }
    }


}