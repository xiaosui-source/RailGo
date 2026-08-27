package com.alibaba.android.bindingx.core;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.alibaba.android.bindingx.core.internal.BindingXOrientationHandler;
import com.alibaba.android.bindingx.core.internal.BindingXTimingHandler;
import com.alibaba.android.bindingx.core.internal.BindingXTouchHandler;
import com.alibaba.android.bindingx.core.internal.ExpressionPair;
import com.alibaba.android.bindingx.core.internal.Utils;
import com.taobao.weex.el.parse.Operators;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class BindingXCore {
    private static final Map<String, ObjectCreator<IEventHandler, Context, PlatformManager>> sGlobalEventHandlerCreatorMap = new HashMap(4);
    private Map<String, Map<String, IEventHandler>> mBindingCouples;
    private final Map<String, ObjectCreator<IEventHandler, Context, PlatformManager>> mInternalEventHandlerCreatorMap = new HashMap(8);
    private final PlatformManager mPlatformManager;

    public interface JavaScriptCallback {
        void callback(Object obj);
    }

    public interface ObjectCreator<Type, ParamA, ParamB> {
        Type createWith(ParamA parama, ParamB paramb, Object... objArr);
    }

    public BindingXCore(PlatformManager platformManager) {
        this.mPlatformManager = platformManager;
        registerEventHandler("pan", new ObjectCreator<IEventHandler, Context, PlatformManager>() { // from class: com.alibaba.android.bindingx.core.BindingXCore.1
            @Override // com.alibaba.android.bindingx.core.BindingXCore.ObjectCreator
            public IEventHandler createWith(Context context, PlatformManager platformManager2, Object... objArr) {
                return new BindingXTouchHandler(context, platformManager2, objArr);
            }
        });
        registerEventHandler("orientation", new ObjectCreator<IEventHandler, Context, PlatformManager>() { // from class: com.alibaba.android.bindingx.core.BindingXCore.2
            @Override // com.alibaba.android.bindingx.core.BindingXCore.ObjectCreator
            public IEventHandler createWith(Context context, PlatformManager platformManager2, Object... objArr) {
                return new BindingXOrientationHandler(context, platformManager2, objArr);
            }
        });
        registerEventHandler(BindingXEventType.TYPE_TIMING, new ObjectCreator<IEventHandler, Context, PlatformManager>() { // from class: com.alibaba.android.bindingx.core.BindingXCore.3
            @Override // com.alibaba.android.bindingx.core.BindingXCore.ObjectCreator
            public IEventHandler createWith(Context context, PlatformManager platformManager2, Object... objArr) {
                return new BindingXTimingHandler(context, platformManager2, objArr);
            }
        });
    }

    public String doBind(Context context, String str, Map<String, Object> map, JavaScriptCallback javaScriptCallback, Object... objArr) {
        Map<String, Object> map2;
        String stringValue = Utils.getStringValue(map, BindingXConstants.KEY_EVENT_TYPE);
        String stringValue2 = Utils.getStringValue(map, "instanceId");
        LogProxy.enableLogIfNeeded(map);
        Object obj = map.get("options");
        if (obj == null || !(obj instanceof Map)) {
            map2 = null;
        } else {
            try {
                map2 = Utils.toMap(new JSONObject((Map) obj));
            } catch (Exception e) {
                LogProxy.e("parse external config failed.\n", e);
            }
        }
        ExpressionPair expressionPair = Utils.getExpressionPair(map, BindingXConstants.KEY_EXIT_EXPRESSION);
        return doBind(Utils.getStringValue(map, BindingXConstants.KEY_ANCHOR), stringValue2, stringValue, map2, expressionPair, Utils.getRuntimeProps(map), Utils.getCustomInterceptors(map), javaScriptCallback, context, str, objArr);
    }

    public void doUnbind(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        doUnbind(Utils.getStringValue(map, BindingXConstants.KEY_TOKEN), Utils.getStringValue(map, BindingXConstants.KEY_EVENT_TYPE));
    }

    public void doUnbind(String str, String str2) {
        LogProxy.d("disable binding [" + str + "," + str2 + Operators.ARRAY_END_STR);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            LogProxy.d("disable binding failed(0x1) [" + str + "," + str2 + Operators.ARRAY_END_STR);
            return;
        }
        Map<String, Map<String, IEventHandler>> map = this.mBindingCouples;
        if (map == null || map.isEmpty()) {
            LogProxy.d("disable binding failed(0x2) [" + str + "," + str2 + Operators.ARRAY_END_STR);
            return;
        }
        Map<String, IEventHandler> map2 = this.mBindingCouples.get(str);
        if (map2 == null || map2.isEmpty()) {
            LogProxy.d("disable binding failed(0x3) [" + str + "," + str2 + Operators.ARRAY_END_STR);
            return;
        }
        IEventHandler iEventHandler = map2.get(str2);
        if (iEventHandler == null) {
            LogProxy.d("disable binding failed(0x4) [" + str + "," + str2 + Operators.ARRAY_END_STR);
            return;
        }
        if (iEventHandler.onDisable(str, str2)) {
            this.mBindingCouples.remove(str);
            LogProxy.d("disable binding success[" + str + "," + str2 + Operators.ARRAY_END_STR);
            return;
        }
        LogProxy.d("disabled failed(0x4) [" + str + "," + str2 + Operators.ARRAY_END_STR);
    }

    public void doRelease() {
        Map<String, Map<String, IEventHandler>> map = this.mBindingCouples;
        if (map != null) {
            try {
                for (Map<String, IEventHandler> map2 : map.values()) {
                    if (map2 != null && !map2.isEmpty()) {
                        for (IEventHandler iEventHandler : map2.values()) {
                            if (iEventHandler != null) {
                                iEventHandler.onDestroy();
                            }
                        }
                    }
                }
                this.mBindingCouples.clear();
                this.mBindingCouples = null;
            } catch (Exception e) {
                LogProxy.e("release failed", e);
            }
        }
    }

    public String doPrepare(Context context, String str, String str2, String str3, String str4, Map<String, Object> map) {
        IEventHandler iEventHandler;
        if (TextUtils.isEmpty(str4)) {
            LogProxy.e("[doPrepare] failed. can not found eventType");
            return null;
        }
        if (context == null) {
            LogProxy.e("[doPrepare] failed. context or wxInstance is null");
            return null;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = generateToken();
        }
        if (this.mBindingCouples == null) {
            this.mBindingCouples = new HashMap();
        }
        Map<String, IEventHandler> map2 = this.mBindingCouples.get(str2);
        if (map2 != null && (iEventHandler = map2.get(str4)) != null) {
            LogProxy.d("you have already enabled binding,[token:" + str2 + ",type:" + str4 + Operators.ARRAY_END_STR);
            iEventHandler.onStart(str2, str4);
            LogProxy.d("enableBinding success.[token:" + str2 + ",type:" + str4 + Operators.ARRAY_END_STR);
            return str2;
        }
        if (map2 == null) {
            map2 = new HashMap<>(4);
            this.mBindingCouples.put(str2, map2);
        }
        IEventHandler iEventHandlerCreateEventHandler = createEventHandler(context, str, str4);
        if (iEventHandlerCreateEventHandler != null) {
            iEventHandlerCreateEventHandler.setAnchorInstanceId(str3);
            iEventHandlerCreateEventHandler.setToken(str2);
            iEventHandlerCreateEventHandler.setGlobalConfig(map);
            if (iEventHandlerCreateEventHandler.onCreate(str2, str4)) {
                iEventHandlerCreateEventHandler.onStart(str2, str4);
                map2.put(str4, iEventHandlerCreateEventHandler);
                LogProxy.d("enableBinding success.[token:" + str2 + ",type:" + str4 + Operators.ARRAY_END_STR);
                return str2;
            }
            LogProxy.e("expression enabled failed. [token:" + str2 + ",type:" + str4 + Operators.ARRAY_END_STR);
            return null;
        }
        LogProxy.e("unknown eventType: " + str4);
        return null;
    }

    public String doBind(String str, String str2, String str3, Map<String, Object> map, ExpressionPair expressionPair, List<Map<String, Object>> list, Map<String, ExpressionPair> map2, JavaScriptCallback javaScriptCallback, Context context, String str4, Object... objArr) {
        IEventHandler iEventHandler;
        String str5;
        Map<String, Map<String, IEventHandler>> map3;
        Map<String, IEventHandler> map4;
        Map<String, IEventHandler> map5;
        IEventHandler iEventHandler2 = null;
        if (TextUtils.isEmpty(str3) || list == null) {
            LogProxy.e("doBind failed,illegal argument.[" + str3 + "," + list + Operators.ARRAY_END_STR);
            return null;
        }
        if (this.mBindingCouples != null && !TextUtils.isEmpty(str) && (map5 = this.mBindingCouples.get(str)) != null) {
            iEventHandler2 = map5.get(str3);
        }
        IEventHandler iEventHandler3 = iEventHandler2;
        if (iEventHandler3 == null) {
            LogProxy.d("binding not enabled,try auto enable it.[sourceRef:" + str + ",eventType:" + str3 + Operators.ARRAY_END_STR);
            String strDoPrepare = doPrepare(context, str4, str, str2, str3, map);
            if (!TextUtils.isEmpty(strDoPrepare) && (map3 = this.mBindingCouples) != null && (map4 = map3.get(strDoPrepare)) != null) {
                iEventHandler3 = map4.get(str3);
            }
            iEventHandler = iEventHandler3;
            str5 = strDoPrepare;
        } else {
            iEventHandler = iEventHandler3;
            str5 = str;
        }
        if (iEventHandler != null) {
            iEventHandler.onBindExpression(str3, map, expressionPair, list, javaScriptCallback);
            LogProxy.d("createBinding success.[exitExp:" + expressionPair + ",args:" + list + Operators.ARRAY_END_STR);
            iEventHandler.setInterceptors(map2);
            iEventHandler.setExtensionParams(objArr);
            return str5;
        }
        LogProxy.e("internal error.binding failed for ref:" + str + ",type:" + str3);
        return str5;
    }

    public void onActivityPause() {
        Map<String, Map<String, IEventHandler>> map = this.mBindingCouples;
        if (map == null) {
            return;
        }
        try {
            Iterator<Map<String, IEventHandler>> it = map.values().iterator();
            while (it.hasNext()) {
                Iterator<IEventHandler> it2 = it.next().values().iterator();
                while (it2.hasNext()) {
                    try {
                        it2.next().onActivityPause();
                    } catch (Exception e) {
                        LogProxy.e("execute activity pause failed.", e);
                    }
                }
            }
        } catch (Exception e2) {
            LogProxy.e("activity pause failed", e2);
        }
    }

    public void onActivityResume() {
        Map<String, Map<String, IEventHandler>> map = this.mBindingCouples;
        if (map == null) {
            return;
        }
        try {
            Iterator<Map<String, IEventHandler>> it = map.values().iterator();
            while (it.hasNext()) {
                Iterator<IEventHandler> it2 = it.next().values().iterator();
                while (it2.hasNext()) {
                    try {
                        it2.next().onActivityResume();
                    } catch (Exception e) {
                        LogProxy.e("execute activity pause failed.", e);
                    }
                }
            }
        } catch (Exception e2) {
            LogProxy.e("activity pause failed", e2);
        }
    }

    public void registerEventHandler(String str, ObjectCreator<IEventHandler, Context, PlatformManager> objectCreator) {
        if (TextUtils.isEmpty(str) || objectCreator == null) {
            return;
        }
        this.mInternalEventHandlerCreatorMap.put(str, objectCreator);
    }

    public static void registerGlobalEventHandler(String str, ObjectCreator<IEventHandler, Context, PlatformManager> objectCreator) {
        if (TextUtils.isEmpty(str) || objectCreator == null) {
            return;
        }
        sGlobalEventHandlerCreatorMap.put(str, objectCreator);
    }

    public static boolean unregisterGlobalEventHandler(String str) {
        return sGlobalEventHandlerCreatorMap.remove(str) != null;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private IEventHandler createEventHandler(Context context, String str, String str2) {
        if (!this.mInternalEventHandlerCreatorMap.isEmpty() && this.mPlatformManager != null) {
            ObjectCreator<IEventHandler, Context, PlatformManager> objectCreator = this.mInternalEventHandlerCreatorMap.get(str2);
            if (objectCreator == null) {
                objectCreator = sGlobalEventHandlerCreatorMap.get(str2);
            }
            if (objectCreator != null) {
                return objectCreator.createWith(context, this.mPlatformManager, str);
            }
        }
        return null;
    }
}
