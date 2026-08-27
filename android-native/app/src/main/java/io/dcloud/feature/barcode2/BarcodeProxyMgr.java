package io.dcloud.feature.barcode2;

import android.text.TextUtils;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.JSUtil;
import java.util.Iterator;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BarcodeProxyMgr {
    private static BarcodeProxyMgr mInstance;
    private LinkedHashMap<String, BarcodeProxy> mBProxyCaches = new LinkedHashMap<>();
    private AbsMgr mFeatureMgr;

    public static BarcodeProxyMgr getBarcodeProxyMgr() {
        if (mInstance == null) {
            mInstance = new BarcodeProxyMgr();
        }
        return mInstance;
    }

    public Object doForFeature(String str, Object obj) {
        if (!str.equals("appendToFrameView")) {
            return null;
        }
        Object[] objArr = (Object[]) obj;
        AdaFrameView adaFrameView = (AdaFrameView) objArr[0];
        String str2 = (String) objArr[1];
        adaFrameView.obtainApp().obtainAppId();
        BarcodeProxy barcodeProxyByUuid = getBarcodeProxyByUuid(str2);
        if (barcodeProxyByUuid == null) {
            return null;
        }
        barcodeProxyByUuid.appendToFrameView(adaFrameView);
        return null;
    }

    public String execute(IWebview iWebview, String str, String[] strArr) {
        BarcodeProxy barcodeProxy;
        if (!str.equals("getBarcodeById")) {
            String str2 = strArr[0];
            if (this.mBProxyCaches.containsKey(str2)) {
                barcodeProxy = this.mBProxyCaches.get(str2);
            } else {
                barcodeProxy = new BarcodeProxy();
                this.mBProxyCaches.put(str2, barcodeProxy);
            }
            barcodeProxy.execute(iWebview, str, strArr);
            return null;
        }
        AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Barcode-" + str);
        BarcodeProxy barcodeProxyById = getBarcodeProxyById(strArr[0]);
        if (barcodeProxyById != null) {
            return JSUtil.wrapJsVar(barcodeProxyById.getJsBarcode());
        }
        return null;
    }

    public IWebview findWebviewByUuid(IWebview iWebview, String str) {
        Object objProcessEvent;
        AbsMgr absMgr = this.mFeatureMgr;
        if (absMgr == null || (objProcessEvent = absMgr.processEvent(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebview, AbsoluteConst.F_UI, "findWebview", new String[]{iWebview.obtainApp().obtainAppId(), str}})) == null) {
            return null;
        }
        return (IWebview) objProcessEvent;
    }

    public BarcodeProxy getBarcodeProxyById(String str) {
        Iterator<String> it = this.mBProxyCaches.keySet().iterator();
        while (it.hasNext()) {
            BarcodeProxy barcodeProxy = this.mBProxyCaches.get(it.next());
            if (!TextUtils.isEmpty(barcodeProxy.mId) && barcodeProxy.mId.equals(str)) {
                return barcodeProxy;
            }
        }
        return null;
    }

    public BarcodeProxy getBarcodeProxyByUuid(String str) {
        if (this.mBProxyCaches.containsKey(str)) {
            return this.mBProxyCaches.get(str);
        }
        return null;
    }

    public void onDestroy() {
        Iterator<String> it = this.mBProxyCaches.keySet().iterator();
        while (it.hasNext()) {
            BarcodeProxy barcodeProxyRemove = this.mBProxyCaches.remove(it.next());
            if (barcodeProxyRemove != null) {
                barcodeProxyRemove.onDestroy();
            }
        }
        this.mBProxyCaches.clear();
    }

    public void removeBarcodeProxy(String str) {
        BarcodeProxy barcodeProxyRemove = this.mBProxyCaches.remove(str);
        if (barcodeProxyRemove != null) {
            barcodeProxyRemove.onDestroy();
        }
    }

    public void setFeatureMgr(AbsMgr absMgr) {
        this.mFeatureMgr = absMgr;
    }
}
