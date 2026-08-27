package io.dcloud.feature.weex;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import com.taobao.weex.WXSDKInstance;
import io.dcloud.common.DHInterface.IKeyHandler;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IUniNView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.weex.adapter.widget.refresh.DCWeexBaseRefreshLayout;
import io.dcloud.feature.weex.adapter.widget.refresh.WeexDcRefreshLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class WXBaseWrapper extends WeexDcRefreshLayout implements IUniNView {
    protected static int DE_INDEX = -1;
    protected String mPath;
    protected String mSrcPath;
    protected WXAnalyzerDelegate mWXAnaly;
    protected WXSDKInstance mWXSDKInstance;
    protected IWebview mWebview;
    protected String mWxId;

    public WXBaseWrapper(Context context) {
        super(context);
        this.mPath = null;
        this.mSrcPath = null;
        setEnabled(false);
    }

    private void parseData(JSONObject jSONObject) {
        IWebview iWebview = this.mWebview;
        if (iWebview == null) {
            return;
        }
        ViewOptions viewOptionsObtainFrameOptions = ((AdaFrameView) iWebview.obtainFrameView()).obtainFrameOptions();
        String strOptString = jSONObject.optString("offset");
        int iConvertToScreenInt = !TextUtils.isEmpty(strOptString) ? PdrUtil.convertToScreenInt(strOptString, viewOptionsObtainFrameOptions.height, 0, this.mWebview.getScale()) : 0;
        String strOptString2 = jSONObject.optString("height");
        int iConvertToScreenInt2 = (int) this.mTotalDragDistance;
        if (!TextUtils.isEmpty(strOptString2)) {
            iConvertToScreenInt2 = PdrUtil.convertToScreenInt(strOptString2, viewOptionsObtainFrameOptions.height, iConvertToScreenInt2, this.mWebview.getScale());
        }
        int i = iConvertToScreenInt2;
        String strOptString3 = jSONObject.optString(AbsoluteConst.PULL_REFRESH_RANGE);
        int iConvertToScreenInt3 = (int) this.mSpinnerFinalOffset;
        if (!TextUtils.isEmpty(strOptString3)) {
            iConvertToScreenInt3 = PdrUtil.convertToScreenInt(strOptString3, viewOptionsObtainFrameOptions.height, iConvertToScreenInt3, this.mWebview.getScale());
        }
        int i2 = iConvertToScreenInt3 + this.mOriginalOffsetTop;
        String strOptString4 = jSONObject.optString("color");
        int color = Color.parseColor("#2BD009");
        if (!TextUtils.isEmpty(strOptString4) && strOptString4.startsWith("#")) {
            try {
                color = Color.parseColor(strOptString4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setColorSchemeColors(color);
        setProgressViewOffset(false, this.mOriginalOffsetTop, i2, i, iConvertToScreenInt);
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public void beginPullRefresh() {
        beginRefresh();
    }

    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setEnabled(false);
        recoveryInstance();
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public void endPullToRefresh() {
        setRefreshing(false);
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public String evalJs(String str, int i) {
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public boolean fireGlobalEvent(String str, Map<String, Object> map) {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance == null) {
            return false;
        }
        wXSDKInstance.fireGlobalEventCallback(str, map);
        return true;
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public String getType() {
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public void initRefresh(JSONObject jSONObject) {
        if (jSONObject != null) {
            boolean z = Boolean.parseBoolean(JSONUtil.getString(jSONObject, AbsoluteConst.PULL_REFRESH_SUPPORT));
            String strOptString = jSONObject.optString("style", "default");
            if (!z || !strOptString.equals("circle")) {
                setOnRefreshListener(null);
                setEnabled(false);
            } else {
                setEnabled(true);
                setOnRefreshListener(new DCWeexBaseRefreshLayout.OnRefreshListener() { // from class: io.dcloud.feature.weex.WXBaseWrapper.1
                    @Override // io.dcloud.feature.weex.adapter.widget.refresh.DCWeexBaseRefreshLayout.OnRefreshListener
                    public void onRefresh() {
                        WXBaseWrapper.this.onRefresh();
                    }
                });
                parseData(jSONObject);
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public void loadTemplate(JSONObject jSONObject) {
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public ViewGroup obtainMainView() {
        return this;
    }

    public void onActivityPause() {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null) {
            wXSDKInstance.onActivityPause();
            WXAnalyzerDelegate wXAnalyzerDelegate = this.mWXAnaly;
            if (wXAnalyzerDelegate != null) {
                wXAnalyzerDelegate.onPause();
            }
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null) {
            wXSDKInstance.onActivityResult(i, i2, intent);
        }
    }

    public void onActivityResume() {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null) {
            wXSDKInstance.onActivityResume();
            WXAnalyzerDelegate wXAnalyzerDelegate = this.mWXAnaly;
            if (wXAnalyzerDelegate != null) {
                wXAnalyzerDelegate.onResume();
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public void onDestroy() {
        destroy();
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        IWebview iWebview;
        boolean zOnKeyDown = super.onKeyDown(i, keyEvent);
        if (zOnKeyDown && (iWebview = this.mWebview) != null && (iWebview.getActivity() instanceof IKeyHandler)) {
            ((IKeyHandler) this.mWebview.getActivity()).onKeyEventExecute(ISysEventListener.SysEventType.onKeyDown, i, keyEvent);
        }
        return zOnKeyDown;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        IWebview iWebview;
        boolean zOnKeyUp = super.onKeyUp(i, keyEvent);
        if (zOnKeyUp && (iWebview = this.mWebview) != null && (iWebview.getActivity() instanceof IKeyHandler)) {
            ((IKeyHandler) this.mWebview.getActivity()).onKeyEventExecute(ISysEventListener.SysEventType.onKeyUp, i, keyEvent);
        }
        return zOnKeyUp;
    }

    public void onReady() {
    }

    protected void onRefresh() {
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null) {
            wXSDKInstance.onRequestPermissionsResult(i, strArr, iArr);
        }
    }

    public void recoveryInstance() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null) {
            wXSDKInstance.registerRenderListener(null);
            this.mWXSDKInstance.onActivityDestroy();
            WXAnalyzerDelegate wXAnalyzerDelegate = this.mWXAnaly;
            if (wXAnalyzerDelegate != null) {
                wXAnalyzerDelegate.onDestroy();
                this.mWXAnaly = null;
            }
            this.mWXSDKInstance = null;
            clearTarget();
        }
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public void reload() {
    }

    @Override // io.dcloud.common.DHInterface.IUniNView
    public void titleNViewRefresh() {
    }
}
