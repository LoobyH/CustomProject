package com.goldmantis.wb.viewdemo.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldmantis.wb.viewdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 00027450 on 2018/3/20.
 */

public class Function1Fragment extends Fragment {
    private final static String TAG = "Function1Fragment";
    @Bind(R.id.tf_qr_fm)
    ImageView mQR_IV;
    @Bind(R.id.tf_sh_fm)
    ImageView mSH_IV;
    @Bind(R.id.tf_sk_fm)
    ImageView mSK_IV;
    @Bind(R.id.tf_sq_fm)
    ImageView mSQ_IV;
    @Bind(R.id.tf_wx_fm)
    ImageView mWX_IV;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tf, container,false);
        if (null == rootView){
            return super.onCreateView(inflater,container,savedInstanceState);
        }

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.tf_qr_fm,R.id.tf_sh_fm,R.id.tf_sk_fm,R.id.tf_sq_fm,R.id.tf_wx_fm})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.tf_qr_fm:
//                Animation da = AnimationUtils.loadAnimation(getContext(), R.anim.animdemo);
//                mQR_IV.startAnimation(da);
                AnimationDrawable rocketAnimation = (AnimationDrawable) mQR_IV.getBackground();
                rocketAnimation.start();
                break;
            case R.id.tf_sh_fm:
//                Animation dalpha = AnimationUtils.loadAnimation(getContext(),R.anim.d_alpha);
//                mSH_IV.startAnimation(dalpha);
                float fromX = mSH_IV.getTranslationX();
                final ValueAnimator va = ValueAnimator.ofFloat(fromX,fromX+200f,fromX);
                va.setDuration(2000);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        float v = (float) va.getAnimatedValue();
                        mSH_IV.setTranslationX(v);
                        Log.e(TAG, "v:" + v);
                    }
                });
                va.start();
                break;
            case R.id.tf_sk_fm:
                Animation drotate = AnimationUtils.loadAnimation(getContext(),R.anim.d_rotate);
                mSK_IV.startAnimation(drotate);
                break;
            case R.id.tf_sq_fm:
//                Animation dscale = AnimationUtils.loadAnimation(getContext(),R.anim.d_scale);
//                mSQ_IV.startAnimation(dscale);
                float fx = mSQ_IV.getTranslationX();
                ObjectAnimator oa = ObjectAnimator.ofFloat(mSQ_IV,"TranslationX",fx,fx+200f,fx);
                oa.setDuration(2000);
                oa.start();
                oa.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        Log.e(TAG, "v1:" + animation.toString());
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Log.e(TAG, "v2:" + animation.toString());
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                        Log.e(TAG, "v3:" + animation.toString());
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        Log.e(TAG, "v4:" + animation.toString());
                    }

                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                        Log.e(TAG, "v5:" + animation.toString());
                    }

                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                        Log.e(TAG, "v6:" + animation.toString());
                    }
                });

                break;
            case R.id.tf_wx_fm:
                Animation dtranslate = AnimationUtils.loadAnimation(getContext(),R.anim.d_translate);
                mWX_IV.startAnimation(dtranslate);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
