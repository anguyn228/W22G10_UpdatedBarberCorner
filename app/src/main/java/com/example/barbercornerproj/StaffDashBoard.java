package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barbercornerproj.model.NotifyModel;

import java.util.ArrayList;

public class StaffDashBoard extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private Button btnBooking, btnMessage, btnAllRating;
    private int userId;
    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dash_board);
        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);

        databaseHelper = new DatabaseHelper(this);
        notificationHelper = new NotificationHelper(this);
        checkNotify();

        btnBooking = findViewById(R.id.btn_booking);
        btnMessage = findViewById(R.id.btn_message);
        btnAllRating = findViewById(R.id.btn_rating_view);

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StaffDashBoard.this, Message.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StaffDashBoard.this, ViewAllBookingActivity.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });

        btnAllRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StaffDashBoard.this, ViewCusReview.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });
    }

    private void checkNotify() {
        ArrayList<NotifyModel> notifyList = databaseHelper.retrieveAllNotifyByUserReceiveId(userId);
        for (int i = 0; i < notifyList.size(); ++i) {
            NotifyModel notify = notifyList.get(i);
            notificationHelper.createNotification(notify.getId(), notify.getTitle(), notify.getDescription());
            databaseHelper.deleteNotify(notify.getId());
        }
    }
}