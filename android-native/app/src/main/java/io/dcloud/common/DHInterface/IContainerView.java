package io.dcloud.common.DHInterface;

import android.view.ViewGroup;
import io.dcloud.common.adapter.ui.AdaFrameItem;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IContainerView {
    void addFrameItem(AdaFrameItem adaFrameItem);

    void addFrameItem(AdaFrameItem adaFrameItem, ViewGroup.LayoutParams layoutParams);

    void removeAllFrameItem();

    void removeFrameItem(AdaFrameItem adaFrameItem);
}
