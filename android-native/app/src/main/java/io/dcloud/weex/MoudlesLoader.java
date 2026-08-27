package io.dcloud.weex;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.WXFileUtils;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.RuningAcitvityUtil;
import io.dcloud.feature.uniapp.UniAppHookProxy;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class MoudlesLoader {
    private static MoudlesLoader sLoader;
    private boolean isInitHooksClass = false;

    private void executeOnCreateProcess(JSONObject jSONObject, Application application, boolean z) {
        try {
            Class<?> cls = Class.forName(jSONObject.getString("hooksClass"));
            Object objNewInstance = cls.newInstance();
            if (!(objNewInstance instanceof AppHookProxy)) {
                Method method = cls.getMethod("onCreate", Application.class);
                if (method != null) {
                    method.setAccessible(true);
                    method.invoke(objNewInstance, application);
                    return;
                }
                return;
            }
            if (z) {
                ((AppHookProxy) objNewInstance).onCreate(application);
            } else if (objNewInstance instanceof UniAppHookProxy) {
                ((UniAppHookProxy) objNewInstance).onSubProcessCreate(application);
            }
        } catch (Throwable unused) {
        }
    }

    public static MoudlesLoader getInstance() {
        if (sLoader == null) {
            synchronized (MoudlesLoader.class) {
                if (sLoader == null) {
                    sLoader = new MoudlesLoader();
                }
            }
        }
        return sLoader;
    }

    private JSONObject getPluginsValue(Application application) {
        String strLoadAsset = WXFileUtils.loadAsset("dcloud_uniplugins.json", application);
        if (TextUtils.isEmpty(strLoadAsset)) {
            return null;
        }
        return JSONObject.parseObject(strLoadAsset);
    }

    private void initMoudle(JSONObject jSONObject, Application application) {
        JSONArray jSONArray;
        if (jSONObject == null || (jSONArray = jSONObject.getJSONArray("plugins")) == null || jSONArray.size() <= 0) {
            return;
        }
        for (int i = 0; i < jSONArray.size(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            if (jSONObject2 != null) {
                registerMoudle(jSONObject2.getString("name"), jSONObject2.getString("class"), jSONObject2.getString("type"));
            }
        }
    }

    private void registerMoudle(String str, String str2, String str3) {
        try {
            Class<?> cls = Class.forName(str2);
            if (!TextUtils.isEmpty(str3) && !str3.equalsIgnoreCase("module")) {
                if (str3.equalsIgnoreCase(WXBridgeManager.COMPONENT)) {
                    WXSDKEngine.registerComponent(str, (Class<? extends WXComponent>) cls);
                    return;
                }
                return;
            }
            WXSDKEngine.registerModule(str, cls);
        } catch (Throwable unused) {
        }
    }

    public void initHooksClass(Application application, Boolean bool) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        JSONArray jSONArray;
        if (this.isInitHooksClass) {
            return;
        }
        JSONObject pluginsValue = getPluginsValue(application);
        if (pluginsValue != null && (jSONArray = pluginsValue.getJSONArray("nativePlugins")) != null && jSONArray.size() > 0) {
            this.isInitHooksClass = true;
            for (int i = 0; i < jSONArray.size(); i++) {
                executeOnCreateProcess(jSONArray.getJSONObject(i), application, bool.booleanValue());
            }
        }
        AppRuntime.initUTSHooksClassArray(application);
    }

    public void onCreate(Application application) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        JSONArray jSONArray;
        JSONObject pluginsValue = getPluginsValue(application);
        initHooksClass(application, Boolean.valueOf(application.getPackageName().equals(RuningAcitvityUtil.getAppName(application))));
        if (pluginsValue == null || (jSONArray = pluginsValue.getJSONArray("nativePlugins")) == null || jSONArray.size() <= 0) {
            return;
        }
        for (int i = 0; i < jSONArray.size(); i++) {
            initMoudle(jSONArray.getJSONObject(i), application);
        }
    }

    public void onCreate(Application application, String str) {
        if (application == null || PdrUtil.isEmpty(str) || !DHFile.exists(str)) {
            return;
        }
        JSONObject object = JSON.parseObject(new String(DHFile.readAll(new File(str))));
        if (PdrUtil.isEmpty(object)) {
            return;
        }
        JSONArray jSONArray = object.getJSONArray("components");
        if (PdrUtil.isEmpty(jSONArray)) {
            return;
        }
        for (int i = 0; i < jSONArray.size(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject != null) {
                registerMoudle(jSONObject.getString("name"), jSONObject.getString("class"), WXBridgeManager.COMPONENT);
            }
        }
    }
}
