package com.rikzah.assignment_2.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.rikzah.assignment_2.MainActivity;
import com.rikzah.assignment_2.MyCartFragment;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.models.MycartModel;
import com.rikzah.assignment_2.models.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OrderPayment_Activity extends AppCompatActivity {
    TextView codcharges,subtotal,total;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    RadioGroup radioGroup;
    RadioButton radioButtoncash;
    RadioButton radioButtoncard;
    LinearLayout codfeelayout,cardpaymentlayout;
    EditText securitycode,expirydate,addressdetail;
    LottieAnimationView animationview10;
    DatabaseReference databaseReference,databaseReference1;
    String userID;
    Button paynow,backbtn;
    //Spinner citySpinner;


    RadioButton Radiobutton;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);


        if((FirebaseAuth.getInstance().getCurrentUser())==null){
            //user not logged in
            customToast.showCustomToast(getApplicationContext(), "User not authenticated");
            startActivity(new Intent(OrderPayment_Activity.this, Login_SignUp_Activity.class));
        }

        animationview10 = findViewById(R.id.AnimationView10);
        animationview10.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview10.playAnimation();
        //getUserData();

        //  UAE Cities
        /**String[] Cities = {"Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Umm Al-Quwain", "Fujairah", "Ras Al Khaimah"};
        // Create ArrayAdapter and set it to the Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(OrderPayment_Activity.this, android.R.layout.simple_spinner_item, Cities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner = findViewById(R.id.Spinner_register_City);
        citySpinner.setAdapter(spinnerAdapter);**/



        addressdetail=findViewById(R.id.Addressdetailed) ;
        radioGroup = findViewById(R.id.radioGroup);
        radioButtoncash = findViewById(R.id.radioButtoncash);
        radioButtoncard = findViewById(R.id.radioButtoncard);
        codfeelayout = findViewById(R.id.codfeelayout);
        cardpaymentlayout = findViewById(R.id.cardpymntlayout);
        securitycode = findViewById(R.id.securitycode);
        expirydate = findViewById(R.id.expirydate);
        subtotal = findViewById(R.id.subtotal);
        codcharges = findViewById(R.id.codcharges);
        total = findViewById(R.id.total);
        paynow = findViewById(R.id.Paybtn);
        backbtn=findViewById(R.id.backbtn);
        auth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference(); // Initialize database reference
        databaseReference1 =FirebaseDatabase.getInstance().getReference("Users");
        userID =auth.getCurrentUser().getUid();
        auth = FirebaseAuth.getInstance();
        getUserData();




        // Retrieve the String value from the intent
        String amountAsString = getIntent().getStringExtra("totalprice");
        // Convert the String to a double
        double amount;
        try {
            amount = Double.parseDouble(amountAsString);
        } catch (NumberFormatException e) {
            // Handle the case where the conversion fails
            amount = 0.0; // Default value or appropriate error handling
        }
        double subtotalamt = amount;
        subtotal.setText(String.valueOf(subtotalamt));
        total.setText(String.valueOf(subtotalamt));

        // Set up the event listener for changes in the radio group
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radiobuttonid) {
                int Radiobuttonclicked = radioGroup.getCheckedRadioButtonId();
                Radiobutton = findViewById(Radiobuttonclicked);
                if (radiobuttonid == R.id.radioButtoncash) {
                    codfeelayout.setVisibility(View.VISIBLE);
                    cardpaymentlayout.setVisibility(View.GONE);
                    //Standard 20 AED charged for COD
                    codcharges.setText("20.0");
                    double codfee = 20.00;
                    double totalamount = subtotalamt + codfee;
                    total.setText(String.valueOf(totalamount));}

                if (radiobuttonid == R.id.radioButtoncard) {
                    double codfee = 0.00;
                    double totalamount = subtotalamt + codfee;
                    total.setText(String.valueOf(totalamount));
                    codfeelayout.setVisibility(View.GONE);
                    cardpaymentlayout.setVisibility(View.VISIBLE);}
            }
        });

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //address field filled
                //String userAddress = citySpinner.getSelectedItem().toString();
                String userDetailedaddress=addressdetail.getText().toString();
                if (TextUtils.isEmpty(userDetailedaddress)) {
                    customToast.showCustomToast(getApplicationContext(), "Address to deliver is missing!");
                    return;
                }

                int radiobuttonid = radioGroup.getCheckedRadioButtonId();
                if (radiobuttonid != R.id.radioButtoncash && radiobuttonid != R.id.radioButtoncard) {
                    customToast.showCustomToast(getApplicationContext(), "Select Payment Method!");
                    return;
                }

                if (radiobuttonid == R.id.radioButtoncash) {
                    codfeelayout.setVisibility(View.VISIBLE);
                    cardpaymentlayout.setVisibility(View.GONE);
                    //Standard 20 AED charged for COD
                    codcharges.setText("20.0");
                    double codfee = 20.00;
                    double totalamount = subtotalamt + codfee;
                    total.setText(String.valueOf(totalamount));

                    String userID = auth.getCurrentUser().getUid();
                    if (userID != null) {
                        payNowForCashOnDelivery(userDetailedaddress);
                    } else {
                        customToast.showCustomToast(getApplicationContext(), "User ID is null!");
                    }
                }

                if (radiobuttonid == R.id.radioButtoncard) {
                    double codfee = 0.00;
                    double totalamount = subtotalamt + codfee;
                    total.setText(String.valueOf(totalamount));
                    codfeelayout.setVisibility(View.GONE);
                    cardpaymentlayout.setVisibility(View.VISIBLE);

                    String userID = auth.getCurrentUser().getUid();
                    if (userID != null) {
                        payNowForCard(userDetailedaddress);
                    } else {
                        customToast.showCustomToast(getApplicationContext(), "User ID is null!");
                    }
                }
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(OrderPayment_Activity.this, MyCartFragment.class);
                startActivity(intent);
            }
        });
    }

    private void getUserData() {
        databaseReference1.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userprofile = snapshot.getValue(UserModel.class);
                if (userprofile != null) {
                    addressdetail.setText(userprofile.getAddressdetails());

                } else {
                    customToast.showCustomToast(OrderPayment_Activity.this, "Error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                customToast.showCustomToast(OrderPayment_Activity.this, "Error");
            }
        });

    }

    /** private void getUserData() {
        databaseReference =FirebaseDatabase.getInstance().getReference("Users");
        String userID =auth.getCurrentUser().getUid();
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userprofile = snapshot.getValue(UserModel.class);
                if (userprofile != null) {
                  //city.setText(userprofile.getAddresscity());
                    addressdetail.setText(userprofile.getAddressdetailcity());
                    String city = userprofile.getAddresscity().toString();


                } else {
                    customToast.showCustomToast(OrderPayment_Activity.this, "Error");
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                customToast.showCustomToast(OrderPayment_Activity.this, "Error");
            }
        });
    }**/

    // Method to handle payment for Cash On Delivery
    private void payNowForCashOnDelivery(String userAddress) {
        String userDetailedaddress=addressdetail.getText().toString();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        List<MycartModel> list = (ArrayList<MycartModel>) getIntent().getSerializableExtra("itemList");

        if (list != null && list.size() > 0) {
            for (MycartModel model : list) {
                final HashMap<String, Object> cartMap = new HashMap<>();
                //cartMap.put("City", userAddress);
                cartMap.put("Address",userDetailedaddress);
                cartMap.put("productName", model.getProductName());
                cartMap.put("productPrice", model.getProductPrice());
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalprice", model.getTotalprice());
                cartMap.put("productImage", model.getProductImage());
                double grandTotal = Double.parseDouble(total.getText().toString());
                cartMap.put("Grandtotal", grandTotal);

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                customToast.showCustomToast(getApplicationContext(), "Order Placed");
                                finish();
                            }
                        });
            }
        }
        // Clear the "AddtoCart" subcollection for the user
        clearCart();
        Intent intent = new Intent(OrderPayment_Activity.this, OrderConfirmed_Activity.class);
        startActivity(intent);
    }

    // Method to handle payment for Card
    private void payNowForCard(String userAddress) {
        String usercardsecuritycode = securitycode.getText().toString();
        String usercardexpirydate = expirydate.getText().toString();


        if (TextUtils.isEmpty(usercardsecuritycode) || usercardsecuritycode.length() != 4) {
            customToast.showCustomToast(getApplicationContext(), "Invalid Security Code!");
            return;
        }

        if (TextUtils.isEmpty(usercardexpirydate)) {
            customToast.showCustomToast(getApplicationContext(), "Card Expiry Date is Empty!");
            return;
        }

        // Extract and parse the expiry date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy", Locale.US);
        Date expiryDate;
        try {
            expiryDate = dateFormat.parse(usercardexpirydate);
        } catch (ParseException e) {
            customToast.showCustomToast(getApplicationContext(), "Invalid Expiry Date Format!");
            return;
        }

        // Check if the card has already expired
        if (expiryDate != null && expiryDate.before(new Date())) {
            customToast.showCustomToast(getApplicationContext(), "Card has already expired!");
            return;
        }

        String userID = auth.getCurrentUser().getUid();
        if (userID != null) {
            // Check if card details match user profile
            databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String user = auth.getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    // Get security code
                    DatabaseReference securityCodeRef = databaseReference.child(user).child("securitycode");
                    securityCodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String securityCode = snapshot.getValue(String.class);
                                if (securityCode != null) {
                                    if (!(usercardsecuritycode.equals(securityCode))) {
                                        customToast.showCustomToast(getApplicationContext(), "Your Card Security Code doesnt not match!");
                                    } else {
                                        // Get expiry date
                                        DatabaseReference expiryDateRef = databaseReference.child(user).child("expirydate");
                                        expiryDateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    String expiryDate = snapshot.getValue(String.class);
                                                    if (expiryDate != null) {
                                                        // Check if values match
                                                        if (!(usercardexpirydate.equals(expiryDate))) {
                                                            customToast.showCustomToast(getApplicationContext(), "Your Card Details do not match!");
                                                        } else {
                                                            // Successful details entered
                                                            performCardPayment(userAddress);
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                // Handle the error
                                                customToast.showCustomToast(getApplicationContext(), "Database error: " + error.getMessage());
                                            }
                                        });
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle the error
                            customToast.showCustomToast(getApplicationContext(), "Database error: " + error.getMessage());
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error
                    customToast.showCustomToast(getApplicationContext(), "Database error: " + error.getMessage());

                }
            });





                    //UserModel userprofile = snapshot.getValue(UserModel.class);

                   // String profileSecurityCode = userprofile != null ? userprofile.getSecuritycode() : "";
                    //String profileExpiryDate = userprofile != null ? userprofile.getExpirydate() : "";

                    // Check if values match
                   /** if (!(usercardsecuritycode.equals(profileSecurityCode) && usercardexpirydate.equals(profileExpiryDate))) {
                        Toast.makeText(OrderPayment_Activity.this, "Your Card Details do not match!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Successful details entered
                        performCardPayment(userAddress);
                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                    Toast.makeText(OrderPayment_Activity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });**/
                } else {
            customToast.showCustomToast(getApplicationContext(), "User ID is null!");
        }
    }

    // Method to handle payment for Card (after validation)
    private void performCardPayment(String userAddress) {
        String userDetailedaddress=addressdetail.getText().toString();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        List<MycartModel> list = (ArrayList<MycartModel>) getIntent().getSerializableExtra("itemList");

        if (list != null && list.size() > 0) {
            for (MycartModel model : list) {
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("City", userAddress);
                cartMap.put("Address",userDetailedaddress);
                cartMap.put("productName", model.getProductName());
                cartMap.put("productPrice", model.getProductPrice());
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalprice", model.getTotalprice());
                cartMap.put("productImage", model.getProductImage());
                double grandTotal = Double.parseDouble(total.getText().toString());
                cartMap.put("Grandtotal", grandTotal);

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                customToast.showCustomToast(getApplicationContext(), "Order Placed");
                                finish();
                            }
                        });
            }
        }
        // Clear the "AddtoCart" subcollection for the user
        clearCart();
        Intent intent = new Intent(OrderPayment_Activity.this, OrderConfirmed_Activity.class);
        startActivity(intent);
    }

    // Method to clear the "AddtoCart"subcollection for the user
    private void clearCart() {
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            WriteBatch batch = firestore.batch();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                batch.delete(document.getReference());
                            }
                            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> batchTask) {
                                    if (batchTask.isSuccessful()) {
                                        // Subcollection deleted successfully
                                        //customToast.showCustomToast(getApplicationContext(), "Cart cleared after order");
                                    } else {
                                        // Handle the error
                                        customToast.showCustomToast(getApplicationContext(), "Error clearing cart: " + batchTask.getException());
                                    }
                                }
                            });
                        } else {
                            // Handle the error
                            customToast.showCustomToast(getApplicationContext(), "Error getting documents: " + task.getException());
                        }
                    }
                });
    }
}

