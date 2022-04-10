package com.example.barbercornerproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.barbercornerproj.adapter.RatingAdapter;
import com.example.barbercornerproj.adapter.ViewAllOrderAdapter;

public class CustomerViewOrderActivity extends AppCompatActivity {
    private int userId;
    private RecyclerView recAllOrder;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_order);
        createActionBar();

        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
        databaseHelper = new DatabaseHelper(this);

        recAllOrder = findViewById(R.id.rec_all_order);
        ViewAllOrderAdapter allOrderAdapter = new ViewAllOrderAdapter(this, databaseHelper.retrieveAllOrderByUserId(userId));
        recAllOrder.setAdapter(allOrderAdapter);
        recAllOrder.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createActionBar() {
        //  Action bar
        Toolbar toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}