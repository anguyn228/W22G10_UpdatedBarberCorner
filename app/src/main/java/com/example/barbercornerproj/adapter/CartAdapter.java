package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.barbercornerproj.Database.OrderContract;
import com.example.barbercornerproj.R;

public class CartAdapter extends CursorAdapter {
    public CartAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.order_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView productName, price, quantity;
        productName = view.findViewById(R.id.product_name);
        price = view.findViewById(R.id.product_price);
        quantity = view.findViewById(R.id.quantity);

        int name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME);
        int priceOfproduct = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE);
        int quantityOfproduct = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY);

        String nameOfProduct = cursor.getString(name);
        String priceOfProduct = cursor.getString(priceOfproduct);
        String quantityOfProduct = cursor.getString(quantityOfproduct);


        productName.setText(nameOfProduct);
        price.setText(priceOfProduct);
        quantity.setText(quantityOfProduct);

    }
}
