package com.example.e_commerce_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce_app.LoginActivity;
import com.example.e_commerce_app.MainActivity;
import com.example.e_commerce_app.ProductActivity;
import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapters.ProductRecyclerViewAdapter;
import com.example.e_commerce_app.adapters.RecyclerItemClickListener;
import com.example.e_commerce_app.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Product> products = new ArrayList<Product>();
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_explore, container, false);

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                         Intent intent = new Intent(getContext(), ProductActivity.class);
                         intent.putExtra("id", products.get(position).getId());
                         intent.putExtra("title", products.get(position).getTitle());
                         intent.putExtra("description", products.get(position).getDescription());
                         intent.putExtra("price", products.get(position).getPrice());
                         intent.putExtra("imageURL", products.get(position).getImageURL());
                         startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(products);
        recyclerView.setAdapter(adapter);

        db.collection("products").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    String id = d.getId();
                                    String title = d.get("title").toString();
                                    String description = d.get("description").toString();
                                    Double price = Double.parseDouble(d.get("price").toString());
                                    String imageURL = d.get("imageURL").toString();

                                    Product product = new Product(title, description, price, imageURL);
                                    product.setId(id);
                                    products.add(product);
                                }

                                adapter.notifyDataSetChanged();
                            }
                        });

//        products.add(new Product("Black iPhone", "Science fiction exploring a mistake", 18.98, "https://minimalissimo.imgix.net/content/products/4_hidden-wallpapers/hidden-walls-preview-5.jpg?w=600&q=60"));
//        products.add(new Product("Hodina Watch", "The best damn fantasy universe in the history", 24.44, "https://minimalissimo.imgix.net/content/products/5_minimalissimo-watch/minimalissimo-watch-1b.jpg?w=600&q=60"));
//        products.add(new Product("ODA Backpack", "A trilogy describing a rise and fall of a galactic emperor", 69.21, "https://minimalissimo.imgix.net/content/products/3_minimalissimo-backpack/oda-hop-backpack-black4.jpg?w=600&q=60"));
//        products.add(new Product("Alcest Pageclip", "Science fiction exploring a mistake", 18.98, "https://minimalissimo.imgix.net/content/articles/431_marc-1/marc-1-chair-1.jpg?w=600&q=60"));
//        products.add(new Product("Song of ice and fire", "The best damn fantasy universe in the history", 24.44, "https://minimalissimo.imgix.net/content/articles/436_naoto-fukasawa-atelier/naoto-fukasawa-atelier-3.jpg?w=600&q=60"));
//        products.add(new Product("The foundation trilogy", "A trilogy describing a rise and fall of a galactic emperor", 69.21, "https://minimalissimo.imgix.net/content/articles/435_opacity-collection/opacity-collection-9.jpg?w=600&q=60"));

        return view;
    }
}