package com.example.thuchanh2.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thuchanh2.view.fragment.HomeFragment;
import com.example.thuchanh2.view.fragment.ProfileFragment;
import com.example.thuchanh2.view.fragment.SearchFragment;

public class ViewPaperAdapter extends FragmentStateAdapter {
    public ViewPaperAdapter(FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            default:
                return new ProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
