package com.example.barbercornerproj;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

public class SendMessageDialog {

    private AlertDialog dialog;
    private Context context;
    private OnSendButtonLickListener onClickListener;

    public interface OnSendButtonLickListener {
        void onClickListener(View view, AlertDialog dialog, String messageInput);
    }

    public SendMessageDialog(Context context, OnSendButtonLickListener onSendButtonClickListener) {
        this.context = context;
        this.onClickListener = onSendButtonClickListener;
        createDialog();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.send_message, null);

        dialog = builder.setView(view).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button sendButton = view.findViewById(R.id.btn_send);
        EditText messageInput = view.findViewById(R.id.edt_send_message);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClickListener(view, dialog, messageInput.getText().toString());
            }
        });
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

    public OnSendButtonLickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnSendButtonLickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
