package com.alibaba.android.bindingx.core;

import android.text.TextUtils;
import com.alibaba.android.bindingx.core.internal.JSFunctionInterface;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BindingXJSFunctionRegister {
    private static final BindingXJSFunctionRegister sInstance = new BindingXJSFunctionRegister();
    private final LinkedHashMap<String, JSFunctionInterface> mJSFunctionMap = new LinkedHashMap<>(8);

    public static BindingXJSFunctionRegister getInstance() {
        return sInstance;
    }

    public void registerJSFunction(String str, JSFunctionInterface jSFunctionInterface) {
        if (TextUtils.isEmpty(str) || jSFunctionInterface == null) {
            return;
        }
        this.mJSFunctionMap.put(str, jSFunctionInterface);
    }

    public boolean unregisterJSFunction(String str) {
        return (TextUtils.isEmpty(str) || this.mJSFunctionMap.remove(str) == null) ? false : true;
    }

    public void clear() {
        this.mJSFunctionMap.clear();
    }

    public Map<String, JSFunctionInterface> getJSFunctions() {
        return Collections.unmodifiableMap(this.mJSFunctionMap);
    }
}
