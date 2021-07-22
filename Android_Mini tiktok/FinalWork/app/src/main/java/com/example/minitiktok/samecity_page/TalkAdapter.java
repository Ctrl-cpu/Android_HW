package com.example.minitiktok.samecity_page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minitiktok.R;

import java.util.ArrayList;
import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

    List<TalkData> list;

    public TalkAdapter(){
        list = new ArrayList<>();
        list = TalkDataList.getData();
    }

    @NonNull
    @Override
    public TalkAdapter.TalkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TalkAdapter.TalkViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.talk_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TalkAdapter.TalkViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TalkViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_touxiang;
        private TextView tv_name, tv_content, tv_time;

        public TalkViewHolder(@NonNull View itemView) {
            super(itemView);
            img_touxiang = itemView.findViewById(R.id.img_touxiang);
            tv_name = itemView.findViewById(R.id.tv_friendname);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
        public void onBind(int position){
            img_touxiang.setImageResource(list.get(position).imgID);
            tv_name.setText(list.get(position).name);
            tv_content.setText(list.get(position).content);
            tv_time.setText(list.get(position).time);
        }
    }
}
