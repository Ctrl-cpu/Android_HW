package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.myapplication.recycler.MyAdapter;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.recycler.LinearItemDecoration;
import com.example.myapplication.recycler.TestData;
import com.example.myapplication.recycler.TestDataSet;

public class MainActivity3 extends AppCompatActivity implements MyAdapter.IOnItemClickListener{

    private static final String TAG = "TAG";

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.i(TAG, "RecyclerViewActivity onCreate");
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(TestDataSet.getData());
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(3000);
        recyclerView.setItemAnimator(animator);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "RecyclerViewActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "RecyclerViewActivity onResume");
    }

    @Override
    public void onItemCLick(int position, TestData data) {
        Toast.makeText(MainActivity3.this, "???????????????" + position + "?????????", Toast.LENGTH_SHORT).show();
        mAdapter.addData(position, new TestData("????????????", "1???"));
    }

    @Override
    public void onItemLongCLick(int position, TestData data) {
        Toast.makeText(MainActivity3.this, "???????????????" + position + "?????????", Toast.LENGTH_SHORT).show();
        mAdapter.removeData(position);
    }
}
