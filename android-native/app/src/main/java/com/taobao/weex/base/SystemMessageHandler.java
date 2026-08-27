package com.taobao.weex.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.Serializable;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class SystemMessageHandler extends Handler implements Serializable {
    private static final int SCHEDULED_WORK = 1;
    private static final String TAG = "SystemMessageHandler";
    private Method mMessageMethodSetAsynchronous;
    private long mMessagePumpDelegateNative;

    private SystemMessageHandler(long j, boolean z) {
        super(z ? Looper.getMainLooper() : Looper.myLooper());
        this.mMessagePumpDelegateNative = j;
        try {
            this.mMessageMethodSetAsynchronous = Class.forName("android.os.Message").getMethod("setAsynchronous", Boolean.TYPE);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Failed to find android.os.Message class:" + e);
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Failed to load Message.setAsynchronous method:" + e2);
        } catch (RuntimeException e3) {
            Log.e(TAG, "Exception while loading Message.setAsynchronous method: " + e3);
        }
    }

    public static SystemMessageHandler create(long j, boolean z) {
        return new SystemMessageHandler(j, z);
    }

    private native void nativeRunWork(long j);

    private Message obtainAsyncMessage(int i) {
        Message messageObtain = Message.obtain();
        messageObtain.what = i;
        return messageObtain;
    }

    private void scheduleDelayedWork(long j) {
        sendMessageDelayed(obtainAsyncMessage(1), j);
    }

    private void scheduleWork() {
        sendMessage(obtainAsyncMessage(1));
    }

    private void stop() {
        removeMessages(1);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        nativeRunWork(this.mMessagePumpDelegateNative);
    }
}
