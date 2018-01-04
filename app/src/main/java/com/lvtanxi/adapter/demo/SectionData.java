package com.lvtanxi.adapter.demo;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SectionData {

    private String title;

    @SuppressLint("DefaultLocale")
    public SectionData(String title, int position) {
        this.title = title;
        mStrings=new ArrayList<>();
        int num = new Random().nextInt(20);
        for (int i = 0; i < num; i++) {
            mStrings.add(String.format("第%d条目的第%d个item",position,i));
        }
    }

    public String getTitle() {
        return title;
    }



    public List<String> mStrings;

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getStrings() {
        return mStrings;
    }

    public void setStrings(List<String> strings) {
        mStrings = strings;
    }
}
