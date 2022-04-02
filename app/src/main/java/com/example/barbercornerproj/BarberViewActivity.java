package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.barbercornerproj.adapter.BarberListAdapter;

public class BarberViewActivity extends AppCompatActivity {
    private int userId;
    private DatabaseHelper databaseHelper;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_view);
        databaseHelper = new DatabaseHelper(this);
        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);

        recyclerView = findViewById(R.id.rel_barber_list);

        BarberListAdapter adapter = new BarberListAdapter(this, databaseHelper.allStaffs());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}