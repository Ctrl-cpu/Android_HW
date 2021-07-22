package com.example.minitiktok.self_page;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.minitiktok.R;
import com.example.minitiktok.home_page.HomePage;
import com.example.minitiktok.home_page.Video;
import com.example.minitiktok.home_page.VideoPlayActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class selfAdapter extends RecyclerView.Adapter<selfAdapter.selfViewHolder> {

    List<Video> videolist = new ArrayList<>();
    Context mContext;
    mClickListener mclicklistener;


    public selfAdapter(List<Video> list, Context context, mClickListener clickListener){
        videolist = list;
        mContext = context;
        mclicklistener = clickListener;
    }

    @NonNull
    @Override
    public selfAdapter.selfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new selfViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_self_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull selfAdapter.selfViewHolder holder, int position) {
        holder.onBind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclicklistener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videolist == null?0:videolist.size();
    }

    class selfViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_fengmian;
        private TextView tv_name_self;
        public selfViewHolder(@NonNull View itemView) {
            super(itemView);
            img_fengmian = itemView.findViewById(R.id.imageview_fengmian_self);
            tv_name_self = itemView.findViewById(R.id.tv_name_self);
        }
        public void onBind(int position){
            Glide.with(mContext)
                    .load(videolist.get(position).getImageUrl())
                    .into(img_fengmian);
            tv_name_self.setText(videolist.get(position).getUserName());
        }
    }

    public interface mClickListener{
        void onItemClick(int position);
    }
}
