package io.dcloud.feature.oauth;

import io.dcloud.common.DHInterface.BaseFeature;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Base64;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class BaseOAuthService extends BaseFeature.BaseModule implements IReflectAble {
    protected static final String KEY_ACCESS_TOKEN = "access_token";
    protected static final String KEY_APPID = "appid";
    protected static final String KEY_APPKEY = "appkey";
    protected static final String KEY_APSECRET = "appsecret";
    protected static final String KEY_AUTHRESULT = "authResult";
    protected static final String KEY_EMAIL = "email";
    protected static final String KEY_EXPIRES_IN = "expires_in";
    protected static final String KEY_HEADIMGURL = "headimgurl";
    protected static final String KEY_NICKNAME = "nickname";
    protected static final String KEY_OPENID = "openid";
    protected static final String KEY_REDIRECT_URI = "redirect_uri";
    protected static final String KEY_REFRESH_TOKEN = "refresh_token";
    protected static final String KEY_SCOPE = "scope";
    protected static final String KEY_STATE = "state";
    protected static final String KEY_UNIONID = "unionid";
    protected static final String KEY_UNIVERIFYINFO = "univerifyInfo";
    protected static final String KEY_USERINFO = "userInfo";
    protected static final String NULL = "null";
    protected JSONObject authResult;
    protected JSONObject univerifyInfo;
    protected JSONObject userInfo;
    protected IWebview mLoginWebViewImpl = null;
    protected String mLoginCallbackId = null;
    protected JSONObject mLoginOptions = null;
    protected boolean nativeClient = false;
    protected IWebview mAuthWebview = null;
    protected String mAuthCallbackId = null;
    protected JSONObject mAuthOptions = null;
    protected IWebview mLogoutWebViewImpl = null;
    protected String mLogoutCallbackId = null;
    protected IWebview mPreLoginWebViewImpl = null;
    protected String mPreLoginCallbackId = null;
    protected IWebview mOtherClickWebViewImpl = null;
    protected String mOtherClickCallbackId = null;
    protected IWebview mGetUserInfoWebViewImpl = null;
    protected String mGetUserInfoCallbackId = null;
    protected IWebview mAddPhoneNumberWebViewImpl = null;
    protected String mAddPhoneNumberCallbackId = null;

    public void addPhoneNumber(IWebview iWebview, JSONArray jSONArray) {
        this.mAddPhoneNumberWebViewImpl = iWebview;
        try {
            this.mAddPhoneNumberCallbackId = jSONArray.getString(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void authorize(IWebview iWebview, JSONArray jSONArray) {
        initMetaData();
        this.mAuthWebview = iWebview;
        this.mAuthCallbackId = jSONArray.optString(1, "");
        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(2);
        this.mAuthOptions = jSONObjectOptJSONObject;
        initAuthOptions(jSONObjectOptJSONObject);
    }

    protected String checkToken(String str) {
        return new String(NetTool.httpGet(str));
    }

    public void closeAuthView(IWebview iWebview, JSONArray jSONArray) {
    }

    public String decrypt(String str) throws Exception {
        return Base64.decodeString(str, true, 5);
    }

    public String encrypt(String str) throws Exception {
        return Base64.encodeString(str, true, 5);
    }

    public boolean getCheckBoxState() {
        return false;
    }

    public JSONObject getErrorJsonbject(int i, String str) {
        try {
            return new JSONObject(DOMException.toJSON(i, str));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    protected String getToken(String str) {
        byte[] bArrHttpGet = NetTool.httpGet(str);
        if (bArrHttpGet != null) {
            return new String(bArrHttpGet);
        }
        return null;
    }

    public void getUserInfo(IWebview iWebview, JSONArray jSONArray) {
        this.mGetUserInfoWebViewImpl = iWebview;
        try {
            this.mGetUserInfoCallbackId = jSONArray.getString(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected String getValue(String str) {
        try {
            String strEncrypt = encrypt(str);
            String bundleData = SP.getBundleData(this.mApplicationContext, "oauth_" + this.id, strEncrypt);
            return bundleData == null ? "{}" : decrypt(bundleData);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public boolean hasFullConfigData() {
        return false;
    }

    public boolean hasGeneralError(IWebview iWebview, String str) {
        if (hasFullConfigData()) {
            return false;
        }
        Deprecated_JSUtil.execCallback(iWebview, str, StringUtil.format(DOMException.JSON_ERROR_INFO, -7, DOMException.toString(DOMException.MSG_BUSINESS_PARAMETER_HAS_NOT)), JSUtil.ERROR, true, false);
        return true;
    }

    public void initAuthOptions(JSONObject jSONObject) {
    }

    public void initMetaData() {
    }

    public void login(IWebview iWebview, JSONArray jSONArray) {
        initMetaData();
        this.mLoginWebViewImpl = iWebview;
        try {
            this.mLoginCallbackId = jSONArray.getString(1);
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(2);
            this.mLoginOptions = jSONObjectOptJSONObject;
            initAuthOptions(jSONObjectOptJSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logout(IWebview iWebview, JSONArray jSONArray) {
        this.mLogoutWebViewImpl = iWebview;
        try {
            this.mLogoutCallbackId = jSONArray.getString(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject makeResultJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_AUTHRESULT, this.authResult);
            jSONObject.put(KEY_USERINFO, this.userInfo);
            jSONObject.put(KEY_UNIVERIFYINFO, this.univerifyInfo);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public JSONObject makeResultJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_AUTHRESULT, this.authResult);
            jSONObject.put(KEY_USERINFO, this.userInfo);
            jSONObject.put(KEY_UNIVERIFYINFO, this.univerifyInfo);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    protected void onAddPhoneNumberFinished(JSONObject jSONObject, boolean z) {
        JSUtil.execCallback(this.mAddPhoneNumberWebViewImpl, this.mAddPhoneNumberCallbackId, jSONObject, z ? JSUtil.OK : JSUtil.ERROR, false);
    }

    protected void onGetUserInfoFinished(JSONObject jSONObject, boolean z) {
        JSUtil.execCallback(this.mGetUserInfoWebViewImpl, this.mGetUserInfoCallbackId, jSONObject, z ? JSUtil.OK : JSUtil.ERROR, false);
    }

    protected void onLoginFinished(JSONObject jSONObject, boolean z) {
        JSUtil.execCallback(this.mLoginWebViewImpl, this.mLoginCallbackId, jSONObject, z ? JSUtil.OK : JSUtil.ERROR, false);
        this.mLoginWebViewImpl = null;
        this.mLoginCallbackId = null;
    }

    protected void onLogoutFinished(JSONObject jSONObject, boolean z) {
        JSUtil.execCallback(this.mLogoutWebViewImpl, this.mLogoutCallbackId, jSONObject, z ? JSUtil.OK : JSUtil.ERROR, false);
    }

    protected void onPreLoginFinished(JSONObject jSONObject, boolean z) {
        JSUtil.execCallback(this.mPreLoginWebViewImpl, this.mPreLoginCallbackId, jSONObject, z ? JSUtil.OK : JSUtil.ERROR, false);
    }

    public void otherLoginButtonClick(IWebview iWebview, JSONArray jSONArray) {
        this.mOtherClickWebViewImpl = iWebview;
        try {
            this.mOtherClickCallbackId = jSONArray.getString(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void preLogin(IWebview iWebview, JSONArray jSONArray) {
        initMetaData();
        this.mPreLoginWebViewImpl = iWebview;
        try {
            this.mPreLoginCallbackId = jSONArray.getString(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected String refreshToken(String str) {
        byte[] bArrHttpGet = NetTool.httpGet(str);
        if (bArrHttpGet != null) {
            return new String(bArrHttpGet);
        }
        return null;
    }

    protected void removeToken() {
        SP.clearBundle(this.mApplicationContext, "oauth_" + this.id);
    }

    protected void removeValue(String str) {
        try {
            String strEncrypt = encrypt(str);
            SP.removeBundleData(this.mApplicationContext, "oauth_" + this.id, strEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void saveValue(String str, String str2) {
        try {
            String strEncrypt = encrypt(str);
            String strEncrypt2 = encrypt(str2);
            SP.setBundleData(this.mApplicationContext, "oauth_" + this.id, strEncrypt, strEncrypt2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // io.dcloud.common.DHInterface.BaseFeature.BaseModule
    public JSONObject toJSONObject() throws JSONException {
        String str = this.id;
        String str2 = this.description;
        Boolean boolValueOf = Boolean.valueOf(this.nativeClient);
        Object obj = this.authResult;
        Object obj2 = NULL;
        if (obj == null) {
            obj = NULL;
        }
        Object obj3 = this.userInfo;
        if (obj3 != null) {
            obj2 = obj3;
        }
        return new JSONObject(StringUtil.format("{id:'%s',description:'%s', nativeClient:%b, authResult:%s,userInfo:%s}", str, str2, boolValueOf, obj, obj2));
    }

    protected String getUserInfo(String str) {
        byte[] bArrHttpGet = NetTool.httpGet(str);
        if (PdrUtil.isEmpty(bArrHttpGet)) {
            return null;
        }
        return new String(bArrHttpGet);
    }

    public JSONObject getErrorJsonbject(String str, String str2) {
        try {
            return new JSONObject(DOMException.toJSON(str, str2));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public JSONObject getErrorJsonbject(int i, String str, int i2) {
        try {
            return new JSONObject(DOMException.toJSON(i, i + ":" + str, i2));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
