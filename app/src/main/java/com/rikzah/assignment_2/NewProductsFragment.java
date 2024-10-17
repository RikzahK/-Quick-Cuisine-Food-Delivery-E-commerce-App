package com.rikzah.assignment_2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.airbnb.lottie.LottieAnimationView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rikzah.assignment_2.adapters.MessageRVAdapter;
import com.rikzah.assignment_2.adapters.MycartAdapter;
import com.rikzah.assignment_2.adapters.ViewAllAdapter;
import com.rikzah.assignment_2.adapters.newproductadapter;
import com.rikzah.assignment_2.models.MessageModal;
import com.rikzah.assignment_2.models.MycartModel;
import com.rikzah.assignment_2.models.ViewAllModel;
import com.rikzah.assignment_2.models.newproductmodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewProductsFragment extends Fragment {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    RecyclerView recyclerView1;
    newproductadapter adapter;
    List<newproductmodel> newproductModelList;
    LottieAnimationView progressbar;
    LinearLayout layout2;
    public NewProductsFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_products,container,false);
        layout2=root.findViewById(R.id.layout2);
        progressbar =root.findViewById(R.id.progressbar);
        progressbar.animate().translationX(0).setDuration(88888).setStartDelay(0);
        progressbar.playAnimation();

        progressbar.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recyclerView1=root.findViewById(R.id.newprdct_recy);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        newproductModelList = new ArrayList<>();
        adapter = new newproductadapter(getActivity(), newproductModelList);
        recyclerView1.setAdapter(adapter);

        // Get the current date and time
        //Calendar calendar = Calendar.getInstance();
        //Date currentDate = calendar.getTime();
        String saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        saveCurrentDate = currentDate.format(calForDate.getTime());

       // Calculate the date and time one week ago
        Calendar calForOneWeekAgo = Calendar.getInstance();
        calForOneWeekAgo.add(Calendar.DAY_OF_YEAR, -7);
        String saveOneWeekAgoDate = currentDate.format(calForOneWeekAgo.getTime());


        // Calculate the date one week ago
        //calendar.add(Calendar.DAY_OF_YEAR, -7);
        //Date oneWeekAgo = calendar.getTime();

        // Convert dates to formatted strings
        //String currentDateTimestamp = String.valueOf(currentDate.getTime());
        //String oneWeekAgoTimestamp = String.valueOf(oneWeekAgo.getTime());


        firestore.collection("AllProducts")
                .whereNotEqualTo("currentDate", "")
                .whereGreaterThanOrEqualTo("currentDate", saveOneWeekAgoDate)
                .whereLessThanOrEqualTo("currentDate", saveCurrentDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressbar.setVisibility(View.GONE);
                            layout2.setVisibility(View.VISIBLE);

                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                newproductmodel productmodel = documentSnapshot.toObject(newproductmodel.class);

                                // Retrieve the date string
                                String dateString = productmodel.getCurrentDate();

                                // Ensure the retrieved date string matches the expected format
                                if (isValidDateFormat(dateString)) {
                                    newproductModelList.add(productmodel);

                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            // Handle errors
                            customToast.showCustomToast(getActivity(), "Error getting documents: " + task.getException());
                        }
                    }
                });



        return root;
    }
    private boolean isValidDateFormat(String dateString) {

        //  Assuming the format is "yyyy-MM-dd HH:mm:ss"
        String pattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        return dateString.matches(pattern);
    }

}

/**public class NewProductsFragment extends Fragment {
 ProgressBar progressBar;
 ScrollView scrollView;
 LottieAnimationView animationview7;


 // creating variables for our
 // widgets in xml file.
 private RecyclerView chatsRV;
 private ImageButton sendMsgIB;
 private EditText userMsgEdt;
 private final String USER_KEY = "user";
 private final String BOT_KEY = "bot";

 // creating a variable for
 // our volley request queue.
 private RequestQueue mRequestQueue;

 // creating a variable for array list and adapter class.
 private ArrayList<MessageModal> messageModalArrayList;
 private MessageRVAdapter messageRVAdapter;

 public NewProductsFragment() {
 // Required empty public constructor
 }
 @SuppressLint("MissingInflatedId")
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 // Inflate the layout for this fragment
 View root = inflater.inflate(R.layout.fragment_new_products,container,false);
 //progressBar=root.findViewById(R.id.progressbar);
 //scrollView=root.findViewById(R.id.scroll_view);

 /**animationview7=root.findViewById(R.id.AnimationView7);
 animationview7.animate().translationX(0).setDuration(88888).setStartDelay(0);
 animationview7.playAnimation();**/

//progressBar.setVisibility(View.VISIBLE);
//scrollView.setVisibility(View.GONE);

//progressBar.setVisibility(View.GONE);
//scrollView.setVisibility(View.VISIBLE);


// on below line we are initializing all our views.
     /**   chatsRV = root.findViewById(R.id.idRVChats);
                sendMsgIB = root.findViewById(R.id.idIBSend);
                userMsgEdt = root.findViewById(R.id.idEdtMessage);

                // below line is to initialize our request queue.
                mRequestQueue = Volley.newRequestQueue(getContext());
                mRequestQueue.getCache().clear();

                // creating a new array list
                messageModalArrayList = new ArrayList<>();

        // adding on click listener for send message button.
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        // checking if the message entered
        // by user is empty or not.
        if (userMsgEdt.getText().toString().isEmpty()) {
        // if the edit text is empty display a toast message.
        Toast.makeText(getContext(), "Please enter your message..", Toast.LENGTH_SHORT).show();
        return;
        }

        // calling a method to send message
        // to our bot to get response.
        sendMessage(userMsgEdt.getText().toString());

        // below line we are setting text in our edit text as empty
        userMsgEdt.setText("");
        }
        });

        // on below line we are initializing our adapter class and passing our array list to it.
        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, getContext());

        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        // below line is to set layout
        // manager to our recycler view.
        chatsRV.setLayoutManager(linearLayoutManager);

        // below line we are setting
        // adapter to our recycler view.
        chatsRV.setAdapter(messageRVAdapter);

        return root;
        }


private void sendMessage(String userMsg) {
        // below line is to pass message to our
        // array list which is entered by the user.
        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();

        // url for our brain
        // make sure to add mshape for uid.
        // make sure to add your url.
        String url = "http://api.brainshop.ai/get?bid=179831&key=RonOr6iNjKF8nboj&uid=[uid]&msg=" + userMsg;

        // creating a variable for our request queue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // on below line we are making a json object request for a get request and passing our url .
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
@Override
public void onResponse(JSONObject response) {
        try {
        // in on response method we are extracting data
        // from json response and adding this response to our array list.
        String botResponse = response.getString("cnt");
        messageModalArrayList.add(new MessageModal(botResponse, BOT_KEY));

        // notifying our adapter as data changed.
        messageRVAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
        e.printStackTrace();

        // handling error response from bot.
        messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
        messageRVAdapter.notifyDataSetChanged();
        }
        }
        }, new Response.ErrorListener() {
@Override
public void onErrorResponse(VolleyError error) {
        // error handling.
        messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
        Toast.makeText(getContext(), "No response from the bot..", Toast.LENGTH_SHORT).show();
        }
        });

        // at last adding json object
        // request to our queue.
        queue.add(jsonObjectRequest);
        }
        }
**/