package com.alibaba.android.bindingx.core;

import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class WeakRunnable implements Runnable {
    private final WeakReference<Runnable> mDelegateRunnable;

    public WeakRunnable(Runnable runnable) {
        this.mDelegateRunnable = new WeakReference<>(runnable);
    }

    @Override // java.lang.Runnable
    public void run() {
        Runnable runnable = this.mDelegateRunnable.get();
        if (runnable != null) {
            runnable.run();
        }
    }
}
