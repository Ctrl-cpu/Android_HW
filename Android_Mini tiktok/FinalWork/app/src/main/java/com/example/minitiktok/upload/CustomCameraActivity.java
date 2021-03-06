package com.example.minitiktok.upload;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.minitiktok.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomCameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mHolder;
    private ImageView mRecordButton;
    private ImageView mGalleryButton;
    private Button ten;
    private boolean isRecording = false;
    private static int mCameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
    private final static int PERMISSION_REQUEST_CODE = 1001;

    private String mp4Path = "";

    private TextView viewById;
    int textnum = 0;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            viewById.setText(msg.obj.toString());
            runCount();
        };
    };

    public static void startUI(Context context) {
        Intent intent = new Intent(context, CustomCameraActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide(); //???????????????
        setContentView(R.layout.activity_custom_camera);
        SurfaceView mSurfaceView = findViewById(R.id.surfaceview);
        mRecordButton = findViewById(R.id.bt_record);
        mGalleryButton = findViewById(R.id.bt_gallery);
        mRecordButton.bringToFront();

        mHolder = mSurfaceView.getHolder();
        initCamera();
        mHolder.addCallback(this);
    }

    private void initCamera() {
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.set("orientation", "portrait");
        parameters.set("rotation", 90);
        //mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
    }

    private boolean prepareVideoRecorder() {
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));        //??????????????????????????????????????????

        // Step 4: Set output file
        mp4Path = getOutputMediaPath();
        mMediaRecorder.setOutputFile(mp4Path);

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(mHolder.getSurface());
        mMediaRecorder.setOrientationHint(90);

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException | IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
            Log.d("123",mp4Path);
            Intent intent = new Intent(CustomCameraActivity.this, PreviewActivity.class);
            intent.putExtra("mp4Path",mp4Path);
            startActivity(intent);
            finish();
        }
    }

    private String getOutputMediaPath() {
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir, "IMG_" + timeStamp + ".mp4");
        if (!mediaFile.exists()) {
            mediaFile.getParentFile().mkdirs();
        }
        return mediaFile.getAbsolutePath();
    }

    public void record(View view) {
 //       if(!checkPermission()) {
 //           ActivityCompat.requestPermissions(CustomCameraActivity.this,
 //                   new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
 //       }else{
        if (isRecording) {
            mRecordButton.setImageResource(R.drawable.icon_record_start);
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setOnInfoListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            try {
                mMediaRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            releaseMediaRecorder();


        } else {

            if(prepareVideoRecorder()) {

                View imageView = findViewById(R.id.bt_record);
                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView,
                        "scaleX", 1.1f, 0.9f);
                scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
                scaleXAnimator.setInterpolator(new LinearInterpolator());
                scaleXAnimator.setDuration(1000);
                scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);

                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView,
                        "scaleY", 1.1f, 0.9f);
                scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
                scaleYAnimator.setInterpolator(new LinearInterpolator());
                scaleYAnimator.setDuration(1000);
                scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                animatorSet.start();

                mMediaRecorder.start();

                viewById = (TextView)findViewById(R.id.tv_count);
                viewById.setText(String.valueOf(textnum));
                runCount();

            }else{
                releaseMediaRecorder();
            }
        }
        isRecording = !isRecording;}
    //}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            return;
        }
        //??????????????????
        mCamera.stopPreview();
        //????????????????????????
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            initCamera();
        }
        mCamera.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCamera!=null){
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;}
    }
//    private boolean checkPermission() {
//        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//                == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        return false;
//    }
    public void gallery(View view){
        startActivity(new Intent(CustomCameraActivity.this, VideoPickActivity.class));
    }

    public void runCount() {
        Message obtain = Message.obtain();
        textnum = Integer.parseInt(viewById.getText().toString());

        Log.i("textnum", String.valueOf(textnum));
        obtain.obj = textnum + 1;
        handler.sendMessageDelayed(obtain, 1000);
    }

}