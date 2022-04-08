package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.barbercornerproj.adapter.OrderAdapter;
import com.example.barbercornerproj.model.ShopHairProductModel;

import java.util.ArrayList;
import java.util.List;

public class ShopHairProductActivity extends AppCompatActivity {

    List<ShopHairProductModel> modelList;
    RecyclerView recyclerView;
    OrderAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_hair_product);

        modelList = new ArrayList<>();
        modelList.add(new ShopHairProductModel("Hair Gel", "Shaping gel for a nourishing and strong hold",
                99, R.drawable.hairprod1));
        modelList.add(new ShopHairProductModel("Taming Gel", "High hold and high shine",
                70, R.drawable.hairprod2));
        modelList.add(new ShopHairProductModel("Opuntia Oil", "Hair beard and normalizing shampoo",
                80, R.drawable.hairprod3));
        modelList.add(new ShopHairProductModel("Daily hydrator", "Shaping gel for a nourishing and strong hold",
                66, R.drawable.hairprod4));
        modelList.add(new ShopHairProductModel("Moroccon Oil", "Luminuous hairspray",
                50, R.drawable.hairprod5));
        modelList.add(new ShopHairProductModel("Kiehls Grooming solutions", "Firm hold with natural looking shine",
                40, R.drawable.hairprod6));
        modelList.add(new ShopHairProductModel("Sonvera Hair Brush", "Haair brush",
                69, R.drawable.hairprod7));
        modelList.add(new ShopHairProductModel("Grooming solutions", "Shaping gel for a nourishing and strong hold",
                100, R.drawable.hairprod6));
        modelList.add(new ShopHairProductModel("Taming Gel", "Shaping gel for a nourishing and strong hold",
                59, R.drawable.hairprod2));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));

        myAdapter = new OrderAdapter(this, modelList);
        recyclerView.setAdapter(myAdapter);
    }
}