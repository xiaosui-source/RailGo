package com.taobao.weex.common;

import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Deprecated
/* loaded from: classes.dex */
public class WXPerformance {
    public static final String CACHE_TYPE = "cacheType";
    public static final String DEFAULT = "default";
    public long JSLibInitTime;
    public double JSLibSize;
    public double JSTemplateSize;
    public long actualNetworkTime;
    public long avgFPS;
    public long backImproveMemory;
    public long callBridgeTime;
    public long callCreateFinishTime;
    public long callCreateInstanceTime;
    public int cellExceedNum;

    @Deprecated
    public long communicateTime;
    public long componentCount;
    public long componentCreateTime;
    public String connectionType;
    public long cssLayoutTime;
    public String errCode;

    @Deprecated
    public String errMsg;
    public long firstScreenJSFExecuteTime;
    public int fsCallEventTotalNum;
    public int fsCallJsTotalNum;
    public long fsCallJsTotalTime;
    public int fsCallNativeTotalNum;
    public long fsCallNativeTotalTime;
    public int fsComponentCount;
    public int fsComponentCreateTime;
    public long fsRenderTime;
    public int fsRequestNum;
    public long interactionRealUnixTime;
    public long interactionTime;
    public int interactionViewAddCount;
    public int interactionViewAddLimitCount;
    public int localInteractionViewAddCount;
    public double localReadTime;
    private String mInstanceId;
    public int maxDeepVDomLayer;
    public int maxDeepViewLayer;
    public long networkTime;
    public long newFsRenderTime;
    public long packageSpendTime;
    public long parseJsonTime;
    public long pureNetworkTime;
    public String renderFailedDetail;
    public long renderTimeOrigin;
    public long renderUnixTimeOrigin;
    public long screenRenderTime;
    public long syncTaskTime;
    public long templateLoadTime;

    @Deprecated
    public String templateUrl;
    public int timerInvokeCount;
    public double totalTime;
    public double wrongImgSizeCount;
    public String zCacheInfo;
    public static final int VIEW_LIMIT_HEIGHT = WXViewUtils.getScreenHeight() / 2;
    public static final int VIEW_LIMIT_WIDTH = WXViewUtils.getScreenWidth() / 2;
    public static boolean TRACE_DATA = WXEnvironment.isApkDebugable();

    @Deprecated
    public String bizType = "weex";
    public String cacheType = "none";
    public double fluency = 100.0d;
    public String pageName = "default";
    public int useScroller = 0;
    public String JSLibVersion = WXEnvironment.JS_LIB_SDK_VERSION;
    public String WXSDKVersion = WXEnvironment.WXSDK_VERSION;
    public String args = "";
    public String requestType = "other";

    @Deprecated
    public String[] wxDims = new String[5];

