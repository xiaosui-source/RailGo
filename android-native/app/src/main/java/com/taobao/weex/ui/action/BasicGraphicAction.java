package com.taobao.weex.ui.action;

import android.text.TextUtils;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.WXLogUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class BasicGraphicAction implements IExecutable, Runnable {
    public static final int ActionTypeBatchBegin = 1;
    public static final int ActionTypeBatchEnd = 2;
    public static final int ActionTypeNormal = 0;
    private WXSDKInstance mInstance;
    private final String mRef;
    public int mActionType = 0;
    public boolean mIsRunByBatch = false;

    public BasicGraphicAction(WXSDKInstance wXSDKInstance, String str) {
        this.mInstance = wXSDKInstance;
        this.mRef = str;
    }

    public void executeActionOnRender() {
        WXSDKInstance wXSDKInstance = this.mInstance;
        if (wXSDKInstance != null) {
            if (!TextUtils.isEmpty(wXSDKInstance.getInstanceId())) {
                WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(this.mInstance.getInstanceId(), this);
                return;
            }
            WXLogUtils.e("[BasicGraphicAction] pageId can not be null");
            if (WXEnvironment.isApkDebugable()) {
                throw new RuntimeException(Operators.ARRAY_START_STR + getClass().getName() + "] pageId can not be null");
            }
        }
    }

    public final String getPageId() {
        WXSDKInstance wXSDKInstance = this.mInstance;
        if (wXSDKInstance != null) {
            return wXSDKInstance.getInstanceId();
        }
        return null;
    }

    public final String getRef() {
        return this.mRef;
    }

    public final WXSDKInstance getWXSDKIntance() {
        return this.mInstance;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            executeAction();
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                WXLogUtils.w("BasicGraphicAction", th);
                return;
            }
            WXLogUtils.e("BasicGraphicAction", "SafeRunnable run throw expection:" + th.getMessage());
            throw th;
        }
    }
}
