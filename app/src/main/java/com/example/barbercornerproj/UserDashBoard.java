package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserDashBoard extends AppCompatActivity {

    private Button btnBook, btnMessage;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        btnBook = findViewById(R.id.btn_book);
        btnMessage = findViewById(R.id.btn_message);

        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);

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
    }
}