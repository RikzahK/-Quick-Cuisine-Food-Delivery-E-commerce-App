package com.rikzah.assignment_2;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.rikzah.assignment_2.Activities.Detailed_Activity;
import com.rikzah.assignment_2.Activities.Login_SignUp_Activity;
import com.rikzah.assignment_2.Activities.OrderConfirmed_Activity;
import com.rikzah.assignment_2.Activities.OrderPayment_Activity;

import com.rikzah.assignment_2.adapters.MycartAdapter;
import com.rikzah.assignment_2.models.MycartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {
    LottieAnimationView animationview8;
    RecyclerView recyclerView;
    MycartAdapter mycartAdapter;
    TextView overTotalAmount;
    List<MycartModel> mycartModelList;
    FirebaseFirestore db;
    FirebaseAuth auth;
    Button buynow;
    LottieAnimationView progressbar;
    LinearLayout layout1,emptycart;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);
        layout1=root.findViewById(R.id.layout1);
        emptycart=root.findViewById(R.id.empty_cart);
        progressbar =root.findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(88888).setStartDelay(0);
        progressbar.playAnimation();

        progressbar.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // User not logged in
            customToast.showCustomToast(getActivity(), "SignIn to enjoy QuickCuisne experience");
            startActivity(new Intent(getActivity(), Login_SignUp_Activity.class));
            return root;  // Return early if the user is not authenticated
        }


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buynow = root.findViewById(R.id.buy_now);
        overTotalAmount = root.findViewById(R.id.textView6);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver, new IntentFilter("MytotalAmount"));

        mycartModelList = new ArrayList<>();
        mycartAdapter = new MycartAdapter(getActivity(), mycartModelList);
        recyclerView.setAdapter(mycartAdapter);



        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressbar.setVisibility(View.GONE);
                            layout1.setVisibility(View.VISIBLE);
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                String documentId = documentSnapshot.getId();
                                MycartModel mycartModel = documentSnapshot.toObject(MycartModel.class);
                                mycartModel.setDocumentId(documentId);
                                mycartModelList.add(mycartModel);
                                mycartAdapter.notifyDataSetChanged();


                            }
                            updateTotalAmount(); // Update total amount after fetching cart items
                        }
                    }
                });



        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total = String.valueOf(overTotalAmount.getText());
                if (mycartModelList.size() > 0) {
                    Intent intent = new Intent(getContext(), OrderPayment_Activity.class);
                    intent.putExtra("itemList", (Serializable) mycartModelList);
                    intent.putExtra("totalprice", total);
                    startActivity(intent);
                } else {
                    customToast.showCustomToast(getActivity(), "Your Cart is Empty!");
                }
            }
        });

        return root;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTotalAmount(); // Update total amount when broadcast received
        }
    };

    private void updateTotalAmount() {
        double totalBill = 0;
        for (MycartModel model : mycartModelList) {
            totalBill += model.getTotalprice();
        }

        overTotalAmount.setText(String.valueOf(totalBill));
    }
}



/**public class MyCartFragment extends Fragment {
    LottieAnimationView animationview8;
    RecyclerView recyclerView;
    MycartAdapter mycartAdapter;
    TextView overTotalAmount;
    List<MycartModel> mycartModelList;
    FirebaseFirestore db;
    FirebaseAuth auth;
    Button buynow;
    int totalBill = 0;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cart,container,false);
        /**animationview8=root.findViewById(R.id.AnimationView8);
        animationview8.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview8.playAnimation();
         **/

        /**if((FirebaseAuth.getInstance().getCurrentUser())==null){
            //user not logged in
            Toast.makeText(getContext(), "SignIn to enjoy QuickCuisne experience", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), Login_SignUp_Activity.class));
            return root;  // Return early if the user is not authenticated
        }
        db = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recyclerView=root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buynow=root.findViewById(R.id.buy_now);
        overTotalAmount=root.findViewById(R.id.textView6);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver,new IntentFilter("MytotalAmount"));

        mycartModelList=new ArrayList<>();
        mycartAdapter=new MycartAdapter(getActivity(),mycartModelList);
        recyclerView.setAdapter(mycartAdapter);


        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                String documentId = documentSnapshot.getId();
                                MycartModel mycartModel = documentSnapshot.toObject(MycartModel.class);
                                mycartModel.setDocumentId(documentId);
                                mycartModelList.add(mycartModel);
                                mycartAdapter.notifyDataSetChanged();
                            }
                            //calculatetotalamount(mycartModelList);
                        }
                    }
                });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String subtotal1 = String.valueOf(totalBill);
                String total= String.valueOf(overTotalAmount.getText());
                if (totalBill != 0) {
                    Intent intent = new Intent(getContext(), OrderPayment_Activity.class);
                    intent.putExtra("itemList", (Serializable) mycartModelList);
                    intent.putExtra("totalprice", total);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Your Cart is Empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int TotalBill=intent.getIntExtra("totalAmount",0);
            overTotalAmount.setText(TotalBill);
        }
    };

    /**private void calculatetotalamount(List<MycartModel> mycartModelList) {
        double totalamount = 0.0;
        for (MycartModel mycartModel : mycartModelList) {
            totalamount += mycartModel.getTotalprice();
        }
        overTotalAmount.setText(String.valueOf(totalamount));
    }**/

    // Method to handle delete all items from cart when order placed
    /**public void deleteAllItemsInCart() {
        CollectionReference cartCollection = db.collection("CurrentUser")
                .document(auth.getCurrentUser().getUid())
                .collection("AddtoCart");
        // Get all documents in the "AddtoCart" collection
        cartCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Create a batch to perform the delete operation
                    WriteBatch batch = db.batch();
                    // Iterate through the documents and add them to the batch
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        batch.delete(document.getReference());
                    }

                    // Commit the batch delete operation
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> batchTask) {
                            if (batchTask.isSuccessful()) {
                                // Clear the local list and update the adapter
                                mycartModelList.clear();
                                mycartAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "All items deleted from cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Error deleting items: " + batchTask.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }**/


//}