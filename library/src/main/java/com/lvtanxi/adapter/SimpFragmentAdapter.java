package com.lvtanxi.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * Date: 2016-05-05
 * Time: 14:39
 * Description:FragmentStatePagerAdapter基类
 */
public class SimpFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private CharSequence[] mTitles;

    public SimpFragmentAdapter(FragmentManager fm, List<Fragment> fragments, CharSequence[] titles) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragments = fragments;
        mTitles = titles;
    }


    public SimpFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
