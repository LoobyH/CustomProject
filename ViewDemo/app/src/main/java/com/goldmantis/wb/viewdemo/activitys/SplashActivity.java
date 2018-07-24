package com.goldmantis.wb.viewdemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.goldmantis.wb.viewdemo.R;
import com.goldmantis.wb.viewdemo.RestMainActivity;
import com.goldmantis.wb.viewdemo.utils.uni.ThreadPoolUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/3/22.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getWindow().getDecorView().setBackgroundResource(R.mipmap.icon_span_background);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        },3000);
    }

    private Context getContext(){
        return this;
    }
}
