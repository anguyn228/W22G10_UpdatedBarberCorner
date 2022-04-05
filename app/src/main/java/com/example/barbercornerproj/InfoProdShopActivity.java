package com.example.barbercornerproj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbercornerproj.Database.OrderContract;

public class InfoProdShopActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ImageView imageView;
    ImageButton plusquantity,minusquantity;
    TextView quantitynumber,productName,productPrice;
    Button addtoCart;
    int quantity;
    public Uri CurrentCartUri;
    boolean hasAllRequiredValues=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_prod_shop);

        imageView=findViewById(R.id.imageViewInfo);
        plusquantity=findViewById(R.id.addquantity);
        minusquantity=findViewById(R.id.subquantity);
        quantitynumber=findViewById(R.id.quantity);
        productName=findViewById(R.id.productNameinInfo);
        productPrice=findViewById(R.id.productPrice);
        addtoCart=findViewById(R.id.addtocart);

        productName.setText("Hair Gel");


        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InfoProdShopActivity.this,SummaryActivity.class);
                startActivity(intent);

                SaveCart();
            }

            private boolean SaveCart() {
                String name=productName.getText().toString();
                String price=productPrice.getText().toString();
                String quantity=quantitynumber.getText().toString();


                ContentValues values=new ContentValues();
                values.put(OrderContract.OrderEntry.COLUMN_NAME,name);
                values.put(OrderContract.OrderEntry.COLUMN_PRICE,price);
                values.put(OrderContract.OrderEntry.COLUMN_QUANTITY,quantity);


                if(CurrentCartUri==null){
                    Uri newUri=getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI,values);

                    if(newUri==null){
                        Toast.makeText(InfoProdShopActivity.this,"Failed to add to cart",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InfoProdShopActivity.this,"Successfully added to cart",Toast.LENGTH_SHORT).show();
                    }
                }
                hasAllRequiredValues=true;
                return hasAllRequiredValues;

            }
        });


        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int basePrice=99;
                quantity++;
                displayQuantity();
                int prodPrice=basePrice*quantity;
                String newPrice=String.valueOf(prodPrice);
                productPrice.setText(newPrice);
            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int basePrice=99;

                if(quantity==0){
                    Toast.makeText(InfoProdShopActivity.this,"Quantitiy can't go less than 0",Toast.LENGTH_SHORT).show();
                }else{
                    quantity--;
                    displayQuantity();
                    int prodPrice=basePrice*quantity;
                    String newPrice=String.valueOf(prodPrice);
                    productPrice.setText(newPrice);
                }
            }
        });
    }

    private void displayQuantity() {

        quantitynumber.setText(String.valueOf(quantity));
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection={OrderContract.OrderEntry._ID,
        OrderContract.OrderEntry.COLUMN_NAME,
        OrderContract.OrderEntry.COLUMN_PRICE,
        OrderContract.OrderEntry.COLUMN_QUANTITY};

        return new CursorLoader(this,CurrentCartUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor==null || cursor.getCount()<1){
            return;
        }
        if(cursor.moveToFirst()){
            int name=cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME);
            int price=cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE);
            int quantity=cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY);


            String nameOfProduct=cursor.getString(name);
            String priceOfProduct=cursor.getString(price);
            String quantityOfProduct=cursor.getString(quantity);


            productName.setText(nameOfProduct);
            productPrice.setText(priceOfProduct);
            quantitynumber.setText(quantityOfProduct);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productName.setText("");
        productPrice.setText("");
        quantitynumber.setText("");
    }
}