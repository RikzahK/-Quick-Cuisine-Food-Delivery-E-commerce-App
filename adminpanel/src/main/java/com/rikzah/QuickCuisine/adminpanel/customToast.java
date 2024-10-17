package com.rikzah.QuickCuisine.adminpanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class customToast {
    // CustomToast.java
        public static void showCustomToast(Context context, String message) {
            // Inflate the custom layout
            View layout = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            // Customize the layout elements
            ImageView icon = layout.findViewById(R.id.apptoasticon);
            icon.setImageResource(R.drawable.applogo); // Replace with your custom icon
            TextView text = layout.findViewById(R.id.toasttext);
            text.setText(message);
            // Create the toast and set the custom view
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();}}