    @Deprecated
    public long[] measureTimes = new long[5];
    public int mActionAddElementCount = 0;
    public int mActionAddElementSumTime = 0;
    private StringBuilder mErrMsgBuilder = new StringBuilder();

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum Dimension {
        JSLibVersion,
        WXSDKVersion,
        pageName,
        spm,
        scheme,
        cacheType,
        requestType,
        networkType,
        connectionType,
        zcacheInfo,
        wxContainerName,
        wxInstanceType,
        wxParentPage,
        wxdim1,
        wxdim2,
        wxdim3,
        wxdim4,
        wxdim5,
        bizType,
        templateUrl,
        useScroller
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum Measure {
        JSLibSize(0.0d, Double.MAX_VALUE),
        JSLibInitTime(0.0d, 80000.0d),
        SDKInitTime(0.0d, 120000.0d),
        SDKInitInvokeTime(0.0d, 5000.0d),
        SDKInitExecuteTime(0.0d, 5000.0d),
        JSTemplateSize(0.0d, 5000.0d),
        pureNetworkTime(0.0d, 15000.0d),
        networkTime(0.0d, 15000.0d),
        fsCreateInstanceTime(0.0d, 3000.0d),
        fsCallJsTotalTime(0.0d, 5000.0d),
        fsCallJsTotalNum(0.0d, Double.MAX_VALUE),
        fsCallNativeTotalTime(0.0d, 5000.0d),
        fsCallNativeTotalNum(0.0d, Double.MAX_VALUE),
        fsCallEventTotalNum(0.0d, Double.MAX_VALUE),
        fsComponentCount(0.0d, 100000.0d),
        fsComponentCreateTime(0.0d, Double.MAX_VALUE),
        fsRenderTime(0.0d, 5000.0d),
        fsRequestNum(0.0d, 100.0d),
        callCreateFinishTime(0.0d, 10000.0d),
        cellExceedNum(0.0d, Double.MAX_VALUE),
        communicateTotalTime(0.0d, 5000.0d),
        maxDeepViewLayer(0.0d, Double.MAX_VALUE),
        maxDeepVDomLayer(0.0d, Double.MAX_VALUE),
        componentCount(0.0d, 1000000.0d),
        componentCreateTime(0.0d, Double.MAX_VALUE),
        avgFps(0.0d, 61.0d),
        timerCount(0.0d, Double.MAX_VALUE),
        MaxImproveMemory(0.0d, Double.MAX_VALUE),
        BackImproveMemory(0.0d, Double.MAX_VALUE),
        PushImproveMemory(0.0d, Double.MAX_VALUE),
        measureTime1(0.0d, Double.MAX_VALUE),
        measureTime2(0.0d, Double.MAX_VALUE),
        measureTime3(0.0d, Double.MAX_VALUE),
        measureTime4(0.0d, Double.MAX_VALUE),
        measureTime5(0.0d, Double.MAX_VALUE),
        callBridgeTime(0.0d, Double.MAX_VALUE),
        cssLayoutTime(0.0d, Double.MAX_VALUE),
        parseJsonTime(0.0d, Double.MAX_VALUE),
        communicateTime(0.0d, 5000.0d),
        screenRenderTime(0.0d, 5000.0d),
        totalTime(0.0d, 5000.0d),
        localReadTime(0.0d, 5000.0d),
        templateLoadTime(0.0d, 5000.0d),
        packageSpendTime(0.0d, 5000.0d),
        syncTaskTime(0.0d, 5000.0d),
        actualNetworkTime(0.0d, 5000.0d),
        firstScreenJSFExecuteTime(0.0d, 5000.0d),
        fluency(0.0d, 101.0d),
        imgSizeCount(0.0d, 2000.0d),
        interactionTime(0.0d, 10000.0d),
        interactionViewAddCount(0.0d, Double.MAX_VALUE),
        interactionViewAddLimitCount(0.0d, Double.MAX_VALUE),
        newFsRenderTime(0.0d, 10000.0d);

        private double mMaxRange;
        private double mMinRange;

        Measure(double d, double d2) {
            this.mMinRange = d;
            this.mMaxRange = d2;
        }

        public double getMaxRange() {
            return this.mMaxRange;
        }

        public double getMinRange() {
            return this.mMinRange;
        }
    }

    public WXPerformance(String str) {
        this.mInstanceId = str;
    }

    public static String[] getDimensions() {
        LinkedList linkedList = new LinkedList();
        for (Dimension dimension : Dimension.values()) {
            linkedList.add(dimension.toString());
        }
        return (String[]) linkedList.toArray(new String[linkedList.size()]);
    }

    public static String[] getMeasures() {
        LinkedList linkedList = new LinkedList();
        for (Measure measure : Measure.values()) {
            linkedList.add(measure.toString());
        }
        return (String[]) linkedList.toArray(new String[linkedList.size()]);
    }

    public void afterInstanceDestroy(String str) {
    }

    public void appendErrMsg(CharSequence charSequence) {
        this.mErrMsgBuilder.append(charSequence);
    }

    public void beforeInstanceRender(String str) {
        this.renderTimeOrigin = System.currentTimeMillis();
        this.renderUnixTimeOrigin = WXUtils.getFixUnixTime();
    }

    public Map<String, String> getDimensionMap() {
        HashMap map = new HashMap();
        map.put(Dimension.JSLibVersion.toString(), this.JSLibVersion);
        map.put(Dimension.WXSDKVersion.toString(), this.WXSDKVersion);
        map.put(Dimension.pageName.toString(), this.pageName);
        map.put(Dimension.requestType.toString(), this.requestType);
        map.put(Dimension.networkType.toString(), "unknown");
        map.put(Dimension.connectionType.toString(), this.connectionType);
        map.put(Dimension.zcacheInfo.toString(), this.zCacheInfo);
        map.put(Dimension.cacheType.toString(), this.cacheType);
        map.put(Dimension.useScroller.toString(), String.valueOf(this.useScroller));
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(this.mInstanceId);
        map.put(WXInstanceApm.KEY_PAGE_PROPERTIES_CONTAINER_NAME, sDKInstance == null ? "unKnow" : sDKInstance.getContainerInfo().get(WXInstanceApm.KEY_PAGE_PROPERTIES_CONTAINER_NAME));
        map.put(WXInstanceApm.KEY_PAGE_PROPERTIES_INSTANCE_TYPE, sDKInstance == null ? "unKnow" : sDKInstance.getContainerInfo().get(WXInstanceApm.KEY_PAGE_PROPERTIES_INSTANCE_TYPE));
        map.put(WXInstanceApm.KEY_PAGE_PROPERTIES_PARENT_PAGE, sDKInstance != null ? sDKInstance.getContainerInfo().get(WXInstanceApm.KEY_PAGE_PROPERTIES_PARENT_PAGE) : "unKnow");
        map.put(Dimension.wxdim1.toString(), this.wxDims[0]);
        map.put(Dimension.wxdim2.toString(), this.wxDims[1]);
        map.put(Dimension.wxdim3.toString(), this.wxDims[2]);
        map.put(Dimension.wxdim4.toString(), this.wxDims[3]);
        map.put(Dimension.wxdim5.toString(), this.wxDims[4]);
        map.put(Dimension.bizType.toString(), this.bizType);
        map.put(Dimension.templateUrl.toString(), this.templateUrl);
        return map;
    }

    public String getErrMsg() {
        return this.mErrMsgBuilder.toString();
    }

    public Map<String, Double> getMeasureMap() {
        double d;
        long j = this.fsRenderTime;
        Double dValueOf = Double.valueOf(0.0d);
        if (j != 0) {
            d = j - this.renderTimeOrigin;
        } else {
            d = this.totalTime;
            if (d == 0.0d) {
                d = -1.0d;
            }
        }
        HashMap map = new HashMap();
        map.put(Measure.JSLibSize.toString(), Double.valueOf(this.JSLibSize));
        map.put(Measure.JSLibInitTime.toString(), Double.valueOf(this.JSLibInitTime));
        map.put(Measure.SDKInitTime.toString(), Double.valueOf(WXEnvironment.sSDKInitTime));
        map.put(Measure.SDKInitInvokeTime.toString(), Double.valueOf(WXEnvironment.sSDKInitInvokeTime));
        map.put(Measure.SDKInitExecuteTime.toString(), Double.valueOf(WXEnvironment.sSDKInitExecuteTime));
        map.put(Measure.JSTemplateSize.toString(), Double.valueOf(this.JSTemplateSize));
        map.put(Measure.pureNetworkTime.toString(), Double.valueOf(this.pureNetworkTime));
        map.put(Measure.networkTime.toString(), Double.valueOf(this.networkTime));
        map.put(Measure.fsCreateInstanceTime.toString(), Double.valueOf(this.callCreateInstanceTime));
        map.put(Measure.fsCallJsTotalTime.toString(), Double.valueOf(this.fsCallJsTotalTime));
        map.put(Measure.fsCallJsTotalNum.toString(), Double.valueOf(this.fsCallJsTotalNum));
        map.put(Measure.fsCallNativeTotalTime.toString(), Double.valueOf(this.fsCallNativeTotalTime));
        map.put(Measure.fsCallNativeTotalNum.toString(), Double.valueOf(this.fsCallNativeTotalNum));
        map.put(Measure.fsComponentCount.toString(), Double.valueOf(this.fsComponentCount));
        map.put(Measure.fsComponentCreateTime.toString(), Double.valueOf(this.fsComponentCreateTime));
        map.put(Measure.fsRenderTime.toString(), Double.valueOf(d));
        map.put(Measure.fsRequestNum.toString(), Double.valueOf(this.fsRequestNum));
        map.put(Measure.communicateTotalTime.toString(), Double.valueOf(this.totalTime));
        map.put(Measure.maxDeepViewLayer.toString(), Double.valueOf(this.maxDeepViewLayer));
        map.put(Measure.maxDeepVDomLayer.toString(), Double.valueOf(this.maxDeepVDomLayer));
        map.put(Measure.componentCount.toString(), Double.valueOf(this.componentCount));
        map.put(Measure.componentCreateTime.toString(), Double.valueOf(this.componentCreateTime));
        map.put(Measure.cellExceedNum.toString(), Double.valueOf(this.cellExceedNum));
        map.put(Measure.timerCount.toString(), Double.valueOf(this.timerInvokeCount));
        map.put(Measure.avgFps.toString(), Double.valueOf(this.avgFPS));
        map.put(Measure.fluency.toString(), Double.valueOf(this.fluency));
        map.put(Measure.MaxImproveMemory.toString(), dValueOf);
        map.put(Measure.BackImproveMemory.toString(), Double.valueOf(this.backImproveMemory));
        map.put(Measure.PushImproveMemory.toString(), dValueOf);
        map.put(Measure.fsCallEventTotalNum.toString(), Double.valueOf(this.fsCallEventTotalNum));
        map.put(Measure.callCreateFinishTime.toString(), Double.valueOf(this.callCreateFinishTime));
        map.put(Measure.imgSizeCount.toString(), Double.valueOf(this.wrongImgSizeCount));
        map.put(Measure.interactionTime.toString(), Double.valueOf(this.interactionTime));
        map.put(Measure.interactionViewAddCount.toString(), Double.valueOf(this.interactionViewAddCount));
        map.put(Measure.interactionViewAddLimitCount.toString(), Double.valueOf(this.interactionViewAddLimitCount));
        map.put(Measure.newFsRenderTime.toString(), Double.valueOf(this.newFsRenderTime));
        map.put(Measure.callBridgeTime.toString(), Double.valueOf(this.callBridgeTime));
        map.put(Measure.cssLayoutTime.toString(), Double.valueOf(this.cssLayoutTime));
        map.put(Measure.parseJsonTime.toString(), Double.valueOf(this.parseJsonTime));
        map.put(Measure.screenRenderTime.toString(), Double.valueOf(this.screenRenderTime));
        map.put(Measure.communicateTime.toString(), Double.valueOf(this.communicateTime));
        map.put(Measure.localReadTime.toString(), Double.valueOf(this.localReadTime));
        map.put(Measure.templateLoadTime.toString(), Double.valueOf(this.templateLoadTime));
        map.put(Measure.firstScreenJSFExecuteTime.toString(), Double.valueOf(this.firstScreenJSFExecuteTime));
        map.put(Measure.actualNetworkTime.toString(), Double.valueOf(this.actualNetworkTime));
        map.put(Measure.syncTaskTime.toString(), Double.valueOf(this.syncTaskTime));
        map.put(Measure.packageSpendTime.toString(), Double.valueOf(this.packageSpendTime));
        map.put(Measure.measureTime1.toString(), Double.valueOf(this.measureTimes[0]));
        map.put(Measure.measureTime2.toString(), Double.valueOf(this.measureTimes[1]));
        map.put(Measure.measureTime3.toString(), Double.valueOf(this.measureTimes[2]));
        map.put(Measure.measureTime4.toString(), Double.valueOf(this.measureTimes[3]));
        map.put(Measure.measureTime5.toString(), Double.valueOf(this.measureTimes[4]));
        return map;
    }

    public String getPerfData() {
        return "networkTime:" + this.networkTime + " actualNetworkTime:" + this.actualNetworkTime + " connectionType:" + this.connectionType + " requestType:" + this.requestType + " firstScreenRenderTime:" + this.screenRenderTime + " firstScreenJSFExecuteTime:" + this.firstScreenJSFExecuteTime + " componentCount:" + this.componentCount + " JSTemplateSize:" + this.JSTemplateSize + " SDKInitTime:" + WXEnvironment.sSDKInitTime + " totalTime:" + this.totalTime + " JSLibVersion:" + this.JSLibVersion + " WXSDKVersion:" + this.WXSDKVersion + " pageName:" + this.pageName + " useScroller:" + this.useScroller;
    }

    public String toString() {
        if (!WXEnvironment.isApkDebugable()) {
            return super.toString();
        }
        return "bizType:" + this.bizType + ",pageName:" + this.pageName + ",templateLoadTime" + this.templateLoadTime + ",localReadTime:" + this.localReadTime + ",JSLibInitTime:" + this.JSLibInitTime + ",JSLibSize:" + this.JSLibSize + ",templateUrl" + this.templateUrl + ",JSTemplateSize:" + this.JSTemplateSize + ",communicateTime:" + this.communicateTime + ",screenRenderTime:" + this.screenRenderTime + ",firstScreenJSFExecuteTime:" + this.firstScreenJSFExecuteTime + ",componentCount:" + this.componentCount + ",syncTaskTime:" + this.syncTaskTime + ",pureNetworkTime:" + this.pureNetworkTime + ",networkTime:" + this.networkTime + ",actualNetworkTime:" + this.actualNetworkTime + ",packageSpendTime:" + this.packageSpendTime + ",connectionType:" + this.connectionType + ",requestType:" + this.requestType + ",initInvokeTime:" + WXEnvironment.sSDKInitInvokeTime + ",initExecuteTime:" + WXEnvironment.sSDKInitExecuteTime + ",SDKInitTime:" + WXEnvironment.sSDKInitTime + ",totalTime:" + this.totalTime + ",JSLibVersion:" + this.JSLibVersion + ",WXSDKVersion:" + this.WXSDKVersion + ",errCode:" + this.errCode + ",renderFailedDetail:" + this.renderFailedDetail + ",arg:" + this.args + ",errMsg:" + getErrMsg();
    }
}
