package com.example.thuchanh2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.thuchanh2.R;
import com.example.thuchanh2.view.ViewPaperAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPaperAdapter adapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_paper);
        tabLayout = findViewById(R.id.tab_layout);
        adapter = new ViewPaperAdapter(this);
        viewPager.setAdapter(adapter);
        String[] titles = {"Đặt vé", "Tìm kiếm", "Thông tin"};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();
    }

    public void insertTicket(View view) {
        Intent intent = new Intent(this, InsertActivity.class);
        startActivity(intent);
    }
}