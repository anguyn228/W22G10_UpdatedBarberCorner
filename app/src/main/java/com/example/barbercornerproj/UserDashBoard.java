package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserDashBoard extends AppCompatActivity {

    private Button btnBook, btnMessage, btnViewBarber, btnShopCart, btnGallery, btnRating, btnAllOrder, btnViewBooking;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        btnBook = findViewById(R.id.btn_book);
        btnMessage = findViewById(R.id.btn_message);
        btnViewBarber = findViewById(R.id.btn_view_barber);
        btnShopCart = findViewById(R.id.btn_shop);
        btnGallery = findViewById(R.id.btn_gallery);
        btnRating  = findViewById(R.id.btn_rating);
        btnAllOrder = findViewById(R.id.btn_all_order);
        btnViewBooking = findViewById(R.id.btn_all_booking);

        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
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

        btnViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, ViewAllBookingActivity.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, Hair_Style_Pics.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });

        btnShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, ShopHairProductActivity.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });
        
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, RatingFeature.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });

        btnAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashBoard.this, CustomerViewOrderActivity.class);
                intent.putExtra(MainActivity.TAG_USER_ID, userId);
                startActivity(intent);
            }
        });
    }
}