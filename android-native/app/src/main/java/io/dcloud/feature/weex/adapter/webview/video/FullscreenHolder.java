package io.dcloud.feature.weex.adapter.webview.video;

import android.R;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class FullscreenHolder extends FrameLayout {
    public FullscreenHolder(Context context) {
        super(context);
        setBackgroundColor(context.getResources().getColor(R.color.black));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }
}
