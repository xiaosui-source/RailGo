package io.dcloud.p;

import android.content.Context;
import com.taobao.weex.common.Constants;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.PdrUtil;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class n3 implements IEventCallback {
    private HashMap a = new HashMap();
    Context b;

    public n3(Context context) {
        this.b = context;
    }

    @Override // io.dcloud.common.DHInterface.IEventCallback
    public Object onCallBack(String str, Object obj) {
        if ((!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_WINDOW_CLOSE) && !PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE)) || !(obj instanceof IWebview)) {
            return null;
        }
        IWebview iWebview = (IWebview) obj;
        a(iWebview);
        ((AdaFrameView) iWebview.obtainFrameView()).removeFrameViewListener(this);
        return null;
    }

    public String a(IWebview iWebview, String str, String[] strArr) {
        if (str.equals("start")) {
            a(iWebview, strArr[0]);
            ((AdaFrameView) iWebview.obtainFrameView()).addFrameViewListener(this);
            return "";
        }
        if (str.equals(Constants.Value.STOP)) {
            a(iWebview);
        }
        return "";
    }

    private void a(IWebview iWebview, String str) {
        m3 m3Var = (m3) this.a.get(iWebview);
        if (m3Var == null) {
            m3Var = new m3(iWebview, str);
            this.a.put(iWebview, m3Var);
        }
        m3Var.a();
    }

    private void a(IWebview iWebview) {
        m3 m3Var = (m3) this.a.remove(iWebview);
        if (m3Var != null) {
            m3Var.b();
        }
    }
}
