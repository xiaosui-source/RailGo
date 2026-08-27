package com.taobao.weex.http;

import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.common.DHInterface.IReflectAble;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class CertJSResponse implements IReflectAble {
    public int code;
    public String message;
    public String type;

    public static CertJSResponse obtainFail(int i, String str) {
        CertJSResponse certJSResponse = new CertJSResponse();
        certJSResponse.type = Constants.Event.FAIL;
        certJSResponse.code = i;
        certJSResponse.message = str;
        return certJSResponse;
    }

    public static CertJSResponse obtainSuccess() {
        CertJSResponse certJSResponse = new CertJSResponse();
        certJSResponse.type = WXImage.SUCCEED;
        certJSResponse.code = 0;
        certJSResponse.message = "";
        return certJSResponse;
    }
}
