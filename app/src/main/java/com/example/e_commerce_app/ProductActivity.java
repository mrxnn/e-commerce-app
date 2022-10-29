package com.example.e_commerce_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    ImageButton backButton;
    TextView titleTextView;
    ImageView imageView;
    TextView descriptionTextView;
    TextView pricingTextView;
    Button deleteButton;
    FirebaseFirestore db;

    String title, description, imageURL, id;
    Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // hide status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init ui
        backButton = findViewById(R.id.backButton);
        titleTextView = findViewById(R.id.titleTextView);
        imageView = findViewById(R.id.imageView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        pricingTextView = findViewById(R.id.pricingTextView);
        deleteButton = findViewById(R.id.deleteButton);
        db = FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            id = extras.getString("id");
            title = extras.getString("title");
            description = extras.getString("description");
            imageURL = extras.getString("imageURL");
            price = extras.getDouble("price");

            titleTextView.setText(title);
            Picasso.get().load(imageURL).into(imageView);
            descriptionTextView.setText(description);
            pricingTextView.setText(price.toString() + " LKR");
        }

        // delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != null) {
                    db.collection("products").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProductActivity.this, "Successfully Deleted", Toast.LENGTH_LONG);
                            startActivity(new Intent(ProductActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }
            }
        });
    }
}