package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.DatabaseHelper;
import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.MessageModel;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    public static String MESSAGE_LIST_SEND = "send";
    public static String MESSAGE_LIST_RECEIVE = "receive";

    private String messageListStyle;

    private Context context;
    private ArrayList<MessageModel> messageList;
    private ItemClickListener clickListener = null;
    private int userId = 1;

    public MessageListAdapter(@NonNull Context context, @NonNull ArrayList<MessageModel> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public MessageListAdapter(
            @NonNull Context context,
            @NonNull ArrayList<MessageModel> messageList,
            @Nullable ItemClickListener clickListener,
            @NonNull String messageListStyle) {
        this.context = context;
        this.messageList = messageList;
        this.clickListener = clickListener;
        this.messageListStyle = messageListStyle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_ui_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMessage.setText(messageList.get(position).getMessage());

        int senderOrReceiverId;
        if (messageListStyle.equals(MESSAGE_LIST_SEND)) {
            senderOrReceiverId = messageList.get(position).getSenderId();
        } else {
            senderOrReceiverId = messageList.get(position).getReceiveId();
        }
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        String senderName = databaseHelper.getUserById(senderOrReceiverId).getName();
        holder.txtName.setText(senderName);

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMessage;
        private TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.message);
            txtName = itemView.findViewById(R.id.sender);
            if (clickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClickListener(getAdapterPosition(), messageList.get(getAdapterPosition()));
                    }
                });
            }
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int position, @NonNull MessageModel message);
    }
}
