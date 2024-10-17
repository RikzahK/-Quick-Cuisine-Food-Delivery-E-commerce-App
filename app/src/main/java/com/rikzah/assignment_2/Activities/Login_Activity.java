package com.rikzah.assignment_2.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.gbuttons.GoogleSignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rikzah.assignment_2.MainActivity;
import com.rikzah.assignment_2.R;
import com.google.android.gms.common.SignInButton;
import com.rikzah.assignment_2.customToast;


public class Login_Activity extends AppCompatActivity {
    Button SignIN;
    EditText  email, password;
    TextView SignUP,forgotpassword;
    FirebaseAuth auth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInButton googleSignInButton;
    GoogleSignInClient googleSignInClient;


   // ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        SignIN=findViewById(R.id.loginButton);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        SignUP=findViewById(R.id.SignUPtext);
        googleSignInButton=findViewById(R.id.googlesignin);
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);

        GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount!=null){
            finish();
            customToast.showCustomToast(getApplicationContext(), "Login Successful");
            startActivity(new Intent( Login_Activity.this,Detailed_Activity.class));
        }
        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data= result.getData();
                            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
                            try {
                                task.getResult(ApiException.class);
                                finish();
                                Intent intent=new Intent(Login_Activity.this, Detailed_Activity.class);
                            } catch (ApiException e) {
                                customToast.showCustomToast(getApplicationContext(), "Something went wrong");
                            }
                        }
                    }
                });
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent=googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signinIntent);
            }
        });
        forgotpassword=findViewById(R.id.forgotpass);
        //progressBar=findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.GONE);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.forgotpassword_dailog,null);
                EditText email= dialogView.findViewById(R.id.restpass_email);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.ResetPasswordbutton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = email.getText().toString();
                        if (TextUtils.isEmpty (userEmail) && ! Patterns.EMAIL_ADDRESS. matcher (userEmail).matches()) {
                            customToast.showCustomToast(getApplicationContext(), "Enter your registered email");
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    customToast.showCustomToast(getApplicationContext(), "Check your Email to reset password");
                                    dialog.dismiss();
                                }else{
                                    customToast.showCustomToast(getApplicationContext(), "Unable to send emai link");
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.cancelbutton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if(dialog.getWindow()!=null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();

            }
        });

        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( Login_Activity.this, SignUp_Activity.class));

            }
        });

        SignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
               // progressBar.setVisibility(View.VISIBLE);


            }
        });

    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
    if (TextUtils.isEmpty(userEmail)) {
        customToast.showCustomToast(getApplicationContext(), "Email is Empty!");
            return;
        }if (TextUtils.isEmpty(userPassword)) {
            customToast.showCustomToast(getApplicationContext(), "Password is Empty!");
            return;
        }if (userPassword.length() < 6){
            customToast.showCustomToast(getApplicationContext(), "Password Length must be greater than 6 letter");
            return;
        }
    //Login User
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            customToast.showCustomToast(getApplicationContext(), "Login Successful");
                            startActivity(new Intent( Login_Activity.this,MainActivity.class));

                            //startActivity(new Intent(Login_Activity.this, MainActivity.class));
                        } else {
                            customToast.showCustomToast(getApplicationContext(), "Error: " + task.isSuccessful());
                        }
                    }
                });
    }
}