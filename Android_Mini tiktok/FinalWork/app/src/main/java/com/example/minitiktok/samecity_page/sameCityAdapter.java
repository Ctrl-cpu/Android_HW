package com.example.minitiktok.samecity_page;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minitiktok.R;

import java.util.ArrayList;
import java.util.List;

public class sameCityAdapter extends RecyclerView.Adapter<sameCityAdapter.sameCityViewHolder> {

    private List<SameCityData> list;

    public sameCityAdapter(){
        list = new ArrayList<>();
        list = SameCityDataList.getSameCityList();
    }

    @NonNull
    @Override
    public sameCityAdapter.sameCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new sameCityViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.samecity_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull sameCityAdapter.sameCityViewHolder holder, int position) {
        holder.onBind(position);
        holder.btn_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn_guanzhu.setText("已关注");
                holder.btn_guanzhu.setBackgroundColor(Color.GRAY);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class sameCityViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_same;
        private TextView tv_name, tv_qianming;
        private Button btn_guanzhu;
        public sameCityViewHolder(@NonNull View itemView) {
            super(itemView);
            img_same = itemView.findViewById(R.id.img_samecity);
            tv_name = itemView.findViewById(R.id.tv_username);
            tv_qianming = itemView.findViewById(R.id.tv_qianming);
            btn_guanzhu = itemView.findViewById(R.id.btn_guanzhu);
        }
        public void onBind(int position)
        {
            img_same.setImageResource(list.get(position).imgID);
        }
    }
}
