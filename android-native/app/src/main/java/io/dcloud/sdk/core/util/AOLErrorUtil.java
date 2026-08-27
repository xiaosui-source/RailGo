package io.dcloud.sdk.core.util;

import android.content.Context;
import io.dcloud.base.R;
import io.dcloud.p.r0;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class AOLErrorUtil {
    public static String getErrorMsg(int i) {
        if (r0.d() == null || r0.d().c() == null) {
            return "zh".equalsIgnoreCase(Locale.getDefault().getCountry()) ? "广告未初始化或者初始化信息为空" : "Please init first";
        }
        Context contextC = r0.d().c();
        if (i == -5100) {
            return contextC.getString(R.string.dcloud_ad_error_code_5100);
        }
        switch (i) {
            case -5022:
                return contextC.getString(R.string.dcloud_ad_error_code_5022);
            case -5021:
                return contextC.getString(R.string.dcloud_ad_error_code_5021);
            case -5020:
                return contextC.getString(R.string.dcloud_ad_error_code_5020);
            case -5019:
                return contextC.getString(R.string.dcloud_ad_error_code_5019);
            case -5018:
                return contextC.getString(R.string.dcloud_ad_error_code_5018);
            case -5017:
                return contextC.getString(R.string.dcloud_ad_error_code_5017);
            case -5016:
                return contextC.getString(R.string.dcloud_ad_error_code_5016);
            case -5015:
                return contextC.getString(R.string.dcloud_ad_error_code_5015);
            case -5014:
                return contextC.getString(R.string.dcloud_ad_error_code_5014);
            case -5013:
                return contextC.getString(R.string.dcloud_ad_error_code_5013);
            case -5012:
                return contextC.getString(R.string.dcloud_ad_error_code_5012);
            case -5011:
                return contextC.getString(R.string.dcloud_ad_error_code_5011);
            default:
                switch (i) {
                    case -5008:
                        return contextC.getString(R.string.dcloud_ad_error_code_5008);
                    case -5007:
                        return contextC.getString(R.string.dcloud_ad_error_code_5007);
                    case -5006:
                        return contextC.getString(R.string.dcloud_ad_error_code_5006);
                    case -5005:
                        return contextC.getString(R.string.dcloud_ad_error_code_5005);
                    case -5004:
                        return contextC.getString(R.string.dcloud_ad_error_code_5004);
                    case -5003:
                        return contextC.getString(R.string.dcloud_ad_error_code_5003);
                    case -5002:
                        return contextC.getString(R.string.dcloud_ad_error_code_5002);
                    case -5001:
                        return contextC.getString(R.string.dcloud_ad_error_code_5001);
                    case -5000:
                        return "";
                    default:
                        if (i == -4002) {
                            return "exception when load";
                        }
                        if (i == -4001) {
                            return "init fail";
                        }
                        if (i == -603) {
                            return contextC.getString(R.string.dcloud_ad_error_code_603);
                        }
                        switch (i) {
                            case 200000:
                                return contextC.getString(R.string.dcloud_ad_error_code_200000);
                            case 200001:
                                return "";
                            case 200002:
                                return "not support";
                            default:
                                switch (i) {
                                    case -51005:
                                        return "广告请求失败，请稍后重试";
                                    case -51004:
                                        return "未获取到广告配置数据";
                                    case -51003:
                                        return "微信当前版本不支持运行小程序";
                                    case -51002:
                                        return "未检测到手机安装微信";
                                    case -51001:
                                        return "广告初始化异常";
                                    default:
                                        return "";
                                }
                        }
                }
        }
    }
}
