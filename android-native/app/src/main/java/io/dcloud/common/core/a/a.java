package io.dcloud.common.core.a;

import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.taobao.weex.ui.module.WXModalUIModule;
import io.dcloud.EntryProxy;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.TestUtil;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class a {
    private static IApp a() {
        return (IApp) EntryProxy.getInstnace().getCoreHandler().dispatchEvent(IMgr.MgrType.AppMgr, 28, BaseInfo.sDefaultBootApp);
    }

    public static void bc(String str) throws IOException {
        SP.setBundleData("pdr", SP.REPORT_UNI_VERIFY_GYUID, "");
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(AbsoluteConst.START_STATISTICS_DATA);
        if ((System.currentTimeMillis() - orCreateBundle.getLong(AbsoluteConst.COMMIT_APP_LIST_TIME, 0L)) / 100000 >= 26000 && !BaseInfo.isChannelGooglePlay() && ((!Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI) || Build.VERSION.SDK_INT < 23 || PlatformUtil.checkGTAndYoumeng()) && (TextUtils.isEmpty(BaseInfo.sChannel) || !BaseInfo.sChannel.endsWith("|xiaomi")))) {
            orCreateBundle.edit().putLong(AbsoluteConst.COMMIT_APP_LIST_TIME, System.currentTimeMillis()).commit();
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("ret") && jSONObject.optInt("ret") == 0 && WXModalUIModule.OK.equals(jSONObject.opt("desc")) && !jSONObject.isNull("did")) {
                SP.setBundleData(SP.getOrCreateBundle("pdr"), SP.STARTUP_DEVICE_ID, jSONObject.optString("did"));
            }
            if (BaseInfo.ISDEBUG || !jSONObject.has("urd")) {
                return;
            }
            String strOptString = jSONObject.optString("urd");
            if (URLUtil.isNetworkUrl(strOptString)) {
                DHFile.writeFile(NetTool.httpGet(strOptString, false), 0, BaseInfo.sURDFilePath);
            }
        } catch (JSONException e) {
            Logger.p("IDBridge", e.getMessage());
        }
    }

    public static String gd() {
        IApp iAppA = a();
        if (iAppA == null) {
            return "";
        }
        HashMap map = new HashMap(TestUtil.PointTime.gsd(iAppA, SP.getOrCreateBundle(iAppA.getActivity(), AbsoluteConst.START_STATISTICS_DATA)));
        try {
            map.put("ps", Integer.valueOf(BaseInfo.existsStreamEnv() ? 1 : 0));
            map.put("psd", Integer.valueOf(BaseInfo.ISDEBUG ? 1 : 0));
            map.put("paid", iAppA.obtainConfigProperty("adid"));
            JSONObject jSONObjectObtainThridInfo = iAppA.obtainThridInfo(IApp.ConfigProperty.ThridInfo.URDJsonData);
            map.put("urv", jSONObjectObtainThridInfo != null ? jSONObjectObtainThridInfo.optString("version") : "0.1");
            if (!TextUtils.isEmpty(AppRuntime.getUniStatistics())) {
                map.put("us", AppRuntime.getUniStatistics());
            }
        } catch (Exception unused) {
        }
        while (map.values().remove(null)) {
        }
        while (map.values().remove("null")) {
        }
        return new JSONObject(map).toString();
    }
}
