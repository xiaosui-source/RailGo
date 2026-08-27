package com.facebook.fresco.vito.renderer;

import android.graphics.drawable.Drawable;
import com.taobao.weex.bridge.WXBridgeManager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: ImageDataModel.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\b\u0010\u0011\u001a\u00020\rH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007\u0082\u0001\u0003\u0012\u0013\u0014¨\u0006\u0015"}, d2 = {"Lcom/facebook/fresco/vito/renderer/ImageDataModel;", "", "<init>", "()V", "width", "", "getWidth", "()I", "height", "getHeight", "defaultPaintFlags", "getDefaultPaintFlags", "setCallback", "", WXBridgeManager.METHOD_CALLBACK, "Landroid/graphics/drawable/Drawable$Callback;", "onAttach", "onDetach", "Lcom/facebook/fresco/vito/renderer/BitmapImageDataModel;", "Lcom/facebook/fresco/vito/renderer/ColorIntImageDataModel;", "Lcom/facebook/fresco/vito/renderer/DrawableImageDataModel;", "renderer_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class ImageDataModel {
    private final int defaultPaintFlags;
    private final int height;
    private final int width;

    public /* synthetic */ ImageDataModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public void onAttach() {
    }

    public void onDetach() {
    }

    public void setCallback(Drawable.Callback callback) {
    }

    private ImageDataModel() {
        this.width = -1;
        this.height = -1;
        this.defaultPaintFlags = 1;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDefaultPaintFlags() {
        return this.defaultPaintFlags;
    }
}
