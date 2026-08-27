package io.dcloud.feature.weex.adapter.webview;

import android.view.View;
import com.taobao.weex.ui.view.IWebView;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public interface IDCWebView extends IWebView {
    View getWebView();

    void setUserAgent(String str, boolean z);
}
