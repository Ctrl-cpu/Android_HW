package com.example.minitiktok.samecity_page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.minitiktok.R;
import com.google.android.material.tabs.TabLayout;

public class SameCityActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager VP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_city);

        tablayout = findViewById(R.id.tablayout);
        VP = findViewById(R.id.viewpager);
        VP.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position == 0) return new SameCityFragment();
                else return new TalkFragment();
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if(position == 0) return "好友";
                return "消息";
            }
        });

        tablayout.setupWithViewPager(VP);
    }
}
