package com.example.barbercornerproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.barbercornerproj.adapter.ViewAllOrderAdapter;

public class ViewAllOrderActivity extends AppCompatActivity {

    private RecyclerView recAllOrder;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_order);
        createActionBar();

        databaseHelper = new DatabaseHelper(this);

        recAllOrder = findViewById(R.id.rec_all_order);
        ViewAllOrderAdapter allOrderAdapter = new ViewAllOrderAdapter(this, databaseHelper.retrieveAllOrder());
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