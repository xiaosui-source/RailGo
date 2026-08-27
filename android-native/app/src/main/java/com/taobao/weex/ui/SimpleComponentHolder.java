package com.taobao.weex.ui;

import android.util.Pair;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.Invoker;
import com.taobao.weex.bridge.MethodInvoker;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXRuntimeException;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXLogUtils;
import io.dcloud.feature.uniapp.UniSDKInstance;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.ui.action.AbsComponentData;
import io.dcloud.feature.uniapp.ui.component.AbsVContainer;
import io.dcloud.feature.uniapp.ui.component.UniComponent;
import io.dcloud.feature.uniapp.ui.component.UniComponentProp;
import io.dcloud.feature.uniapp.ui.component.UniVContainer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class SimpleComponentHolder implements IFComponentHolder {
    public static final String TAG = "SimpleComponentHolder";
    private final Class<? extends WXComponent> mClz;
    private ComponentCreator mCreator;
    private Map<String, Invoker> mMethodInvokers;
    private Map<String, Invoker> mPropertyInvokers;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class ClazzComponentCreator implements ComponentCreator {
        private Constructor<? extends WXComponent> mAbsConstructor;
        private final Class<? extends WXComponent> mCompClz;
        private Constructor<? extends WXComponent> mConstructor;

        public ClazzComponentCreator(Class<? extends WXComponent> cls) {
            this.mCompClz = cls;
        }

        private Constructor<? extends WXComponent> getComponentConstructor(Boolean bool) {
            Class<?> cls;
            Class<?> cls2;
            Class<?> cls3;
            Class<? extends WXComponent> cls4 = this.mCompClz;
            if (bool.booleanValue()) {
                cls = UniSDKInstance.class;
                cls2 = AbsVContainer.class;
                cls3 = AbsComponentData.class;
            } else {
                cls = WXSDKInstance.class;
                cls2 = WXVContainer.class;
                cls3 = BasicComponentData.class;
            }
            try {
                return cls4.getConstructor(cls, cls2, cls3);
            } catch (NoSuchMethodException unused) {
                WXLogUtils.d("ClazzComponentCreator", "Use deprecated component constructor");
                try {
                    return cls4.getConstructor(cls, cls2, Boolean.TYPE, cls3);
                } catch (NoSuchMethodException unused2) {
                    try {
                        return cls4.getConstructor(cls, cls2, String.class, Boolean.TYPE, cls3);
                    } catch (NoSuchMethodException unused3) {
                        throw new WXRuntimeException("Can't find constructor of component.");
                    }
                }
            }
        }

        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            Constructor<? extends WXComponent> constructor;
            boolean z = UniVContainer.class.isAssignableFrom(this.mCompClz) || UniComponent.class.isAssignableFrom(this.mCompClz);
            if (z) {
                if (this.mAbsConstructor == null) {
                    this.mAbsConstructor = getComponentConstructor(Boolean.valueOf(z));
                }
                constructor = this.mAbsConstructor;
            } else {
                if (this.mConstructor == null) {
                    this.mConstructor = getComponentConstructor(Boolean.valueOf(z));
                }
                constructor = this.mConstructor;
            }
            int length = constructor.getParameterTypes().length;
            return length == 3 ? constructor.newInstance(wXSDKInstance, wXVContainer, basicComponentData) : length == 4 ? constructor.newInstance(wXSDKInstance, wXVContainer, Boolean.FALSE, basicComponentData) : constructor.newInstance(wXSDKInstance, wXVContainer, wXSDKInstance.getInstanceId(), Boolean.valueOf(wXVContainer.isLazy()));
        }
    }

    public SimpleComponentHolder(Class<? extends WXComponent> cls) {
        this(cls, new ClazzComponentCreator(cls));
    }

    private synchronized void generate() {
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d("SimpleComponentHolder", "Generate Component:" + this.mClz.getSimpleName());
        }
        Pair<Map<String, Invoker>, Map<String, Invoker>> methods = getMethods(this.mClz);
        this.mPropertyInvokers = (Map) methods.first;
        this.mMethodInvokers = (Map) methods.second;
    }

    public static Pair<Map<String, Invoker>, Map<String, Invoker>> getMethods(Class cls) throws SecurityException {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        try {
            for (Method method : cls.getMethods()) {
                try {
                    Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                    int length = declaredAnnotations.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        Annotation annotation = declaredAnnotations[i];
                        if (annotation != null) {
                            if (annotation instanceof WXComponentProp) {
                                map.put(((WXComponentProp) annotation).name(), new MethodInvoker(method, true));
                                break;
                            }
                            if (annotation instanceof JSMethod) {
                                JSMethod jSMethod = (JSMethod) annotation;
                                String strAlias = jSMethod.alias();
                                if ("_".equals(strAlias)) {
                                    strAlias = method.getName();
                                }
                                map2.put(strAlias, new MethodInvoker(method, jSMethod.uiThread()));
                            } else {
                                if (annotation instanceof UniComponentProp) {
                                    map.put(((UniComponentProp) annotation).name(), new MethodInvoker(method, true));
                                    break;
                                }
                                if (annotation instanceof UniJSMethod) {
                                    UniJSMethod uniJSMethod = (UniJSMethod) annotation;
                                    String strAlias2 = uniJSMethod.alias();
                                    if ("_".equals(strAlias2)) {
                                        strAlias2 = method.getName();
                                    }
                                    map2.put(strAlias2, new MethodInvoker(method, uniJSMethod.uiThread()));
                                }
                            }
                        }
                        i++;
                    }
                } catch (ArrayIndexOutOfBoundsException | IncompatibleClassChangeError unused) {
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            WXLogUtils.e("SimpleComponentHolder", e2);
        }
        return new Pair<>(map, map2);
    }

    @Override // com.taobao.weex.ui.ComponentCreator
    public synchronized WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        WXComponent wXComponentCreateInstance;
        wXComponentCreateInstance = this.mCreator.createInstance(wXSDKInstance, wXVContainer, basicComponentData);
        wXComponentCreateInstance.bindHolder(this);
        return wXComponentCreateInstance;
    }

    @Override // com.taobao.weex.bridge.JavascriptInvokable
    public Invoker getMethodInvoker(String str) {
        if (this.mMethodInvokers == null) {
            generate();
        }
        return this.mMethodInvokers.get(str);
    }

    @Override // io.dcloud.feature.uniapp.ui.AbsIComponentHolder
    public synchronized Invoker getPropertyInvoker(String str) {
        if (this.mPropertyInvokers == null) {
            generate();
        }
        return this.mPropertyInvokers.get(str);
    }

    @Override // io.dcloud.feature.uniapp.ui.AbsIComponentHolder
    public void loadIfNonLazy() {
        for (Annotation annotation : this.mClz.getDeclaredAnnotations()) {
            if (annotation instanceof Component) {
                if (((Component) annotation).lazyload() || this.mMethodInvokers != null) {
                    return;
                }
                generate();
                return;
            }
        }
    }

    public SimpleComponentHolder(Class<? extends WXComponent> cls, ComponentCreator componentCreator) {
        this.mClz = cls;
        this.mCreator = componentCreator;
    }

    @Override // com.taobao.weex.bridge.JavascriptInvokable
    public synchronized String[] getMethods() {
        Set<String> setKeySet;
        if (this.mMethodInvokers == null) {
            generate();
        }
        setKeySet = this.mMethodInvokers.keySet();
        try {
        } catch (Throwable unused) {
            Class<? extends WXComponent> cls = this.mClz;
            if (cls != null) {
                WXExceptionUtils.commitCriticalExceptionRT(null, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT, WXBridgeManager.METHOD_REGISTER_COMPONENTS, cls.getName() + ": gen methods failed", null);
            }
            return new String[1];
        }
        return (String[]) setKeySet.toArray(new String[setKeySet.size()]);
    }
}
