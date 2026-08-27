package io.dcloud.feature.payment;

import android.app.Application;
import android.content.Context;
import io.dcloud.application.DCloudApplication;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PaymentFeatureImpl implements IFeature {
    String a;
    AbsMgr b;
    Context c;
    ArrayList d = null;

    private void a(IWebview iWebview) throws IllegalAccessException, InstantiationException {
        HashMap map = (HashMap) this.b.processEvent(IMgr.MgrType.FeatureMgr, 4, this.a);
        if (map == null || map.isEmpty()) {
            return;
        }
        for (String str : map.keySet()) {
            try {
                Object objNewInstance = Class.forName((String) map.get(str)).newInstance();
                if (objNewInstance instanceof AbsPaymentChannel) {
                    AbsPaymentChannel absPaymentChannel = (AbsPaymentChannel) objNewInstance;
                    absPaymentChannel.init(this.c);
                    absPaymentChannel.name = str;
                    absPaymentChannel.featureName = this.a;
                    if (absPaymentChannel.id == null) {
                        absPaymentChannel.id = str;
                    }
                    this.d.add(absPaymentChannel);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) throws IllegalAccessException, InstantiationException {
        AbsPaymentChannel absPaymentChannelA;
        AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Payment-getUserInfo");
        if ("getChannels".equals(str)) {
            if (this.d.isEmpty()) {
                a(iWebview);
            }
            JSUtil.execCallback(iWebview, strArr[0], a(), JSUtil.OK, false);
            return null;
        }
        if (AbsURIAdapter.REQUEST.equals(str)) {
            AbsPaymentChannel absPaymentChannelA2 = a(strArr[0]);
            String str2 = strArr[1];
            String str3 = strArr[2];
            if (absPaymentChannelA2 == null) {
                Deprecated_JSUtil.execCallback(iWebview, str3, StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(IPaymentListener.CODE_UNKNOWN), "not found channel"), JSUtil.ERROR, true, false);
                return null;
            }
            if (absPaymentChannelA2.id.equalsIgnoreCase("wxpay")) {
                Application application = iWebview.getActivity().getApplication();
                if (application instanceof DCloudApplication) {
                    ((DCloudApplication) application).stopB2FOnce();
                }
            }
            absPaymentChannelA2.updateWebview(iWebview);
            absPaymentChannelA2.a(str2, str3);
            return null;
        }
        if ("installService".equals(str)) {
            AbsPaymentChannel absPaymentChannelA3 = a(strArr[0]);
            if (absPaymentChannelA3 == null) {
                return null;
            }
            absPaymentChannelA3.updateWebview(iWebview);
            absPaymentChannelA3.installService();
            return null;
        }
        if (!"isReadyToPay".equals(str) || (absPaymentChannelA = a(strArr[0])) == null) {
            return null;
        }
        absPaymentChannelA.updateWebview(iWebview);
        absPaymentChannelA.isReadyToPay(strArr[1], strArr[2]);
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.a = str;
        this.b = absMgr;
        this.c = absMgr.getContext();
        this.d = new ArrayList(2);
    }

    private JSONArray a() {
        JSONArray jSONArray = new JSONArray();
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            jSONArray.put(((AbsPaymentChannel) this.d.get(i)).toJSONObject());
        }
        return jSONArray;
    }

    private AbsPaymentChannel a(String str) {
        ArrayList arrayList = this.d;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            AbsPaymentChannel absPaymentChannel = (AbsPaymentChannel) obj;
            if (absPaymentChannel.id.equals(str)) {
                return absPaymentChannel;
            }
        }
        return null;
    }
}
