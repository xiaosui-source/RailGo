package io.dcloud.feature.weex;

import android.text.TextUtils;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXImage;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public enum EnumStateCode {
    SUCCESS_NO_BODY(WXImage.SUCCEED, 0, ""),
    FAIL_BY_INVALID_PARAMETER(Constants.Event.FAIL, -1, "invalid parameter."),
    FAIL_BY_NO_PERMISSION(Constants.Event.FAIL, -2, "no permission."),
    FAIL_BY_NO_MESSAGE_LISTENER_RECEIVED(Constants.Event.FAIL, -3, "no message listener received."),
    FAIL_BY_NO_INIT(Constants.Event.FAIL, -4, "not initialized."),
    FAIL_BY_RELEASE_WGT_ERROR(Constants.Event.FAIL, -5, "wgt release error."),
    FAIL_BY_UNKNOWN_ERROR(Constants.Event.FAIL, DCloudAlertDialog.DARK_THEME, "unknown error."),
    FAIL_BY_NO_RESOURCE_EXIST(Constants.Event.FAIL, -1001, "mp resource do not exist."),
    FAIL_BY_NO_WGT_EXIST(Constants.Event.FAIL, -1004, "wgt do not exist."),
    FAIL_BY_MP_IS_NOT_RUNNING(Constants.Event.FAIL, -3001, "target mp is not running.");

    private int mCode;
    private String mDesc;
    private String mResult;

    EnumStateCode(String str, int i, String str2) {
        this.mCode = i;
        this.mDesc = str2;
        this.mResult = str;
    }

    public static Map<String, Object> obtainMap(EnumStateCode enumStateCode) {
        HashMap map = new HashMap();
        map.put("type", enumStateCode.mResult);
        map.put("code", Integer.valueOf(enumStateCode.mCode));
        if (!TextUtils.isEmpty(enumStateCode.mDesc)) {
            map.put("message", enumStateCode.mDesc);
        }
        return map;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getDesc() {
        return this.mDesc;
    }

    public String getResult() {
        return this.mResult;
    }

    public static Map<String, Object> obtainMap(EnumStateCode enumStateCode, String str, Object obj) {
        HashMap map = new HashMap();
        map.put("type", enumStateCode.mResult);
        map.put("code", Integer.valueOf(enumStateCode.mCode));
        if (!TextUtils.isEmpty(str)) {
            map.put(str, obj);
        }
        return map;
    }
}
