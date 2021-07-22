package com.example.minitiktok.home_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.minitiktok.R;

import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayActivity extends AppCompatActivity {

    private Video video;

    private customVideo CV;
    private SeekBar SB;
    private int Duration;
    private boolean isPause = false;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                SB.setProgress(CV.getCurrentPosition());
            }
            super.handleMessage(msg);
        }
    };
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message mes = new Message();
            mes.what = 1;
            mHandler.sendMessage(mes);
        }
    };
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);


        Intent intent = getIntent();
        video = (Video) intent.getSerializableExtra("Video");
        String VideoURL = video.getVideoUrl();
        CV = findViewById(R.id.customvideo);
        Uri uri = Uri.parse(VideoURL);
        CV.setVideoURI(uri);
        CV.requestFocus();
        CV.getHolder().setFormat(PixelFormat.TRANSPARENT);

        SB = findViewById(R.id.seekbar);
        timer.schedule(task,0,50);
        CV.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                CV.setEnabled(true);
                Duration = CV.getDuration();
                SB.setMax(Duration);
                CV.start();
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        if(isPause){
                            CV.start();
                            isPause = false;
                        }
                    }
                });
            }
        });
        CV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
        CV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isPause)
                {
                    CV.start();
                    isPause = false;
                    Toast.makeText(VideoPlayActivity.this, "视频开始播放",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CV.pause();
                    isPause = true;
                    Toast.makeText(VideoPlayActivity.this, "视频暂停",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("SeekBar", " " + seekBar.getProgress() + " " + SB.getMax());
                Log.d("CV", " " + CV.getDuration());
                //CV.seekTo(seekBar.getProgress() * CV.getDuration());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //如果原视频文件中没有关键帧则该方法无效
                Log.d("SeekBar", " " + seekBar.getProgress() + " " + SB.getMax());
                Log.d("CV", " " + CV.getDuration());
                CV.seekTo(seekBar.getProgress());
            }
        });
    }
}
