package com.example.minitiktok.samecity_page;

import com.example.minitiktok.R;

import java.util.ArrayList;
import java.util.List;

public class TalkDataList {
    public static List<TalkData> getData(){
        List<TalkData> ret = new ArrayList<>();
        ret.add(new TalkData("上午9:04","迪迦奥特曼","你别逼我打你！", R.mipmap.dijia));
        ret.add(new TalkData("上午9:06","赛罗奥特曼","还早两万年呢！", R.mipmap.zero));
        ret.add(new TalkData("上午9:20","艾斯奥特曼","奥特断头台！看招！", R.mipmap.aisi));
        ret.add(new TalkData("上午10:12","盖亚奥特曼","我是你哥。谁欺负你，我干他！", R.mipmap.gaiya));
        ret.add(new TalkData("上午10:40","迪迦的哥哥","我弟弟不听话！", R.mipmap.dijia));
        ret.add(new TalkData("上午11:00","赛罗的哥哥","我弟弟不听话！", R.mipmap.zero));
        ret.add(new TalkData("上午11:30","艾斯的弟弟","谁说我是弟弟我打谁！", R.mipmap.aisi));
        ret.add(new TalkData("上午11:59","奥特之父","我永远是你们的爸爸！", R.mipmap.father));
        return ret;
    }
}
