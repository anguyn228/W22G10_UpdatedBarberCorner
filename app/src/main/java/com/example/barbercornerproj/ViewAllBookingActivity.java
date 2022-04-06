package com.example.barbercornerproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.barbercornerproj.adapter.BookingAdapter;

public class ViewAllBookingActivity extends AppCompatActivity {

    private int userId;
    private RecyclerView allBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_booking);
        createActionBar();

        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
        allBooking = findViewById(R.id.rel_view_all_booking);
        BookingAdapter bookingAdapter = new BookingAdapter(userId, this);
        allBooking.setAdapter(bookingAdapter);
        allBooking.setLayoutManager(new LinearLayoutManager(this));
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