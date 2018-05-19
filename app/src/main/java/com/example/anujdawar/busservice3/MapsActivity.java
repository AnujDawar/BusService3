package com.example.anujdawar.busservice3;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    String selectedBusNumber;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String latitude = "", longitude = "";
    boolean zoomCameraFlag;
    String buses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle b = getIntent().getExtras();
        selectedBusNumber = b.get("selectedBusNumber").toString();
        buses = b.get("sourceDestination").toString();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        initFirebaseConnection();
        zoomCameraFlag = true;
    }

    private void initFirebaseConnection()
    {
        database = FirebaseDatabase.getInstance();

        myRef = database.getReferenceFromUrl("https://busservice3.firebaseio.com/bus_locations/" + selectedBusNumber + "/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                latitude = dataSnapshot.child("lat").getValue(String.class);
                longitude = dataSnapshot.child("lon").getValue(String.class);

                try
                {
                    go();
                }
                catch (Exception e) {
                    e.printStackTrace();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            Toast.makeText(MapsActivity.this, "This Bus Cannot Be Tracked Right Now :'/", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(MapsActivity.this, MainActivity.class);
                            myIntent.putExtra("sourceDestination", buses);
                            startActivity(myIntent);
                            finish();
                        }
                    }, 0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)     {   }
        });
    }

    void go() throws Exception
    {
        mMap.clear();
        LatLng busPosition = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions().position(busPosition).title(selectedBusNumber));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(busPosition));

        if (zoomCameraFlag)
        {
            zoomCameraFlag = false;
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }
}