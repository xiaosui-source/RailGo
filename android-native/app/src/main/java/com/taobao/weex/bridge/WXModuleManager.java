package com.taobao.weex.bridge;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.common.Destroyable;
import com.taobao.weex.common.IWXObject;
import com.taobao.weex.common.WXException;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.config.ConfigModuleFactory;
import com.taobao.weex.ui.module.WXDomModule;
import com.taobao.weex.ui.module.WXTimerModule;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.cache.RegisterCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXModuleManager {
    private static ArrayList<String> mBlackModuleList;
    private static volatile ConcurrentMap<String, ModuleFactoryImpl> sModuleFactoryMap = new ConcurrentHashMap();
    private static Map<String, WXModule> sGlobalModuleMap = new HashMap();
    private static Map<String, WXDomModule> sDomModuleMap = new HashMap();
    private static Map<String, Map<String, WXModule>> sInstanceModuleMap = new ConcurrentHashMap();

    static Object callModuleMethod(String str, String str2, String str3, JSONArray jSONArray) {
        ModuleFactory moduleFactory = sModuleFactoryMap.get(str2).mFactory;
        if (moduleFactory == null) {
            WXLogUtils.e("[WXModuleManager] module factory not found.");
            return null;
        }
        WXModule wXModuleFindModule = findModule(str, str2, moduleFactory);
        if (wXModuleFindModule == null) {
            return null;
        }
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        wXModuleFindModule.mWXSDKInstance = sDKInstance;
        wXModuleFindModule.mUniSDKInstance = sDKInstance;
        Invoker methodInvoker = moduleFactory.getMethodInvoker(str3);
        try {
            try {
                if (sDKInstance == null) {
                    WXLogUtils.e("callModuleMethod >>> instance is null");
                    if (!(wXModuleFindModule instanceof WXDomModule) && !(wXModuleFindModule instanceof WXTimerModule)) {
                        return null;
                    }
                    wXModuleFindModule.mWXSDKInstance = null;
                    wXModuleFindModule.mUniSDKInstance = null;
                    return null;
                }
                IWXUserTrackAdapter iWXUserTrackAdapter = WXSDKManager.getInstance().getIWXUserTrackAdapter();
                if (iWXUserTrackAdapter != null) {
                    HashMap map = new HashMap();
                    map.put(IWXUserTrackAdapter.MONITOR_ERROR_CODE, "101");
                    map.put(IWXUserTrackAdapter.MONITOR_ARG, str2 + Operators.DOT_STR + str3);
                    map.put(IWXUserTrackAdapter.MONITOR_ERROR_MSG, sDKInstance.getBundleUrl());
                    iWXUserTrackAdapter.commit(sDKInstance.getContext(), null, IWXUserTrackAdapter.INVOKE_MODULE, null, map);
                }
                Object objDispatchCallModuleMethod = dispatchCallModuleMethod(sDKInstance, wXModuleFindModule, jSONArray, methodInvoker);
                if (!(wXModuleFindModule instanceof WXDomModule) && !(wXModuleFindModule instanceof WXTimerModule)) {
                    return objDispatchCallModuleMethod;
                }
                wXModuleFindModule.mWXSDKInstance = null;
                wXModuleFindModule.mUniSDKInstance = null;
                return objDispatchCallModuleMethod;
            } catch (Exception e) {
                WXLogUtils.e("callModuleMethod >>> invoke module:" + str2 + ", method:" + str3 + " failed. ", e);
                if ((wXModuleFindModule instanceof WXDomModule) || (wXModuleFindModule instanceof WXTimerModule)) {
                    wXModuleFindModule.mWXSDKInstance = null;
                    wXModuleFindModule.mUniSDKInstance = null;
                }
                return null;
            }
        } finally {
        }
    }

    public static void createDomModule(WXSDKInstance wXSDKInstance) {
        if (wXSDKInstance != null) {
            sDomModuleMap.put(wXSDKInstance.getInstanceId(), new WXDomModule(wXSDKInstance));
        }
    }

    public static void destoryDomModule(String str) {
        sDomModuleMap.remove(str);
    }

    public static void destroyInstanceModules(String str) {
        sDomModuleMap.remove(str);
        Map<String, WXModule> mapRemove = sInstanceModuleMap.remove(str);
        if (mapRemove == null || mapRemove.size() < 1) {
            return;
        }
        Iterator<Map.Entry<String, WXModule>> it = mapRemove.entrySet().iterator();
        while (it.hasNext()) {
            IWXObject iWXObject = (WXModule) it.next().getValue();
            if (iWXObject instanceof Destroyable) {
                ((Destroyable) iWXObject).destroy();
            }
        }
    }

    private static Object dispatchCallModuleMethod(WXSDKInstance wXSDKInstance, WXModule wXModule, JSONArray jSONArray, Invoker invoker) {
        if (!wXSDKInstance.isPreRenderMode()) {
            return wXSDKInstance.getNativeInvokeHelper().invoke(wXModule, invoker, jSONArray);
        }
        if (invoker.isRunOnUIThread()) {
            return null;
        }
        return wXSDKInstance.getNativeInvokeHelper().invoke(wXModule, invoker, jSONArray);
    }

    private static WXModule findModule(String str, String str2, ModuleFactory moduleFactory) {
        WXModule wXModuleBuildInstance;
        WXModule wXModule = sGlobalModuleMap.get(str2);
        if (wXModule != null) {
            return wXModule;
        }
        Map<String, WXModule> concurrentHashMap = sInstanceModuleMap.get(str);
        if (concurrentHashMap == null) {
            concurrentHashMap = new ConcurrentHashMap<>();
            sInstanceModuleMap.put(str, concurrentHashMap);
        }
        WXModule wXModule2 = concurrentHashMap.get(str2);
        if (wXModule2 != null) {
            return wXModule2;
        }
        try {
            if (moduleFactory instanceof ConfigModuleFactory) {
                wXModuleBuildInstance = ((ConfigModuleFactory) moduleFactory).buildInstance(WXSDKManager.getInstance().getSDKInstance(str));
            } else {
                wXModuleBuildInstance = moduleFactory.buildInstance();
            }
            wXModuleBuildInstance.setModuleName(str2);
            concurrentHashMap.put(str2, wXModuleBuildInstance);
            return wXModuleBuildInstance;
        } catch (Exception e) {
            WXLogUtils.e(str2 + " module build instace failed.", e);
            return null;
        }
    }

    private static ArrayList<String> getBlackList() {
        if (mBlackModuleList == null) {
            ArrayList<String> arrayList = new ArrayList<>();
            mBlackModuleList = arrayList;
            arrayList.add("webview");
            mBlackModuleList.add("animation");
            mBlackModuleList.add("binding");
            mBlackModuleList.add("bindingx");
            mBlackModuleList.add("instanceWrap");
            mBlackModuleList.add("meta");
            mBlackModuleList.add("navigator");
            mBlackModuleList.add("expressionBinding");
        }
        return mBlackModuleList;
    }

    public static WXDomModule getDomModule(String str) {
        return sDomModuleMap.get(str);
    }

    public static JSONObject getRegisterJsModules() {
        if (sModuleFactoryMap == null || sModuleFactoryMap.size() <= 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, ModuleFactoryImpl> entry : sModuleFactoryMap.entrySet()) {
            try {
                if (!getBlackList().contains(entry.getKey()) && entry.getValue().mFactory != null) {
                    jSONObject.put(entry.getKey(), (Object) modulesToJSONArray(entry.getValue().mFactory.getMethods(), entry.getValue().mFactory));
                }
            } catch (Throwable unused) {
            }
        }
        return jSONObject;
    }

    public static boolean hasModule(String str) {
        return sGlobalModuleMap.containsKey(str) || sModuleFactoryMap.containsKey(str);
    }

    public static JSONArray modulesToJSONArray(String[] strArr, ModuleFactory moduleFactory) {
        JSONArray jSONArray = null;
        if (strArr != null && strArr.length > 0) {
            for (int i = 0; i < strArr.length; i++) {
                if (jSONArray == null) {
                    jSONArray = new JSONArray();
                }
                if (!strArr[i].equals("addEventListener") && !strArr[i].equals("removeAllEventListeners")) {
                    boolean zIsRunOnUIThread = moduleFactory.getMethodInvoker(strArr[i]).isRunOnUIThread();
                    StringBuilder sb = new StringBuilder();
                    sb.append(strArr[i]);
                    sb.append(!zIsRunOnUIThread ? ":sync" : ":async");
                    jSONArray.add(sb.toString());
                }
            }
        }
        return jSONArray;
    }

    public static boolean onActivityBack(String str) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map == null) {
            return false;
        }
        for (String str2 : map.keySet()) {
            WXModule wXModule = map.get(str2);
            if (wXModule != null) {
                return wXModule.onActivityBack();
            }
            WXLogUtils.w("onActivityCreate can not find the " + str2 + " module");
        }
        return false;
    }

    public static void onActivityCreate(String str) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onActivityCreate();
                } else {
                    WXLogUtils.w("onActivityCreate can not find the " + str2 + " module");
                }
            }
        }
    }

    public static void onActivityDestroy(String str) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onActivityDestroy();
                } else {
                    WXLogUtils.w("onActivityDestroy can not find the " + str2 + " module");
                }
            }
        }
    }

    public static void onActivityPause(String str) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onActivityPause();
                } else {
                    WXLogUtils.w("onActivityPause can not find the " + str2 + " module");
                }
            }
        }
    }

    public static void onActivityResult(String str, int i, int i2, Intent intent) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onActivityResult(i, i2, intent);
                } else {
                    WXLogUtils.w("onActivityResult can not find the " + str2 + " module");
                }
            }
        }
    }

    public static void onActivityResume(String str) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onActivityResume();
                } else {
                    WXLogUtils.w("onActivityResume can not find the " + str2 + " module");
                }
            }
        }
    }

    public static void onActivityStart(String str) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onActivityStart();
                } else {
                    WXLogUtils.w("onActivityStart can not find the " + str2 + " module");
                }
            }
        }
    }

    public static void onActivityStop(String str) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onActivityStop();
                } else {
                    WXLogUtils.w("onActivityStop can not find the " + str2 + " module");
                }
            }
        }
    }

    public static boolean onCreateOptionsMenu(String str, Menu menu) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map == null) {
            return false;
        }
        for (String str2 : map.keySet()) {
            WXModule wXModule = map.get(str2);
            if (wXModule != null) {
                wXModule.onCreateOptionsMenu(menu);
            } else {
                WXLogUtils.w("onActivityResult can not find the " + str2 + " module");
            }
        }
        return false;
    }

    public static void onRequestPermissionsResult(String str, int i, String[] strArr, int[] iArr) {
        Map<String, WXModule> map = sInstanceModuleMap.get(str);
        if (map != null) {
            for (String str2 : map.keySet()) {
                WXModule wXModule = map.get(str2);
                if (wXModule != null) {
                    wXModule.onRequestPermissionsResult(i, strArr, iArr);
                } else {
                    WXLogUtils.w("onActivityResult can not find the " + str2 + " module");
                }
            }
        }
    }

    static boolean registerJSModule(String str, ModuleFactory moduleFactory) {
        HashMap map = new HashMap();
        map.put(str, moduleFactory.getMethods());
        WXSDKManager.getInstance().registerModules(map);
        return true;
    }

    public static boolean registerModule(Map<String, RegisterCache.ModuleCache> map) {
        if (map.isEmpty()) {
            return true;
        }
        final Iterator<Map.Entry<String, RegisterCache.ModuleCache>> it = map.entrySet().iterator();
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.bridge.WXModuleManager.1
            @Override // java.lang.Runnable
            public void run() {
                HashMap map2 = new HashMap();
                while (it.hasNext()) {
                    RegisterCache.ModuleCache moduleCache = (RegisterCache.ModuleCache) ((Map.Entry) it.next()).getValue();
                    String str = moduleCache.name;
                    if (TextUtils.equals(str, WXDomModule.WXDOM)) {
                        WXLogUtils.e("Cannot registered module with name 'dom'.");
                    } else {
                        if (WXModuleManager.sModuleFactoryMap != null && WXModuleManager.sModuleFactoryMap.containsKey(str)) {
                            WXLogUtils.w("WXComponentRegistry Duplicate the Module name: " + str);
                        }
                        ModuleFactory moduleFactory = moduleCache.factory;
                        try {
                            WXModuleManager.registerNativeModule(str, moduleFactory);
                        } catch (WXException e) {
                            WXLogUtils.e("registerNativeModule" + e);
                        }
                        if (moduleCache.global) {
                            try {
                                WXModule wXModuleBuildInstance = moduleFactory.buildInstance();
                                wXModuleBuildInstance.setModuleName(str);
                                WXModuleManager.sGlobalModuleMap.put(str, wXModuleBuildInstance);
                            } catch (Exception e2) {
                                WXLogUtils.e(str + " class must have a default constructor without params. ", e2);
                            }
                        }
                        try {
                            WXModuleManager.sModuleFactoryMap.put(str, new ModuleFactoryImpl(moduleFactory));
                        } catch (Throwable unused) {
                        }
                        map2.put(str, moduleFactory.getMethods());
                    }
                }
                WXSDKManager.getInstance().registerModules(map2);
            }
        });
        return true;
    }

    static boolean registerNativeModule(String str, ModuleFactory moduleFactory) {
        if (moduleFactory == null) {
            return false;
        }
        try {
            if (sModuleFactoryMap.containsKey(str)) {
                return true;
            }
            sModuleFactoryMap.put(str, new ModuleFactoryImpl(moduleFactory));
            return true;
        } catch (ArrayStoreException e) {
            e.printStackTrace();
            WXLogUtils.e("[WXModuleManager] registerNativeModule Error moduleName:" + str + " Error:" + e.toString());
            return true;
        }
    }

    public static void registerWhenCreateInstance() {
        if (sModuleFactoryMap == null || sModuleFactoryMap.size() <= 0) {
            return;
        }
        for (Map.Entry<String, ModuleFactoryImpl> entry : sModuleFactoryMap.entrySet()) {
            try {
                if (!entry.getValue().hasRigster) {
                    registerJSModule(entry.getKey(), entry.getValue().mFactory);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public static void reload() {
        if (sModuleFactoryMap == null || sModuleFactoryMap.size() <= 0) {
            return;
        }
        for (Map.Entry<String, ModuleFactoryImpl> entry : sModuleFactoryMap.entrySet()) {
            try {
                registerJSModule(entry.getKey(), entry.getValue().mFactory);
            } catch (Throwable unused) {
            }
        }
    }

    public static void resetAllModuleState() {
        if (sModuleFactoryMap == null || sModuleFactoryMap.size() <= 0) {
            return;
        }
        Iterator<Map.Entry<String, ModuleFactoryImpl>> it = sModuleFactoryMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().hasRigster = false;
        }
    }

    public static void resetModuleState(String str, boolean z) {
        if (sModuleFactoryMap == null || sModuleFactoryMap.size() <= 0) {
            return;
        }
        for (Map.Entry<String, ModuleFactoryImpl> entry : sModuleFactoryMap.entrySet()) {
            try {
                if (entry.getKey() != null && entry.getKey().equals(str)) {
                    entry.getValue().hasRigster = z;
                }
            } catch (Throwable unused) {
            }
        }
    }

    public static boolean registerModule(final String str, final ModuleFactory moduleFactory, final boolean z) {
        if (str == null || moduleFactory == null) {
            return false;
        }
        if (TextUtils.equals(str, WXDomModule.WXDOM)) {
            WXLogUtils.e("Cannot registered module with name 'dom'.");
            return false;
        }
        if (RegisterCache.getInstance().cacheModule(str, moduleFactory, z)) {
            return true;
        }
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.bridge.WXModuleManager.2
            @Override // java.lang.Runnable
            public void run() {
                if (WXModuleManager.sModuleFactoryMap != null && WXModuleManager.sModuleFactoryMap.containsKey(str)) {
                    WXLogUtils.w("WXComponentRegistry Duplicate the Module name: " + str);
                }
                try {
                    WXModuleManager.registerNativeModule(str, moduleFactory);
                } catch (WXException e) {
                    WXLogUtils.e("registerNativeModule" + e);
                }
                if (z) {
                    try {
                        WXModule wXModuleBuildInstance = moduleFactory.buildInstance();
                        wXModuleBuildInstance.setModuleName(str);
                        WXModuleManager.sGlobalModuleMap.put(str, wXModuleBuildInstance);
                    } catch (Exception e2) {
                        WXLogUtils.e(str + " class must have a default constructor without params. ", e2);
                    }
                }
                WXModuleManager.registerJSModule(str, moduleFactory);
                try {
                    WXModuleManager.sModuleFactoryMap.put(str, new ModuleFactoryImpl(moduleFactory));
                } catch (Throwable unused) {
                }
            }
        });
        return true;
    }
}
