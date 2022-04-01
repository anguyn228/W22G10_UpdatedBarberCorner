package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.DatabaseHelper;
import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.MessageModel;
import com.example.barbercornerproj.model.StaffModel;

import java.util.ArrayList;

public class BarberListAdapter extends RecyclerView.Adapter<BarberListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<StaffModel> barberList;
    private DatabaseHelper databaseHelper;

    public BarberListAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.insertTestBarber();
        barberList = databaseHelper.allStaffs();
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

                    Button sendButton = sendMessageView.findViewById(R.id.btn_send);
                    EditText messageInput = sendMessageView.findViewById(R.id.edt_send_message);
                    sendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String message = messageInput.getText().toString();
                            int firstId = 1;
                            String userName = "NAME";
                            StaffModel staffModel = barberList.get(getAdapterPosition());
                            MessageModel messageModel = new MessageModel(firstId, userName, message);
                            databaseHelper.addMessage(messageModel);
                            MessageModel messageModel1 = new MessageModel(staffModel.getId(), userName, message);
                            databaseHelper.addMessage(messageModel1);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
