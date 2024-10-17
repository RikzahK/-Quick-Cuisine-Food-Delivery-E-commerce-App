package com.rikzah.assignment_2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.SupportModelClass;

import java.util.List;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.ViewHolder>{
    List<SupportModelClass> list;
    public SupportAdapter(List<SupportModelClass> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public SupportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.supportrec_item,parent,false);
        return new ViewHolder(view);}
    @Override
    public void onBindViewHolder(@NonNull SupportAdapter.ViewHolder holder, int position) {
        SupportModelClass supportModelClass=list.get(position);
        holder.question.setText(supportModelClass.getQuestion());
        holder.answer.setText(supportModelClass.getAnswers());
        boolean isExpandable=list.get(position).isExpandable();
        holder.expandablelayout.setVisibility(isExpandable ? View.VISIBLE:View.GONE);}
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question, answer;
        RelativeLayout expandablelayout;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           question=itemView.findViewById(R.id.faq_question);
            answer=itemView.findViewById(R.id.faq_answer);
            expandablelayout=itemView.findViewById(R.id.expandable_layout);
            linearLayout=itemView.findViewById(R.id.linearlayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SupportModelClass supportModelClass=list.get(getAdapterPosition());
                    supportModelClass.setExpandable(!supportModelClass.isExpandable());
                    notifyItemChanged(getAdapterPosition());}});}}}
