package com.alibaba.android.bindingx.core.internal;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.android.bindingx.core.BindingXCore;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.alibaba.android.bindingx.core.internal.OrientationDetector;
import com.taobao.weex.el.parse.Operators;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class BindingXOrientationHandler extends AbstractEventHandler implements OrientationDetector.OnOrientationChangedListener {
    private boolean isStarted;
    private OrientationEvaluator mEvaluator3D;
    private OrientationEvaluator mEvaluatorX;
    private OrientationEvaluator mEvaluatorY;
    private double mLastAlpha;
    private double mLastBeta;
    private double mLastGamma;
    private OrientationDetector mOrientationDetector;
    private LinkedList<Double> mRecordsAlpha;
    private String mSceneType;
    private double mStartAlpha;
    private double mStartBeta;
    private double mStartGamma;
    private ValueHolder mValueHolder;
    private Vector3 mVectorX;
    private Vector3 mVectorY;

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onStart(String str, String str2) {
    }

    public BindingXOrientationHandler(Context context, PlatformManager platformManager, Object... objArr) {
        super(context, platformManager, objArr);
        this.isStarted = false;
        this.mRecordsAlpha = new LinkedList<>();
        this.mVectorX = new Vector3(0.0d, 0.0d, 1.0d);
        this.mVectorY = new Vector3(0.0d, 1.0d, 1.0d);
        this.mValueHolder = new ValueHolder(0.0d, 0.0d, 0.0d);
        if (context != null) {
            this.mOrientationDetector = OrientationDetector.getInstance(context);
        }
    }

    BindingXOrientationHandler(Context context, PlatformManager platformManager, OrientationDetector orientationDetector, Object... objArr) {
        super(context, platformManager, objArr);
        this.isStarted = false;
        this.mRecordsAlpha = new LinkedList<>();
        this.mVectorX = new Vector3(0.0d, 0.0d, 1.0d);
        this.mVectorY = new Vector3(0.0d, 1.0d, 1.0d);
        this.mValueHolder = new ValueHolder(0.0d, 0.0d, 0.0d);
        this.mOrientationDetector = orientationDetector;
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public boolean onCreate(String str, String str2) {
        OrientationDetector orientationDetector = this.mOrientationDetector;
        if (orientationDetector == null) {
            return false;
        }
        orientationDetector.addOrientationChangedListener(this);
        return this.mOrientationDetector.start(1);
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void onBindExpression(String str, Map<String, Object> map, ExpressionPair expressionPair, List<Map<String, Object>> list, BindingXCore.JavaScriptCallback javaScriptCallback) {
        String lowerCase;
        super.onBindExpression(str, map, expressionPair, list, javaScriptCallback);
        if (map != null) {
            String str2 = (String) map.get(BindingXConstants.KEY_SCENE_TYPE);
            if (TextUtils.isEmpty(str2)) {
                lowerCase = "2d";
            } else {
                lowerCase = str2.toLowerCase();
            }
        } else {
            lowerCase = null;
        }
        if (TextUtils.isEmpty(lowerCase) || (!"2d".equals(lowerCase) && !"3d".equals(lowerCase))) {
            lowerCase = "2d";
        }
        this.mSceneType = lowerCase;
        LogProxy.d("[ExpressionOrientationHandler] sceneType is " + lowerCase);
        if ("2d".equals(lowerCase)) {
            this.mEvaluatorX = new OrientationEvaluator(null, Double.valueOf(90.0d), null);
            this.mEvaluatorY = new OrientationEvaluator(Double.valueOf(0.0d), null, Double.valueOf(90.0d));
        } else if ("3d".equals(lowerCase)) {
            this.mEvaluator3D = new OrientationEvaluator(null, null, null);
        }
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public boolean onDisable(String str, String str2) {
        clearExpressions();
        if (this.mOrientationDetector == null) {
            return false;
        }
        fireEventByState("end", this.mLastAlpha, this.mLastBeta, this.mLastGamma, new Object[0]);
        return this.mOrientationDetector.removeOrientationChangedListener(this);
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void onDestroy() {
        super.onDestroy();
        OrientationDetector orientationDetector = this.mOrientationDetector;
        if (orientationDetector != null) {
            orientationDetector.removeOrientationChangedListener(this);
            this.mOrientationDetector.stop();
        }
        if (this.mExpressionHoldersMap != null) {
            this.mExpressionHoldersMap.clear();
            this.mExpressionHoldersMap = null;
        }
    }

    @Override // com.alibaba.android.bindingx.core.internal.OrientationDetector.OnOrientationChangedListener
    public void onOrientationChanged(double d, double d2, double d3) {
        boolean zCalculate3D;
        double dRound = Math.round(d);
        double dRound2 = Math.round(d2);
        double dRound3 = Math.round(d3);
        if (dRound == this.mLastAlpha && dRound2 == this.mLastBeta && dRound3 == this.mLastGamma) {
            return;
        }
        if (!this.isStarted) {
            this.isStarted = true;
            fireEventByState("start", dRound, dRound2, dRound3, new Object[0]);
            dRound = dRound;
            dRound2 = dRound2;
            dRound3 = dRound3;
            this.mStartAlpha = dRound;
            this.mStartBeta = dRound2;
            this.mStartGamma = dRound3;
        }
        if ("2d".equals(this.mSceneType)) {
            zCalculate3D = calculate2D(dRound, dRound2, dRound3);
        } else {
            zCalculate3D = "3d".equals(this.mSceneType) ? calculate3D(dRound, dRound2, dRound3) : false;
        }
        if (zCalculate3D) {
            double d4 = this.mValueHolder.x;
            double d5 = this.mValueHolder.y;
            double d6 = this.mValueHolder.z;
            this.mLastAlpha = dRound;
            this.mLastBeta = dRound2;
            this.mLastGamma = dRound3;
            try {
                if (LogProxy.sEnableLog) {
                    LogProxy.d(String.format(Locale.getDefault(), "[OrientationHandler] orientation changed. (alpha:%f,beta:%f,gamma:%f,x:%f,y:%f,z:%f)", Double.valueOf(dRound), Double.valueOf(dRound2), Double.valueOf(dRound3), Double.valueOf(d4), Double.valueOf(d5), Double.valueOf(d6)));
                }
                JSMath.applyOrientationValuesToScope(this.mScope, dRound, dRound2, dRound3, this.mStartAlpha, this.mStartBeta, this.mStartGamma, d4, d5, d6);
                if (evaluateExitExpression(this.mExitExpressionPair, this.mScope)) {
                    return;
                }
                consumeExpression(this.mExpressionHoldersMap, this.mScope, "orientation");
            } catch (Exception e) {
                LogProxy.e("runtime error", e);
            }
        }
    }

    private boolean calculate2D(double d, double d2, double d3) {
        if (this.mEvaluatorX != null && this.mEvaluatorY != null) {
            this.mRecordsAlpha.add(Double.valueOf(d));
            if (this.mRecordsAlpha.size() > 5) {
                this.mRecordsAlpha.removeFirst();
            }
            formatRecords(this.mRecordsAlpha, 360);
            LinkedList<Double> linkedList = this.mRecordsAlpha;
            double dDoubleValue = (linkedList.get(linkedList.size() - 1).doubleValue() - this.mStartAlpha) % 360.0d;
            Quaternion quaternionCalculate = this.mEvaluatorX.calculate(d, d2, d3, dDoubleValue);
            Quaternion quaternionCalculate2 = this.mEvaluatorY.calculate(d, d2, d3, dDoubleValue);
            this.mVectorX.set(0.0d, 0.0d, 1.0d);
            this.mVectorX.applyQuaternion(quaternionCalculate);
            this.mVectorY.set(0.0d, 1.0d, 1.0d);
            this.mVectorY.applyQuaternion(quaternionCalculate2);
            double degrees = Math.toDegrees(Math.acos(this.mVectorX.x)) - 90.0d;
            double degrees2 = Math.toDegrees(Math.acos(this.mVectorY.y)) - 90.0d;
            if (Double.isNaN(degrees) || Double.isNaN(degrees2) || Double.isInfinite(degrees) || Double.isInfinite(degrees2)) {
                return false;
            }
            double dRound = Math.round(degrees);
            double dRound2 = Math.round(degrees2);
            this.mValueHolder.x = dRound;
            this.mValueHolder.y = dRound2;
        }
        return true;
    }

    private boolean calculate3D(double d, double d2, double d3) {
        if (this.mEvaluator3D != null) {
            this.mRecordsAlpha.add(Double.valueOf(d));
            if (this.mRecordsAlpha.size() > 5) {
                this.mRecordsAlpha.removeFirst();
            }
            formatRecords(this.mRecordsAlpha, 360);
            LinkedList<Double> linkedList = this.mRecordsAlpha;
            Quaternion quaternionCalculate = this.mEvaluator3D.calculate(d, d2, d3, (linkedList.get(linkedList.size() - 1).doubleValue() - this.mStartAlpha) % 360.0d);
            if (Double.isNaN(quaternionCalculate.x) || Double.isNaN(quaternionCalculate.y) || Double.isNaN(quaternionCalculate.z) || Double.isInfinite(quaternionCalculate.x) || Double.isInfinite(quaternionCalculate.y) || Double.isInfinite(quaternionCalculate.z)) {
                return false;
            }
            this.mValueHolder.x = quaternionCalculate.x;
            this.mValueHolder.y = quaternionCalculate.y;
            this.mValueHolder.z = quaternionCalculate.z;
        }
        return true;
    }

    private void formatRecords(List<Double> list, int i) {
        int size = list.size();
        if (size > 1) {
            for (int i2 = 1; i2 < size; i2++) {
                int i3 = i2 - 1;
                if (list.get(i3) != null && list.get(i2) != null) {
                    if (list.get(i2).doubleValue() - list.get(i3).doubleValue() < (-i) / 2) {
                        double d = i;
                        list.set(i2, Double.valueOf(list.get(i2).doubleValue() + ((Math.floor(list.get(i3).doubleValue() / d) + 1.0d) * d)));
                    }
                    if (list.get(i2).doubleValue() - list.get(i3).doubleValue() > i / 2) {
                        list.set(i2, Double.valueOf(list.get(i2).doubleValue() - i));
                    }
                }
            }
        }
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler
    protected void onExit(Map<String, Object> map) {
        fireEventByState(BindingXConstants.STATE_EXIT, ((Double) map.get("alpha")).doubleValue(), ((Double) map.get("beta")).doubleValue(), ((Double) map.get("gamma")).doubleValue(), new Object[0]);
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler
    protected void onUserIntercept(String str, Map<String, Object> map) {
        fireEventByState(BindingXConstants.STATE_INTERCEPTOR, ((Double) map.get("alpha")).doubleValue(), ((Double) map.get("beta")).doubleValue(), ((Double) map.get("gamma")).doubleValue(), Collections.singletonMap(BindingXConstants.STATE_INTERCEPTOR, str));
    }

    private void fireEventByState(String str, double d, double d2, double d3, Object... objArr) {
        if (this.mCallback != null) {
            HashMap map = new HashMap();
            map.put("state", str);
            map.put("alpha", Double.valueOf(d));
            map.put("beta", Double.valueOf(d2));
            map.put("gamma", Double.valueOf(d3));
            map.put(BindingXConstants.KEY_TOKEN, this.mToken);
            if (objArr != null && objArr.length > 0) {
                Object obj = objArr[0];
                if (obj instanceof Map) {
                    map.putAll((Map) obj);
                }
            }
            this.mCallback.callback(map);
            LogProxy.d(">>>>>>>>>>>fire event:(" + str + "," + d + "," + d2 + "," + d3 + Operators.BRACKET_END_STR);
        }
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onActivityPause() {
        OrientationDetector orientationDetector = this.mOrientationDetector;
        if (orientationDetector != null) {
            orientationDetector.stop();
        }
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onActivityResume() {
        OrientationDetector orientationDetector = this.mOrientationDetector;
        if (orientationDetector != null) {
            orientationDetector.start(1);
        }
    }

    static class ValueHolder {
        double x;
        double y;
        double z;

        ValueHolder() {
        }

        ValueHolder(double d, double d2, double d3) {
            this.x = d;
            this.y = d2;
            this.z = d3;
        }
    }
}
