package com.rikzah.assignment_2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.customToast;
import com.rikzah.assignment_2.models.MycartModel;

import java.util.List;
public class MycartAdapter extends RecyclerView.Adapter<MycartAdapter.ViewHolder> {
    Context context;
    List<MycartModel> mycartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    int totalamount = 0;

    public MycartAdapter(Context context, List<MycartModel> mycartModelList) {
        this.context = context;
        this.mycartModelList = mycartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MycartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MycartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int newprice = (Integer.parseInt(mycartModelList.get(position).getProductPrice())) * Integer.parseInt(mycartModelList.get(position).getTotalQuantity());
        // Update totalamount for each item
        totalamount += newprice;
        Glide.with(context).load(mycartModelList.get(position).getProductImage()).into(holder.img);
        holder.productname.setText(mycartModelList.get(position).getProductName());
        holder.productprice.setText(mycartModelList.get(position).getProductPrice());
        holder.qtyordered.setText(mycartModelList.get(position).getTotalQuantity());
        holder.qty.setText(mycartModelList.get(position).getTotalQuantity());
        holder.totalprice.setText(String.valueOf(newprice));
        holder.orderday.setText(mycartModelList.get(position).getCurrentDate());
        holder.ordertime.setText(mycartModelList.get(position).getCurrentTime());
        Intent intent = new Intent("MytotalAmount");
        intent.putExtra("totalAmount", totalamount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddtoCart")
                        .document(mycartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mycartModelList.remove(mycartModelList.get(position));
                                    totalamount -= newprice;
                                    notifyDataSetChanged();
                                    updateTotalAmount(); // Update total amount after item deletion
                                    customToast.showCustomToast(context, "Item Deleted");
                                } else {
                                    customToast.showCustomToast(context, "Error " + task.getException());
                                }
                            }
                        });
            }
        });

        holder.increaseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalQuantity = Integer.parseInt(mycartModelList.get(position).getTotalQuantity());
                if (totalQuantity <= 10) {
                    totalQuantity++;
                    mycartModelList.get(position).setTotalQuantity(String.valueOf(totalQuantity));
                    int newprice = (Integer.parseInt(mycartModelList.get(position).getProductPrice())) * totalQuantity;
                    mycartModelList.get(position).setTotalprice(newprice);
                    // Update totalamount after increasing quantity
                    totalamount += (newprice - mycartModelList.get(position).getTotalprice());
                    updateFirestoreQuantityAndTotalPrice(position, totalQuantity, newprice);
                } else {
                    customToast.showCustomToast(context, "Can't order more than 10 packages at a time");
                }
                notifyDataSetChanged();
                updateTotalAmount(); // Update total amount after increasing quantity
            }
        });

        holder.decreaseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalQuantity = Integer.parseInt(mycartModelList.get(position).getTotalQuantity());
                if (totalQuantity > 0) {
                    totalQuantity--;
                    mycartModelList.get(position).setTotalQuantity(String.valueOf(totalQuantity));
                    int newprice = (Integer.parseInt(mycartModelList.get(position).getProductPrice())) * totalQuantity;
                    mycartModelList.get(position).setTotalprice(newprice);
                    // Update totalamount after decreasing quantity
                    totalamount += (newprice - mycartModelList.get(position).getTotalprice());
                    updateFirestoreQuantityAndTotalPrice(position, totalQuantity, newprice);
                } else {
                    customToast.showCustomToast(context, "No items in the cart");
                }
                notifyDataSetChanged();
                updateTotalAmount(); // Update total amount after decreasing quantity
            }
        });



    }

    // Add this method to update quantity and total price in Firestore
    private void updateFirestoreQuantityAndTotalPrice(int position, int quantity, int totalPrice) {
        String documentId = mycartModelList.get(position).getDocumentId();
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddtoCart").document(documentId)
                .update("totalQuantity", String.valueOf(quantity), "totalprice", totalPrice)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Successfully updated in Firestore
                            // You can add a log or additional actions if needed
                        } else {
                            customToast.showCustomToast(context, "Error updating Firestore: " + task.getException());
                        }
                    }
                });
    }

    private void updateTotalAmount() {
        totalamount = 0;
        for (MycartModel model : mycartModelList) {
            totalamount += model.getTotalprice();
        }

        Intent intent = new Intent("MytotalAmount");
        intent.putExtra("totalAmount", totalamount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    @Override
    public int getItemCount() {
        return mycartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname, productprice, qtyordered, totalprice, ordertime, orderday, qty;
        ImageView img;
        Button deleteItem;
        ImageView increaseitem, decreaseitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.prdctimg);
            productname = itemView.findViewById(R.id.product_name);
            productprice = itemView.findViewById(R.id.product_price);
            qtyordered = itemView.findViewById(R.id.product_qty);
            totalprice = itemView.findViewById(R.id.product_totalprice);
            orderday = itemView.findViewById(R.id.orderday);
            ordertime = itemView.findViewById(R.id.ordertime);
            deleteItem = itemView.findViewById(R.id.delete);
            increaseitem = itemView.findViewById(R.id.add_item);
            decreaseitem = itemView.findViewById(R.id.remove_item);
            qty = itemView.findViewById(R.id.quantity);
        }
    }
}
/**package com.rikzah.assignment_2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.MycartModel;

import java.util.List;

public class MycartAdapter extends RecyclerView.Adapter<MycartAdapter.ViewHolder>{
    Context context;
    List<MycartModel> mycartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    int totalamount=0;


    public MycartAdapter() {
    }

    public MycartAdapter(Context context, List<MycartModel> mycartModelList) {
        this.context = context;
        this.mycartModelList = mycartModelList;
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MycartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MycartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int newprice = (Integer.parseInt(mycartModelList.get(position).getProductPrice())) * Integer.parseInt(mycartModelList.get(position).getTotalQuantity());
        // Update totalamount for each item
        totalamount += newprice;
        Glide.with(context).load(mycartModelList.get(position).getProductImage()).into(holder.img);
        holder.productname.setText(mycartModelList.get(position).getProductName());
        holder.productprice.setText(mycartModelList.get(position).getProductPrice());
        holder.qtyordered.setText(mycartModelList.get(position).getTotalQuantity());
        holder.qty.setText(mycartModelList.get(position).getTotalQuantity());
        holder.totalprice.setText(String.valueOf(newprice));
        holder.orderday.setText(mycartModelList.get(position).getCurrentDate());
        holder.ordertime.setText(mycartModelList.get(position).getCurrentTime());
        Intent intent = new Intent("MytotalAmount");
        intent.putExtra("totalAmount",totalamount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddtoCart")
                        .document(mycartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mycartModelList.remove(mycartModelList.get(position));
                                    totalamount -= newprice;
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        holder.increaseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalQuantity = Integer.parseInt(mycartModelList.get(position).getTotalQuantity());
                if (totalQuantity <= 10) {
                    totalQuantity++;
                    mycartModelList.get(position).setTotalQuantity(String.valueOf(totalQuantity));
                    int newprice = (Integer.parseInt(mycartModelList.get(position).getProductPrice())) * totalQuantity;
                    mycartModelList.get(position).setTotalprice(newprice);
                    // Update totalamount after increasing quantity
                    totalamount += (newprice - mycartModelList.get(position).getTotalprice());
                } else {
                    Toast.makeText(context, "Can't order more than 10 packages at a time", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });

        holder.decreaseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalQuantity = Integer.parseInt(mycartModelList.get(position).getTotalQuantity());
                if (totalQuantity > 0) {
                    totalQuantity--;
                    mycartModelList.get(position).setTotalQuantity(String.valueOf(totalQuantity));
                    int newprice = (Integer.parseInt(mycartModelList.get(position).getProductPrice())) * totalQuantity;
                    mycartModelList.get(position).setTotalprice(newprice);
                    // Update totalamount after decreasing quantity
                    totalamount += (newprice - mycartModelList.get(position).getTotalprice());
                } else {
                    Toast.makeText(context, "No items in the cart", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });
    }


        @Override
    public int getItemCount() {
        return mycartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname, productprice, qtyordered, totalprice, ordertime, orderday, qty;
        ImageView img;
        Button deleteItem;
        ImageView increaseitem, decreaseitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.prdctimg);
            productname = itemView.findViewById(R.id.product_name);
            productprice = itemView.findViewById(R.id.product_price);
            qtyordered = itemView.findViewById(R.id.product_qty);
            totalprice = itemView.findViewById(R.id.product_totalprice);
            orderday = itemView.findViewById(R.id.orderday);
            ordertime = itemView.findViewById(R.id.ordertime);
            deleteItem = itemView.findViewById(R.id.delete);
            increaseitem = itemView.findViewById(R.id.add_item);
            decreaseitem = itemView.findViewById(R.id.remove_item);
            qty = itemView.findViewById(R.id.quantity);
        }
    }
}**/

