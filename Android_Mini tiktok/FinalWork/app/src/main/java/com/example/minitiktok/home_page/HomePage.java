package com.example.minitiktok.home_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.minitiktok.R;
import com.example.minitiktok.db.LikedUtil;
import com.example.minitiktok.samecity_page.SameCityActivity;
import com.example.minitiktok.self_page.SelfInfoActivity;
import com.example.minitiktok.upload.CustomCameraActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private final static int PERMISSION_REQUEST_CODE = 1001;

    private static final String TAG = "--------------->";
    private Button btn_homepage, btn_video, btn_self, btn_samecity;
    private RecyclerView RV;
    private List<Video> videoList;
    private myLayoutManager layoutmanager;
    private myAdapter adapter;
    private static Context mContext;

    //database
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mContext = this;
        RV = findViewById(R.id.recyclerView);
        btn_homepage = findViewById(R.id.btn_homepage);
        btn_video = findViewById(R.id.btn_video);
        btn_self = findViewById(R.id.btn_self);
        btn_samecity = findViewById(R.id.btn_samecity);

        layoutmanager = new myLayoutManager(this, OrientationHelper.VERTICAL,false);
        adapter = new myAdapter(new myAdapter.mClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(HomePage.this, "You click " + position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePage.this, VideoPlayActivity.class);
                //TODO: 传入数据
                intent.putExtra("Video",videoList.get(position));
                startActivity(intent);
            }
            @Override
            public void onItemDoubleClick(int position){
                Toast.makeText(HomePage.this, "You double click "+ position, Toast.LENGTH_SHORT).show();
            }
        }, HomePage.this);
        RV.setLayoutManager(layoutmanager);
        RV.setAdapter(adapter);
        getData("");

        layoutmanager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG,"释放位置:"+position +" 下一页:"+isNext);
                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
            }

            @Override
            public void onPageSelected(int position, boolean isNext) {
                Log.e(TAG,"释放位置:"+position +" 下一页:"+isNext);

                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
                //playVideo(index);
            }
        });

        btn_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, SelfInfoActivity.class);
                startActivity(intent);
            }
        });

        btn_samecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, SameCityActivity.class));
            }
        });

    }

    private void getData(String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: "+content);
                List<Video> video_list = baseGetVideoFromRemote("");
                videoList = video_list;
                List<Video> filters = new ArrayList<>();
                if(!content.equals("")){
                    for(Video video : video_list){
                        if(video.getUserName().contains(content)){
                            filters.add(video);
                        }
                    }
                }
                if (!filters.isEmpty() || !video_list.isEmpty()){
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if(content.equals("")){
                                adapter.setData(video_list,HomePage.this);
                            }
                            else {
                                adapter.setData(filters,HomePage.this);
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public List<Video> baseGetVideoFromRemote(String Text){
        String urlStr =
                String.format("https://api-android-camp.bytedance.com/zju/invoke/video?student_id=%s",Text);
        VideoListResponse result = null;
        try{
            URL url  = new URL(urlStr);
            HttpURLConnection connection =(HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("token", Constants.token);
            if (connection.getResponseCode() == 200){
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                result = new Gson().fromJson(reader, new TypeToken<VideoListResponse>(){
                }.getType());

                reader.close();
                in.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert result != null;
        return result.feeds;
    }
    public static Context getContext() { return mContext; }

    public void customCamera(View view) {
        requestPermission();
    }

    private void recordVideo() {
        CustomCameraActivity.startUI(HomePage.this);
    }

    private void requestPermission() {
        boolean hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        if (hasCameraPermission && hasAudioPermission) {
            recordVideo();
        } else {
            List<String> permission = new ArrayList<String>();
            if (!hasCameraPermission) {
                permission.add(Manifest.permission.CAMERA);
            }
            if (!hasAudioPermission) {
                permission.add(Manifest.permission.RECORD_AUDIO);
            }
            ActivityCompat.requestPermissions(this, permission.toArray(new String[permission.size()]), PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
                break;
            }
        }
        if (hasPermission) {
            recordVideo();
        } else {
            Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
        }
    }

}
