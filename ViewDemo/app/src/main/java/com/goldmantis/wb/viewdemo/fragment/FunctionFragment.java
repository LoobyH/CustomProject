package com.goldmantis.wb.viewdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldmantis.wb.viewdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 00027450 on 2018/3/20.
 */

public class FunctionFragment extends Fragment {
    @Bind(R.id.tv_info)
    TextView mTvInfo;
    @Bind(R.id.et_interval)
    EditText mEtInterval;
    @Bind(R.id.layout_interval)
    LinearLayout mLayoutInterval;
    @Bind(R.id.et_alarm)
    EditText mEtAlarm;
    @Bind(R.id.cb_needAddress)
    CheckBox mCbNeedAddress;
    @Bind(R.id.cb_gpsFirst)
    CheckBox mCbGpsFirst;
    @Bind(R.id.bt_location)
    Button mBtLocation;
    @Bind(R.id.tv_result)
    TextView mTvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_alarm_location, container,false);
        if (null == rootView){
            return super.onCreateView(inflater,container,savedInstanceState);
        }

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtLocation.setText("FunctionFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
