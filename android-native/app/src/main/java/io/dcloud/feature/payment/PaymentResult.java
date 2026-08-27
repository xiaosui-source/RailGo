package io.dcloud.feature.payment;

import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.PdrUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class PaymentResult implements IReflectAble {
    AbsPaymentChannel a;
    public String description;
    public String rawDataJson;
    public String signature;
    public String tradeno;
    public String url;

    public PaymentResult(AbsPaymentChannel absPaymentChannel) {
        this.a = absPaymentChannel;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AbsoluteConst.XML_CHANNEL, this.a.toJSONObject());
            jSONObject.put("description", this.description);
            jSONObject.put("url", this.url);
            jSONObject.put("signature", this.signature);
            jSONObject.put("tradeno", this.tradeno);
            if (!PdrUtil.isEmpty(this.rawDataJson)) {
                jSONObject.put("rawdata", new JSONObject(this.rawDataJson).toString());
            }
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }
}
