package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
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
import com.example.barbercornerproj.model.ShopHairProductModel;

public class InfoProdShopActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String TAG_PRODUCT_NAME = "productName";
    public static String TAG_PRODUCT_DETAIL = "productDetail";
    public static String TAG_PRODUCT_PRICE = "productPrice";
    public static String TAG_PRODUCT_IMAGE = "productImage";

    public static String TAG_PRODUCT_QUANTITY = "productQuantity";

    private int userId;

    private ShopHairProductModel productModel;

    ImageView imageView;
    ImageButton plusQuantity, minusQuantity;
    TextView quantityNumber, productName, productPrice;
    Button addToCart;
    int quantity;
    public Uri CurrentCartUri;
    boolean hasAllRequiredValues = false;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_prod_shop);

        productModel = new ShopHairProductModel(
              getIntent().getStringExtra(TAG_PRODUCT_NAME),
              getIntent().getStringExtra(TAG_PRODUCT_DETAIL),
              getIntent().getIntExtra(TAG_PRODUCT_PRICE, 0),
              getIntent().getIntExtra(TAG_PRODUCT_IMAGE, 0)
        );

        databaseHelper = new DatabaseHelper(this);

        imageView = findViewById(R.id.imageViewInfo);
        plusQuantity = findViewById(R.id.addquantity);
        minusQuantity = findViewById(R.id.subquantity);
        quantityNumber = findViewById(R.id.quantity);
        productName = findViewById(R.id.productNameinInfo);
        productPrice = findViewById(R.id.productPrice);
        addToCart = findViewById(R.id.addtocart);

        imageView.setImageDrawable(getDrawable(productModel.getProductPic()));
        productName.setText(productModel.getProductName());
        productPrice.setText("$" + productModel.getProductPrice());

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SaveCart();
                String name = productName.getText().toString();
                int price = Integer.parseInt(productPrice.getText().toString());
                int quantity = Integer.parseInt(quantityNumber.getText().toString());
                addToCart(name, quantity, price);
                Intent intent = new Intent(InfoProdShopActivity.this, SummaryActivity.class);
                intent.putExtra(TAG_PRODUCT_NAME, name);
                intent.putExtra(TAG_PRODUCT_PRICE, price);
                intent.putExtra(TAG_PRODUCT_QUANTITY, quantity);
                startActivity(intent);
            }

            private void addToCart(String productName, int quantity, int totalPrice) {
                String order = productName + "|" + String.valueOf(quantity) + "|" + String.valueOf(totalPrice);
                SharedPreferences sharedPreferences  = InfoProdShopActivity.this.getSharedPreferences(
                        "shopping_cart_" + String.valueOf(userId),
                        Context.MODE_PRIVATE
                );

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(String.valueOf(sharedPreferences.getAll().size()), order);
                editor.apply();
            }

            private boolean SaveCart() {
                String name = productName.getText().toString();
                int price = Integer.parseInt(productPrice.getText().toString());
                int quantity = Integer.parseInt(quantityNumber.getText().toString());

                //databaseHelper.addProduct(name, quantity, price);
                /*
                ContentValues values = new ContentValues();
                values.put(OrderContract.OrderEntry.COLUMN_NAME,name);
                values.put(OrderContract.OrderEntry.COLUMN_PRICE,price);
                values.put(OrderContract.OrderEntry.COLUMN_QUANTITY,quantity);

                if(CurrentCartUri == null){
                    Uri newUri = getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI,values);

                    if(newUri==null){
                        Toast.makeText(InfoProdShopActivity.this,"Failed to add to cart",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InfoProdShopActivity.this,"Successfully added to cart",Toast.LENGTH_SHORT).show();
                    }
                }*/

                hasAllRequiredValues = true;
                return hasAllRequiredValues;
            }
        });


        plusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int price = productModel.getProductPrice();
                quantity++;
                displayQuantity();
                int prodPrice = price * quantity;
                String newPrice = String.valueOf(prodPrice);
                productPrice.setText(newPrice);
            }
        });

        minusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int price = productModel.getProductPrice();

                if (quantity == 0) {
                    Toast.makeText(InfoProdShopActivity.this, "Quantitiy can't go less than 0", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                    int prodPrice = price * quantity;
                    String newPrice = String.valueOf(prodPrice);
                    productPrice.setText(newPrice);
                }
            }
        });
    }

    private void displayQuantity() {

        quantityNumber.setText(String.valueOf(quantity));
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {OrderContract.OrderEntry._ID,
                OrderContract.OrderEntry.COLUMN_NAME,
                OrderContract.OrderEntry.COLUMN_PRICE,
                OrderContract.OrderEntry.COLUMN_QUANTITY};

        return new CursorLoader(this, CurrentCartUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            int name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME);
            int price = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE);
            int quantity = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY);


            String nameOfProduct = cursor.getString(name);
            String priceOfProduct = cursor.getString(price);
            String quantityOfProduct = cursor.getString(quantity);


            productName.setText(nameOfProduct);
            productPrice.setText(priceOfProduct);
            quantityNumber.setText(quantityOfProduct);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productName.setText("");
        productPrice.setText("");
        quantityNumber.setText("");
    }
}