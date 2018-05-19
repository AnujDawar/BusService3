package com.example.anujdawar.busservice3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class storeSelectedBusesDatabase extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "SelectedBuses.db";
    public static final String TABLE_NAME = "SelectedBusesTable";
    public static final String COL_1 = "BUS_NUMBER";
    public static final String COL_2 = "COMPANY";
    public static final String COL_3 = "DATE_TIME";
    public static final String COL_4 = "LAT";
    public static final String COL_5 = "LON";
    public static final String COL_6 = "TYPE";

    public storeSelectedBusesDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (BUS_NUMBER PRIMARY KEY,COMPANY TEXT,DATE_TIME TEXT,LAT TEXT,LON TEXT,TYPE TEXT)");
    }

    public boolean insertData(String bus_number, String company, String date_time, String lat, String lon, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, bus_number);
        contentValues.put(COL_2, company);
        contentValues.put(COL_3, date_time);
        contentValues.put(COL_4, lat);
        contentValues.put(COL_5, lon);
        contentValues.put(COL_6, type);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "BUS_NUMBER = ?", new String[] {id});
    }

    public boolean updateData(String bus_number, String company, String date_time, String lat, String lon, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, bus_number);
        contentValues.put(COL_2, company);
        contentValues.put(COL_3, date_time);
        contentValues.put(COL_4, lat);
        contentValues.put(COL_5, lon);
        contentValues.put(COL_6, type);

        db.update(TABLE_NAME, contentValues, "BUS_NUMBER = ?", new String[] {bus_number});

        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}