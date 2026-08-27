package com.taobao.weex.common;

import android.widget.ImageView;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXImageStrategy {
    public int blurRadius;
    ImageListener imageListener;
    public String instanceId;
    boolean isAutoCompression = true;

    @Deprecated
    public boolean isClipping;
    public boolean isSharpen;
    public String placeHolder;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface ImageListener {
        void onImageFinish(String str, ImageView imageView, boolean z, Map map);
    }

    public WXImageStrategy() {
    }

    public ImageListener getImageListener() {
        return this.imageListener;
    }

    public boolean isAutoCompression() {
        return this.isAutoCompression;
    }

    public void setAutoCompression(boolean z) {
        this.isAutoCompression = z;
    }

    public void setImageListener(ImageListener imageListener) {
        this.imageListener = imageListener;
    }

    public WXImageStrategy(String str) {
        this.instanceId = str;
    }
}
