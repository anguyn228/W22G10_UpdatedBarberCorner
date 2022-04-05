package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barbercornerproj.model.NotifyModel;

import java.util.ArrayList;

public class AdminDashBoard extends AppCompatActivity {

    private Button btnAddBarber;
    private DatabaseHelper databaseHelper;
    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        btnAddBarber = findViewById(R.id.btn_add_barber);
        databaseHelper = new DatabaseHelper(this);
        notificationHelper = new NotificationHelper(this);
        notificationHelper.createNotificationChannel();
        checkNotify();

        btnAddBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashBoard.this, AddAndUpdateUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkNotify() {
        ArrayList<NotifyModel> notifyList = databaseHelper.retrieveAllNotifyByUserReceiveId(DatabaseHelper.ADMIN_USER_ID);
        for (int i = 0; i < notifyList.size(); ++i) {
            NotifyModel notify = notifyList.get(i);
            notificationHelper.createNotification(notify.getId(), notify.getTitle(), notify.getDescription());
            databaseHelper.deleteNotify(notify.getId());
        }
    }
}