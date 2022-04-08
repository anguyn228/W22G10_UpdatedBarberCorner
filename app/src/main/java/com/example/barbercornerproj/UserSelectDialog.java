package com.example.barbercornerproj;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.adapter.BarberAdapter;
import com.example.barbercornerproj.adapter.UserAdapter;
import com.example.barbercornerproj.model.DataModel;

import java.util.ArrayList;

public class UserSelectDialog {

    private AlertDialog dialog;
    private Context context;
    private ArrayList<DataModel> userList;
    private UserAdapter.OnItemClick onItemClick = null;

    private RecyclerView recBarberSelect;
    private UserAdapter userAdapter;

    public UserSelectDialog(Context context, ArrayList<DataModel> userList) {
        this.context = context;
        this.userList = userList;
        createDialog();
    }

    public UserSelectDialog(Context context,
                            ArrayList<DataModel> userList,
                            UserAdapter.OnItemClick onItemClick) {
        this.context = context;
        this.userList = userList;
        this.onItemClick = onItemClick;
        createDialog();
    }

    private void createDialog() {
        //  Inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View barberSelectView = inflater.inflate(R.layout.select_user_dialog, null);

        //  Init Recycler view
        recBarberSelect = barberSelectView.findViewById(R.id.rel_barber_select);
        userAdapter = new UserAdapter(userList, context, onItemClick);
        recBarberSelect.setAdapter(userAdapter);
        recBarberSelect.setLayoutManager(new LinearLayoutManager(context));

        //  Create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(barberSelectView);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<DataModel> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<DataModel> userList) {
        this.userList = userList;
    }

    public UserAdapter.OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(UserAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        userAdapter.setOnItemClick(onItemClick);
    }

    public interface OnDataSet {
        void onDataSet(DataModel dataModel, AlertDialog dialog);
    }
}
