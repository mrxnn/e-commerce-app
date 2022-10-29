package com.example.e_commerce_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_commerce_app.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProductFormActivity extends AppCompatActivity {
    Button createButton;
    Button pickImageButton;
    TextInputEditText titleInput;
    TextInputEditText descriptionInput;
    TextInputEditText priceInput;
    TextInputEditText imageInput;
    FirebaseFirestore firebaseFirestore;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

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
        pickImageButton = findViewById(R.id.pickImageButton);
        titleInput = findViewById(R.id.titleInputEditText);
        descriptionInput = findViewById(R.id.descriptionInputEditText);
        priceInput = findViewById(R.id.priceInputEditText);
        imageInput = findViewById(R.id.imageInputEditText);
        firebaseFirestore = FirebaseFirestore.getInstance();

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageInput.setText(data.getData().getPath());
//            Picasso.get().load(imageUri).into(imageView);
        }
    }
}