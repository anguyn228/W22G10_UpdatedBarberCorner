package com.example.barbercornerproj.Database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.barbercornerproj.adapter.OrderAdapter;

public class OrderContract {

    public OrderContract(){

    }

    public static final String CONTENT_AUTHORITY="com.example.barbercornerproj";
    public static final Uri BASE_URI=Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH="shop";

    public static abstract class OrderEntry implements BaseColumns{


        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_URI,PATH);

        public static final String TABLE_NAME="shop";

        public static final String _ID=BaseColumns._ID;

        public static final String COLUMN_NAME="name";

        public static final String COLUMN_QUANTITY="quantity";

        public static final String COLUMN_PRICE="price";


    }
}