/**package com.rikzah.assignment_2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.MycartModel;

import java.util.List;

public class MycartAdapter extends RecyclerView.Adapter<MycartAdapter.ViewHolder>{
    Context context;
    List<MycartModel> mycartModelList;
    int totalprice=0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MycartAdapter() {
    }

    public MycartAdapter(Context context, List<MycartModel> mycartModelList) {
        this.context = context;
        this.mycartModelList = mycartModelList;
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MycartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MycartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(mycartModelList.get(position).getProductImage()).into(holder.img);
        holder.productname.setText(mycartModelList.get(position).getProductName());
        holder.productprice.setText(mycartModelList.get(position).getProductPrice());
        holder.qtyordered.setText(mycartModelList.get(position).getTotalQuantity());
        holder.totalprice.setText(String.valueOf(mycartModelList.get(position).getTotalprice()));
        holder.orderday.setText(mycartModelList.get(position).getCurrentDate());
        holder.ordertime.setText(mycartModelList.get(position).getCurrentTime());
        // Add click listener for increase quantity button
        holder.increaseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase the quantity and update the model
                int newQuantity = Integer.parseInt(mycartModelList.get(position).getTotalQuantity()) + 1;
                mycartModelList.get(position).setTotalQuantity(String.valueOf(newQuantity));

                // Update the total price and notify the adapter
                updateTotalPrice();
                notifyDataSetChanged();
            }
        });
        // Add click listener for decrease quantity button
        holder.decreaseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure the quantity is greater than 0 before decreasing
                int currentQuantity = Integer.parseInt(mycartModelList.get(position).getTotalQuantity());
                if (currentQuantity > 0) {
                    // Decrease the quantity and update the model
                    int newQuantity = currentQuantity - 1;
                    mycartModelList.get(position).setTotalQuantity(String.valueOf(newQuantity));

                    // Update the total price and notify the adapter
                    updateTotalPrice();
                    notifyDataSetChanged();
                }
            }
        });

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddtoCart")
                        .document(mycartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    mycartModelList.remove(mycartModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        //pass total amount to My Cart Fragment
        totalprice = totalprice + mycartModelList.get(position).getTotalprice();
        Intent intent = new Intent( "MyTotalAmount");
        intent.putExtra( "totalAmount", totalprice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return mycartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname,productprice,qtyordered,totalprice,ordertime,orderday;
        ImageView img;
        Button deleteItem;
        Button increaseitem,decreaseitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.prdctimg);
            productname=itemView.findViewById(R.id.product_name);
            productprice=itemView.findViewById(R.id.product_price);
            qtyordered=itemView.findViewById(R.id.product_qty);
            totalprice=itemView.findViewById(R.id.product_totalprice);
            orderday=itemView.findViewById(R.id.orderday);
            ordertime=itemView.findViewById(R.id.ordertime);
            deleteItem=itemView.findViewById(R.id.delete);
            increaseitem=itemView.findViewById(R.id.add_item);
            decreaseitem=itemView.findViewById(R.id.remove_item);

        }
    }
    // Method to update the total price
    private void updateTotalPrice() {
        totalprice = 0;
        for (MycartModel model : mycartModelList) {
            totalprice += model.getTotalprice();
        }

        // Send the updated total amount to My Cart Fragment
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalprice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
**/