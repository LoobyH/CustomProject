package com.goldmantis.wb.viewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.goldmantis.wb.viewdemo.model.TabModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 00027450 on 2018/3/20.
 */

public class RestMainActivity extends AppCompatActivity {

    @Bind(R.id.rm_vp_ay)
    ViewPager mRmVpAy;
    @Bind(android.R.id.tabcontent)
    FrameLayout mTabcontent;
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabhost;

    private List<TabModel> list = new ArrayList<>(4);
    private ViewPager viewPager;
    private List<Fragment> FragmentList = new ArrayList<>(4);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_main);
        ButterKnife.bind(this);
        initTab();
    }

    private void initTab() {
        mTabhost.setup(this,getSupportFragmentManager(),R.id.rm_vp_ay);
    }

}
