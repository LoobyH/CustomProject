package com.goldmantis.wb.viewdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 00027450 on 2018/3/20.
 */

public class RestMainAdapter extends FragmentPagerAdapter {

    private List<Fragment> views;

    public RestMainAdapter(FragmentManager fm,List<Fragment> fs) {
        super(fm);
        views = fs;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }
}
