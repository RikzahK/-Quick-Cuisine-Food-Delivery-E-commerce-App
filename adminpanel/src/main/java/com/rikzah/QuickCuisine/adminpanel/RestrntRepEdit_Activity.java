package com.rikzah.QuickCuisine.adminpanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RestrntRepEdit_Activity extends AppCompatActivity {
    LottieAnimationView animationview23,animationview24;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Spinner removeprodct;
    LinearLayout adddishlayout, rmeovedishlayout;
    Button addproduct, setprofile,removeproductbutton;
    ImageView productimage;
    private String imageUrl;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrnt_rep_edit);
        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        adddishlayout = findViewById(R.id.adddishlayout);
        rmeovedishlayout=findViewById(R.id.removedishlayout);
        addproduct = findViewById(R.id.addproduct);
        removeproductbutton=findViewById(R.id.removeproductbtn);
        EditText restaurantEditText = findViewById(R.id.Restaurant);
        EditText productTypeEditText = findViewById(R.id.producttype);
        EditText productNameEditText = findViewById(R.id.productName);
        EditText productPriceEditText = findViewById(R.id.productprice);
        EditText productDescriptionEditText = findViewById(R.id.productdescription);
        setprofile = findViewById(R.id.setprdctimg);
        productimage = findViewById(R.id.productimage);
        String functionValue = getIntent().getStringExtra("function");
        if ("add dish".equals(functionValue)) {
            adddishlayout.setVisibility(View.VISIBLE);
            rmeovedishlayout.setVisibility(View.GONE);
            animationview23 = findViewById(R.id.AnimationView23);
            animationview23.animate().translationX(0).setDuration(88888).setStartDelay(0);
            animationview23.playAnimation();
            setprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 34);}});
            addproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(restaurantEditText.getText().toString())) {
                        customToast.showCustomToast(getApplicationContext(), "Restaurant Name is Empty!");
                        return;}
                    if (TextUtils.isEmpty(productTypeEditText.getText().toString())) {
                        customToast.showCustomToast(getApplicationContext(), "Product Type is Empty!");
                        return;}
                    if (TextUtils.isEmpty(productNameEditText.getText().toString())) {
                        customToast.showCustomToast(getApplicationContext(), "Product Name is Empty!");
                        return;}
                    if (TextUtils.isEmpty(productPriceEditText.getText().toString())) {
                        customToast.showCustomToast(getApplicationContext(),"Product Price is Empty!" );
                        return;}
                    if (TextUtils.isEmpty(productDescriptionEditText.getText().toString())) {
                        customToast.showCustomToast(getApplicationContext(), "Product Description is Empty!");}
                    //Extract text from EditText widgets
                    String productType = productTypeEditText.getText().toString();
                    String productPrice = productPriceEditText.getText().toString();
                    // Convert the productPrice string to an integer
                    int priceValue = Integer.parseInt(productPrice);
                    String productDescription = productDescriptionEditText.getText().toString();
                    String productName = productNameEditText.getText().toString();
                    String restaurantName = restaurantEditText.getText().toString();
                    db.collection("AllProducts").whereEqualTo("Restaurant", restaurantName)
                            .whereEqualTo("name", productName).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (!task.getResult().isEmpty()) {
                                            // Product with the same name already exists in the restaurant
                                            Toast.makeText(getApplicationContext(), "Product already exists in the restaurant!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Product does not exist, add it to the Firestore collection
                                            db = FirebaseFirestore.getInstance();
                                            auth = FirebaseAuth.getInstance();
                                            String saveCurrentDate;
                                            Calendar calForDate = Calendar.getInstance();
                                            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            saveCurrentDate = currentDate.format(calForDate.getTime());

                                            // Create a HashMap with the extracted values
                                            final HashMap<String, Object> addproduct = new HashMap<>();
                                            addproduct.put("Restaurant", restaurantName);
                                            addproduct.put("description", productDescription);
                                            addproduct.put("img_url", imageUrl);
                                            addproduct.put("currentDate", saveCurrentDate);
                                            addproduct.put("name", productName);
                                            addproduct.put("price", priceValue);
                                            addproduct.put("type", productType);
                                            db.collection("AllProducts").add(addproduct).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    customToast.showCustomToast(getApplicationContext(),"Product Added" );
                                                    finish();}});}}}});}});}
         if ("remove dish".equals(functionValue)) {
            adddishlayout.setVisibility(View.GONE);
            rmeovedishlayout.setVisibility(View.VISIBLE);
            animationview24 = findViewById(R.id.AnimationView24);
            animationview24.animate().translationX(0).setDuration(88888).setStartDelay(0);
            animationview24.playAnimation();
            //Create an ArrayList to store product names
            ArrayList<String> productNamesList = new ArrayList<>();
            //Fetch product names from Firestore
            db.collection("AllProducts")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String productName = document.getString("name");
                                    if (productName != null) {
                                        // Add the product name to the list
                                        productNamesList.add(productName);}}
                                //  Populate the spinner with product names
                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(RestrntRepEdit_Activity.this, android.R.layout.simple_spinner_item, productNamesList);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                removeprodct = findViewById(R.id.Spinner_removeproduct);
                                removeprodct.setAdapter(spinnerAdapter);
                            } else {
                                customToast.showCustomToast(getApplicationContext(),"Error getting documents: "  + task.getException());}}});
            rmeovedishlayout.setVisibility(View.VISIBLE);
            removeproductbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String productnametoremove = removeprodct.getSelectedItem().toString();
                    // Perform a query to find the document with the specified name
                    db.collection("AllProducts")
                            .whereEqualTo("name", productnametoremove)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            // Delete the document with the specified name
                                            document.getReference().delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Document successfully deleted
                                                            customToast.showCustomToast(getApplicationContext(),"Product removed successfully");}});}
                                    }else {
                                        customToast.showCustomToast(getApplicationContext(),"Error removing product");}}});}});} //else stement ends
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            Uri profileUri = data.getData();
            productimage.setImageURI(profileUri);
            // Upload the image to Firebase Storage
            final StorageReference storageReference = storage.getReference().child("product_picture")
                    .child(FirebaseAuth.getInstance().getUid());
            storageReference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully, now get the download URL
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // uri contains the download URL for the uploaded image
                                imageUrl = uri.toString();
                            customToast.showCustomToast(getApplicationContext(),"Product Image Uploaded");
                            }});}});}}}

