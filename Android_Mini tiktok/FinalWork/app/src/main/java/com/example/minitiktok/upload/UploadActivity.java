package com.example.minitiktok.upload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.minitiktok.R;
import com.example.minitiktok.Util_;
import com.example.minitiktok.home_page.Constants;
import com.example.minitiktok.home_page.HomePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {
    private String mp4Path;
    private String CoverImagePath;
    private String extra_value;
    private TextView privacytextview;
    private ImageView edit;
    private TextView placetextview;
    private ImageView load;
    private ImageView cover;
    private Button bt_prev;
    private Button bt_upload;
    private final String TAG = "Upload";
    private static final long MAX_FILE_SIZE = 30 * 1024 * 1024;
    private EditText myTitle;
    public String privacy = "公开：所有人可见";
    public String place = "你在哪里";
    private PostApi api;
    private static final int REQUEST_CODE_COVER_IMAGE = 101;
    private static final String COVER_IMAGE_TYPE = "image/*";
    private Uri coverImageUri;
    private ProgressDialog pd;

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        mp4Path = intent.getStringExtra("mp4Path");
        privacytextview = findViewById(R.id.privacy);
        edit = findViewById(R.id.edit);
        placetextview = findViewById(R.id.place);
        load = findViewById(R.id.load);
        bt_prev = findViewById(R.id.prev_button);
        bt_upload = findViewById(R.id.next_button);
        myTitle = findViewById(R.id.Title);
        cover = findViewById(R.id.cover);
        CoverImagePath = getCoverImage();
        coverImageUri = Uri.fromFile(new File(CoverImagePath));
        showCoverImage(coverImageUri);
        placetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPoplocal(placetextview);
                placetextview.setText(place);
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPoplocal(placetextview);
                placetextview.setText(place);
            }
        });
        privacytextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu(privacytextview);
                privacytextview.setText(privacy);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu(privacytextview);
                privacytextview.setText(privacy);
            }
        });
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile(REQUEST_CODE_COVER_IMAGE, COVER_IMAGE_TYPE, "选择图片");
            }
        });
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, PreviewActivity.class);
                intent.putExtra("mp4Path",mp4Path);
                startActivity(intent);
            }
        });
        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        myTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforechanged" + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG,"onTextChanged" + s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                extra_value = s.toString();
            }
        });

        // 创建Retrofit实例
        // 生成api对象
        final Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://api-android-camp.bytedance.com/zju/invoke/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(PostApi.class);

        EditText edittext1=(EditText)findViewById(R.id.Title);
        Button btn1 = (Button)findViewById(R.id.huati);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext1.append("#");
            }
        });

        EditText edittext2=(EditText)findViewById(R.id.Title);
        Button btn2 = (Button)findViewById(R.id.call);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext2.append("@");
            }
        });

        EditText edittext_1=(EditText)findViewById(R.id.Title);
        Button btn_1 = (Button)findViewById(R.id.title1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext_1.append("#感谢祖国");
            }
        });

        EditText edittext_2=(EditText)findViewById(R.id.Title);
        Button btn_2 = (Button)findViewById(R.id.title2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext_2.append("#科研狗的日常");
            }
        });

        EditText edittext_3=(EditText)findViewById(R.id.Title);
        Button btn_3 = (Button)findViewById(R.id.title3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext_3.append("#上班摸鱼必备");
            }
        });

        EditText edittext_4=(EditText)findViewById(R.id.Title);
        Button btn_4 = (Button)findViewById(R.id.title4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext_4.append("#数据分析");
            }
        });

        EditText edittext_5=(EditText)findViewById(R.id.Title);
        Button btn_5 = (Button)findViewById(R.id.title5);
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext_5.append("#工作vlog");
            }
        });

        EditText edittext_6=(EditText)findViewById(R.id.Title);
        Button btn_6 = (Button)findViewById(R.id.title6);
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext_6.append("#河南暴雨互助");
            }
        });

        EditText edittext_7=(EditText)findViewById(R.id.Title);
        Button btn_7 = (Button)findViewById(R.id.title7);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edittext_7.append("#懂的人自然懂");
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    private String getCoverImage(){
        String CoverImagePath  = null;
        Bitmap coverimage;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mp4Path);
        //下面的时间单位是微秒
        coverimage = mmr.getScaledFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST,
                300,300);

        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        CoverImagePath = mediaStorageDir.getPath() + File.separator + "JPG_" + timeStamp + ".jpg";
        File tempFile = new File(CoverImagePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile);
            coverimage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return CoverImagePath;
    }

    private void showCoverImage(Uri coverImageUri){
        cover.setImageURI(coverImageUri);
    }

    private void showPoplocal(final View view){
        final PopupMenu popupMenu = new PopupMenu(this,view);
        //menu.xml 布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_local,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.hangzhou_:
                        place = "杭州市";
                        break;
                    case R.id.zijingang_:
                        place = "浙江大学紫金港校区";
                        break;
                    case R.id.bifeng_:
                        place = "碧峰";
                        break;
                    case R.id.wenguang_:
                        place = "浙江大学紫金港校区文化广场";
                        break;
                    case R.id.sushe_:
                        place = "浙江大学紫金港校区学生宿舍";
                        break;
                    case R.id.caochang_:
                        place = "浙江大学紫金港校区风雨操场";
                        break;
                    default:
                        break;
                }
                placetextview.setText(place);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            } });
        popupMenu.show();
    }

    private void showPopMenu(final View view){
        final PopupMenu popupMenu = new PopupMenu(this,view);
        //menu.xml 布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_upload,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.private_:
                        privacy = "私密·仅自己可见";
                        break;
                    case R.id.protected_:
                        privacy = "朋友·互关朋友可见";
                        break;
                    case R.id.public_:
                        privacy = "公开·所有人可见";
                        break;
                    default:
                        break;
                }
                privacytextview.setText(privacy);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            } });
        popupMenu.show();
    }

    private void submit() throws IOException {

        final String student_id = Constants.STUDENT_ID;
        final String user_name = Constants.USER_NAME;
        String video_name = mp4Path;
        final MultipartBody.Part video;
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
            return;
        }
        File f = new File(CoverImagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),f);
        MultipartBody.Part coverImage = MultipartBody.Part.createFormData("cover_image",f.getName(),requestFile);
        try{
            video = getMultipart("video",video_name);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG","视频读取失败");
            return;
        }
        pd = new ProgressDialog(UploadActivity.this);
        pd.setMessage("正在上传...");
        pd.setCancelable(false);
        pd.show();

        // 使用Retrofit同步方法请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call <UploadResponse> upload =  api.submitMessage(student_id,user_name,extra_value,coverImage,video);
                try {
                    Response<UploadResponse> response = upload.execute();
                    if (response.body().error != null){
                        Log.d(TAG,response.body().error);
                    }
                    if (response.isSuccessful() && response.body().success) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UploadActivity.this, HomePage.class));
                                UploadActivity.this.finish();
                                pd.dismiss();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UploadActivity.this, "上传失败" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UploadActivity.this, HomePage.class));
                                UploadActivity.this.finish();
                                pd.dismiss();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private MultipartBody.Part getMultipart(String name, String path) throws IOException {
        final String partKey=name;
        InputStream inputStream = new FileInputStream(path);
        byte[] bytedFile = Util_.inputStream2bytes(inputStream);
        RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/from-data"),bytedFile);
        return MultipartBody.Part.createFormData(partKey,path,requestFile);
    }

    private void getFile(int requestCode, String type, String title) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, requestCode);
    }

    private byte[] readDataFromUri(Uri uri) {
        byte[] data1 = null;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            data1 = Util_.inputStream2bytes(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_COVER_IMAGE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                coverImageUri = data.getData();
                showCoverImage(coverImageUri);

                if (coverImageUri != null) {
                    Log.d(TAG, "pick cover image " + coverImageUri.toString());
                } else {
                    Log.d(TAG, "uri2File fail " + data.getData());
                }
            } else {
                Log.d(TAG, "file pick fail");
            }
        }
    }

    public void storage(View source){
        Toast toast = Toast.makeText(getApplicationContext(), "已保存至个人草稿箱",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }

}