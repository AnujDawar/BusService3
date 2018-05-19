package com.example.anujdawar.busservice3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected ListView lvProduct;
    protected productListAdapter adapter;
    protected List<product> mProductList;
    String selectedBusNumber;
    String buses;
    Database_Local db;
    String startPointOfBus, endPointOfBus;
    String[] routeArray, faresArray;
    int fare;
    TextView noBusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProduct = (ListView) findViewById(R.id.myListID);
        mProductList = new ArrayList<>();
        adapter = new productListAdapter(getApplicationContext(), mProductList);
        lvProduct.setAdapter(adapter);

        Bundle b = getIntent().getExtras();
        buses = b.get("sourceDestination").toString();
        db = new Database_Local(this);

        noBusTV = (TextView) findViewById(R.id.noBusFoundTV);

//        if(buses == null || buses.length() > 1)
//            noBusTV.setVisibility(View.GONE);
//        else
//            noBusTV.setVisibility(View.VISIBLE);

        String source = buses.substring(0, buses.indexOf(";"));
        String destination = buses.substring(buses.indexOf(";") + 1, buses.length());

        findBusNumber(source, destination);
        Collections.sort(mProductList, new ComparatorClass());

        setOnListClickListener();
    }

    public void findBusNumber(final String source, final String destination)
    {
        Cursor cursor = db.viewAllData();
        int sourceIndex = -1, destinationIndex = -1;

        while(cursor.moveToNext())
        {
            routeArray = cursor.getString(1).split(";");
            faresArray = cursor.getString(2).split(";");
            startPointOfBus = routeArray[0];
            endPointOfBus = routeArray[routeArray.length - 1];
            fare = 0;

            for(int i = 0; i < routeArray.length; i++)
            {
                if(routeArray[i].equals(source))
                    sourceIndex = i;

                if(routeArray[i].equals(destination))
                    destinationIndex = i;
            }

            if (sourceIndex < destinationIndex && sourceIndex != -1 && destinationIndex != -1)
            {
                for(int i = sourceIndex; i <= destinationIndex; i++)
                    fare += Integer.parseInt(faresArray[i]);

                mProductList.add(new product(cursor.getString(0), cursor.getString(4), cursor.getString(3),
                        String.valueOf(fare), startPointOfBus, endPointOfBus));

                noBusTV.setVisibility(View.GONE);
            }

            sourceIndex = destinationIndex = -1;
        }

        cursor.close();
    }

    private void setOnListClickListener()
    {
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                product temp = (product) adapterView.getItemAtPosition(i);
                selectedBusNumber = temp.getMyBusNumber();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                        myIntent.putExtra("selectedBusNumber", selectedBusNumber);
                        myIntent.putExtra("sourceDestination", buses);
                        startActivity(myIntent);
                    }
                }, 0);
            }
        });
    }

}