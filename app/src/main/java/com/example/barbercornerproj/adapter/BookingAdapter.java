package com.example.barbercornerproj.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.DatabaseHelper;
import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.BookingModel;
import com.example.barbercornerproj.model.DataModel;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private DatabaseHelper databaseHelper;
    private ArrayList<BookingModel> bookingList;
    private Context context;
    private int userId;

    OnDeleteButtonClickListener onDeleteButtonClickListener = null;

    public BookingAdapter(int userId, Context context) {
        this.context = context;
        this.userId = userId;
        databaseHelper = new DatabaseHelper(context);
        this.bookingList = databaseHelper.retrieveAllBookingByBarberId(userId);
    }

    public BookingAdapter(int userId, Context context, @NonNull OnDeleteButtonClickListener onDeleteButtonClickListener) {
        this.context = context;
        this.userId = userId;
        databaseHelper = new DatabaseHelper(context);
        this.bookingList = databaseHelper.retrieveAllBookingByBarberId(userId);
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    public BookingAdapter(
            int userId,
            ArrayList<BookingModel> bookingList,
            Context context,
            OnDeleteButtonClickListener onDeleteButtonClickListener) {
        databaseHelper = new DatabaseHelper(context);
        this.userId = userId;
        this.bookingList = bookingList;
        this.context = context;
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingModel bookingModel = bookingList.get(position);
        DateFormatSymbols dateFormat = new DateFormatSymbols();
        holder.txtDay.setText(String.valueOf(bookingModel.getDay()));
        holder.txtMonth.setText(dateFormat.getMonths()[(bookingModel.getMonth()) - 1]);

        int dataModelId = 0;
        if (databaseHelper.getUserById(userId).getTitle().toLowerCase().equals("barber")) {
            dataModelId = bookingModel.getUserId();
        } else {
            dataModelId = bookingModel.getBarberId();
        }
        DataModel dataModel = databaseHelper.getUserById(dataModelId);
        holder.txtCustomerName.setText(dataModel.getName());

        int day = bookingModel.getDay();
        int month = bookingModel.getMonth();
        int year = bookingModel.getYear();
        int hour = bookingModel.getHour();
        int minute = bookingModel.getMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day, hour, minute,0);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        String time = formatter.format(date);

        holder.txtTime.setText(time);

        if (onDeleteButtonClickListener != null) {
            holder.setOnDeleteButtonClickListener(onDeleteButtonClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDay, txtMonth, txtCustomerName, txtTime;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.img_delete);
            txtDay = itemView.findViewById(R.id.txt_day);
            txtMonth = itemView.findViewById(R.id.txt_month);
            txtCustomerName = itemView.findViewById(R.id.txt_customer_name);
            txtTime = itemView.findViewById(R.id.txt_time);
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener onClickListener) {
            imgDelete.setOnClickListener(
                    view -> {
                        onClickListener.onClick(BookingAdapter.this, bookingList.get(getAdapterPosition()));
                        bookingList.remove(getAdapterPosition());
//                        System.out.println("CLICKED");
//                        bookingList = BookingAdapter.this.databaseHelper.retrieveAllBookingByBarberId(BookingAdapter.this.barberId);
                        BookingAdapter.this.notifyDataSetChanged();
                    }
            );
        }
    }
    public interface OnDeleteButtonClickListener {
        void onClick(BookingAdapter adapter, BookingModel bookingModel);
    }
}
