package com.example.anujdawar.busservice3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_Local extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "BusService.db";
    public static final String TABLE_NAME = "BusRoutesTable";
    public static final String COL_1 = "BUS_NUMBER";
    public static final String COL_2 = "ROUTE";
    public static final String COL_3 = "FARE";
    public static final String COL_4 = "COMPANY";
    public static final String COL_5 = "TYPE";

    public Database_Local(Context context)
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
        db.execSQL("create table " + TABLE_NAME + " (BUS_NUMBER PRIMARY KEY,ROUTE TEXT,FARE TEXT,COMPANY TEXT,TYPE TEXT)");
    }

    public boolean insertData(String bus_number, String route, String fare, String company, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, bus_number);
        contentValues.put(COL_2, route);
        contentValues.put(COL_3, fare);
        contentValues.put(COL_4, company);
        contentValues.put(COL_5, type);

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

    public boolean updateData(String bus_number, String route, String fare, String company, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, bus_number);
        contentValues.put(COL_2, route);
        contentValues.put(COL_3, fare);
        contentValues.put(COL_4, company);
        contentValues.put(COL_5, type);

        db.update(TABLE_NAME, contentValues, "BUS_NUMBER = ?", new String[] {bus_number});

        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}