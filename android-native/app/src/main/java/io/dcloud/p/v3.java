package io.dcloud.p;

import com.taobao.weex.common.Constants;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.PdrUtil;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class v3 implements IEventCallback {
    HashMap a = new HashMap();

    public String a(IWebview iWebview, String str, String[] strArr) {
        u3 u3Var;
        if (str.equals("getCurrentProximity")) {
            String str2 = strArr[0];
            u3 u3Var2 = (u3) this.a.get(iWebview);
            if (u3Var2 == null) {
                u3Var2 = new u3(iWebview);
                this.a.put(iWebview, u3Var2);
            }
            u3Var2.d = str2;
            u3Var2.a();
            return null;
        }
        if (!str.equals("start")) {
            if (!str.equals(Constants.Value.STOP) || (u3Var = (u3) this.a.get(iWebview)) == null) {
                return null;
            }
            u3Var.b();
            return null;
        }
        String str3 = strArr[0];
        ((AdaFrameView) iWebview.obtainFrameView()).addFrameViewListener(this);
        u3 u3Var3 = (u3) this.a.get(iWebview);
        if (u3Var3 == null) {
            u3Var3 = new u3(iWebview);
            this.a.put(iWebview, u3Var3);
        }
        u3Var3.e = str3;
        u3Var3.a();
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IEventCallback
    public Object onCallBack(String str, Object obj) {
        if ((!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_WINDOW_CLOSE) && !PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE)) || !(obj instanceof IWebview)) {
            return null;
        }
        IWebview iWebview = (IWebview) obj;
        u3 u3Var = (u3) this.a.remove(iWebview);
        if (u3Var != null) {
            u3Var.b();
        }
        ((AdaFrameView) iWebview.obtainFrameView()).removeFrameViewListener(this);
        return null;
    }
}
