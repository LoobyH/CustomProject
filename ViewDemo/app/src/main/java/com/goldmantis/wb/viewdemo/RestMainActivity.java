package com.goldmantis.wb.viewdemo;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.goldmantis.wb.viewdemo.adapter.RestMainAdapter;
import com.goldmantis.wb.viewdemo.fragment.Function1Fragment;
import com.goldmantis.wb.viewdemo.fragment.Function2Fragment;
import com.goldmantis.wb.viewdemo.fragment.Function3Fragment;
import com.goldmantis.wb.viewdemo.fragment.FunctionFragment;
import com.goldmantis.wb.viewdemo.model.TabModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
    LayoutInflater inflater;

    private List<TabModel> list = new ArrayList<>(4);
    private List<Fragment> FragmentList = new ArrayList<>(4);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_main);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initTab();
    }

    private void initTab() {
        mTabhost.setup(this,getSupportFragmentManager(),R.id.rm_vp_ay);
        TabModel tabmain = new TabModel(R.string.home, R.drawable.tab_home_slc, FunctionFragment.class);
        TabModel tabcontact = new TabModel(R.string.theme, R.drawable.tab_theme_slc, Function1Fragment.class);
        TabModel tabmine = new TabModel(R.string.style, R.drawable.tab_style_slc, Function2Fragment.class);
        TabModel tabfound = new TabModel(R.string.funtion, R.drawable.tab_funtion_slc, Function3Fragment.class);
        list.add(tabmain);
        list.add(tabcontact);
        list.add(tabmine);
        list.add(tabfound);

        for (TabModel tab : list) {
            TabHost.TabSpec spec= mTabhost.newTabSpec(getString(tab.getTitle()))//
                    .setIndicator(getItemView(tab));
            mTabhost.addTab(spec,tab.getFragment(),null);
        }
        //清除分界线
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        initFragment();
    }

    private void initFragment() {
        FragmentList.add(new FunctionFragment());
        FragmentList.add(new Function1Fragment());
        FragmentList.add(new Function2Fragment());
        FragmentList.add(new Function3Fragment());
        //设置适配器
        mRmVpAy.setAdapter(new RestMainAdapter(getSupportFragmentManager(),FragmentList));
        //设置界面滑动监听事件
//        mRmVpAy.addOnPageChangeListener(new RestMainAdapter(getSupportFragmentManager(),tabHost));
        //设置TabHost的点击切换监听事件
        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int position  =mTabhost.getCurrentTab();
                mRmVpAy.setCurrentItem(position);
            }
        });
    }
    //给indicator配置View
    private View getItemView(TabModel tab) {
       View  view = inflater.inflate(R.layout.view_tab_content, null);

//        ImageView img = (ImageView) view.findViewById(R.id.tab_imageview);
        TextView textview = (TextView) view.findViewById(R.id.tab_textview);
        Drawable drawable = ContextCompat.getDrawable(getContext(),tab.getImg());
        int tabImgSize = getResources().getDimensionPixelSize(R.dimen.dp_20);
//        int tabPdSize = getResources().getDimensionPixelSize(R.dimen.dp_8);
        drawable.setBounds(0,0,tabImgSize,tabImgSize);

//        img.setImageResource(tab.getImg());
        textview.setText(tab.getTitle());
        textview.setCompoundDrawables(null,drawable,null,null);
//        textview.setCompoundDrawablePadding(tabPdSize);

        return view;
    }

    private Context getContext(){
        return this;
    }

}
