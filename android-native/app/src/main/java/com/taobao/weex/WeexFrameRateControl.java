package com.taobao.weex;

import android.util.Log;
import android.view.Choreographer;
import com.taobao.weex.common.WXErrorCode;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WeexFrameRateControl {
    private static final long VSYNC_FRAME = 16;
    private WeakReference<VSyncListener> mListener;
    private final Choreographer mChoreographer = Choreographer.getInstance();
    private final Choreographer.FrameCallback mVSyncFrameCallback = new Choreographer.FrameCallback() { // from class: com.taobao.weex.WeexFrameRateControl.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            VSyncListener vSyncListener;
            if (WeexFrameRateControl.this.mListener == null || (vSyncListener = (VSyncListener) WeexFrameRateControl.this.mListener.get()) == 0) {
                return;
            }
            try {
                vSyncListener.OnVSync();
                WeexFrameRateControl.this.mChoreographer.postFrameCallback(WeexFrameRateControl.this.mVSyncFrameCallback);
            } catch (UnsatisfiedLinkError e) {
                if (vSyncListener instanceof WXSDKInstance) {
                    ((WXSDKInstance) vSyncListener).onRenderError(WXErrorCode.WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED.getErrorCode(), Log.getStackTraceString(e));
                }
            }
        }
    };
    private final Runnable runnable = null;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    /* renamed from: com.taobao.weex.WeexFrameRateControl$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            VSyncListener vSyncListener;
            if (WeexFrameRateControl.this.mListener == null || (vSyncListener = (VSyncListener) WeexFrameRateControl.this.mListener.get()) == 0) {
                return;
            }
            try {
                vSyncListener.OnVSync();
                WXSDKManager.getInstance().getWXRenderManager().postOnUiThread(WeexFrameRateControl.this.runnable, WeexFrameRateControl.VSYNC_FRAME);
            } catch (UnsatisfiedLinkError e) {
                if (vSyncListener instanceof WXSDKInstance) {
                    ((WXSDKInstance) vSyncListener).onRenderError(WXErrorCode.WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED.getErrorCode(), Log.getStackTraceString(e));
                }
            }
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface VSyncListener {
        void OnVSync();
    }

    public WeexFrameRateControl(VSyncListener vSyncListener) {
        this.mListener = new WeakReference<>(vSyncListener);
    }

    public void start() {
        Choreographer choreographer = this.mChoreographer;
        if (choreographer != null) {
            choreographer.postFrameCallback(this.mVSyncFrameCallback);
        } else if (this.runnable != null) {
            WXSDKManager.getInstance().getWXRenderManager().postOnUiThread(this.runnable, VSYNC_FRAME);
        }
    }

    public void stop() {
        Choreographer choreographer = this.mChoreographer;
        if (choreographer != null) {
            choreographer.removeFrameCallback(this.mVSyncFrameCallback);
        } else if (this.runnable != null) {
            WXSDKManager.getInstance().getWXRenderManager().removeTask(this.runnable);
        }
    }
}
