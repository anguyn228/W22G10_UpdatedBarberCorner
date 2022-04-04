package com.example.barbercornerproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.barbercornerproj.adapter.MessageListAdapter;
import com.example.barbercornerproj.model.MessageModel;

import java.util.ArrayList;

public class Message extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtSent, txtReceived;
    private RecyclerView recyclerViewSent;
    private RecyclerView recyclerViewReceived;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
        databaseHelper = new DatabaseHelper(this);

        initViews();
        initViesEventHandle();

        //  Action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<MessageModel> sentMessageList = databaseHelper.retrieveAllSentMessageByUserId(userId);
        MessageListAdapter adapterSent = new MessageListAdapter(this, sentMessageList);
        recyclerViewSent.setAdapter(adapterSent);
        recyclerViewSent.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<MessageModel> receivedMessageList = databaseHelper.retrieveAllReceivedMessageByUserId(userId);
        MessageListAdapter adapterReceived = new MessageListAdapter(this, receivedMessageList);
        recyclerViewReceived.setAdapter(adapterReceived);
        recyclerViewReceived.setLayoutManager(new LinearLayoutManager(this));
    }

    //  Finish activity when click back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        recyclerViewSent = findViewById(R.id.message_list_sent);
        recyclerViewReceived = findViewById(R.id.message_list_received);
        toolbar = findViewById(R.id.action_bar);
        txtSent = findViewById(R.id.txt_view_sent);
        txtReceived = findViewById(R.id.txt_view_received);
    }

    private void initViesEventHandle() {
        txtSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewSent.setVisibility(View.VISIBLE);
                txtSent.setBackground(getResources().getDrawable(R.drawable.rounded_corner_light_blue));
                txtSent.setTextColor(getResources().getColor(R.color.white));

                recyclerViewReceived.setVisibility(View.INVISIBLE);
                txtReceived.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                txtReceived.setTextColor(getResources().getColor(R.color.light_blue));
            }
        });

        txtReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewReceived.setVisibility(View.VISIBLE);
                txtReceived.setBackground(getResources().getDrawable(R.drawable.rounded_corner_light_blue));
                txtReceived.setTextColor(getResources().getColor(R.color.white));

                recyclerViewSent.setVisibility(View.INVISIBLE);
                txtSent.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                txtSent.setTextColor(getResources().getColor(R.color.light_blue));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}