package com.goldmantis.wb.viewdemo.activitys.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;

/**
 * Created by Administrator on 2018/7/7.
 */

public class IPCMessgerService extends Service {
    private static final String Tag = "IPCMessgerService";
    private static class MessgerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 11:
                    Log.e(Tag,"client request:"+msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message relpyMessage = Message.obtain(null,12);
                    Bundle relpyData = new Bundle();
                    relpyData.putString("msg","hello , i am service,i receiver your msg");
                    relpyMessage.setData(relpyData);
                    try {
                        client.send(relpyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessgerHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
