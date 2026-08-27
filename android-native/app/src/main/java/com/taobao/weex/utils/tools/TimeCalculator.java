package com.taobao.weex.utils.tools;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TimeCalculator {
    public static final String PLATFORM_ANDROID = "Android";
    public static final String TIMELINE_TAG = "timeline";
    private String test = "{\"time\":{\"execTime\":0,\"constructor\":0,\"destructor\":0,\"taskStart\":0,\"taskEnd\":0,\"waitTime\":0},\"Info\":{\"taskInfo\":{\"relateTaskId\":0,\"args\":\"stringReplace\"},\"taskName\":\"stringReplace\",\"instanceId\":\"1\",\"platform\":\"stringReplace\",\"taskId\":0}}";
    private CopyOnWriteArrayList<LogDetail> logRecorder = new CopyOnWriteArrayList<>();

    public TimeCalculator(WXSDKInstance wXSDKInstance) {
    }

    public void addLog(LogDetail logDetail) {
        if (WXEnvironment.isPerf()) {
            this.logRecorder.add(logDetail);
        }
    }

    public LogDetail createLogDetail(String str) {
        LogDetail logDetail = new LogDetail();
        logDetail.name(str);
        addLog(logDetail);
        return logDetail;
    }

    public void println() {
        if (WXEnvironment.isPerf()) {
            Iterator<LogDetail> it = this.logRecorder.iterator();
            while (it.hasNext()) {
                Log.e(TIMELINE_TAG, JSON.toJSONString(it.next()));
            }
        }
    }
}
