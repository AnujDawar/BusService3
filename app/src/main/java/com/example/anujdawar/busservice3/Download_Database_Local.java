package com.example.anujdawar.busservice3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Download_Database_Local extends AppCompatActivity {

    ProgressBar updateTableProgressBar;
    SharedPreferences sharedPref;
    SharedPreferences.Editor myEditor;
    String updateBit = "";
    Database_Local db;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String myID;
    TextView progressView;
    int counterToInsert;
    String route, fare, bus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download__database__local);

        init();

        sharedPref = getSharedPreferences("BusServicePref", MODE_PRIVATE);
        updateBit = sharedPref.getString("updateBit", "");
        myID = sharedPref.getString("myID", "");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://busservice3.firebaseio.com/BusRoutes/");
        db = new Database_Local(this);
        db.deleteAll();

        counterToInsert = 0;

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                long count = dataSnapshot.getChildrenCount();
                int progressIncrement = (int) (100/count);
                int currentProgress = progressIncrement;

                for(DataSnapshot x : dataSnapshot.child("BusNumbers").getChildren())
                {
                    db.insertData(String.valueOf(x.getKey()),
                            dataSnapshot.child("States_Details").child(String.valueOf(x.getKey())).getValue(String.class),
                            dataSnapshot.child("Fares_Details").child(String.valueOf(x.getKey())).getValue(String.class),
                            dataSnapshot.child("Company_Details").child(String.valueOf(x.getKey())).getValue(String.class),
                            dataSnapshot.child("Type_Details").child(String.valueOf(x.getKey())).getValue(String.class));

                    progressView.setText(String.valueOf("UPDATING..." + currentProgress + "%"));
                    updateTableProgressBar.setProgress(currentProgress);

                    if ((currentProgress + progressIncrement) <= 100)
                        currentProgress += progressIncrement;
                }

                myRef.getParent().child("user_details").child(myID).setValue("0");

                myEditor = sharedPref.edit();
                myEditor.putString("updateBit", "0");
                myEditor.apply();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Intent myIntent = new Intent(Download_Database_Local.this, SelectSourceDestination.class);
                        startActivity(myIntent);
                        finish();
                    }
                }, 0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                }
        });
    }

    private void init()
    {
        updateTableProgressBar = (ProgressBar) findViewById(R.id.updateDatabaseProgressBar);
        updateTableProgressBar.setProgress(0);
        progressView = (TextView) findViewById(R.id.progressTextView);
    }
}