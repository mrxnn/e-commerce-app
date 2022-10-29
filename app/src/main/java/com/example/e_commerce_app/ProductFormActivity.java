package com.example.e_commerce_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commerce_app.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductFormActivity extends AppCompatActivity {
    Button createButton;
    TextInputEditText titleInput;
    TextInputEditText descriptionInput;
    TextInputEditText priceInput;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        // hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // hide status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        createButton = findViewById(R.id.create_button);
        titleInput = findViewById(R.id.titleInputEditText);
        descriptionInput = findViewById(R.id.descriptionInputEditText);
        priceInput = findViewById(R.id.priceInputEditText);
        firebaseFirestore = FirebaseFirestore.getInstance();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                int price = Integer.parseInt( priceInput.getText().toString());
                Product product = new Product(title, description, price, "");

                DocumentReference documentReference = firebaseFirestore.collection("products").document();
                documentReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ProductFormActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                        titleInput.setText("");
                        descriptionInput.setText("");
                        priceInput.setText("");
                    }
                });
            }
        });
    }
}