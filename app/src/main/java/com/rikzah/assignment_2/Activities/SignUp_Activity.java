package com.rikzah.assignment_2.Activities;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.models.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignUp_Activity extends AppCompatActivity {
    Button SignUP;
    EditText name, email, password,phoneno,cardname,cardnumber,expirydate,securitycode,detailaddress;

    //Spinner citySpinner;
    TextView SignIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
   // ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        SignUP=findViewById(R.id.Signupbtn);
        name=findViewById(R.id.editText_register_name);
        email=findViewById(R.id.editText_register_email);
        password=findViewById(R.id.editText_register_password);
        phoneno=findViewById(R.id.editText_register_phone);
        cardname=findViewById(R.id.editText_register_NameOnCard);
        cardnumber=findViewById(R.id.editText_register_CardNumber);
        securitycode=findViewById(R.id.editText_register_SecurityCode);
        expirydate=findViewById(R.id.editText_register_ExpiryDate);
        detailaddress=findViewById(R.id.editText_register_detailedaddress);


        //  UAE Cities
        /**String[] Cities = {"Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Umm Al-Quwain", "Fujairah", "Ras Al Khaimah"};
        // Create ArrayAdapter and set it to the Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(SignUp_Activity.this, android.R.layout.simple_spinner_item, Cities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner = findViewById(R.id.Spinner_register_City);
        citySpinner.setAdapter(spinnerAdapter);**/

        SignIn=findViewById(R.id.SignINtext);
       // progressBar=findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.GONE);
        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
               // progressBar.setVisibility(View.VISIBLE);


            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp_Activity.this, Login_Activity.class));

            }
        });


    }

    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userphone = phoneno.getText().toString();
        //String userAddress = citySpinner.getSelectedItem().toString();
        String usercardname = cardname.getText().toString();
        String usercardnumber = cardnumber.getText().toString();
        String usercardsecuritycode = securitycode.getText().toString();
        String usercardexpirydate = expirydate.getText().toString();
        String userdetailaddress=detailaddress.getText().toString();
        //String userAddress=address.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            customToast.showCustomToast(getApplicationContext(), "Name is Empty!");
            return;}
        if (TextUtils.isEmpty(userEmail)) {
            customToast.showCustomToast(getApplicationContext(), "Email is Empty!");
            return;
        }// Password validation
        if (!TextUtils.isEmpty(userPassword)) {
            // Check if the password meets the criteria
            if ((userPassword.length() < 6)){
                customToast.showCustomToast(getApplicationContext(), "Password is less than 6 characters!");
                return;
            }
            if (!containsUppercase(userPassword)){
                customToast.showCustomToast(getApplicationContext(), "Password doesn't contain Uppercase character!");
                return;
            }if (!containsLowercase(userPassword)){
                customToast.showCustomToast(getApplicationContext(), "Password doesn't contain Lowercase character!");
                return;
            }if (!containsNumber(userPassword)){
                customToast.showCustomToast(getApplicationContext(), "Password doesn't contain Number");
                return;}
        } else if(TextUtils.isEmpty(userPassword)) {
            // Show error: Password is empty
            customToast.showCustomToast(getApplicationContext(), "Password is empty");
            return;
        }if (TextUtils.isEmpty(userphone)){
            customToast.showCustomToast(getApplicationContext(), "Phone Number is Empty!");
        return;
        }if (userphone.length() != 12){
            customToast.showCustomToast(getApplicationContext(), "Password Length must be 12 letters 97150xxxxxxx");
            return;
        /**}if (TextUtils.isEmpty(userAddress)){
            customToast.showCustomToast(getApplicationContext(), "City is Empty!");
            return;**/
        }if (TextUtils.isEmpty(userdetailaddress)) {
            customToast.showCustomToast(getApplicationContext(), "Detailed Address is Empty!");
            return;
        }if (TextUtils.isEmpty(usercardname)) {
            customToast.showCustomToast(getApplicationContext(), "Card Name is Empty!");
            return;
        }if (TextUtils.isEmpty(usercardnumber)) {
            customToast.showCustomToast(getApplicationContext(), "Card Number is Empty!");
            return;
        }if (usercardnumber.length()!=16) {
            customToast.showCustomToast(getApplicationContext(), "Card Number must have 16 digits");
            return;
        }if (TextUtils.isEmpty(usercardsecuritycode)) {
            customToast.showCustomToast(getApplicationContext(), "Card Security Code is Empty!");
            return;
        }if (usercardsecuritycode.length()!=4) {
            customToast.showCustomToast(getApplicationContext(), "Card Security Code must have 4 digits");
            return;
        }if (TextUtils.isEmpty(usercardexpirydate)) {
            customToast.showCustomToast(getApplicationContext(), "Card Expiry Date is Empty!");
            return;
        }// Extract and parse the expiry date
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


        //Create User
        auth.createUserWithEmailAndPassword (userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserModel userModel= new UserModel(userName,userEmail,userPassword,userphone,userdetailaddress,usercardname,usercardnumber,usercardsecuritycode,usercardexpirydate);
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            //progressBar.setVisibility(View.GONE);
                            customToast.showCustomToast(getApplicationContext(), "Registration Successful");
                            startActivity(new Intent(SignUp_Activity.this, Login_Activity.class));
                        } else {
                            //progressBar.setVisibility(View.GONE);
                            customToast.showCustomToast(getApplicationContext(), "Error: " + task.getException());
                        }
                    }
                });
    }

    // Helper methods for checking conditions
    private boolean containsUppercase(String s) {
        return !s.equals(s.toLowerCase());
    }

    private boolean containsLowercase(String s) {
        return !s.equals(s.toUpperCase());
    }

    private boolean containsNumber(String s) {
        return s.matches(".*\\d.*");
    }

}