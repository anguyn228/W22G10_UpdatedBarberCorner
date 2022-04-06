package com.example.barbercornerproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbercornerproj.adapter.MessageListAdapter;
import com.example.barbercornerproj.model.DataModel;
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
        MessageListAdapter adapterSent = new MessageListAdapter(this, sentMessageList, null, MessageListAdapter.MESSAGE_LIST_RECEIVE);
        recyclerViewSent.setAdapter(adapterSent);
        recyclerViewSent.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<MessageModel> receivedMessageList = databaseHelper.retrieveAllReceivedMessageByUserId(userId);
        MessageListAdapter.ItemClickListener itemClickListener = new MessageListAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int position, MessageModel messageModel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Message.this);
                LayoutInflater inflater = LayoutInflater.from(Message.this);
                View sendMessageView = inflater.inflate(R.layout.send_message, null);
                AlertDialog dialog = builder.setView(sendMessageView).create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button sendButton = sendMessageView.findViewById(R.id.btn_send);
                EditText messageInput = sendMessageView.findViewById(R.id.edt_send_message);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = messageInput.getText().toString();
                        int userId = ((AppCompatActivity) Message.this).getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
                        String userName = databaseHelper.getUserById(userId).getName();
                        DataModel receiver = databaseHelper.getUserById(messageModel.getSenderId());
                        MessageModel messageModel = new MessageModel(userId, receiver.getId(), message);
                        databaseHelper.addMessage(messageModel);
                        Toast.makeText(Message.this, "Message sent.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        };
        MessageListAdapter adapterReceived = new MessageListAdapter(this, receivedMessageList, itemClickListener, MessageListAdapter.MESSAGE_LIST_SEND);
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

    private void showSendMessageDialog() {

    }
}