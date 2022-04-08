package com.example.barbercornerproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.barbercornerproj.adapter.BookingAdapter;
import com.example.barbercornerproj.model.BookingModel;
import com.example.barbercornerproj.model.DataModel;
import com.example.barbercornerproj.model.NotifyModel;

import java.util.ArrayList;

public class ViewAllBookingActivity extends AppCompatActivity {

    private int userId;
    private RecyclerView allBooking;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_booking);

        databaseHelper = new DatabaseHelper(this);

        createActionBar();

        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
        allBooking = findViewById(R.id.rel_view_all_booking);

        BookingAdapter.OnDeleteButtonClickListener onDeleteButtonClickListener = null;

        DataModel dataModel = databaseHelper.getUserById(userId);
        ArrayList<BookingModel> bookingList = new ArrayList<>();
        //  Action if user is barber
        if(dataModel.getTitle().equals("barber")) {
            bookingList = databaseHelper.retrieveAllBookingByBarberId(userId);
            onDeleteButtonClickListener = (adapter, bookingModel) -> {
                databaseHelper.deleteBooking(bookingModel.getBookingId());

                String notifyDescription = bookingModel.toString(databaseHelper.getUserById(bookingModel.getBarberId()));
                NotifyModel notifyModel = new NotifyModel(
                        "Your booking has been canceled by barber",
                        notifyDescription,
                        bookingModel.getUserId()
                );
                databaseHelper.addNotify(notifyModel);
            };
        }
        //  Action if user is customer
        else if (dataModel.getTitle().toLowerCase().equals("customer")) {
            bookingList = databaseHelper.retrieveAllBookingByCustomerId(userId);
            onDeleteButtonClickListener = (adapter, bookingModel) -> {
                databaseHelper.deleteBooking(bookingModel.getBookingId());

                String notifyDescription = bookingModel.toString(databaseHelper.getUserById(bookingModel.getBarberId()));
                NotifyModel notifyModel = new NotifyModel(
                        "Your booking has been canceled by customer: " +
                                databaseHelper.getUserById(bookingModel.getUserId()).getUserName(),
                        notifyDescription,
                        bookingModel.getBarberId()
                );
                databaseHelper.addNotify(notifyModel);
            };
        }
        BookingAdapter bookingAdapter = new BookingAdapter(
                userId,
            bookingList, this, onDeleteButtonClickListener
        );

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}