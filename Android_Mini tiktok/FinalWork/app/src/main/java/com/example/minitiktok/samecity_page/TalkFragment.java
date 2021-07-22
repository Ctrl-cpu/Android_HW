package com.example.minitiktok.samecity_page;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minitiktok.R;


public class TalkFragment extends Fragment {
    private RecyclerView RV_talk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_talk, container, false);

        RV_talk = v.findViewById(R.id.recyclerview_talk);
        RV_talk.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RV_talk.setLayoutManager(layoutManager);
        TalkAdapter adapter = new TalkAdapter();
        RV_talk.setAdapter(adapter);
        RV_talk.addItemDecoration(new myDecoration());
        return v;
    }

    class myDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.samecity_margin));
        }
    }
}
