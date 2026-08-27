package com.taobao.weex.ui.animation;

import android.util.Property;
import android.view.View;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class CameraDistanceProperty extends Property<View, Float> {
    private static final String TAG = "CameraDistance";
    private static CameraDistanceProperty instance;

    private CameraDistanceProperty() {
        super(Float.class, TAG);
    }

    static Property<View, Float> getInstance() {
        return instance;
    }

    @Override // android.util.Property
    public void set(View view, Float f) {
        view.setCameraDistance(f.floatValue());
    }

    @Override // android.util.Property
    public Float get(View view) {
        return Float.valueOf(view.getCameraDistance());
    }
}
