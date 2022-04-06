package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private int barberId;

    public BookingAdapter(int barberId, Context context) {
        this.context = context;
        this.barberId = barberId;
        databaseHelper = new DatabaseHelper(context);
        this.bookingList = databaseHelper.retrieveAllBookingByBarberId(barberId);
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
        DataModel dataModel = databaseHelper.getUserById(bookingModel.getUserId());
        holder.txtCustomerName.setText(dataModel.getUserName());

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
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDay, txtMonth, txtCustomerName, txtTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txt_day);
            txtMonth = itemView.findViewById(R.id.txt_month);
            txtCustomerName = itemView.findViewById(R.id.txt_customer_name);
            txtTime = itemView.findViewById(R.id.txt_time);
        }
    }
}
