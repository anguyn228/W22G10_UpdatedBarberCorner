package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.DataModel;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<DataModel> userList;
    private Context context;
    private OnItemClick onItemClick = null;
    private Drawable avatarDrawable = null;

    public UserAdapter(ArrayList<DataModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public UserAdapter(ArrayList<DataModel> userList, Context context, OnItemClick onItemClick) {
        this.userList = userList;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public UserAdapter(ArrayList<DataModel> userList, Context context, OnItemClick onItemClick, Drawable avatarDrawable) {
        this.userList = userList;
        this.context = context;
        this.onItemClick = onItemClick;
        this.avatarDrawable = avatarDrawable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(userList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            if (onItemClick != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick.onClick(getAdapterPosition(), userList.get(getAdapterPosition()));
                    }
                });
            }
            if (avatarDrawable != null) {
                imgAvatar.setImageDrawable(avatarDrawable);
            }
        }
    }

    public interface OnItemClick {
        void onClick(int position, DataModel dataModel);
    }
}
