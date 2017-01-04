package com.mmbuycar.managelevel.framework.handler;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public class RepeatedClickHandler {
    private static final long COOL_DOWN_TIME = 1000;
    private static final int MSG_WHAT_COOL_DOWN_COMPLETED = 10043;
    private MyHandler handler;

    public RepeatedClickHandler() {
        handler = new MyHandler();
    }

    /**
     * 处理重复点击
     */
    public void handleRepeatedClick(final View view) {
        view.setEnabled(false);
        new Thread() {
            public void run() {
                try {
                    sleep(COOL_DOWN_TIME);
                    Message msg = new Message();
                    msg.what = MSG_WHAT_COOL_DOWN_COMPLETED;
                    msg.obj = view;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_COOL_DOWN_COMPLETED:
                    ((View) msg.obj).setEnabled(true);
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
