package com.example.barbercornerproj.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barbercornerproj.InfoProdShopActivity;
import com.example.barbercornerproj.MainActivity;
import com.example.barbercornerproj.R;
import com.example.barbercornerproj.model.ShopHairProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ShopHairProductModel> modelList;
    Context context;
    private int userId;

    public ProductAdapter(Context context, List<ShopHairProductModel> modelList, int userId) {
        this.context = context;
        this.modelList = modelList;
        this.userId = userId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_shop_products_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nameOfProduct = modelList.get(position).getProductName();
        String descriptionOfProduct = modelList.get(position).getProductDetail();
        int images = modelList.get(position).getProductPic();

        holder.ProductName.setText(nameOfProduct);
        holder.ProductDescription.setText(descriptionOfProduct);
        holder.imageView.setImageResource(images);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ProductName, ProductDescription;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.productName);
            ProductDescription = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.productImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, InfoProdShopActivity.class);
            ShopHairProductModel productModel = modelList.get(position);
            intent.putExtra(InfoProdShopActivity.TAG_PRODUCT_NAME, productModel.getProductName());
            intent.putExtra(InfoProdShopActivity.TAG_PRODUCT_DETAIL, productModel.getProductDetail());
            intent.putExtra(InfoProdShopActivity.TAG_PRODUCT_PRICE, productModel.getProductPrice());
            intent.putExtra(InfoProdShopActivity.TAG_PRODUCT_IMAGE, productModel.getProductPic());
            intent.putExtra(MainActivity.TAG_USER_ID, userId);
            context.startActivity(intent);
        }
    }
}
