package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.barbercornerproj.adapter.RatingAdapter;

public class ViewCusReview extends AppCompatActivity {

    private int userId;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cus_review);
        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.customerReviewSec);
        RatingAdapter ratingAdapter = new RatingAdapter(
                databaseHelper.retrieveReviewFromBarberId(userId),
                this
        );
        recyclerView.setAdapter(ratingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}