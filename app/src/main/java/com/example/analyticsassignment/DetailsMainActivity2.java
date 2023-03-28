package com.example.analyticsassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class DetailsMainActivity2 extends AppCompatActivity {
    TextView nameTv, detailsTv;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAnalytics mFirebaseAnalytics;
    Calendar calendar = Calendar.getInstance();

    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_main2);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        screenTrack("NoteDetailsScreen");

        nameTv = findViewById(R.id.noteName);
        detailsTv = findViewById(R.id.noteDetails);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            nameTv.setText(bundle.getString("name"));
            detailsTv.setText(bundle.getString("details"));
        }
    }
    public void screenTrack(String screenName){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,"noteMainActivity2");

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,bundle);//save it at firebase
    }

    public void btnEvent(String id, String type, String content) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);//save it at firebase
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
        users.put("name","NoteDetailsScreen");
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