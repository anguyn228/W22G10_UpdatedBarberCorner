package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.DatabaseHelper;
import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.BookingModel;
import com.example.barbercornerproj.model.DataModel;
import com.example.barbercornerproj.model.RatingModel;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private DatabaseHelper databaseHelper;
    private ArrayList<RatingModel> ratingList;
    private Context context;
    private int userId;

    public RatingAdapter(ArrayList<RatingModel> ratingList, Context context) {
        this.ratingList = ratingList;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.customer_review_list, null);
        RatingAdapter.ViewHolder viewHolder = new RatingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, int position) {
        RatingModel ratingModel = ratingList.get(position);
        DataModel customer = databaseHelper.getUserById(ratingModel.getCusId());

        System.out.println("RateId: " + ratingModel.getRatingId()
            +   "BarId: " + ratingModel.getBarberId() +
                "CusId: " + ratingModel.getCusId() +
                "Rating: " + ratingModel.getRating() +
                "Comment: " + ratingModel.getComment()
        );

        holder.txtCustomerName.setText(customer.getUserName());
        System.out.println("USS: " + customer.getUserName());
        holder.txtCustomerComment.setText(ratingModel.getComment());
        holder.ratingBar.setRating(ratingModel.getRating());
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomerName, txtCustomerComment;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar2);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtCustomerComment = itemView.findViewById(R.id.txtCustomerComment);
        }
    }
}

