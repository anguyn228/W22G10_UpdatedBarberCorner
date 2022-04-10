package com.example.barbercornerproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbercornerproj.adapter.UserAdapter;
import com.example.barbercornerproj.model.DataModel;
import com.example.barbercornerproj.model.RatingModel;

public class RatingFeature extends AppCompatActivity {

    private int userId;
    private DatabaseHelper databaseHelper;
    private RelativeLayout relBarberSelect;
    private EditText edtComment;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private TextView txtBarberName;

    private DataModel barberSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_feature);
        userId = getIntent().getIntExtra(MainActivity.TAG_USER_ID, 0);

        databaseHelper = new DatabaseHelper(this);
        relBarberSelect = findViewById(R.id.rel_barber_select1);
        edtComment = findViewById(R.id.editTextComment);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.buttonSubmitReview);
        txtBarberName = findViewById(R.id.txt_barber_name1);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println(v);
            }
        });

        relBarberSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSelectDialog userSelectDialog = new UserSelectDialog(
                        RatingFeature.this,
                        databaseHelper.retrieveAllBarber()
                );
                userSelectDialog.setOnItemClick(new UserAdapter.OnItemClick() {
                    @Override
                    public void onClick(int position, DataModel dataModel) {
                        barberSelected = dataModel;
                        txtBarberName.setText(barberSelected.getName());
                        userSelectDialog.dismiss();
                    }
                });
                userSelectDialog.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingModel ratingModel = new RatingModel(
                        barberSelected.getId(), userId, (int) ratingBar.getRating(),
                        edtComment.getText().toString()
                );
                databaseHelper.addReview(ratingModel);
                Toast.makeText(RatingFeature.this, "rated.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}