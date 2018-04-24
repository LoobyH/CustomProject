package com.goldmantis.wb.viewdemo.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
                Animation dalpha = AnimationUtils.loadAnimation(getContext(),R.anim.d_alpha);
                mSH_IV.startAnimation(dalpha);
                break;
            case R.id.tf_sk_fm:
                Animation drotate = AnimationUtils.loadAnimation(getContext(),R.anim.d_rotate);
                mSK_IV.startAnimation(drotate);
                break;
            case R.id.tf_sq_fm:
                Animation dscale = AnimationUtils.loadAnimation(getContext(),R.anim.d_scale);
                mSQ_IV.startAnimation(dscale);
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
