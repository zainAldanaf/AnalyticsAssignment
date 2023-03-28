package com.example.analyticsassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.analyticsassignment.Adapter.noteAdapter;
import com.example.analyticsassignment.modal.details;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class noteMainActivity2 extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAnalytics mFirebaseAnalytics;

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    ArrayList<details> items;
    noteAdapter note_adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView note_rv;
    private TextView msgTV;
    TextView textViewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main2);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        screenTrack("NoteScreen");


        textViewNote = findViewById(R.id.details);

        note_rv = findViewById(R.id.note_rv);
        items = new ArrayList<details>();
        note_adapter = new noteAdapter(this,items);

        GetNoteDetails();
    }

        private void GetNoteDetails() {
         db.collection("note")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.d("zzz", "onSuccess: LIST EMPTY");
                                return;
                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {

                                        String id = documentSnapshot.getId();
                                        String name = documentSnapshot.getString("name");
                                        String details = documentSnapshot.getString("details");

                                        details detail = new details( id, name,details);
                                        items.add(detail);

                                        note_rv.setLayoutManager(layoutManager);
                                        note_rv.setHasFixedSize(true);
                                        note_rv.setAdapter(note_adapter);
                                        note_adapter.notifyDataSetChanged();
                                        Log.e("zzz", items.toString());
                                    }
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("LogDATA", "get failed with ");


                        }
                    });
    }
    public void screenTrack(String screenName){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,"noteMainActivity2");

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,bundle);//save it at firebase
    }

    protected void onPause() {
        Calendar calendar = Calendar.getInstance();
        int hour2 = calendar.get(Calendar.HOUR);
        int minute2 = calendar.get(Calendar.MINUTE);
        int second2 = calendar.get(Calendar.SECOND);

        int h = hour2 - hour;
        int m = minute2 - minute;
        int s = second2 - second;

        HashMap<String,Object> users = new HashMap<>();
        users.put("name","NoteScreen");
        users.put("hours",h);
        users.put("minute",m);
        users.put("seconds",s);
        db.collection("track")
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("zzz", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("zzz", "Error adding document", e);
                    }
                });

        super.onPause();
    }
}