package io.dcloud.common.core.ui;

import android.text.TextUtils;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IJsInterface;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.TestUtil;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class h implements IJsInterface, IReflectAble {
    static boolean e = false;
    static final Class[] f = {String.class, String.class};
    AbsMgr a;
    IWebview b;
    String c;
    MessageHandler.IMessages d = new a();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements MessageHandler.IMessages {
        a() {
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            Object[] objArr = (Object[]) obj;
            h.this.exec(String.valueOf(objArr[0]), String.valueOf(objArr[1]), (JSONArray) objArr[2]);
        }
    }

    h(IWebview iWebview) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.b = iWebview;
        this.c = iWebview.obtainFrameView().obtainApp().obtainAppId();
        this.a = this.b.obtainFrameView().obtainWindowMgr();
    }

    @Override // io.dcloud.common.DHInterface.IJsInterface
    public String exec(String str, String str2, String str3) {
        return exec(str, str2, JSONUtil.createJSONArray(str3));
    }

    @Override // io.dcloud.common.DHInterface.IJsInterface
    public void forceStop(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.a.processEvent(IMgr.MgrType.WindowMgr, 20, str);
    }

    @Override // io.dcloud.common.DHInterface.IJsInterface
    public String prompt(String str, String str2) throws JSONException {
        if (!e) {
            TestUtil.record("JsInterfaceImpl", Thread.currentThread());
            e = true;
        }
        if (str2 != null && str2.length() > 3 && str2.substring(0, 4).equals("pdr:")) {
            try {
                JSONArray jSONArray = new JSONArray(str2.substring(4));
                String string = jSONArray.getString(0);
                String string2 = jSONArray.getString(1);
                boolean z = jSONArray.getBoolean(2);
                JSONArray jSONArrayCreateJSONArray = JSONUtil.createJSONArray(str);
                if (!z) {
                    return exec(string, string2, jSONArrayCreateJSONArray);
                }
                MessageHandler.sendMessage(this.d, new Object[]{string, string2, jSONArrayCreateJSONArray});
                return null;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IJsInterface
    public String exec(String str, String str2, JSONArray jSONArray) {
        if (this.b.getContext() == null) {
            return "";
        }
        try {
            String lowerCase = str.toLowerCase(Locale.ENGLISH);
            "io.dcloud.HBuilder".equals(this.b.getContext().getPackageName());
            return String.valueOf(this.a.processEvent(IMgr.MgrType.FeatureMgr, 1, new Object[]{this.b, lowerCase, str2, jSONArray}));
        } catch (Exception e2) {
            Logger.w("JsInterfaceImpl.exec pApiFeatureName=" + str + ";pActionName=" + str2 + ";pArgs=" + String.valueOf(jSONArray), e2);
            return null;
        }
    }
}
