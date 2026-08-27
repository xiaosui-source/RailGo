package com.dcloud.android.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class StatusBarView extends View {
    private int mStatusBarHeight;

    public StatusBarView(Context context) {
        super(context);
        this.mStatusBarHeight = 0;
    }

    public void setStatusBarHeight(int i) {
        this.mStatusBarHeight = i;
        setMeasuredDimension(-1, i);
        if (getLayoutParams() == null) {
            setLayoutParams(new ViewGroup.MarginLayoutParams(-1, this.mStatusBarHeight));
        }
    }
}
