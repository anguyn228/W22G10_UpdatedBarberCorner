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
import com.example.barbercornerproj.adapter.UserAdapter;
import com.example.barbercornerproj.model.DataModel;
import com.example.barbercornerproj.model.MessageModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Message extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtSent, txtReceived;
    private RecyclerView recyclerViewSent;
    private RecyclerView recyclerViewReceived;
    private FloatingActionButton floatingActionButton;
    private DatabaseHelper databaseHelper;
    private int userId;

    private ArrayList<MessageModel> receivedMessageList, sentMessageList;

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

        sentMessageList = databaseHelper.retrieveAllSentMessageByUserId(userId);
        MessageListAdapter adapterSent = new MessageListAdapter(this, sentMessageList, null, MessageListAdapter.MESSAGE_LIST_RECEIVE);
        recyclerViewSent.setAdapter(adapterSent);
        recyclerViewSent.setLayoutManager(new LinearLayoutManager(this));

        receivedMessageList = databaseHelper.retrieveAllReceivedMessageByUserId(userId);
        MessageListAdapter.ItemClickListener itemClickListener = new MessageListAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int position, @NonNull MessageModel messageModel) {
                SendMessageDialog.OnSendButtonLickListener sendButtonLickListener =
                        (view, dialog, messageInput) -> {
                            int userId = ((AppCompatActivity) Message.this).getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
                            int receiverId = messageModel.getSenderId();
                            MessageModel messageModel1 = new MessageModel(userId, receiverId, messageInput);
                            databaseHelper.addMessage(messageModel1);

                            Toast.makeText(Message.this, "Message sent.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        };

                SendMessageDialog sendMessageDialog = new SendMessageDialog(
                        Message.this,
                        sendButtonLickListener
                );
                sendMessageDialog.show();
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
        floatingActionButton = findViewById(R.id.float_btn_add);
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DataModel> userHasConversationList = getUserHasConversationWith();

                //  Create send message dialog when click item
                UserAdapter.OnItemClick onItemClick = (position, dataModel) -> {
                    SendMessageDialog.OnSendButtonLickListener sendButtonLickListener =
                            (view1, dialog, messageInput) -> {
                                int userId = ((AppCompatActivity) Message.this).getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
                                int receiverId = dataModel.getId();
                                MessageModel messageModel1 = new MessageModel(userId, receiverId, messageInput);
                                databaseHelper.addMessage(messageModel1);

                                Toast.makeText(Message.this, "Message sent.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            };

                    SendMessageDialog sendMessageDialog = new SendMessageDialog(
                            Message.this,
                            sendButtonLickListener
                    );
                    sendMessageDialog.show();
                };

                UserSelectDialog userSelectDialog = new UserSelectDialog(
                        Message.this,
                        userHasConversationList,
                        onItemClick
                );
                userSelectDialog.show();
            }
        });
    }

    private ArrayList<DataModel> getUserHasConversationWith() {
        ArrayList<DataModel> userHasConversationList = new ArrayList<>();
        for (int i = 0; i < receivedMessageList.size(); ++i) {
            boolean userExistsInList = false;
            //  Check if user is not exists in userHasConversationList
            for (int j = 0; j < userHasConversationList.size(); ++j) {
                if (userHasConversationList.get(j).getId() == receivedMessageList.get(i).getSenderId()) {
                    userExistsInList = true;
                    break;
                }
            }
            if (!userExistsInList) {
                userHasConversationList.add(
                        databaseHelper.getUserById(receivedMessageList.get(i).getSenderId())
                );
            }
        }

        //  Get user had received message from this user
        for (int i = 0; i < sentMessageList.size(); ++i) {
            boolean userExistsInList = false;
            //  Check if user is not exists in userHasConversationList
            for (int j = 0; j < userHasConversationList.size(); ++j) {
                if (userHasConversationList.get(j).getId() == sentMessageList.get(i).getReceiveId()) {
                    userExistsInList = true;
                    break;
                }
            }
            if (!userExistsInList) {
                userHasConversationList.add(
                        databaseHelper.getUserById(sentMessageList.get(i).getReceiveId())
                );
            }
        }
        return userHasConversationList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }

    private void showSendMessageDialog() {
    }
}