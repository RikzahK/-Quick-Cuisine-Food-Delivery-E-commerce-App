package com.rikzah.assignment_2.ui.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rikzah.assignment_2.Activities.Login_SignUp_Activity;
import com.rikzah.assignment_2.Activities.SignUp_Activity;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.databinding.*;
import com.rikzah.assignment_2.models.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    LottieAnimationView animationview3;
    CircleImageView profileimg;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    // Data binding simplifies UI updates by connecting UI components directly to underlying data sources.
   // FragmentProfileBinding binding;
    DatabaseReference databaseReference;
    MaterialTextView email;
    //Spinner citySpinner;
    EditText name,phoneno,cardname,cardnumber,cardsceuritycode,cardexpirydate,addressdetail;
    Button update,setprofile;
    String user, userID;
    LottieAnimationView progressbar;
    ScrollView layout2;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile,container,false);
        layout2=root.findViewById(R.id.layout2);
        progressbar =root.findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(8888).setStartDelay(0);
        progressbar.playAnimation();

        progressbar.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);

        if((FirebaseAuth.getInstance().getCurrentUser())==null){
            //user not logged in
            customToast.showCustomToast(getActivity(), "SignIn to enjoy QuickCuisne experience");
            startActivity(new Intent(getActivity(), Login_SignUp_Activity.class));
            return root;  // Return early if the user is not authenticated
        }
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();




        databaseReference =FirebaseDatabase.getInstance().getReference("Users");
        userID =auth.getCurrentUser().getUid();
        auth = FirebaseAuth.getInstance();

        //  UAE Cities
        /**String[] Cities = {" Change city","Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Umm Al-Quwain", "Fujairah", "Ras Al Khaimah"};
        // Create ArrayAdapter and set it to the Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Cities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner = root.findViewById(R.id.Spinner_profile_City);
        citySpinner.setAdapter(spinnerAdapter);
        citySpinner.setSelection(0);**/

        profileimg=root.findViewById(R.id.profile_image);
        setprofile=root.findViewById(R.id.setprofile);
        name=root.findViewById(R.id.profile_name);
        email=root.findViewById(R.id.profile_email);
        phoneno=root.findViewById(R.id.profile_phone);
        addressdetail=root.findViewById(R.id.profile_address);
       // city=root.findViewById(R.id.profile_City);
        cardname=root.findViewById(R.id.profile_nameoncard);
        cardnumber=root.findViewById(R.id.profile_cardnumber);
        cardsceuritycode=root.findViewById(R.id.profile_securitycode);
        cardexpirydate=root.findViewById(R.id.profile_expirydate);
        update=root.findViewById(R.id.Update);

       getUserData();



        /**databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userprofile=snapshot.getValue(UserModel.class);
                if(userprofile!=null){
                    name.setText("");
                    phoneno.setText("");
                    city.setText("");
                    cardname.setText("");
                    cardnumber.setText("");
                    cardsceuritycode.setText("");
                    cardexpirydate.setText("");


                }
                else{
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });**/

        animationview3 = root.findViewById(R.id.AnimationView3);
        animationview3.animate().translationX(0).setDuration(88888).setStartDelay(0);
        animationview3.playAnimation();



        setprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
        return root;
    }

    private void getUserData() {
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userprofile = snapshot.getValue(UserModel.class);
                if (userprofile != null) {
                    email.setText(userprofile.getEmail());
                    name.setText(userprofile.getName());
                    phoneno.setText(userprofile.getPhone());
                   // city.setText(userprofile.getAddresscity());
                    addressdetail.setText(userprofile.getAddressdetails());
                    cardname.setText(userprofile.getCardname());
                    cardnumber.setText(userprofile.getCardnumber());
                    cardsceuritycode.setText(userprofile.getSecuritycode());
                    cardexpirydate.setText(userprofile.getExpirydate());

                    progressbar.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);

                } else {
                  customToast.showCustomToast(getActivity(), "Error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                customToast.showCustomToast(getActivity(), "Error");
            }
        });
    }


    private void updateUserProfile() {
        String nameValue = name.getText().toString();
        String emailValue = email.getText().toString();
        String phoneValue = phoneno.getText().toString();
      //  String cityValue = citySpinner.getSelectedItem().toString();
        String cityaddress=addressdetail.getText().toString();
        String cardNameValue = cardname.getText().toString();
        String cardNumberValue = cardnumber.getText().toString();
        String securityCodeValue = cardsceuritycode.getText().toString();
        String expiryDateValue = cardexpirydate.getText().toString();
        //citySpinner.setSelection(0);

        if (TextUtils.isEmpty(nameValue)) {
            customToast.showCustomToast(getActivity(), "Name is Empty!");
            return;
        }if (TextUtils.isEmpty(phoneValue)){
            customToast.showCustomToast(getActivity(), "Phone Number is Empty!");
            return;
        }if (phoneValue.length() != 12){
            customToast.showCustomToast(getActivity(), "Phone number must be 12 letters 97150xxxxxxx");
            return;
        }if (TextUtils.isEmpty(cityaddress)){
            customToast.showCustomToast(getActivity(), "Address is Empty!");
            return;
        }if (TextUtils.isEmpty(cardNameValue)) {
            customToast.showCustomToast(getActivity(),"Card Name is Empty!");
            return;
        }if (TextUtils.isEmpty(cardNumberValue)) {
            customToast.showCustomToast(getActivity(), "Card Number is Empty!");
            return;
        }if (cardNumberValue.length()!=16) {
            customToast.showCustomToast(getActivity(), "Card Number must have 16 digits");
            return;
        }if (TextUtils.isEmpty(securityCodeValue)) {
            customToast.showCustomToast(getActivity(), "Card Security Code is Empty!");
            return;
        }if (securityCodeValue.length()!=4) {
            customToast.showCustomToast(getActivity(), "Card Security Code must have 4 digits");
            return;
        }if (TextUtils.isEmpty(expiryDateValue)) {
            customToast.showCustomToast(getActivity(), "Card Expiry Date is Empty!");
            return;
        }// Extract and parse the expiry date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy", Locale.US);
        Date expiryDate;
        try {
            expiryDate = dateFormat.parse(expiryDateValue);
        } catch (ParseException e) {
            customToast.showCustomToast(getActivity(), "Invalid Expiry Date Format!");
            return;
        }
        // Check if the card has already expired
        if (expiryDate != null && expiryDate.before(new Date())) {
            customToast.showCustomToast(getActivity(), "Card has already expired!");
            return;
        }

        HashMap User= new HashMap();
        User.put("name",nameValue);
        User.put("phone",phoneValue);
        //User.put("addresscity",cityValue);
        User.put("cardname",cardNameValue);
        User.put("cardnumber",cardNumberValue);
        User.put("securitycode",securityCodeValue);
        User.put("expirydate",expiryDateValue);
        User.put("addressdetails",cityaddress);


        String user= auth.getCurrentUser().getUid();
        databaseReference =FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(user).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                  getUserData();
                    customToast.showCustomToast(getActivity(), "Data Updated");
                    
                }else{
                    customToast.showCustomToast(getActivity(), "Error"+task.getException());
                }
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null){
            profileimg.setVisibility(View.VISIBLE);
            animationview3.setVisibility(View.GONE);
            Uri profileUri=data.getData();
            profileimg.setImageURI(profileUri);

            final StorageReference storageReference= storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());
            storageReference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    customToast.showCustomToast(getActivity(), "Image Uploaded");
                }
            });
        }
        else{
            profileimg.setVisibility(View.GONE);
            animationview3.setVisibility(View.VISIBLE);

        }
    }
}