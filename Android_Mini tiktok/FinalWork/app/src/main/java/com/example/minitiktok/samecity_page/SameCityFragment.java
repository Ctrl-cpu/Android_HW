package com.example.minitiktok.samecity_page;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.minitiktok.R;


public class SameCityFragment extends Fragment {
    private RecyclerView RV_same;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_same_city, container, false);

        RV_same = v.findViewById(R.id.samecity_recyclerview);
        RV_same.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RV_same.setLayoutManager(layoutManager);
        sameCityAdapter adapter = new sameCityAdapter();
        RV_same.setAdapter(adapter);
        return v;
    }
}
