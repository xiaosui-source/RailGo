package com.taobao.weex.ui.config;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.ModuleFactory;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXLogUtils;
import java.io.IOException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class AutoScanConfigRegister {
    public static final String TAG = "WeexScanConfigRegister";
    private static long scanDelay;

    public static void doScanConfig() {
        if (scanDelay > 0) {
            WXSDKManager.getInstance().getWXRenderManager().postOnUiThread(new Runnable() { // from class: com.taobao.weex.ui.config.AutoScanConfigRegister.1
                @Override // java.lang.Runnable
                public void run() {
                    AutoScanConfigRegister.doScanConfigAsync();
                }
            }, scanDelay);
        } else {
            doScanConfigAsync();
        }
    }

    public static void doScanConfigAsync() {
        Thread thread = new Thread(new Runnable() { // from class: com.taobao.weex.ui.config.AutoScanConfigRegister.2
            @Override // java.lang.Runnable
            public void run() throws IOException {
                AutoScanConfigRegister.doScanConfigSync();
            }
        });
        thread.setName("AutoScanConfigRegister");
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doScanConfigSync() throws IOException {
        Application application = WXEnvironment.sApplication;
        if (application == null) {
            return;
        }
        try {
            String[] list = new String[0];
            try {
                list = application.getApplicationContext().getAssets().list("");
            } catch (IOException e) {
                WXLogUtils.e("WeexScanConfigRegister", e);
            }
            if (list != null && list.length != 0) {
                for (String str : list) {
                    if (!TextUtils.isEmpty(str) && str.startsWith("weex_config_") && str.endsWith(".json")) {
                        if (TextUtils.isEmpty(str)) {
                            return;
                        }
                        try {
                            String strLoadAsset = WXFileUtils.loadAsset(str, WXEnvironment.getApplication());
                            if (!TextUtils.isEmpty(strLoadAsset)) {
                                if (WXEnvironment.isApkDebugable()) {
                                    WXLogUtils.d("WeexScanConfigRegister", str + " find config " + strLoadAsset);
                                }
                                JSONObject object = JSON.parseObject(strLoadAsset);
                                if (object.containsKey("modules")) {
                                    JSONArray jSONArray = object.getJSONArray("modules");
                                    for (int i = 0; i < jSONArray.size(); i++) {
                                        ConfigModuleFactory configModuleFactoryFromConfig = ConfigModuleFactory.fromConfig(jSONArray.getJSONObject(i));
                                        if (configModuleFactoryFromConfig != null) {
                                            WXSDKEngine.registerModule(configModuleFactoryFromConfig.getName(), (ModuleFactory) configModuleFactoryFromConfig, false);
                                        }
                                    }
                                }
                                if (object.containsKey("components")) {
                                    JSONArray jSONArray2 = object.getJSONArray("components");
                                    for (int i2 = 0; i2 < jSONArray2.size(); i2++) {
                                        ConfigComponentHolder configComponentHolderFromConfig = ConfigComponentHolder.fromConfig(jSONArray2.getJSONObject(i2));
                                        if (configComponentHolderFromConfig == null) {
                                            return;
                                        }
                                        WXSDKEngine.registerComponent(configComponentHolderFromConfig, configComponentHolderFromConfig.isAppendTree(), configComponentHolderFromConfig.getType());
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } catch (Throwable th) {
                            WXLogUtils.e("WeexScanConfigRegister", th);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            WXLogUtils.e("WeexScanConfigRegister", e2);
        }
    }

    public static void setScanDelay(long j) {
        scanDelay = j;
    }
}
