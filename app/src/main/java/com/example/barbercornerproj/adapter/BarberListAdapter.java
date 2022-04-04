package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.DatabaseHelper;
import com.example.barbercornerproj.MainActivity;
import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.MessageModel;
import com.example.barbercornerproj.model.StaffModel;

import java.util.ArrayList;

public class BarberListAdapter extends RecyclerView.Adapter<BarberListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<StaffModel> barberList;
    private DatabaseHelper databaseHelper;

    public BarberListAdapter(Context context, @NonNull ArrayList<StaffModel> barberList) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.insertTestBarber();
        this.barberList = barberList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.barber_row, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtBarberName.setText(barberList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return barberList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBarberAvatar;
        private TextView txtBarberName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarberAvatar = itemView.findViewById(R.id.img_barber_avatar);
            txtBarberName = itemView.findViewById(R.id.txt_barber_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View sendMessageView = inflater.inflate(R.layout.send_message, null);
                    AlertDialog dialog = builder.setView(sendMessageView).create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    Button sendButton = sendMessageView.findViewById(R.id.btn_send);
                    EditText messageInput = sendMessageView.findViewById(R.id.edt_send_message);
                    sendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String message = messageInput.getText().toString();
                            int userId = ((AppCompatActivity) context).getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);
                            String userName = databaseHelper.getUserById(userId).getName();
                            StaffModel staffModel = barberList.get(getAdapterPosition());
                            MessageModel messageModel = new MessageModel(userId, staffModel.getName(), message, MessageModel.MESSAGE_TYPE_SEND);
                            databaseHelper.addMessage(messageModel);
                            MessageModel messageModel1 = new MessageModel(staffModel.getId(), userName, message, MessageModel.MESSAGE_TYPE_RECEIVE);
                            databaseHelper.addMessage(messageModel1);
                            Toast.makeText(context, "Message sent.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
