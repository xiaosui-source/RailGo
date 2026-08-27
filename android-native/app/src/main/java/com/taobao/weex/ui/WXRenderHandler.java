package com.taobao.weex.ui;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class WXRenderHandler extends Handler {
    public WXRenderHandler() {
        super(Looper.getMainLooper());
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
    }

    public final boolean post(String str, Runnable runnable) {
        Message messageObtain = Message.obtain(this, runnable);
        messageObtain.what = str.hashCode();
        return sendMessageDelayed(messageObtain, 0L);
    }
}
