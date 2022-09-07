package com.example.e_commerce_app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapters.ProductRecyclerViewAdapter;
import com.example.e_commerce_app.models.Product;

public class ExploreFragment extends Fragment {
    RecyclerView recyclerView;
    Product[] products = new Product[]
    {
        new Product("Black iPhone", "Science fiction exploring a mistake", 18.98, "https://minimalissimo.imgix.net/content/products/4_hidden-wallpapers/hidden-walls-preview-5.jpg?w=600&q=60"),
        new Product("Hodina Watch", "The best damn fantasy universe in the history", 24.44, "https://minimalissimo.imgix.net/content/products/5_minimalissimo-watch/minimalissimo-watch-1b.jpg?w=600&q=60"),
        new Product("ODA Backpack", "A trilogy describing a rise and fall of a galactic emperor", 69.21, "https://minimalissimo.imgix.net/content/products/3_minimalissimo-backpack/oda-hop-backpack-black4.jpg?w=600&q=60"),
        new Product("Alcest Pageclip", "Science fiction exploring a mistake", 18.98, "https://minimalissimo.imgix.net/content/articles/431_marc-1/marc-1-chair-1.jpg?w=600&q=60"),
        new Product("Song of ice and fire", "The best damn fantasy universe in the history", 24.44, "https://minimalissimo.imgix.net/content/articles/436_naoto-fukasawa-atelier/naoto-fukasawa-atelier-3.jpg?w=600&q=60"),
        new Product("The foundation trilogy", "A trilogy describing a rise and fall of a galactic emperor", 69.21, "https://minimalissimo.imgix.net/content/articles/435_opacity-collection/opacity-collection-9.jpg?w=600&q=60")
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_explore, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(products);
        recyclerView.setAdapter(adapter);

        return view;
    }
}