package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barbercornerproj.R;
import com.example.barbercornerproj.adapter.imageAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Hair_Style_Pics extends AppCompatActivity {

    ArrayList<Integer> myImageIds=new ArrayList<>(Arrays.asList(R.drawable.imghair,
            R.drawable.img_hair2,
            R.drawable.images,R.drawable.download,R.drawable.img4,
            R.drawable.img5,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,R.drawable.img7
            ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_style_pics);

        GridView gridView=findViewById(R.id.hairstyle_img);
        gridView.setAdapter(new imageAdapter(myImageIds,this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int item_pos=myImageIds.get(i);

                ShowDialog(item_pos);
            }
        });


    }

    public void ShowDialog(int item_pos){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        TextView Image_name=dialog.findViewById(R.id.txt_Img_name);
        ImageView Image=dialog.findViewById(R.id.img);
        Button btn_Full=dialog.findViewById(R.id.addToCart);
        Button btn_Close=dialog.findViewById(R.id.close);

        String title=getResources().getResourceName(item_pos);

        int index=title.indexOf("/");
        String name=title.substring(index+1,title.length());
        //Image_name.setText(name);

        Image.setImageResource(item_pos);

        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_Full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Hair_Style_Pics.this,FullViewGalleryImg.class);
                i.putExtra("img_id",item_pos);
                startActivity(i);
            }
        });

        dialog.show();

    }
}