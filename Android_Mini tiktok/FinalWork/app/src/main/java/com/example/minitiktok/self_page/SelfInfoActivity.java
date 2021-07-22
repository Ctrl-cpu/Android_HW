package com.example.minitiktok.self_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.minitiktok.R;
import com.example.minitiktok.db.LikedUtil;
import com.example.minitiktok.home_page.HomePage;
import com.example.minitiktok.home_page.Video;
import com.example.minitiktok.home_page.VideoPlayActivity;
import com.example.minitiktok.samecity_page.SameCityActivity;

import java.util.ArrayList;
import java.util.List;

public class SelfInfoActivity extends AppCompatActivity {

    private RecyclerView RV;
    private List<Video> LikeList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutmanager;
    private selfAdapter adapter;
    private Button btn_addfriend;
    private LinearLayout ll_fan,ll_star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_info);
        RV = findViewById(R.id.recyclerview_self);
        btn_addfriend = findViewById(R.id.btn_addfriend);
        ll_fan = findViewById(R.id.linear_fans);
        ll_star = findViewById(R.id.linear_star);

        btn_addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfInfoActivity.this, SameCityActivity.class));
            }
        });
        ll_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfInfoActivity.this, SameCityActivity.class));
            }
        });
        ll_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfInfoActivity.this, SameCityActivity.class));
            }
        });


        LikeList = LikedUtil.getInstance(HomePage.getContext()).queryLikeList();
        Log.d("list size", "" + LikeList.size());
        layoutmanager = new GridLayoutManager(this,2);

        RV.setLayoutManager(layoutmanager);
        adapter = new selfAdapter(LikeList, SelfInfoActivity.this, new selfAdapter.mClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SelfInfoActivity.this, VideoPlayActivity.class);
                //TODO: 传入数据
                intent.putExtra("Video",LikeList.get(position));
                startActivity(intent);
            }
        });
        RV.setAdapter(adapter);


    }

}
