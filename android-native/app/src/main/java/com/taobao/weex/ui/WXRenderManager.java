package com.taobao.weex.ui;

import android.opengl.GLES10;
import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXRuntimeException;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.dom.RenderContext;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.action.BasicGraphicAction;
import com.taobao.weex.ui.action.GraphicActionBatchAction;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXRenderManager {
    private static int mOpenGLRenderLimitValue = 0;
    private static int nativeBatchTimes = 0;
    private static final String sKeyAction = "Action";
    private String mCurrentBatchInstanceId = null;
    private ArrayList<Map<String, Object>> mBatchActions = new ArrayList<>();
    private final int MAX_DROP_FRAME_NATIVE_BATCH = 2000;
    private volatile ConcurrentHashMap<String, RenderContextImpl> mRenderContext = new ConcurrentHashMap<>();
    private WXRenderHandler mWXRenderHandler = new WXRenderHandler();

    public static int getOpenGLRenderLimitValue() {
        if (mOpenGLRenderLimitValue == 0) {
            int i = 0;
            try {
                EGL10 egl10 = (EGL10) EGLContext.getEGL();
                EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
                egl10.eglInitialize(eGLDisplayEglGetDisplay, new int[2]);
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                int[] iArr = new int[1];
                egl10.eglChooseConfig(eGLDisplayEglGetDisplay, new int[]{12351, 12430, 12329, 0, 12339, 1, 12344}, eGLConfigArr, 1, iArr);
                if (iArr[0] == 0) {
                    i = -1;
                    egl10.eglTerminate(eGLDisplayEglGetDisplay);
                } else {
                    EGLConfig eGLConfig = eGLConfigArr[0];
                    EGLSurface eGLSurfaceEglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eGLDisplayEglGetDisplay, eGLConfig, new int[]{12375, 64, 12374, 64, 12344});
                    EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
                    EGLContext eGLContextEglCreateContext = egl10.eglCreateContext(eGLDisplayEglGetDisplay, eGLConfig, eGLContext, new int[]{12440, 1, 12344});
                    egl10.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface, eGLSurfaceEglCreatePbufferSurface, eGLContextEglCreateContext);
                    int[] iArr2 = new int[1];
                    GLES10.glGetIntegerv(3379, iArr2, 0);
                    EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
                    egl10.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurface, eGLSurface, eGLContext);
                    egl10.eglDestroySurface(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface);
                    egl10.eglDestroyContext(eGLDisplayEglGetDisplay, eGLContextEglCreateContext);
                    egl10.eglTerminate(eGLDisplayEglGetDisplay);
                    i = iArr2[0];
                }
            } catch (Exception e) {
                WXLogUtils.e(WXLogUtils.getStackTrace(e));
            }
            mOpenGLRenderLimitValue = i;
        }
        return mOpenGLRenderLimitValue;
    }

    private void postAllStashedGraphicAction(String str, BasicGraphicAction basicGraphicAction) {
        BasicGraphicAction basicGraphicAction2;
        int i;
        RenderContextImpl renderContextImpl = this.mRenderContext.get(str);
        ArrayList arrayList = renderContextImpl != null ? new ArrayList(this.mBatchActions) : null;
        this.mBatchActions.clear();
        this.mCurrentBatchInstanceId = null;
        nativeBatchTimes = 0;
        if (renderContextImpl == null) {
            return;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Object obj = ((Map) arrayList.get(i2)).get(sKeyAction);
            if ((obj instanceof BasicGraphicAction) && (i = (basicGraphicAction2 = (BasicGraphicAction) obj).mActionType) != 1 && i != 2) {
                arrayList2.add(basicGraphicAction2);
            }
        }
        postGraphicAction(str, new GraphicActionBatchAction(basicGraphicAction.getWXSDKIntance(), basicGraphicAction.getRef(), arrayList2));
    }

    public List<WXSDKInstance> getAllInstances() {
        if (this.mRenderContext == null || this.mRenderContext.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, RenderContextImpl>> it = this.mRenderContext.entrySet().iterator();
        while (it.hasNext()) {
            RenderContextImpl value = it.next().getValue();
            if (value != null) {
                arrayList.add(value.getWXSDKInstance());
            }
        }
        return arrayList;
    }

    public RenderContext getRenderContext(String str) {
        return this.mRenderContext.get(str);
    }

    public WXComponent getWXComponent(String str, String str2) {
        RenderContext renderContext;
        if (str == null || TextUtils.isEmpty(str2) || (renderContext = getRenderContext(str)) == null) {
            return null;
        }
        return renderContext.getComponent(str2);
    }

    public WXComponent getWXComponentById(String str, String str2) {
        RenderContextImpl renderContextImpl;
        if (str == null || TextUtils.isEmpty(str2) || (renderContextImpl = this.mRenderContext.get(str)) == null) {
            return null;
        }
        return renderContextImpl.getComponentById(str2);
    }

    public WXSDKInstance getWXSDKInstance(String str) {
        RenderContextImpl renderContextImpl = this.mRenderContext.get(str);
        if (renderContextImpl == null) {
            return null;
        }
        return renderContextImpl.getWXSDKInstance();
    }

    public void postGraphicAction(String str, BasicGraphicAction basicGraphicAction) {
        if (this.mRenderContext.get(str) == null) {
            return;
        }
        String str2 = this.mCurrentBatchInstanceId;
        if (str2 != null && str != null && !str2.equals(str) && this.mBatchActions.size() > 0) {
            ArrayList<Map<String, Object>> arrayList = this.mBatchActions;
            Object obj = arrayList.get(arrayList.size() - 1).get(sKeyAction);
            if (obj instanceof BasicGraphicAction) {
                postAllStashedGraphicAction(this.mCurrentBatchInstanceId, (BasicGraphicAction) obj);
            }
        }
        int i = basicGraphicAction.mActionType;
        if (i == 2) {
            postAllStashedGraphicAction(str, basicGraphicAction);
            return;
        }
        if (i == 1 || this.mBatchActions.size() > 0) {
            int i2 = nativeBatchTimes + 1;
            nativeBatchTimes = i2;
            if (i2 <= 2000) {
                HashMap map = new HashMap(1);
                basicGraphicAction.mIsRunByBatch = true;
                map.put(sKeyAction, basicGraphicAction);
                this.mBatchActions.add(map);
                this.mCurrentBatchInstanceId = str;
                return;
            }
            postAllStashedGraphicAction(str, basicGraphicAction);
        }
        this.mWXRenderHandler.post(str, basicGraphicAction);
    }

    public void postOnUiThread(Runnable runnable, long j) {
        this.mWXRenderHandler.postDelayed(WXThread.secure(runnable), j);
    }

    public void registerComponent(String str, String str2, WXComponent wXComponent) {
        RenderContextImpl renderContextImpl = this.mRenderContext.get(str);
        if (renderContextImpl != null) {
            renderContextImpl.registerComponent(str2, wXComponent);
            if (renderContextImpl.getInstance() != null) {
                renderContextImpl.getInstance().getApmForInstance().updateMaxStats(WXInstanceApm.KEY_PAGE_STATS_MAX_COMPONENT_NUM, renderContextImpl.getComponentCount());
            }
        }
    }

    public void registerInstance(WXSDKInstance wXSDKInstance) {
        if (wXSDKInstance.getInstanceId() != null) {
            this.mRenderContext.put(wXSDKInstance.getInstanceId(), new RenderContextImpl(wXSDKInstance));
            return;
        }
        WXErrorCode wXErrorCode = WXErrorCode.WX_RENDER_ERR_INSTANCE_ID_NULL;
        WXExceptionUtils.commitCriticalExceptionRT(null, wXErrorCode, "registerInstance", wXErrorCode.getErrorMsg() + "instanceId is null", null);
    }

    public void removeRenderStatement(String str) {
        if (!WXUtils.isUiThread()) {
            throw new WXRuntimeException("[WXRenderManager] removeRenderStatement can only be called in main thread");
        }
        RenderContextImpl renderContextImplRemove = this.mRenderContext.remove(str);
        if (renderContextImplRemove != null) {
            renderContextImplRemove.destroy();
        }
        if (str == null) {
            this.mWXRenderHandler.removeCallbacksAndMessages(null);
        } else {
            this.mWXRenderHandler.removeMessages(str.hashCode());
        }
    }

    public void removeTask(Runnable runnable) {
        this.mWXRenderHandler.removeCallbacks(runnable);
    }

    public void setScrollable(String str, boolean z, String str2) {
        if (str == null) {
            return;
        }
        this.mRenderContext.get(str).setAllScrollerScrollable(z, str2);
    }

    public WXComponent unregisterComponent(String str, String str2) {
        RenderContextImpl renderContextImpl = this.mRenderContext.get(str);
        if (renderContextImpl == null) {
            return null;
        }
        if (renderContextImpl.getInstance() != null) {
            renderContextImpl.getInstance().getApmForInstance().updateMaxStats(WXInstanceApm.KEY_PAGE_STATS_MAX_COMPONENT_NUM, renderContextImpl.getComponentCount());
        }
        return renderContextImpl.unregisterComponent(str2);
    }

    public void postOnUiThread(Runnable runnable, String str) {
        this.mWXRenderHandler.post(str, WXThread.secure(runnable));
    }

    public void postOnUiThread(Runnable runnable) {
        this.mWXRenderHandler.post(WXThread.secure(runnable));
    }
}
