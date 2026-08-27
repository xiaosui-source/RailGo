package io.dcloud.common.adapter.util;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ViewOptions extends ViewRect {
    public static final int BG_NONE = -1;
    public String animationAlphaBackground;
    public int coverage;
    public HashMap<String, DragBean> dragData;
    public JSONObject mPullToRefresh;
    public JSONArray mSubNViews;
    public Object mTag;
    public boolean mUseHardwave;
    public String name;
    public JSONObject titleNView;
    public JSONObject transform;
    public JSONObject transition;
    public boolean scalable = false;
    public String mInjection = AbsoluteConst.TRUE;
    public String mPlusrequire = "normal";
    public boolean mDisablePlus = false;
    private String mScrollIndicator = "all";
    public float opacity = -1.0f;
    public int background = -1;
    public int maskColor = -1;
    public String strBackground = null;
    public boolean webviewBGTransparent = false;
    public String strTabBG = null;
    public String errorPage = null;
    public boolean mBounce = false;
    public String mCacheMode = "default";
    public String mVideoFullscree = "auto";
    public String popGesture = "none";
    public String historyBack = "none";
    public String mGeoInject = "none";
    public boolean dragH5NeedTouchEvent = false;
    public String backButtonAutoControl = "none";
    public boolean isAnimationOptimization = false;
    public boolean isUserSelect = true;
    public String softinputMode = "adjustResize";
    public JSONObject mUniNViewJson = null;
    public JSONObject mProgressJson = null;
    public JSONObject mDebugRefresh = null;
    public JSONObject mUniPageUrl = null;
    public Boolean isTabItem = Boolean.FALSE;
    public boolean isUniH5 = false;

    public ViewOptions() {
        this.mUseHardwave = true;
        this.mUseHardwave = MobilePhoneModel.checkPhoneBanAcceleration(Build.BRAND);
    }

    public static ViewOptions createViewOptionsData(ViewOptions viewOptions, ViewRect viewRect) {
        return createViewOptionsData(viewOptions, null, viewRect);
    }

    public String getScrollIndicator() {
        return this.mScrollIndicator;
    }

    public boolean hasBackground() {
        if (this.background == -1) {
            return (PdrUtil.isEmpty(this.strBackground) || this.strBackground.equals("transparent")) ? false : true;
        }
        return true;
    }

    public boolean hasMask() {
        return this.maskColor != -1;
    }

    public boolean hasTransparentValue() {
        if (isTransparent() || PdrUtil.checkAlphaTransparent(this.background)) {
            return true;
        }
        float f = this.opacity;
        return f >= 0.0f && f < 1.0f;
    }

    public boolean isTabHasBg() {
        return this.isTabItem.booleanValue() && !PdrUtil.isEmpty(this.strTabBG);
    }

    public boolean isTransparent() {
        return PdrUtil.isEquals("transparent", this.strBackground);
    }

    public void setBackButtonAutoControl(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has("backButtonAutoControl")) {
            return;
        }
        String string = JSONUtil.getString(jSONObject, "backButtonAutoControl");
        if ("none".equals(string) || "hide".equals(string) || AbsoluteConst.EVENTS_CLOSE.equals(string) || "quit".equals(string)) {
            this.backButtonAutoControl = string;
        }
    }

    public void setDragData(JSONObject jSONObject, JSONObject jSONObject2, IFrameView iFrameView, IFrameView iFrameView2, String str, View view) throws JSONException {
        try {
            if (this.dragData == null) {
                this.dragData = new HashMap<>();
            }
            DragBean dragBean = new DragBean();
            dragBean.dragCurrentViewOp = jSONObject;
            dragBean.dragBindViewOp = jSONObject2;
            dragBean.dragBindWebView = iFrameView;
            dragBean.dragCallBackWebView = iFrameView2;
            dragBean.dragCbId = str;
            dragBean.nativeView = view;
            if (jSONObject.has("direction")) {
                String string = jSONObject.getString("direction");
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                this.dragData.put(string.toLowerCase(Locale.ENGLISH), dragBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitleNView(JSONObject jSONObject, IWebview iWebview) throws JSONException {
        if (jSONObject != null) {
            if ("transparent".equals(jSONObject.optString("type"))) {
                String strOptString = jSONObject.has("titleColor") ? jSONObject.optString("titleColor") : jSONObject.has("titlecolor") ? jSONObject.optString("titlecolor") : null;
                if (!TextUtils.isEmpty(strOptString)) {
                    try {
                        int color = Color.parseColor(strOptString);
                        String hexString = Integer.toHexString(0);
                        if (1 == hexString.length()) {
                            hexString = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + hexString.toUpperCase();
                        }
                        String hexString2 = Integer.toHexString(Color.red(color));
                        if (1 == hexString2.length()) {
                            hexString2 = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + hexString2.toUpperCase();
                        }
                        String hexString3 = Integer.toHexString(Color.green(color));
                        if (1 == hexString3.length()) {
                            hexString3 = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + hexString3.toUpperCase();
                        }
                        String hexString4 = Integer.toHexString(Color.blue(color));
                        if (1 == hexString4.length()) {
                            hexString4 = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT + hexString4.toUpperCase();
                        }
                        String str = "#" + hexString + hexString2 + hexString3 + hexString4;
                        if (jSONObject.has("titleColor")) {
                            jSONObject.put("titleColor", str);
                        } else if (jSONObject.has("titlecolor")) {
                            jSONObject.put("titlecolor", str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            this.coverage = PdrUtil.convertToScreenInt("136px", PlatformUtil.SCREEN_WIDTH(iWebview.getContext()), 0, iWebview.getScale());
            this.coverage = PdrUtil.convertToScreenInt(jSONObject.optString("coverage"), PlatformUtil.SCREEN_WIDTH(iWebview.getContext()), this.coverage, iWebview.getScale());
            this.titleNView = jSONObject;
        }
    }

    @Override // io.dcloud.common.adapter.util.ViewRect
    public void updateViewData(ViewRect viewRect) {
        super.updateViewData(viewRect);
    }

    public static ViewOptions createViewOptionsData(ViewOptions viewOptions, ViewRect viewRect, ViewRect viewRect2) {
        if (viewOptions == null) {
            return null;
        }
        ViewOptions viewOptions2 = new ViewOptions();
        if (viewRect != null) {
            viewOptions2.setFrameParentViewRect(viewRect);
        }
        viewOptions2.mWebviewScale = viewOptions.mWebviewScale;
        viewOptions2.setParentViewRect(viewRect2);
        viewOptions2.updateViewData(viewOptions.mJsonViewOption);
        return viewOptions2;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x00bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0253  */
    @Override // io.dcloud.common.adapter.util.ViewRect
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean updateViewData(org.json.JSONObject r7) {
        /*
            Method dump skipped, instructions count: 624
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.util.ViewOptions.updateViewData(org.json.JSONObject):boolean");
    }
}
