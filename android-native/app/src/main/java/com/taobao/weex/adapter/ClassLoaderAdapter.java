package com.taobao.weex.adapter;

import android.content.Context;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.component.WXComponent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ClassLoaderAdapter {
    public Class<? extends WXComponent> getComponentClass(String str, String str2, WXSDKInstance wXSDKInstance) {
        try {
            return wXSDKInstance.getContext().getClassLoader().loadClass(str2);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<? extends WXModule> getModuleClass(String str, String str2, Context context) {
        try {
            return context.getClassLoader().loadClass(str2);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
