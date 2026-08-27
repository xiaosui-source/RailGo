package com.taobao.weex.bridge;

import android.util.SparseArray;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class ResultCallbackManager {
    private static SparseArray<ResultCallback> mResultCallbacks = new SparseArray<>();
    private static long sCallbackId;

    ResultCallbackManager() {
    }

    static long generateCallbackId(ResultCallback resultCallback) {
        if (sCallbackId >= 2147483647L) {
            sCallbackId = 0L;
        }
        long j = sCallbackId;
        sCallbackId = 1 + j;
        int i = (int) j;
        mResultCallbacks.put(i, resultCallback);
        return i;
    }

    static ResultCallback removeCallbackById(long j) {
        int i = (int) j;
        ResultCallback resultCallback = mResultCallbacks.get(i);
        mResultCallbacks.remove(i);
        return resultCallback;
    }
}