/**package com.rikzah.assignment_2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.rikzah.assignment_2.MainActivity;
import com.rikzah.assignment_2.MyCartFragment;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.MycartModel;
import com.rikzah.assignment_2.models.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OrderPayment_Activity extends AppCompatActivity {
    TextView codcharges,subtotal,total;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    RadioGroup radioGroup;
    RadioButton radioButtoncash;
    RadioButton radioButtoncard;
    // Declare radiobuttonid as a class variable
    private int radiobuttonid;
    LinearLayout codfeelayout,cardpaymentlayout;
    EditText securitycode,expirydate;
    LottieAnimationView animationview10;
    DatabaseReference databaseReference;
    Button paynow;
    Spinner citySpinner;

    RadioButton Radiobutton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);

        animationview10 = findViewById(R.id.AnimationView10);
        animationview10.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview10.playAnimation();

        //  UAE Cities
        String[] Cities = {"Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Umm Al-Quwain", "Fujairah", "Ras Al Khaimah"};
        // Create ArrayAdapter and set it to the Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(OrderPayment_Activity.this, android.R.layout.simple_spinner_item, Cities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner = findViewById(R.id.Spinner_register_City);
        citySpinner.setAdapter(spinnerAdapter);

        radioGroup = findViewById(R.id.radioGroup);
        radioButtoncash = findViewById(R.id.radioButtoncash);
        radioButtoncard = findViewById(R.id.radioButtoncard);
        codfeelayout = findViewById(R.id.codfeelayout);
        cardpaymentlayout = findViewById(R.id.cardpymntlayout);
        securitycode = findViewById(R.id.securitycode);
        expirydate = findViewById(R.id.expirydate);
        subtotal = findViewById(R.id.subtotal);
        codcharges = findViewById(R.id.codcharges);
        total = findViewById(R.id.total);
        paynow = findViewById(R.id.Paybtn);

        // Retrieve the String value from the intent
        String amountAsString = getIntent().getStringExtra("totalprice");
        // Convert the String to a double
        double amount;
        try {
            amount = Double.parseDouble(amountAsString);
        } catch (NumberFormatException e) {
            // Handle the case where the conversion fails
            amount = 0.0; // Default value or appropriate error handling
        }
        double subtotalamt = amount;
        subtotal.setText(String.valueOf(subtotalamt));
        total.setText(String.valueOf(subtotalamt));

        // Set up the event listener for changes in the radio group
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radiobuttonid = checkedId;
            }
        });

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //address field filled
                String userAddress = citySpinner.getSelectedItem().toString();
                if (TextUtils.isEmpty(userAddress)) {
                    Toast.makeText(OrderPayment_Activity.this, "Select Address to deliver!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (radiobuttonid != R.id.radioButtoncash && radiobuttonid != R.id.radioButtoncash)  {
                    Toast.makeText(OrderPayment_Activity.this, "Select Payment Method!", Toast.LENGTH_SHORT).show();
                }
                if (radiobuttonid == R.id.radioButtoncash || radiobuttonid == R.id.radioButtoncash) {
                    if (radiobuttonid == R.id.radioButtoncash) {
                        codfeelayout.setVisibility(View.VISIBLE);
                        cardpaymentlayout.setVisibility(View.GONE);
                        //Standard 20 AED charged for COD
                        codcharges.setText("20.0");
                        double codfee = 20.00;
                        double totalamount = subtotalamt + codfee;
                        total.setText(String.valueOf(totalamount));
                        double grandTotal = Double.parseDouble(total.getText().toString());

                        firestore = FirebaseFirestore.getInstance();
                        auth = FirebaseAuth.getInstance();
                        List<MycartModel> list = (ArrayList<MycartModel>) getIntent().getSerializableExtra("itemList");

                        if (list != null && list.size() > 0) {
                            for (MycartModel model : list) {
                                final HashMap<String, Object> cartMap = new HashMap<>();
                                cartMap.put("Address", userAddress);
                                cartMap.put("productName", model.getProductName());
                                cartMap.put("productPrice", model.getProductPrice());
                                cartMap.put("currentDate", model.getCurrentDate());
                                cartMap.put("currentTime", model.getCurrentTime());
                                cartMap.put("totalQuantity", model.getTotalQuantity());
                                cartMap.put("totalprice", model.getTotalprice());
                                cartMap.put("productImage", model.getProductImage());
                                cartMap.put("Grandtotal", grandTotal);

                                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(OrderPayment_Activity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                            }
                        }

                    }

                    if (radiobuttonid == R.id.radioButtoncard) {
                        double codfee = 0.00;
                        double totalamount = subtotalamt + codfee;
                        total.setText(String.valueOf(totalamount));

                        codfeelayout.setVisibility(View.GONE);
                        cardpaymentlayout.setVisibility(View.VISIBLE);

                        String usercardsecuritycode = securitycode.getText().toString();
                        String usercardexpirydate = expirydate.getText().toString();

                        if (TextUtils.isEmpty(usercardsecuritycode)) {
                            Toast.makeText(getApplicationContext(), "Card Security Code is Empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (usercardsecuritycode.length() != 4) {
                            Toast.makeText(getApplicationContext(), "Card Security Code must have 4 digits", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(usercardexpirydate)) {
                            Toast.makeText(getApplicationContext(), "Card Expiry Date is Empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }// Extract and parse the expiry date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy", Locale.US);
                        Date expiryDate;
                        try {
                            expiryDate = dateFormat.parse(usercardexpirydate);
                        } catch (ParseException e) {
                            Toast.makeText(getApplicationContext(), "Invalid Expiry Date Format!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Check if the card has already expired
                        if (expiryDate != null && expiryDate.before(new Date())) {
                            Toast.makeText(getApplicationContext(), "Card has already expired!", Toast.LENGTH_SHORT).show();
                        }
                        String userID = auth.getCurrentUser().getUid();
                        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModel userprofile = snapshot.getValue(UserModel.class);
                                String profileSecurityCode = userprofile.getSecuritycode();
                                String profileExpiryDate = userprofile.getExpirydate();
                                // Check if values match
                                if (!(usercardsecuritycode.equals(profileSecurityCode) && usercardexpirydate.equals(profileExpiryDate))) {
                                    Toast.makeText(OrderPayment_Activity.this, "Your Card Details do not match!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database error
                                Toast.makeText(OrderPayment_Activity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        firestore = FirebaseFirestore.getInstance();
                        auth = FirebaseAuth.getInstance();
                        List<MycartModel> list = (ArrayList<MycartModel>) getIntent().getSerializableExtra("itemList");

                        if (list != null && list.size() > 0) {
                            for (MycartModel model : list) {
                                final HashMap<String, Object> cartMap = new HashMap<>();
                                cartMap.put("Address", userAddress);
                                cartMap.put("productName", model.getProductName());
                                cartMap.put("productPrice", model.getProductPrice());
                                cartMap.put("currentDate", model.getCurrentDate());
                                cartMap.put("currentTime", model.getCurrentTime());
                                cartMap.put("totalQuantity", model.getTotalQuantity());
                                cartMap.put("totalprice", model.getTotalprice());
                                cartMap.put("productImage", model.getProductImage());
                                double grandTotal = Double.parseDouble(total.getText().toString());
                                cartMap.put("Grandtotal", grandTotal);

                                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(OrderPayment_Activity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                            }
                        }
                    }

                    // Clear the "AddtoCart" subcollection for the user
                    clearCart();
                    Intent intent = new Intent(OrderPayment_Activity.this, OrderConfirmed_Activity.class);
                    startActivity(intent);
                }

            }
        });
    }




    // Method to clear the "AddtoCart"subcollection for the user
    private void clearCart(){
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            WriteBatch batch = firestore.batch();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                batch.delete(document.getReference());
                            }
                            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> batchTask) {
                                    if (batchTask.isSuccessful()) {
                                        // Subcollection deleted successfully
                                        Toast.makeText(OrderPayment_Activity.this, "Cart cleared after order", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle the error
                                        Toast.makeText(OrderPayment_Activity.this, "Error clearing cart: " + batchTask.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Handle the error
                            Toast.makeText(OrderPayment_Activity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}**/
