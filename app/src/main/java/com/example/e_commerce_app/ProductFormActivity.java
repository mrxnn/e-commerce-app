package com.example.e_commerce_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_commerce_app.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProductFormActivity extends AppCompatActivity {
    Button createButton;
    Button pickImageButton;
    ImageButton backButton;
    TextInputEditText titleInput;
    TextInputEditText descriptionInput;
    TextInputEditText priceInput;
    TextInputEditText imageInput;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
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
        backButton = findViewById(R.id.backButton);
        titleInput = findViewById(R.id.titleInputEditText);
        descriptionInput = findViewById(R.id.descriptionInputEditText);
        priceInput = findViewById(R.id.priceInputEditText);
        imageInput = findViewById(R.id.imageInputEditText);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductFormActivity.this, MainActivity.class));
            }
        });

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Upload image
                if (imageUri != null) {
                    StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                    fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String title = titleInput.getText().toString();
                                    String description = descriptionInput.getText().toString();
                                    int price = Integer.parseInt( priceInput.getText().toString());
                                    Product product = new Product(title, description, price, uri.toString());

                                    // Add product to database
                                    DocumentReference documentReference = firebaseFirestore.collection("products").document();
                                    documentReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProductFormActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                                            titleInput.setText("");
                                            descriptionInput.setText("");
                                            priceInput.setText("");
                                            imageInput.setText("");
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProductFormActivity.this, "Failed to upload file", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(ProductFormActivity.this, "No file selected", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
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
        }
    }
}