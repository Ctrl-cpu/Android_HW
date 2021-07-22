package com.example.minitiktok.home_page;

import android.content.Context;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.minitiktok.R;
import com.example.minitiktok.db.LikedUtil;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {


    private mClickListener mclicklistener;
    private Context mContext;
    private List<Video> VideoList;

    public  void setData(List<Video> videoList, Context context){
        mContext = context;
        VideoList = videoList;
        notifyDataSetChanged();
    }

    public myAdapter(mClickListener clicklistener, Context context)
    {
        mclicklistener = clicklistener;
        mContext = context;
    }

    @NonNull
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //初始化喜欢按钮
        if(LikedUtil.getInstance(HomePage.getContext()).isExist(VideoList.get(position).getId())){
            holder.imgbtn_like.setImageResource(R.mipmap.heart_icon2);
            holder.liked = true;
        }
        else
        {
            holder.imgbtn_like.setImageResource(R.mipmap.heart_icon1);
            holder.liked = false;
        }

        holder.onBind(position);
        holder.itemView.setOnTouchListener(new myClickListener(new myClickListener.MyClickCallBack() {
//            @Override
//            public void oneClick() {
//                mclicklistener.onItemClick(position);
//            }

            @Override
            public void doubleClick() {
                mclicklistener.onItemDoubleClick(position);
                LikedUtil.getInstance(HomePage.getContext()).addLike(VideoList.get(position));
                holder.imgbtn_like.setImageResource(R.mipmap.heart_icon2);
                holder.liked = true;
            }
        }));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclicklistener.onItemClick(position);
            }
        });

        holder.imgbtn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.liked) {
                    LikedUtil.getInstance(HomePage.getContext()).addLike(VideoList.get(position));
                    holder.imgbtn_like.setImageResource(R.mipmap.heart_icon2);
                    holder.liked = true;
                } else {
                    LikedUtil.getInstance(HomePage.getContext()).deleteLike(VideoList.get(position).getId());
                    holder.imgbtn_like.setImageResource(R.mipmap.heart_icon1);
                    holder.liked = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return VideoList == null ? 0:VideoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_test;
        //VideoView videoView;
        TextView tv_name, tv_info;
        ImageButton imgbtn_like, imgbtn_share, imgbtn_comment;
        //Love lovelayout;
        private boolean liked = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_test = itemView.findViewById(R.id.img_test);
            //videoView = itemView.findViewById(R.id.videoview);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_info = itemView.findViewById(R.id.tv_info);
            imgbtn_like = itemView.findViewById(R.id.imgbtn_like);
            imgbtn_comment = itemView.findViewById(R.id.imgbtn_comment);
            imgbtn_share = itemView.findViewById(R.id.imgbtn_share);
            //lovelayout = itemView.findViewById(R.id.love_layout);
        }

        public void onBind(int position)
        {
            Glide.with(mContext)
               .load(VideoList.get(position).getImageUrl())
                    .into(img_test);
            tv_info.setText(VideoList.get(position).getExtraValue());
            tv_name.setText(VideoList.get(position).getUserName());

        }

    }

    public interface mClickListener{
        void onItemClick(int position);
        void onItemDoubleClick(int position);
    }
}
