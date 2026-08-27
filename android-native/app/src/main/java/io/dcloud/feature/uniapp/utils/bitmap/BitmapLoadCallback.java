package io.dcloud.feature.uniapp.utils.bitmap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface BitmapLoadCallback<T> {
    void onFailure(String str, Throwable th);

    void onSuccess(String str, T t);
}
