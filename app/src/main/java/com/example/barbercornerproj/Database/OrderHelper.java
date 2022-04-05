package com.example.barbercornerproj.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OrderHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="barbershopData.db";

    public OrderHelper( Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_TABLE=" CREATE TABLE "+OrderContract.OrderEntry.TABLE_NAME+" ("
                +OrderContract.OrderEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +OrderContract.OrderEntry.COLUMN_NAME+" TEXT NOT NULL,"
                +OrderContract.OrderEntry.COLUMN_QUANTITY+"TEXT NOT NULL,"
                + OrderContract.OrderEntry.COLUMN_PRICE+" TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
