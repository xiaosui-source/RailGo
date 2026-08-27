package com.taobao.weex.ui;

import android.util.Pair;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.Invoker;
import com.taobao.weex.ui.SimpleComponentHolder;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ExternalLoaderComponentHolder implements IFComponentHolder {
    public static final String TAG = "SimpleComponentHolder";
    private Class mClass;
    private final IExternalComponentGetter mClzGetter;
    private Map<String, Invoker> mMethodInvokers;
    private Map<String, Invoker> mPropertyInvokers;
    private final String mType;

    public ExternalLoaderComponentHolder(String str, IExternalComponentGetter iExternalComponentGetter) {
        this.mClzGetter = iExternalComponentGetter;
        this.mType = str;
    }

    private synchronized boolean generate() {
        Class cls = this.mClass;
        if (cls == null) {
            return false;
        }
        Pair<Map<String, Invoker>, Map<String, Invoker>> methods = SimpleComponentHolder.getMethods(cls);
        this.mPropertyInvokers = (Map) methods.first;
        this.mMethodInvokers = (Map) methods.second;
        return true;
    }

    @Override // com.taobao.weex.ui.ComponentCreator
    public synchronized WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        WXComponent wXComponentCreateInstance;
        if (this.mClass == null) {
            this.mClass = this.mClzGetter.getExternalComponentClass(this.mType, wXSDKInstance);
        }
        wXComponentCreateInstance = new SimpleComponentHolder.ClazzComponentCreator(this.mClass).createInstance(wXSDKInstance, wXVContainer, basicComponentData);
        wXComponentCreateInstance.bindHolder(this);
        return wXComponentCreateInstance;
    }

    @Override // com.taobao.weex.bridge.JavascriptInvokable
    public Invoker getMethodInvoker(String str) {
        if (this.mMethodInvokers != null || generate()) {
            return this.mMethodInvokers.get(str);
        }
        return null;
    }

    @Override // com.taobao.weex.bridge.JavascriptInvokable
    public synchronized String[] getMethods() {
        if (this.mMethodInvokers == null && !generate()) {
            return new String[0];
        }
        Set<String> setKeySet = this.mMethodInvokers.keySet();
        return (String[]) setKeySet.toArray(new String[setKeySet.size()]);
    }

    @Override // io.dcloud.feature.uniapp.ui.AbsIComponentHolder
    public synchronized Invoker getPropertyInvoker(String str) {
        if (this.mPropertyInvokers == null && !generate()) {
            return null;
        }
        return this.mPropertyInvokers.get(str);
    }

    @Override // io.dcloud.feature.uniapp.ui.AbsIComponentHolder
    public void loadIfNonLazy() {
    }
}
