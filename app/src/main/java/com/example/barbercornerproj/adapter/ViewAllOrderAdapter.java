package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.DatabaseHelper;
import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.DataModel;
import com.example.barbercornerproj.model.OrderModel;

import java.util.ArrayList;

public class ViewAllOrderAdapter extends RecyclerView.Adapter<ViewAllOrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OrderModel> orderList;
    private DatabaseHelper databaseHelper;

    public ViewAllOrderAdapter(Context context, ArrayList<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_order_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel = orderList.get(position);
        DataModel dataModel = databaseHelper.getUserById(orderModel.getUserId());
        holder.customerName.setText(dataModel.getUserName());
        String orderDetail = orderModel.toString();
        holder.orderDetail.setText(orderDetail);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, orderDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.txt_customer_name);
            orderDetail = itemView.findViewById(R.id.txt_order_detail);
        }
    }
}
