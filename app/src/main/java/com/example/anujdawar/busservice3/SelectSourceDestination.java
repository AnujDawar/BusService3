package com.example.anujdawar.busservice3;

import android.content.Intent;
import android.database.Cursor;
import android.media.audiofx.AudioEffect;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SelectSourceDestination extends AppCompatActivity {

    Spinner sourceSpinner, destinationSpinner;
    Button submitButton;
    String[] routeArray, faresArray;
    Database_Local db;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String sourceSelected, destinationSelected, startPointOfBus, endPointOfBus;
    Intent ListOfBusesIntent;
    storeSelectedBusesDatabase storeDB;
    ArrayList<String> resultBusNumbers;
    ArrayList<product> busesDetailsToSend;
    int fare = 0;
    List<String> ArraySource, ArrayDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_source_destination);
        init();
    }

    private void init()
    {
        sourceSpinner = (Spinner) findViewById(R.id.sourceSpinner);
        destinationSpinner = (Spinner) findViewById(R.id.destinationSpinner);
        submitButton = (Button) findViewById(R.id.stateSelectButton);

        ArraySource = new ArrayList<>();
        ArrayDestination = new ArrayList<>();

        populateSourceArray();
        populateDestinationArray();

        HintAdapter adapter1 = new HintAdapter(this,R.layout.spinner_list,ArraySource);
        sourceSpinner.setAdapter(adapter1);
        sourceSpinner.setSelection(adapter1.getCount());

        final HintAdapter adapter2 = new HintAdapter(this,R.layout.spinner_list,ArrayDestination);
        destinationSpinner.setAdapter(adapter2);
        destinationSpinner.setSelection(adapter2.getCount());

        db = new Database_Local(this);
        storeDB = new storeSelectedBusesDatabase(this);
        resultBusNumbers = new ArrayList<>();
        busesDetailsToSend = new ArrayList<>();

        ListOfBusesIntent = new Intent(SelectSourceDestination.this, MainActivity.class);
        database = FirebaseDatabase.getInstance();

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                sourceSelected = String.valueOf(sourceSpinner.getItemAtPosition(i));
                ArrayDestination.clear();
                populateDestinationArray();
                ArrayDestination.remove(sourceSelected);
                destinationSpinner.setSelection(adapter2.getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {           }
        });

        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                destinationSelected = String.valueOf(destinationSpinner.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {          }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Intent listIntent = new Intent(SelectSourceDestination.this, MainActivity.class);
                        listIntent.putExtra("sourceDestination", sourceSelected + ";" + destinationSelected);
                        startActivity(listIntent);
                    }
                }, 0);
            }
        });
    }

    private void populateSourceArray()
    {
        ArraySource.add("state1");
        ArraySource.add("state2");
        ArraySource.add("state3");
        ArraySource.add("state4");
        ArraySource.add("state5");
        ArraySource.add("state6");
        ArraySource.add("state7");
        ArraySource.add("Select Source");
    }

    private void populateDestinationArray()
    {
        ArrayDestination.add("state1");
        ArrayDestination.add("state2");
        ArrayDestination.add("state3");
        ArrayDestination.add("state4");
        ArrayDestination.add("state5");
        ArrayDestination.add("state6");
        ArrayDestination.add("state7");
        ArrayDestination.add("Select Destination");
    }
}