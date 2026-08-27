package io.dcloud.feature.ui;

import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.PdrUtil;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class d extends b implements IEventCallback {
    ArrayList u;
    IFrameView v;

    public d(String str, ArrayList arrayList, JSONObject jSONObject) {
        super(str);
        this.u = arrayList;
    }

    @Override // io.dcloud.feature.ui.b
    public void a(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    @Override // io.dcloud.feature.ui.b
    public AdaFrameItem d() {
        return (AdaFrameItem) this.v;
    }

    @Override // io.dcloud.feature.ui.b
    protected void e() {
    }

    public void i() {
        this.c.c(this.v);
        ArrayList arrayList = this.u;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            IFrameView iFrameView = (IFrameView) obj;
            this.c.c(iFrameView);
            ((AdaFrameView) iFrameView).isChildOfFrameView = true;
        }
    }

    @Override // io.dcloud.common.DHInterface.IEventCallback
    public Object onCallBack(String str, Object obj) {
        if (PdrUtil.isEquals(str, AbsoluteConst.EVENTS_PAGER_SELECTED)) {
            a(str, PdrUtil.isEmpty(obj) ? null : String.valueOf(obj), false);
        }
        return null;
    }

    public void a(boolean z) {
        this.v.setVisible(z, true);
    }

    public void a(IFrameView iFrameView) {
        this.v = iFrameView;
    }

    @Override // io.dcloud.feature.ui.b
    public String a(IWebview iWebview, String str, JSONArray jSONArray) throws JSONException {
        try {
            if ("addEventListener".equals(str) && iWebview != null) {
                a(jSONArray.getString(1), jSONArray.getString(0), (String) this.b.get(iWebview.getWebviewANID()));
                return null;
            }
            "setSelectIndex".equals(str);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
