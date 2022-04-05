    package com.example.barbercornerproj.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrderProvider extends ContentProvider {

   public static final int ORDER=100;

   public static UriMatcher sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
   static{
       sUriMatcher.addURI(OrderContract.CONTENT_AUTHORITY,OrderContract.PATH,ORDER);
   }

    public OrderHelper myHelper;

    @Override
    public boolean onCreate() {
       myHelper=new OrderHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1,  String s1) {


        SQLiteDatabase database=myHelper.getReadableDatabase();

        Cursor cursor;

        int match=sUriMatcher.match(uri);
        switch (match){
            case ORDER:
                cursor=database.query(OrderContract.OrderEntry.TABLE_NAME,strings,s,strings1,null,null,s1);
            break;

            default:
                throw new IllegalArgumentException("Can't Query");
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues contentValues) {
        int match =sUriMatcher.match(uri);
        switch(match){
            case ORDER:
                return insertCart(uri,contentValues);

            default:
                throw new IllegalArgumentException("Can't insert");
        }
    }

    private Uri insertCart(Uri uri, ContentValues contentValues) {

        String name= contentValues.getAsString(OrderContract.OrderEntry.COLUMN_NAME);

        if(name==null){

            throw new IllegalArgumentException("Name is required");

        }


        String quantity= contentValues.getAsString(OrderContract.OrderEntry.COLUMN_QUANTITY);

        if(quantity==null){

            throw new IllegalArgumentException("quantity is required");

        }

        String price= contentValues.getAsString(OrderContract.OrderEntry.COLUMN_PRICE);

        if(price==null){

            throw new IllegalArgumentException("price is required");

        }

        SQLiteDatabase database=myHelper.getWritableDatabase();
        long id=database.insert(OrderContract.OrderEntry.TABLE_NAME,null,contentValues);

        if(id==-1){
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete( Uri uri,  String s, String[] strings) {


        int rowsDeleted;
        SQLiteDatabase database=myHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        switch (match){
            case ORDER:
                rowsDeleted=database.delete(OrderContract.OrderEntry.TABLE_NAME,s,strings);
                break;
            default:
                throw new IllegalArgumentException("Cannot delete");
        }

        if (rowsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsDeleted;
    }

    @Override
    public int update( Uri uri, ContentValues contentValues,  String s,  String[] strings) {
        return 0;
    }
}
