package com.goldmantis.wb.viewdemo.activitys;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldmantis.wb.viewdemo.R;
import com.goldmantis.wb.viewdemo.fragment.SecondLayerFragment;
import com.goldmantis.wb.viewdemo.utils.DisplayUtil;
import com.goldmantis.wb.viewdemo.view.ColorBar;
import com.goldmantis.wb.viewdemo.view.IndicatorViewPager;
import com.goldmantis.wb.viewdemo.view.ScrollIndicatorView;
import com.goldmantis.wb.viewdemo.view.transition.OnTransitionTextListener;

/**
 * Created by 00027450 on 2017/8/31.
 */

public class ViewPageActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ScrollIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        indicator = (ScrollIndicatorView) findViewById(R.id.fragment_tabmain_indicator);
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.RED, 5));
        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;

        int selectColor = ContextCompat.getColor(this,R.color.tab_top_text_2);
        int unSelectColor = ContextCompat.getColor(this,R.color.tab_top_text_1);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));

        viewPager.setOffscreenPageLimit(5);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());

        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MViewPageAdapter(getSupportFragmentManager()));
    }

    private class MViewPageAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] titles = new String[]{"基本信息","房屋信息","跟进记录","装修意向","签约信息"};

        public MViewPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(titles[position]+ position);
            int witdh = getTextWidth(textView);
            int padding = DisplayUtil.dipToPix(getApplicationContext(), 8);
            //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
            //1.3f是根据上面字体大小变化的倍数1.3f设置
            textView.setWidth((int) (witdh * 1.3f) + padding);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            SecondLayerFragment mainFragment = new SecondLayerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SecondLayerFragment.INTENT_STRING_TABNAME, "title");
            bundle.putInt(SecondLayerFragment.INTENT_INT_POSITION, position);
            mainFragment.setArguments(bundle);
            return mainFragment;
        }

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }
    }
}
