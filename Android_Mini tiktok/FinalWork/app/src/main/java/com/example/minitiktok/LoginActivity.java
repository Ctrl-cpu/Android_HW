package com.example.minitiktok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.minitiktok.home_page.HomePage;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_name, et_cipher;
    private ImageButton imgbtn_permit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        et_name = findViewById(R.id.et_name);
        et_cipher = findViewById(R.id.et_cipher);
        imgbtn_permit = findViewById(R.id.imgbtn_permit);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zhanghao, mima;
                //后面添加一个map来匹配账号密码

                Intent intent = new Intent(LoginActivity.this, HomePage.class);
                startActivity(intent);

            }
        });
        imgbtn_permit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtn_permit.setImageResource(R.drawable.duigou_yellow);
            }
        });

    }
}
