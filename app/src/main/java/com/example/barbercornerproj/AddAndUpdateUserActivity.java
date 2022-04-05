package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.barbercornerproj.model.DataModel;

import java.util.List;

public class AddAndUpdateUserActivity extends AppCompatActivity {
    Button btnAdd, btnViewAll, btnUpdate;
    EditText edtUserName, edtPassword, edtBarberName;
    ListView dataList;
    ArrayAdapter arrayAdapter;

    DatabaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addand_update_user);

        dataBaseHelper = new DatabaseHelper(AddAndUpdateUserActivity.this);

        btnUpdate = findViewById(R.id.buttonUpdate);
        btnAdd = findViewById(R.id.buttonAdd);
        btnViewAll = findViewById(R.id.buttonView);
        edtUserName = findViewById(R.id.editTextUserName);
        edtPassword = findViewById(R.id.editTextPassword);
        edtBarberName = findViewById(R.id.editTextName);

        dataList = findViewById(R.id.userListView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel dataModel;
                try {
                    dataModel = new DataModel(
                            edtUserName.getText().toString(),
                            edtPassword.getText().toString(),
                            edtBarberName.getText().toString(),
                            "barber"
                    );
                    Toast.makeText(AddAndUpdateUserActivity.this, dataModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddAndUpdateUserActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                    dataModel = new DataModel("error", "", "", "");
                }


                boolean success = dataBaseHelper.addUser(dataModel);
                Toast.makeText(AddAndUpdateUserActivity.this, "Success " + success, Toast.LENGTH_SHORT).show();
                ShowUsersOnListView(dataBaseHelper);
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowUsersOnListView(dataBaseHelper);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAndUpdateUserActivity.this, UpdateUserActivity.class));
            }
        });

    }

        private void ShowUsersOnListView (DatabaseHelper dataBaseHelper){
            arrayAdapter = new ArrayAdapter<DataModel>(AddAndUpdateUserActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.retrieveAllBarber());
            dataList.setAdapter(arrayAdapter);
        }
    }
