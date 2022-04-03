package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.barbercornerproj.model.DataModel;

import java.util.List;

public class AddandUpdateUserActivity extends AppCompatActivity {
    Button btn_add, btn_viewAll, btn_update;
    EditText et_name, et_title, et_userId, et_password;
    ListView lv_dataList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addand_update_user);

        btn_update = findViewById(R.id.buttonUpdate);
        btn_add = findViewById(R.id.buttonAdd);
        btn_viewAll = findViewById(R.id.buttonView);
        et_name = findViewById(R.id.editTextUserName1);
        et_title = findViewById(R.id.editTextTitle);
        et_userId = findViewById(R.id.editTextUserId);
        et_password = findViewById(R.id.editTextPassword1);
        lv_dataList = findViewById(R.id.userListView);
        DatabaseHelper dataBaseHelper = new DatabaseHelper(AddandUpdateUserActivity.this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel dataModel;
                try {
                    dataModel = new DataModel(-1, et_name.getText().toString(), et_title.getText().toString(),
                            et_userId.getText().toString(), et_password.getText().toString());
                    Toast.makeText(AddandUpdateUserActivity.this, dataModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddandUpdateUserActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                    dataModel = new DataModel(-1, "error", "", "", "");
                }


                boolean success = dataBaseHelper.addUser(dataModel);
                Toast.makeText(AddandUpdateUserActivity.this, "Success " + success, Toast.LENGTH_SHORT).show();
                ShowUsersOnListView(dataBaseHelper);
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dataBaseHelper = new DatabaseHelper(AddandUpdateUserActivity.this);
                List<DataModel> everyone = dataBaseHelper.retrieveAllUsers();

                ShowUsersOnListView(dataBaseHelper);
              
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddandUpdateUserActivity.this, UpdateUserActivity.class));
            }
        });

    }


        private void ShowUsersOnListView (DatabaseHelper dataBaseHelper2){
            arrayAdapter = new ArrayAdapter<DataModel>(AddandUpdateUserActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.retrieveAllUsers());
            lv_dataList.setAdapter(arrayAdapter);
        }
    }
