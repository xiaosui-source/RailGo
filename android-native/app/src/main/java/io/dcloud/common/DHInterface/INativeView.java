package io.dcloud.common.DHInterface;

import android.view.View;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface INativeView {
    void attachToViewGroup(IFrameView iFrameView);

    int getInnerHeight();

    String getStyleBackgroundColor();

    int getStyleLeft();

    int getStyleWidth();

    String getViewId();

    String getViewType();

    String getViewUUId();

    boolean isAnimate();

    boolean isDock();

    boolean isDockTop();

    boolean isStatusBar();

    View obtanMainView();

    void setNativeShowType(boolean z);

    void setStyleBackgroundColor(int i);

    void setStyleBackgroundColor(String str);

    void setStyleLeft(int i);

    void setWebAnimationRuning(boolean z);

    JSONObject toJSON();
}
