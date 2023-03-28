package com.example.analyticsassignment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.analyticsassignment.DetailsMainActivity2;
import com.example.analyticsassignment.R;
import com.example.analyticsassignment.modal.details;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {
    private FirebaseAnalytics mFirebaseAnalytics;

    Context context ;

    private List<details> details;

    public noteAdapter(Context context, List<details> details){
        this.context = context;
        this.details = details ;
    }

    @NonNull
    @Override
    public noteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_view_note,parent,false);
        return new noteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(details.get(position).getName());
        holder.details.setText(details.get(position).getDetails());
        holder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsMainActivity2.class);
                intent.putExtra("name",details.get(holder.getAdapterPosition()).getName());
                intent.putExtra("details",details.get(holder.getAdapterPosition()).getDetails());
                btnEvent("DetailsNote@","click","Button");
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public  TextView details;
        public Button showMore;

        ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.nameTitle);
            this.details = itemView.findViewById(R.id.details);
            this.showMore = itemView.findViewById(R.id.showMore);
        }

        @Override
        public void onClick(View v) {


        }


    }
    private void btnEvent(String id, String type, String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,id);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,type);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);//save it at firebase
        Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();

    }
}
