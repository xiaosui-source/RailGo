package io.dcloud.feature.payment;

import android.content.Context;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AbsPaymentChannel implements IReflectAble {
    private String a;
    protected String description;
    protected String featureName;
    protected String id;
    protected Context mContext;
    protected final IPaymentListener mListener = new a();
    protected IWebview mWebview;
    protected String name;
    protected boolean serviceReady;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements IPaymentListener {
        a() {
        }

        @Override // io.dcloud.feature.payment.IPaymentListener
        public void onError(int i, String str) {
            String json = DOMException.toJSON(i, str);
            AbsPaymentChannel absPaymentChannel = AbsPaymentChannel.this;
            Deprecated_JSUtil.execCallback(absPaymentChannel.mWebview, absPaymentChannel.a, json, JSUtil.ERROR, true, false);
        }

        @Override // io.dcloud.feature.payment.IPaymentListener
        public void onSuccess(PaymentResult paymentResult) {
            AbsPaymentChannel absPaymentChannel = AbsPaymentChannel.this;
            JSUtil.execCallback(absPaymentChannel.mWebview, absPaymentChannel.a, paymentResult.toJSONObject(), JSUtil.OK, false);
        }
    }

    public String getFullDescription() {
        return this.featureName + this.description;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    protected abstract void installService();

    protected void isReadyToPay(String str, String str2) {
    }

    protected abstract void request(String str);

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", this.id);
            jSONObject.put("description", this.description);
            jSONObject.put("serviceReady", this.serviceReady);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public void updateWebview(IWebview iWebview) {
        this.mWebview = iWebview;
    }

    final void a(String str, String str2) {
        this.a = str2;
        request(str);
    }
}
