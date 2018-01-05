package com.lvtanxi.adapter.demo;

import android.annotation.SuppressLint;

import com.lvtanxi.adapter.listener.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SectionData implements Section {

    private String title;

    @SuppressLint("DefaultLocale")
    public SectionData(String title, int position) {
        this.title = title;
        mStrings=new ArrayList<>();
        int num = new Random().nextInt(20);
        for (int i = 0; i < num; i++) {
            mStrings.add(String.format("条目%d的第%d个",position,i));
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

    @Override
    public int getSectionChildCount() {
        return mStrings.size();
    }

    @Override
    public Object getSectionChildItem(int childPosition) {
        return mStrings.get(childPosition);
    }
}
