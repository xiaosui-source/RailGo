package com.taobao.weex.bridge;

import android.text.TextUtils;
import com.taobao.weex.common.WXJSService;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.StringUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXServiceManager {
    private static volatile ConcurrentHashMap<String, WXJSService> sInstanceJSServiceMap = new ConcurrentHashMap<>();

    public static void execAllCacheJsService() {
        Iterator<String> it = sInstanceJSServiceMap.keySet().iterator();
        while (it.hasNext()) {
            WXJSService wXJSService = sInstanceJSServiceMap.get(it.next());
            registerService(wXJSService.getName(), wXJSService.getScript(), wXJSService.getOptions());
        }
    }

    public static WXJSService getService(String str) {
        if (sInstanceJSServiceMap != null) {
            return sInstanceJSServiceMap.get(str);
        }
        return null;
    }

    public static boolean registerService(String str, String str2, Map<String, Object> map) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        String str3 = "serviceName: \"" + str + JSUtil.QUOTE;
        for (String str4 : map.keySet()) {
            Object obj = map.get(str4);
            str3 = obj instanceof String ? str3 + ", '" + str4 + "': '" + obj + "'" : str3 + ", '" + str4 + "': " + obj;
        }
        String str5 = StringUtil.format(";(function(service, options){ ;%s; })({ %s }, { %s });", str2, "register: global.registerService, unregister: global.unregisterService", str3);
        WXJSService wXJSService = new WXJSService();
        wXJSService.setName(str);
        wXJSService.setScript(str2);
        wXJSService.setOptions(map);
        sInstanceJSServiceMap.put(str, wXJSService);
        WXBridgeManager.getInstance().execJSService(str5);
        return true;
    }

    public static void reload() {
        if (sInstanceJSServiceMap == null || sInstanceJSServiceMap.size() <= 0) {
            return;
        }
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.bridge.WXServiceManager.1
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = WXServiceManager.sInstanceJSServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    WXJSService wXJSService = (WXJSService) ((Map.Entry) it.next()).getValue();
                    WXServiceManager.registerService(wXJSService.getName(), wXJSService.getScript(), wXJSService.getOptions());
                }
            }
        });
    }

    public static boolean unRegisterService(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        sInstanceJSServiceMap.remove(str);
        WXBridgeManager.getInstance().execJSService(StringUtil.format("global.unregisterService( \"%s\" );", str));
        return true;
    }
}
