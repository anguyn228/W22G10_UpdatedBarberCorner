package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barbercornerproj.model.NotifyModel;

import java.util.ArrayList;

public class UserDashBoard extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Button btnBook, btnMessage, btnViewBarber;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);

        databaseHelper = new DatabaseHelper(this);
        checkNotify();

        btnBook = findViewById(R.id.btn_book);
        btnMessage = findViewById(R.id.btn_message);
        btnViewBarber = findViewById(R.id.btn_view_barber);

        System.out.println("USER DASHBOARD ID: " + userId);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, BookingActivity.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, Message.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });

        btnViewBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, BarberViewActivity.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });
    }

    private void checkNotify() {
        ArrayList<NotifyModel> notifyList = databaseHelper.retrieveAllNotifyByUserReceiveId(userId);
        NotificationHelper notificationHelper = new NotificationHelper(this);
        for (int i = 0; i < notifyList.size(); ++i) {
            NotifyModel notify = notifyList.get(i);
            notificationHelper.createNotification(notify.getId(), notify.getTitle(), notify.getDescription());
            databaseHelper.deleteNotify(notify.getId());
        }
    }
}