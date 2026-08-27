package com.alibaba.android.bindingx.core.internal;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.android.bindingx.core.BindingXCore;
import com.alibaba.android.bindingx.core.BindingXJSFunctionRegister;
import com.alibaba.android.bindingx.core.BindingXPropertyInterceptor;
import com.alibaba.android.bindingx.core.IEventHandler;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.taobao.weex.el.parse.Operators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class AbstractEventHandler implements IEventHandler {
    protected String mAnchorInstanceId;
    protected BindingXCore.JavaScriptCallback mCallback;
    protected Context mContext;
    protected ExpressionPair mExitExpressionPair;
    protected volatile Map<String, List<ExpressionHolder>> mExpressionHoldersMap;
    protected Object[] mExtensionParams;
    protected String mInstanceId;
    protected volatile Map<String, ExpressionPair> mInterceptorsMap;
    protected PlatformManager mPlatformManager;
    protected String mToken;
    protected final Map<String, Object> mScope = new HashMap();
    private Cache<String, Expression> mCachedExpressionMap = new Cache<>(16);

    protected abstract void onExit(Map<String, Object> map);

    protected abstract void onUserIntercept(String str, Map<String, Object> map);

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void setGlobalConfig(Map<String, Object> map) {
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public AbstractEventHandler(android.content.Context r3, com.alibaba.android.bindingx.core.PlatformManager r4, java.lang.Object... r5) {
        /*
            r2 = this;
            r2.<init>()
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r2.mScope = r0
            com.alibaba.android.bindingx.core.internal.AbstractEventHandler$Cache r0 = new com.alibaba.android.bindingx.core.internal.AbstractEventHandler$Cache
            r1 = 16
            r0.<init>(r1)
            r2.mCachedExpressionMap = r0
            r2.mContext = r3
            r2.mPlatformManager = r4
            if (r5 == 0) goto L26
            int r3 = r5.length
            if (r3 <= 0) goto L26
            r3 = 0
            r3 = r5[r3]
            boolean r4 = r3 instanceof java.lang.String
            if (r4 == 0) goto L26
            java.lang.String r3 = (java.lang.String) r3
            goto L27
        L26:
            r3 = 0
        L27:
            r2.mInstanceId = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.android.bindingx.core.internal.AbstractEventHandler.<init>(android.content.Context, com.alibaba.android.bindingx.core.PlatformManager, java.lang.Object[]):void");
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void setAnchorInstanceId(String str) {
        this.mAnchorInstanceId = str;
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onBindExpression(String str, Map<String, Object> map, ExpressionPair expressionPair, List<Map<String, Object>> list, BindingXCore.JavaScriptCallback javaScriptCallback) {
        clearExpressions();
        transformArgs(str, list);
        this.mCallback = javaScriptCallback;
        this.mExitExpressionPair = expressionPair;
        if (!this.mScope.isEmpty()) {
            this.mScope.clear();
        }
        applyFunctionsToScope();
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onDestroy() {
        this.mCachedExpressionMap.clear();
        BindingXPropertyInterceptor.getInstance().clearCallbacks();
    }

    private void applyFunctionsToScope() {
        JSMath.applyToScope(this.mScope);
        TimingFunctions.applyToScope(this.mScope);
        Map<String, JSFunctionInterface> jSFunctions = BindingXJSFunctionRegister.getInstance().getJSFunctions();
        if (jSFunctions == null || jSFunctions.isEmpty()) {
            return;
        }
        this.mScope.putAll(jSFunctions);
    }

    private void transformArgs(String str, List<Map<String, Object>> list) {
        Map<String, Object> map;
        String str2;
        if (this.mExpressionHoldersMap == null) {
            this.mExpressionHoldersMap = new HashMap();
        }
        for (Map<String, Object> map2 : list) {
            String stringValue = Utils.getStringValue(map2, BindingXConstants.KEY_ELEMENT);
            String stringValue2 = Utils.getStringValue(map2, "instanceId");
            String stringValue3 = Utils.getStringValue(map2, "property");
            ExpressionPair expressionPair = Utils.getExpressionPair(map2, BindingXConstants.KEY_EXPRESSION);
            Object obj = map2.get(BindingXConstants.KEY_CONFIG);
            if (obj == null || !(obj instanceof Map)) {
                map = null;
            } else {
                try {
                    map = Utils.toMap(new JSONObject((Map) obj));
                } catch (Exception e) {
                    LogProxy.e("parse config failed", e);
                }
            }
            Map<String, Object> map3 = map;
            if (TextUtils.isEmpty(stringValue) || TextUtils.isEmpty(stringValue3) || expressionPair == null) {
                str2 = str;
                LogProxy.e("skip illegal binding args[" + stringValue + "," + stringValue3 + "," + expressionPair + Operators.ARRAY_END_STR);
            } else {
                str2 = str;
                ExpressionHolder expressionHolder = new ExpressionHolder(stringValue, stringValue2, expressionPair, stringValue3, str2, map3);
                List<ExpressionHolder> list2 = this.mExpressionHoldersMap.get(stringValue);
                if (list2 == null) {
                    ArrayList arrayList = new ArrayList(4);
                    this.mExpressionHoldersMap.put(stringValue, arrayList);
                    arrayList.add(expressionHolder);
                } else if (!list2.contains(expressionHolder)) {
                    list2.add(expressionHolder);
                }
            }
            str = str2;
        }
    }

    protected boolean evaluateExitExpression(ExpressionPair expressionPair, Map<String, Object> map) {
        boolean zBooleanValue;
        if (ExpressionPair.isValid(expressionPair)) {
            try {
                zBooleanValue = ((Boolean) new Expression(expressionPair.transformed).execute(map)).booleanValue();
            } catch (Exception e) {
                LogProxy.e("evaluateExitExpression failed. ", e);
            }
        } else {
            zBooleanValue = false;
        }
        if (zBooleanValue) {
            clearExpressions();
            try {
                onExit(map);
            } catch (Exception e2) {
                LogProxy.e("execute exit expression failed: ", e2);
            }
            LogProxy.d("exit = true,consume finished");
        }
        return zBooleanValue;
    }

    @Override // com.alibaba.android.bindingx.core.IEventInterceptor
    public void setInterceptors(Map<String, ExpressionPair> map) {
        this.mInterceptorsMap = map;
    }

    @Override // com.alibaba.android.bindingx.core.IEventInterceptor
    public void performInterceptIfNeeded(String str, ExpressionPair expressionPair, Map<String, Object> map) {
        boolean zBooleanValue;
        if (ExpressionPair.isValid(expressionPair)) {
            try {
                zBooleanValue = ((Boolean) new Expression(expressionPair.transformed).execute(map)).booleanValue();
            } catch (Exception e) {
                LogProxy.e("evaluate interceptor [" + str + "] expression failed. ", e);
                zBooleanValue = false;
            }
            if (zBooleanValue) {
                onUserIntercept(str, map);
            }
        }
    }

    private void tryInterceptAllIfNeeded(Map<String, Object> map) {
        if (this.mInterceptorsMap == null || this.mInterceptorsMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ExpressionPair> entry : this.mInterceptorsMap.entrySet()) {
            String key = entry.getKey();
            ExpressionPair value = entry.getValue();
            if (!TextUtils.isEmpty(key) && value != null) {
                performInterceptIfNeeded(key, value, map);
            }
        }
    }

    protected void consumeExpression(Map<String, List<ExpressionHolder>> map, Map<String, Object> map2, String str) throws JSONException, IllegalArgumentException {
        Map<String, Object> map3 = map2;
        tryInterceptAllIfNeeded(map3);
        if (map == null) {
            LogProxy.e("expression args is null");
            return;
        }
        if (map.isEmpty()) {
            LogProxy.e("no expression need consumed");
            return;
        }
        if (LogProxy.sEnableLog) {
            LogProxy.d(String.format(Locale.getDefault(), "consume expression with %d tasks. event type is %s", Integer.valueOf(map.size()), str));
        }
        LinkedList linkedList = new LinkedList();
        Iterator<List<ExpressionHolder>> it = map.values().iterator();
        while (it.hasNext()) {
            for (ExpressionHolder expressionHolder : it.next()) {
                if (str.equals(expressionHolder.eventType)) {
                    linkedList.clear();
                    Object[] objArr = this.mExtensionParams;
                    if (objArr != null && objArr.length > 0) {
                        Collections.addAll(linkedList, objArr);
                    }
                    String str2 = TextUtils.isEmpty(expressionHolder.targetInstanceId) ? this.mInstanceId : expressionHolder.targetInstanceId;
                    if (!TextUtils.isEmpty(str2)) {
                        linkedList.add(str2);
                    }
                    ExpressionPair expressionPair = expressionHolder.expressionPair;
                    if (ExpressionPair.isValid(expressionPair)) {
                        Expression expression = this.mCachedExpressionMap.get(expressionPair.transformed);
                        if (expression == null) {
                            expression = new Expression(expressionPair.transformed);
                            this.mCachedExpressionMap.put(expressionPair.transformed, expression);
                        }
                        Object objExecute = expression.execute(map3);
                        if (objExecute == null) {
                            LogProxy.e("failed to execute expression,expression result is null");
                        } else if (((objExecute instanceof Double) && Double.isNaN(((Double) objExecute).doubleValue())) || ((objExecute instanceof Float) && Float.isNaN(((Float) objExecute).floatValue()))) {
                            LogProxy.e("failed to execute expression,expression result is NaN");
                        } else {
                            View viewFindViewBy = this.mPlatformManager.getViewFinder().findViewBy(expressionHolder.targetRef, linkedList.toArray());
                            BindingXPropertyInterceptor.getInstance().performIntercept(viewFindViewBy, expressionHolder.prop, objExecute, this.mPlatformManager.getResolutionTranslator(), expressionHolder.config, expressionHolder.targetRef, str2);
                            if (viewFindViewBy == null) {
                                LogProxy.e("failed to execute expression,target view not found.[ref:" + expressionHolder.targetRef + Operators.ARRAY_END_STR);
                            } else {
                                this.mPlatformManager.getViewUpdater().synchronouslyUpdateViewOnUIThread(viewFindViewBy, expressionHolder.prop, objExecute, this.mPlatformManager.getResolutionTranslator(), expressionHolder.config, expressionHolder.targetRef, str2);
                            }
                            map3 = map2;
                        }
                    }
                } else {
                    LogProxy.d("skip expression with wrong event type.[expected:" + str + ",found:" + expressionHolder.eventType + Operators.ARRAY_END_STR);
                }
            }
            map3 = map2;
        }
    }

    protected void clearExpressions() {
        LogProxy.d("all expression are cleared");
        if (this.mExpressionHoldersMap != null) {
            this.mExpressionHoldersMap.clear();
            this.mExpressionHoldersMap = null;
        }
        this.mExitExpressionPair = null;
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void setToken(String str) {
        this.mToken = str;
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void setExtensionParams(Object[] objArr) {
        this.mExtensionParams = objArr;
    }

    static class Cache<K, V> extends LinkedHashMap<K, V> {
        private int maxSize;

        Cache(int i) {
            super(4, 0.75f, true);
            this.maxSize = Math.max(i, 4);
        }

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry entry) {
            return size() > this.maxSize;
        }
    }
}
