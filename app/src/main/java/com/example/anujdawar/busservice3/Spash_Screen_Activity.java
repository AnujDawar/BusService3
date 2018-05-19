package com.example.anujdawar.busservice3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Spash_Screen_Activity extends AppCompatActivity {

    DatabaseReference myRef;
    long count = 0;
    SharedPreferences sharedPref;
    SharedPreferences.Editor myEditor;
    String myID = "";
    FirebaseDatabase database;
    String updateBit = "default";
    TextView splashTextView;

    Handler handlerToSetMaintenance = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            splashTextView.setText("Server Under Maintenance\nKindly Wait Or Try Again Later");
        }
    };

    Handler handlerToSetMaintenance2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            splashTextView.setText("\nServer Under Maintenance");
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash__screen_);

        sharedPref = getSharedPreferences("BusServicePref", MODE_PRIVATE);
        myID = sharedPref.getString("myID", "");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://busservice3.firebaseio.com/maintenance/");

        splashTextView = (TextView) findViewById(R.id.connectToServerTV);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(String.class).equals("1"))
                    handlerToSetMaintenance.sendEmptyMessage(0);

                else {
                    DatabaseReference myRef2 = database.getReferenceFromUrl("https://busservice3.firebaseio.com/user_details/");

                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (myID.length() == 0)
                            {
                                count = dataSnapshot.getChildrenCount();
                                myEditor = sharedPref.edit();
                                myEditor.putString("myID", String.valueOf(count));
                                myEditor.putString("updateBit", "1");
                                myEditor.apply();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent myIntent = new Intent(Spash_Screen_Activity.this, Download_Database_Local.class);
                                        startActivity(myIntent);
                                        finish();
                                    }
                                }, 0);
                            }

                            else {
                                updateBit = dataSnapshot.child(myID).getValue(String.class);

                                if (updateBit.equals("1")) {
                                    myEditor = sharedPref.edit();
                                    myEditor.putString("updateBit", "1");
                                    myEditor.apply();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent myIntent = new Intent(Spash_Screen_Activity.this, Download_Database_Local.class);
                                            startActivity(myIntent);
                                            finish();
                                        }
                                    }, 0);
                                }

                                else {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent myIntent = new Intent(Spash_Screen_Activity.this, SelectSourceDestination.class);
                                            startActivity(myIntent);
                                            finish();
                                        }
                                    }, 0);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}