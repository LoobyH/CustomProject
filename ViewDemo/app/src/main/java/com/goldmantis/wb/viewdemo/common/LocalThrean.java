package com.goldmantis.wb.viewdemo.common;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by 00027450 on 2018/4/25.
 */

public class LocalThrean extends Thread {
    private Handler mHandler1;
    private Handler mHandler2;
    /*使用
      *   post(Runnable), postAtTime(Runnable, long), postDelayed(Runnable, long),
      *  sendEmptyMessage(int), sendMessage(Message), sendMessageAtTime(Message, long)和 sendMessageDelayed(Message, long)
      *  这些方法向MQ上发送消息了
     */

//    public final boolean sendMessageDelayed(Message msg, long delayMillis)
//    {
//        if (delayMillis < 0) {
//            delayMillis = 0;
//        }
//        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
//    }
//
//    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
//        //得到消息队列
//        MessageQueue queue = mQueue;
//        if (queue == null) {
//            RuntimeException e = new RuntimeException(
//                    this + " sendMessageAtTime() called with no mQueue");
//            Log.w("Looper", e.getMessage(), e);
//            return false;
//        }
//        return enqueueMessage(queue, msg, uptimeMillis);
//    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        mHandler1 = new Handler();
        mHandler2 = new Handler();
        Looper.loop();
    }
}
