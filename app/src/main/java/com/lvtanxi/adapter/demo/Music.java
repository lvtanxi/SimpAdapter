package com.lvtanxi.adapter.demo;

/**
 * Created by linshuaibin on 20/04/2017.
 */

public class Music {

    private String name;
    private int coverRes;

    public Music(String name, int coverRes) {
        this.name = name;
        this.coverRes = coverRes;
    }

    public String getName() {
        return name;
    }

    public int getCoverRes() {
        return coverRes;
    }
}
