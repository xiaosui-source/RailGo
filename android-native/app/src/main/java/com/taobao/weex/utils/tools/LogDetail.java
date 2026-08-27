package com.taobao.weex.utils.tools;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.Constants;
import java.util.Locale;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class LogDetail {
    public static final String KeyWords_Render = "Weex_Render";
    public static final String KeyWrod_Init = "Weex_Init";
    public String keyWords = KeyWords_Render;

    @JSONField(name = Constants.Value.TIME)
    public Time time = new Time();

    @JSONField(name = "Info")
    public Info info = new Info();

    public void keyWorkds(String str) {
        this.keyWords = str;
    }

    public void name(String str) {
        this.time.constructor();
        this.info.taskName = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if (lowerCase.contains("module") || lowerCase.contains(WXBridgeManager.COMPONENT) || lowerCase.contains("framework")) {
            this.keyWords = KeyWrod_Init;
        }
    }

    public void println() {
        if (WXEnvironment.isPerf()) {
            Log.e(TimeCalculator.TIMELINE_TAG, " timeline " + this.keyWords + " java LogDetail: " + toString());
        }
    }

    public void taskEnd() {
        this.time.taskEnd();
        println();
    }

    public void taskStart() {
        this.time.taskStart();
    }

    public String toString() {
        return "taskName : " + this.info.taskName + " - LogDetail : {time = '" + this.time + "', info = '" + this.info + "'}";
    }
}
