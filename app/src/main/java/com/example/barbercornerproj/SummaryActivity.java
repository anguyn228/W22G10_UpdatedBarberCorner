package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.barbercornerproj.Database.OrderContract;
import com.example.barbercornerproj.adapter.CartAdapter;
import com.example.barbercornerproj.adapter.OrderAdapter;
import com.example.barbercornerproj.model.OrderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private int userId;
    public CartAdapter mAdapter;
    private RecyclerView recAllOrder;
    private Button btnOrder, btnClearCart;
    public static final int LOADER = 0;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);

        databaseHelper = new DatabaseHelper(this);

        String productName = getIntent().getStringExtra(InfoProdShopActivity.TAG_PRODUCT_NAME);
        int productPrice = getIntent().getIntExtra(InfoProdShopActivity.TAG_PRODUCT_PRICE, 0);
        int productQuantity = getIntent().getIntExtra(InfoProdShopActivity.TAG_PRODUCT_QUANTITY, 0);

        SharedPreferences sharedPreferences = this.getApplication().getSharedPreferences(
                "shopping_cart_" + String.valueOf(userId),
                Context.MODE_PRIVATE
        );

        ArrayList<OrderModel> orderList = new ArrayList<>();
        Map<String, ?> allOrder = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allOrder.entrySet()) {
            String order = (String) entry.getValue();
            System.out.println(order);
            String orderProductName = order.split("\\|")[0];
            System.out.println(order.split("\\|")[0]);
            int orderQuantity = Integer.parseInt(order.split("\\|")[1]);
            System.out.println(order.split("\\|")[1]);
            int orderPrice = Integer.parseInt(order.split("\\|")[2]);
            System.out.println(order.split("\\|")[2]);
            orderList.add(new OrderModel(userId, orderProductName, orderQuantity, orderPrice));
        }

        btnClearCart = findViewById(R.id.btn_clear_cart);
        btnOrder = findViewById(R.id.btn_order);
        recAllOrder = findViewById(R.id.rec_all_order);
        OrderAdapter orderAdapter = new OrderAdapter(this, orderList);
        recAllOrder.setAdapter(orderAdapter);
        recAllOrder.setLayoutManager(new LinearLayoutManager(this));


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < orderList.size(); ++i) {
                    databaseHelper.addOrder(orderList.get(i));
                }
                sharedPreferences.edit().clear().commit();
                Toast.makeText(SummaryActivity.this, "Order", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().commit();
                Toast.makeText(SummaryActivity.this, "Cart cleared", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        /*getLoaderManager().initLoader(LOADER, null, this);
        ListView listView = findViewById(R.id.list);
        mAdapter = new CartAdapter(this, null);
        listView.setAdapter(mAdapter);*/
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {OrderContract.OrderEntry._ID,
                OrderContract.OrderEntry.COLUMN_NAME,
                OrderContract.OrderEntry.COLUMN_PRICE,
                OrderContract.OrderEntry.COLUMN_QUANTITY};

        return new CursorLoader(this, OrderContract.OrderEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}