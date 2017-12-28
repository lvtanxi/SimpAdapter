package com.lvtanxi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Date: 2016-05-05
 * Time: 14:39
 * Description:FragmentStatePagerAdapter基类
 */
public class SimplicityFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private CharSequence[] mTitles;

    public SimplicityFragmentAdapter(FragmentManager fm, List<Fragment> fragments, CharSequence[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }


    public SimplicityFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null)
            return super.getPageTitle(position);
        return mTitles[position];
    }
}
