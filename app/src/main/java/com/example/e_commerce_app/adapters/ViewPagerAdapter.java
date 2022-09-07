package com.example.e_commerce_app.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.e_commerce_app.fragments.CartFragment;
import com.example.e_commerce_app.fragments.ExploreFragment;
import com.example.e_commerce_app.fragments.WishlistFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ExploreFragment();
            case 1:
                return new CartFragment();
            default:
                return new WishlistFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

