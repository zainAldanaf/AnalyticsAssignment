package com.example.analyticsassignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.analyticsassignment.R;
import com.example.analyticsassignment.modal.Notes;
import com.example.analyticsassignment.modal.details;
import com.example.analyticsassignment.noteMainActivity2;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;


public class addressAdapter extends RecyclerView.Adapter<addressAdapter.ViewHolder> {
    private FirebaseAnalytics mFirebaseAnalytics;

    Context context ;
    private List<Notes> data;
    private LayoutInflater mInflater;

    public addressAdapter(Context context, List<Notes> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_view,parent,false);
        return new addressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes note = data.get(position);
        holder.name.setText(note.getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public Button btn;

        ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.btn = itemView.findViewById(R.id.addressbtn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,noteMainActivity2.class));
                        btnEvent("addressNote@","click","Button");
                }
            });
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

    }
}
