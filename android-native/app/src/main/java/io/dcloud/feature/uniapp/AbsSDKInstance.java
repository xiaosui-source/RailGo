package io.dcloud.feature.uniapp;

import android.content.Context;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import com.taobao.weex.IWXActivityStateListener;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.adapter.IDrawableLoader;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.adapter.URIAdapter;
import com.taobao.weex.bridge.EventResult;
import com.taobao.weex.common.WXModule;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface AbsSDKInstance extends IWXActivityStateListener, View.OnLayoutChangeListener {
    void addEventListener(String str, String str2);

    void fireEvent(String str, String str2);

    void fireEvent(String str, String str2, Map<String, Object> map);

    void fireEvent(String str, String str2, Map<String, Object> map, Map<String, Object> map2);

    void fireEvent(String str, String str2, Map<String, Object> map, Map<String, Object> map2, List<Object> list);

    void fireEvent(String str, String str2, Map<String, Object> map, Map<String, Object> map2, List<Object> list, EventResult eventResult);

    void fireGlobalEventCallback(String str, Map<String, Object> map);

    void fireModuleEvent(String str, WXModule wXModule, Map<String, Object> map);

    String getBundleUrl();

    Context getContext();

    int getDefaultFontSize();

    IDrawableLoader getDrawableLoader();

    IWXImgLoaderAdapter getImgLoaderAdapter();

    String getInstanceId();

    int getInstanceViewPortWidth();

    float getInstanceViewPortWidthWithFloat();

    Context getOriginalContext();

    WXSDKInstance getParentInstance();

    String getRenderType();

    View getRootView();

    URIAdapter getURIAdapter();

    int getWeexHeight();

    int getWeexWidth();

    boolean isCompilerWithUniapp();

    boolean isDestroy();

    boolean isFrameViewShow();

    boolean isImmersive();

    boolean onBackPressed();

    void onCreateFinish();

    boolean onCreateOptionsMenu(Menu menu);

    void onShowAnimationEnd();

    boolean onSupportNavigateUp();

    void registerRenderListener(IWXRenderListener iWXRenderListener);

    void reloadImages();

    void reloadPage(boolean z);

    void removeEventListener(String str);

    void removeEventListener(String str, String str2);

    Uri rewriteUri(Uri uri, String str);

    void runOnUiThread(Runnable runnable);

    void setBundleUrl(String str);

    void setDefaultFontSize(int i);

    void setImmersive(boolean z);

    void setRenderType(String str);

    void setSize(int i, int i2);

    void setUniPagePath(String str);
}
