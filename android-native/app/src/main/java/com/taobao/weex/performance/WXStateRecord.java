package com.taobao.weex.performance;

import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXConfigAdapter;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.WXUtils;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXStateRecord {
    private long jsThreadTime;
    private Runnable jsThreadWatchTask;
    private RecordList<Info> mActionHistory;
    private RecordList<Info> mExceptionHistory;
    private RecordList<Info> mIPCExceptionHistory;
    private RecordList<Info> mJsThradWatchHistory;
    private RecordList<Info> mJscCrashHistory;
    private RecordList<Info> mJscReloadHistory;
    private RecordList<Info> mJsfmInitHistory;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class Info implements Comparable<Info> {
        private String instanceId;
        private String msg;
        private long time;

        public Info(long j, String str, String str2) {
            this.time = j;
            this.instanceId = str;
            this.msg = str2;
        }

        public String toString() {
            return Operators.ARRAY_START_STR + this.instanceId + Operators.ARRAY_SEPRATOR + this.time + Operators.ARRAY_SEPRATOR + this.msg + "]->";
        }

        @Override // java.lang.Comparable
        public int compareTo(Info info) {
            long j = this.time;
            long j2 = info.time;
            if (j == j2) {
                return 0;
            }
            return j > j2 ? 1 : -1;
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class RecordList<E> extends ConcurrentLinkedQueue<E> {
        private int maxSize;

        public RecordList(int i) {
            this.maxSize = i;
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class SingleTonHolder {
        private static final WXStateRecord S_INSTANCE = new WXStateRecord();

        private SingleTonHolder() {
        }
    }

    public static WXStateRecord getInstance() {
        return SingleTonHolder.S_INSTANCE;
    }

    private void recordCommon(RecordList<Info> recordList, Info info) {
        if (recordList == null || info == null) {
            return;
        }
        try {
            recordList.add(info);
            if (recordList.isEmpty() || recordList.size() <= ((RecordList) recordList).maxSize) {
                return;
            }
            recordList.poll();
        } catch (Throwable th) {
            th.getStackTrace();
        }
    }

    public Map<String, String> getStateInfo() {
        HashMap map = new HashMap(5);
        map.put("reInitCount", String.valueOf(WXBridgeManager.reInitCount));
        ArrayList arrayList = new ArrayList(this.mExceptionHistory.size() + this.mActionHistory.size() + this.mJsfmInitHistory.size() + this.mJscCrashHistory.size() + this.mJscReloadHistory.size() + this.mJsThradWatchHistory.size());
        arrayList.addAll(this.mExceptionHistory);
        arrayList.addAll(this.mActionHistory);
        arrayList.addAll(this.mJsfmInitHistory);
        arrayList.addAll(this.mJscCrashHistory);
        arrayList.addAll(this.mJscReloadHistory);
        arrayList.addAll(this.mJsThradWatchHistory);
        arrayList.addAll(this.mIPCExceptionHistory);
        Collections.sort(arrayList);
        map.put("stateInfoList", arrayList.toString());
        IWXConfigAdapter wxConfigAdapter = WXSDKManager.getInstance().getWxConfigAdapter();
        if (wxConfigAdapter != null && AbsoluteConst.TRUE.equalsIgnoreCase(wxConfigAdapter.getConfig("wxapm", "dumpIpcPageInfo", AbsoluteConst.TRUE))) {
            map.put("pageQueueInfo", WXBridgeManager.getInstance().dumpIpcPageInfo());
        }
        return map;
    }

    public void onJSCCrash(String str) {
        recordCommon(this.mJscCrashHistory, new Info(WXUtils.getFixUnixTime(), str, "onJSCCrash"));
    }

    public void onJSEngineReload(String str) {
        recordCommon(this.mJscReloadHistory, new Info(WXUtils.getFixUnixTime(), str, "onJSEngineReload"));
    }

    public void onJSFMInit() {
        recoreJsfmInitHistory("setJsfmVersion");
    }

    public void recordAction(String str, String str2) {
        recordCommon(this.mActionHistory, new Info(WXUtils.getFixUnixTime(), str, str2));
    }

    public void recordException(String str, String str2) {
        if (str2.length() > 200) {
            str2 = str2.substring(0, 200);
        }
        recordCommon(this.mExceptionHistory, new Info(WXUtils.getFixUnixTime(), str, str2));
    }

    public void recordIPCException(String str, String str2) {
        if (str2.length() > 200) {
            str2 = str2.substring(0, 200);
        }
        recordCommon(this.mIPCExceptionHistory, new Info(WXUtils.getFixUnixTime(), str, str2));
    }

    public void recordJsThreadWatch(String str) {
        recordCommon(this.mJsThradWatchHistory, new Info(WXUtils.getFixUnixTime(), "jsWatch", str));
    }

    public void recoreJsfmInitHistory(String str) {
        recordCommon(this.mJsfmInitHistory, new Info(WXUtils.getFixUnixTime(), "JSFM", str));
    }

    public void startJSThreadWatchDog() {
        WXBridgeManager.getInstance().post(this.jsThreadWatchTask);
    }

    private WXStateRecord() {
        this.jsThreadTime = -1L;
        this.jsThreadWatchTask = new Runnable() { // from class: com.taobao.weex.performance.WXStateRecord.1
            @Override // java.lang.Runnable
            public void run() {
                if (WXStateRecord.this.jsThreadTime == -1) {
                    WXStateRecord.this.jsThreadTime = WXUtils.getFixUnixTime();
                }
                long fixUnixTime = WXUtils.getFixUnixTime() - WXStateRecord.this.jsThreadTime;
                WXStateRecord.this.recordJsThreadWatch("diff:" + fixUnixTime);
                WXStateRecord.this.jsThreadTime = WXUtils.getFixUnixTime();
                WXBridgeManager.getInstance().postDelay(WXStateRecord.this.jsThreadWatchTask, 500L);
            }
        };
        this.mExceptionHistory = new RecordList<>(10);
        this.mActionHistory = new RecordList<>(20);
        this.mJsfmInitHistory = new RecordList<>(10);
        this.mJscCrashHistory = new RecordList<>(10);
        this.mJscReloadHistory = new RecordList<>(10);
        this.mJsThradWatchHistory = new RecordList<>(20);
        this.mIPCExceptionHistory = new RecordList<>(20);
    }
}
