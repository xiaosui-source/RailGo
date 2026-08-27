package io.dcloud.common.util;

import com.taobao.weex.common.Constants;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AppStreamUtil {
    public static ArrayList<String> AppStreamSchemeWhiteDefaultList;

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        AppStreamSchemeWhiteDefaultList = arrayList;
        arrayList.add("sms");
        AppStreamSchemeWhiteDefaultList.add(Constants.Value.TEL);
        AppStreamSchemeWhiteDefaultList.add("mailto");
        AppStreamSchemeWhiteDefaultList.add("callto");
        AppStreamSchemeWhiteDefaultList.add("weixin");
        AppStreamSchemeWhiteDefaultList.add("alipay");
        AppStreamSchemeWhiteDefaultList.add("alipays");
        AppStreamSchemeWhiteDefaultList.add("alipayqr");
        AppStreamSchemeWhiteDefaultList.add("weibo");
        AppStreamSchemeWhiteDefaultList.add("mqq");
        AppStreamSchemeWhiteDefaultList.add("mqqapi");
        AppStreamSchemeWhiteDefaultList.add("qqmap");
        AppStreamSchemeWhiteDefaultList.add("baidumap");
        AppStreamSchemeWhiteDefaultList.add("amap");
        AppStreamSchemeWhiteDefaultList.add("iosamap");
        AppStreamSchemeWhiteDefaultList.add(AbsoluteConst.XML_STREAMAPP);
    }
}
