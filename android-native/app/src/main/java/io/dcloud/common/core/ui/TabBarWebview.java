package io.dcloud.common.core.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dcloud.android.widget.TabView;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.ui.ReceiveJSValue;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.ArrayList;
import org.json.JSONException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class TabBarWebview extends AdaWebview {
    private boolean isVisible;
    private IApp mApp;
    private JSONArray mChildJson;
    private ArrayList<String> mPagePaths;
    ViewGroup mRoot;
    float mScale;
    private int mSelectIndex;
    private TabView mTabBar;
    private JSONObject mTabBarJson;
    private ArrayList<io.dcloud.common.core.ui.b> mTabItems;
    private FrameLayout mTabLayout;
    private l mWindowMgr;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        final /* synthetic */ io.dcloud.common.core.ui.b a;

        a(io.dcloud.common.core.ui.b bVar) {
            this.a = bVar;
        }

        @Override // java.lang.Runnable
        public void run() throws NumberFormatException {
            this.a.resize();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Animation.AnimationListener {
        b() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            TabBarWebview.this.mTabBar.setVisibility(8);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Runnable {
        c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            TabBarWebview.this.mTabBar.setVisibility(8);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Animation.AnimationListener {
        d() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) throws JSONException, NumberFormatException {
            TabBarWebview tabBarWebview = TabBarWebview.this;
            tabBarWebview.setTabItemsBottomMargin(tabBarWebview.mTabBar.getTabHeight());
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements Runnable {
        final /* synthetic */ io.dcloud.common.core.ui.b a;

        e(io.dcloud.common.core.ui.b bVar) {
            this.a = bVar;
        }

        @Override // java.lang.Runnable
        public void run() throws NumberFormatException {
            ViewGroup viewGroup = (ViewGroup) this.a.obtainWebviewParent().obtainMainView();
            if (viewGroup != null && viewGroup.getHeight() != this.a.obtainMainView().getHeight()) {
                AdaFrameItem.LayoutParamsUtil.setViewLayoutParams(viewGroup, 0, 0, -1, -1);
            }
            this.a.resize();
        }
    }

    public TabBarWebview(Context context, IApp iApp, l lVar, io.dcloud.common.core.ui.c cVar, org.json.JSONObject jSONObject) {
        super(context);
        this.mScale = 3.0f;
        this.mSelectIndex = 0;
        this.mFrameView = cVar;
        this.mWindowMgr = lVar;
        this.mTabItems = new ArrayList<>();
        this.mPagePaths = new ArrayList<>();
        initWebviewUUID("TabBar");
        initPagePaths(JSON.parseObject(jSONObject.toString()));
        this.mScale = context.getResources().getDisplayMetrics().density;
        this.mApp = iApp;
        JSONObject object = JSON.parseObject(jSONObject.toString());
        this.mTabBarJson = object;
        this.mChildJson = object.getJSONArray("child");
        this.mTabLayout = new FrameLayout(context);
        if (this.mTabBarJson.containsKey("selected")) {
            String string = this.mTabBarJson.getString("selected");
            if (!TextUtils.isEmpty(string)) {
                this.mSelectIndex = Integer.valueOf(string).intValue();
            }
        }
        this.mRoot = (FrameLayout) cVar.obtainMainView();
        TabView tabView = new TabView(context, this.mRoot, this.mTabBarJson, this.mScale, iApp);
        this.mTabBar = tabView;
        setMainView(tabView);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.bottomMargin = this.mTabBar.getTabHeight();
        this.mRoot.addView(this.mTabLayout, layoutParams);
        TabBarWebviewMgr.getInstance().setLancheTabBar(this);
        this.isVisible = true;
        cVar.addFrameItem(this, new ViewGroup.LayoutParams(-1, -1));
    }

    private void initPagePaths(JSONObject jSONObject) {
        if (jSONObject.containsKey(WXBasicComponentType.LIST)) {
            JSONArray jSONArray = jSONObject.getJSONArray(WXBasicComponentType.LIST);
            for (int i = 0; i < jSONArray.size(); i++) {
                String string = jSONArray.getJSONObject(i).getString("pagePath");
                if (!TextUtils.isEmpty(string)) {
                    if (string.startsWith("/")) {
                        string = string.substring(1);
                    }
                    this.mPagePaths.add(string);
                }
            }
        }
    }

    public void append(String str, ICallBack iCallBack) throws JSONException {
        l lVar;
        if (TextUtils.isEmpty(str) || (lVar = this.mWindowMgr) == null) {
            iCallBack.onCallBack(-1, null);
            return;
        }
        IMgr.MgrType mgrType = IMgr.MgrType.FeatureMgr;
        IApp iApp = this.mApp;
        Object objProcessEvent = lVar.processEvent(mgrType, 10, new Object[]{iApp, AbsoluteConst.F_UI, "findWebview", new String[]{iApp.obtainAppId(), str}});
        if (objProcessEvent == null || !(objProcessEvent instanceof IWebview)) {
            iCallBack.onCallBack(-1, null);
            return;
        }
        IFrameView iFrameViewObtainFrameView = ((IWebview) objProcessEvent).obtainFrameView();
        if (iFrameViewObtainFrameView instanceof io.dcloud.common.core.ui.b) {
            append((io.dcloud.common.core.ui.b) iFrameViewObtainFrameView);
            iCallBack.onCallBack(0, null);
        }
    }

    public boolean checkPagePathIsTab(String str) {
        ArrayList<String> arrayList = this.mPagePaths;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            String str2 = arrayList.get(i);
            i++;
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUrlToReload(String str) {
        try {
            ArrayList<io.dcloud.common.core.ui.b> arrayList = this.mTabItems;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                io.dcloud.common.core.ui.b bVar = arrayList.get(i);
                i++;
                io.dcloud.common.core.ui.b bVar2 = bVar;
                if (str.endsWith(".js")) {
                    bVar2.mWindowMgr.processEvent(IMgr.MgrType.FeatureMgr, 10, new Object[]{bVar2.obtainApp(), "weex,io.dcloud.feature.weex.WeexFeature", "updateReload", new Object[]{str}});
                    return true;
                }
                if (bVar2.obtainWebView().obtainUrl().startsWith(str)) {
                    bVar2.obtainWebView().reload();
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        super.dispose();
        TabView tabView = this.mTabBar;
        if (tabView != null) {
            tabView.dispose();
        }
        this.mTabItems.clear();
        ArrayList<String> arrayList = this.mPagePaths;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void evalJS(String str) {
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void evalJS(String str, ReceiveJSValue.ReceiveJSValueCallback receiveJSValueCallback) {
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void executeScript(String str) {
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public float getScale() {
        return this.mScale;
    }

    public int getSelectIndex() {
        return this.mSelectIndex;
    }

    public String getTabBarHeight() {
        return this.mTabBar.getTabHeightStr().substring(0, this.mTabBar.getTabHeightStr().length() - 2);
    }

    public void hideTabBar(JSONObject jSONObject) throws JSONException, NumberFormatException {
        if (this.isVisible) {
            boolean zBooleanValue = jSONObject.containsKey("animation") ? jSONObject.getBoolean("animation").booleanValue() : false;
            setTabItemsBottomMargin(0);
            this.mTabBar.bringToFront();
            this.mTabBar.bringMaskToFront();
            if (zBooleanValue) {
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, this.mTabBar.getTabHeight() + this.mTabBar.getMidHeight());
                translateAnimation.setDuration(100L);
                translateAnimation.setAnimationListener(new b());
                this.mTabBar.startAnimation(translateAnimation);
            } else {
                this.mTabBar.postDelayed(new c(), 150L);
            }
            this.isVisible = false;
        }
    }

    public void hideTabBarRedDot(JSONObject jSONObject) {
        this.mTabBar.hideTabBarRedDot(jSONObject);
    }

    public boolean isInsertLauch() {
        if (this.mChildJson != null) {
            for (int i = 0; i < this.mChildJson.size(); i++) {
                if (this.mChildJson.getString(i).equals("lauchwebview")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public IApp obtainApp() {
        return this.mApp;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public ViewGroup obtainWindowView() {
        return this.mRoot;
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    protected void onResize() {
        super.onResize();
    }

    public void popFrame(String str) {
        if (this.mTabLayout != null) {
            l lVar = this.mWindowMgr;
            IMgr.MgrType mgrType = IMgr.MgrType.FeatureMgr;
            IApp iApp = this.mApp;
            Object objProcessEvent = lVar.processEvent(mgrType, 10, new Object[]{iApp, AbsoluteConst.F_UI, "findWebview", new String[]{iApp.obtainAppId(), str}});
            if (objProcessEvent == null || !(objProcessEvent instanceof IWebview)) {
                return;
            }
            ((IWebview) objProcessEvent).obtainFrameView().obtainMainView().setVisibility(8);
        }
    }

    public void pushFrame(String str) {
        if (this.mTabLayout != null) {
            l lVar = this.mWindowMgr;
            IMgr.MgrType mgrType = IMgr.MgrType.FeatureMgr;
            IApp iApp = this.mApp;
            Object objProcessEvent = lVar.processEvent(mgrType, 10, new Object[]{iApp, AbsoluteConst.F_UI, "findWebview", new String[]{iApp.obtainAppId(), str}});
            if (objProcessEvent == null || !(objProcessEvent instanceof IWebview)) {
                return;
            }
            ((IWebview) objProcessEvent).obtainFrameView().obtainMainView().setVisibility(0);
        }
    }

    public void removeFrameView(io.dcloud.common.core.ui.b bVar) {
        FrameLayout frameLayout;
        if (bVar == null || (frameLayout = this.mTabLayout) == null) {
            return;
        }
        frameLayout.removeView(bVar.obtainMainView());
        if (this.mTabItems.contains(bVar)) {
            this.mTabItems.remove(bVar);
        }
        if (this.mTabItems.size() == 0 && this.mTabBar.getVisibility() == 0) {
            this.mTabBar.setVisibility(4);
        }
    }

    public void removeTabBarBadge(JSONObject jSONObject) {
        this.mTabBar.removeTabBarBadge(jSONObject);
    }

    public void setClickCallBack(ICallBack iCallBack) {
        this.mTabBar.setSingleCallbackListener(iCallBack);
    }

    public void setDoubleClickCallBack(ICallBack iCallBack) {
        this.mTabBar.setDoubleCallbackListener(iCallBack);
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void setIWebViewFocusable(boolean z) {
        super.setIWebViewFocusable(z);
        ArrayList<io.dcloud.common.core.ui.b> arrayList = this.mTabItems;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                io.dcloud.common.core.ui.b bVar = arrayList.get(i);
                i++;
                io.dcloud.common.core.ui.b bVar2 = bVar;
                if (bVar2 != null && bVar2.obtainWebView() != null) {
                    bVar2.obtainWebView().setIWebViewFocusable(z);
                }
            }
        }
        TabView tabView = this.mTabBar;
        if (tabView != null) {
            tabView.setIWebViewFocusable(z);
        }
    }

    public void setItem(JSONObject jSONObject) throws NumberFormatException {
        this.mTabBar.setTabBarItem(jSONObject);
    }

    public void setMask(JSONObject jSONObject) {
        this.mTabBar.setMask(jSONObject);
    }

    public void setMaskButtonClickCallBack(ICallBack iCallBack) {
        this.mTabBar.setMaskCallbackListener(iCallBack);
    }

    public void setMidButtonClickCallBack(ICallBack iCallBack) {
        this.mTabBar.setMidCallbackListener(iCallBack);
    }

    public void setStyle(JSONObject jSONObject) throws JSONException, NumberFormatException {
        if (jSONObject != null) {
            this.mTabBar.setTabBarStyle(jSONObject);
            if (jSONObject.containsKey("height")) {
                setTabItemsBottomMargin(this.isVisible ? this.mTabBar.getTabHeight() : 0);
            }
        }
    }

    public void setTabBarBadge(JSONObject jSONObject) {
        this.mTabBar.setTabBarBadge(jSONObject);
    }

    public void setTabItemsBottomMargin(int i) throws JSONException, NumberFormatException {
        if (this.mTabItems != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mTabLayout.getLayoutParams();
            layoutParams.bottomMargin = i;
            this.mTabLayout.setLayoutParams(layoutParams);
            ArrayList<io.dcloud.common.core.ui.b> arrayList = this.mTabItems;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                io.dcloud.common.core.ui.b bVar = arrayList.get(i2);
                i2++;
                io.dcloud.common.core.ui.b bVar2 = bVar;
                if (bVar2 != null && bVar2.obtainMainView() != null) {
                    org.json.JSONObject jSONObject = new org.json.JSONObject();
                    try {
                        jSONObject.put("top", "0px");
                        jSONObject.put("bottom", i > 0 ? this.mTabBar.getTabHeightStr() : "0px");
                        jSONObject.put("isTab", true);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    bVar2.obtainFrameOptions().updateViewData(jSONObject);
                    bVar2.obtainMainView().post(new e(bVar2));
                }
            }
            this.mFrameView.resize();
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void show(Animation animation) {
    }

    public void showTabBar(JSONObject jSONObject) throws JSONException, NumberFormatException {
        if (this.isVisible) {
            return;
        }
        if (jSONObject.containsKey("animation") ? jSONObject.getBoolean("animation").booleanValue() : false) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, this.mTabBar.getTabHeight() + this.mTabBar.getMidHeight(), 0.0f);
            translateAnimation.setDuration(100L);
            translateAnimation.setAnimationListener(new d());
            this.mTabBar.startAnimation(translateAnimation);
        } else {
            setTabItemsBottomMargin(this.mTabBar.getTabHeight());
        }
        this.mTabBar.setVisibility(0);
        this.isVisible = true;
    }

    public void showTabBarRedDot(JSONObject jSONObject) {
        this.mTabBar.showTabBarRedDot(jSONObject);
    }

    public void switchSelect(int i) throws NumberFormatException {
        this.mSelectIndex = i;
        this.mTabBar.switchTab(i);
    }

    public void tabItemActive(io.dcloud.common.core.ui.b bVar) {
        for (int i = 0; i < this.mTabItems.size(); i++) {
            io.dcloud.common.core.ui.b bVar2 = this.mTabItems.get(i);
            if (bVar == null || bVar2 != bVar) {
                this.mTabItems.get(i).obtainMainView().setImportantForAccessibility(4);
                bVar2.obtainMainView().setImportantForAccessibility(4);
            } else {
                bVar2.obtainMainView().setImportantForAccessibility(0);
            }
        }
    }

    public void updateMidButton(JSONObject jSONObject) {
        this.mTabBar.updateMidButton(jSONObject);
    }

    public void append(io.dcloud.common.core.ui.b bVar) throws JSONException {
        int iIndexOfChild;
        if (bVar != null) {
            View viewObtainMainView = bVar.obtainMainView();
            if (viewObtainMainView.getParent() != null) {
                bVar.p();
                bVar.getAnimOptions().mOption = (byte) 1;
                bVar.k.b(bVar);
                if (bVar.e()) {
                    this.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 28, bVar.b);
                    bVar.b = null;
                }
                bVar.r();
                bVar.b(false);
                bVar.getAnimOptions().mOption = (byte) 0;
                ViewGroup viewGroup = (ViewGroup) viewObtainMainView.getParent();
                iIndexOfChild = viewGroup == this.mTabLayout ? viewGroup.indexOfChild(viewObtainMainView) : -1;
                viewGroup.removeView(viewObtainMainView);
            } else {
                iIndexOfChild = -1;
            }
            bVar.setTabItem(true);
            this.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 22, bVar);
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            try {
                jSONObject.put("top", "0px");
                jSONObject.put("bottom", this.isVisible ? this.mTabBar.getTabHeightStr() : "0px");
                jSONObject.put("isTab", true);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            bVar.obtainFrameOptions().setParentViewRect(this.mFrameView.obtainFrameOptions());
            bVar.obtainFrameOptions().updateViewData(jSONObject);
            bVar.setParentFrameItem(this.mFrameView);
            this.mFrameView.mChildArrayList.add(bVar);
            bVar.inStack = true;
            bVar.isChildOfFrameView = true;
            this.mTabLayout.addView(viewObtainMainView, iIndexOfChild, new FrameLayout.LayoutParams(-1, -1));
            bVar.obtainWebView().setIWebViewFocusable(true);
            if (!this.mTabItems.contains(bVar)) {
                this.mTabItems.add(bVar);
            }
            if (this.mTabItems.size() > 0 && this.mTabBar.getVisibility() != 0 && this.isVisible) {
                this.mTabBar.setVisibility(0);
            }
            if (viewObtainMainView.getImportantForAccessibility() == 4) {
                viewObtainMainView.setImportantForAccessibility(0);
            }
            bVar.obtainMainView().post(new a(bVar));
        }
    }
}
