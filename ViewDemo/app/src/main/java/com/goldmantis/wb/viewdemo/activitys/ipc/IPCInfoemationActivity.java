package com.goldmantis.wb.viewdemo.activitys.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.goldmantis.wb.viewdemo.R;
import com.goldmantis.wb.viewdemo.model.PostPic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2018/7/7.
 */

public class IPCInfoemationActivity extends AppCompatActivity {

    private static final String Tag = "IPCInfoemationActivity";

    private Messenger mService;
    private ServiceConnection mconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null,11);
            Bundle data = new Bundle();
            data.putString("msg","hello,this is client.");
            msg.setData(data);
            msg.replyTo = mGetrelpy;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger mGetrelpy = new Messenger(new RelpyHandler());
    private static  class RelpyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 12:
                    Log.e(Tag,"service relpy :"+msg.getData().getString("msg"));
                    break;
                default: super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(this,IPCMessgerService.class);
        bindService(intent,mconnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        unbindService(mconnection);
        super.onDestroy();
    }

    private void persistToFile(){
        final String sdcardpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Thread toWRFileThread = new Thread(new Runnable() {
            @Override
            public void run() {
                PostPic postPic = new PostPic();
                postPic.setId("2324");
                postPic.setUrl("http://172.31.53.34");
                File dir = new File(sdcardpath+File.separator+"wb"+File.separator+"temp.txt");
                if (!dir.exists()){
                    dir.mkdirs();
                }
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(dir));
                    objectOutputStream.writeObject(postPic);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        toWRFileThread.start();
    }

}
