package io.dcloud.feature.weex.adapter.Fresco;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.VisibilityCallback;
import com.facebook.drawee.generic.RootDrawable;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCRootDrawable extends RootDrawable {
    private boolean isRefresh;
    private VisibilityCallback mVisibilityCallback;

    public DCRootDrawable(Drawable drawable) {
        super(drawable);
        this.isRefresh = true;
    }

    @Override // com.facebook.drawee.generic.RootDrawable, com.facebook.drawee.drawable.ForwardingDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (isVisible()) {
            VisibilityCallback visibilityCallback = this.mVisibilityCallback;
            if (visibilityCallback != null) {
                visibilityCallback.onDraw();
            }
            super.draw(canvas);
        }
    }

    public void setRefresh(boolean z) {
        this.isRefresh = z;
    }

    @Override // com.facebook.drawee.generic.RootDrawable, com.facebook.drawee.drawable.VisibilityAwareDrawable
    public void setVisibilityCallback(VisibilityCallback visibilityCallback) {
        this.mVisibilityCallback = visibilityCallback;
    }

    @Override // com.facebook.drawee.generic.RootDrawable, com.facebook.drawee.drawable.ForwardingDrawable, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        VisibilityCallback visibilityCallback = this.mVisibilityCallback;
        if (visibilityCallback != null && this.isRefresh) {
            try {
                visibilityCallback.onVisibilityChange(z);
            } catch (Exception unused) {
            }
        }
        return super.setVisible(z, z2);
    }
}
