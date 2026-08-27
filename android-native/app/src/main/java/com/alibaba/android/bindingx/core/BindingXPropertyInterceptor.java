package com.alibaba.android.bindingx.core;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.alibaba.android.bindingx.core.PlatformManager;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BindingXPropertyInterceptor {
    private static BindingXPropertyInterceptor sInstance = new BindingXPropertyInterceptor();
    private final Handler sUIHandler = new Handler(Looper.getMainLooper());
    private final LinkedList<IPropertyUpdateInterceptor> mPropertyInterceptors = new LinkedList<>();

    public interface IPropertyUpdateInterceptor {
        boolean updateView(View view, String str, Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map, Object... objArr);
    }

    private BindingXPropertyInterceptor() {
    }

    public static BindingXPropertyInterceptor getInstance() {
        return sInstance;
    }

    public void addInterceptor(IPropertyUpdateInterceptor iPropertyUpdateInterceptor) {
        if (iPropertyUpdateInterceptor != null) {
            this.mPropertyInterceptors.add(iPropertyUpdateInterceptor);
        }
    }

    public boolean removeInterceptor(IPropertyUpdateInterceptor iPropertyUpdateInterceptor) {
        if (iPropertyUpdateInterceptor != null) {
            return this.mPropertyInterceptors.remove(iPropertyUpdateInterceptor);
        }
        return false;
    }

    public void clear() {
        this.mPropertyInterceptors.clear();
    }

    public void performIntercept(final View view, final String str, final Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, final Map<String, Object> map, final Object... objArr) {
        if (this.mPropertyInterceptors.isEmpty()) {
            return;
        }
        this.sUIHandler.post(new WeakRunnable(new Runnable() { // from class: com.alibaba.android.bindingx.core.BindingXPropertyInterceptor.1
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = BindingXPropertyInterceptor.this.mPropertyInterceptors.iterator();
                while (it.hasNext()) {
                    ((IPropertyUpdateInterceptor) it.next()).updateView(view, str, obj, iDeviceResolutionTranslator, map, objArr);
                }
            }
        }));
    }

    public void clearCallbacks() {
        this.sUIHandler.removeCallbacksAndMessages(null);
    }

    public List<IPropertyUpdateInterceptor> getInterceptors() {
        return Collections.unmodifiableList(this.mPropertyInterceptors);
    }
}
