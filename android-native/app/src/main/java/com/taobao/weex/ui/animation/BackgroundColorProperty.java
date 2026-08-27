package com.taobao.weex.ui.animation;

import android.graphics.drawable.ColorDrawable;
import android.util.Property;
import android.view.View;
import com.taobao.weex.ui.view.border.BorderDrawable;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXViewUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class BackgroundColorProperty extends Property<View, Integer> {
    private static final String TAG = "BackgroundColorAnimation";

    public BackgroundColorProperty() {
        super(Integer.class, "backgroundColor");
    }

    @Override // android.util.Property
    public Integer get(View view) {
        int color;
        BorderDrawable borderDrawable = WXViewUtils.getBorderDrawable(view);
        if (borderDrawable != null) {
            color = borderDrawable.getColor();
        } else if (view.getBackground() instanceof ColorDrawable) {
            color = ((ColorDrawable) view.getBackground()).getColor();
        } else {
            WXLogUtils.e(TAG, "Unsupported background type");
            color = 0;
        }
        return Integer.valueOf(color);
    }

    @Override // android.util.Property
    public void set(View view, Integer num) {
        BorderDrawable borderDrawable = WXViewUtils.getBorderDrawable(view);
        if (borderDrawable != null) {
            borderDrawable.setColor(num.intValue());
        } else if (view.getBackground() instanceof ColorDrawable) {
            ((ColorDrawable) view.getBackground()).setColor(num.intValue());
        } else {
            WXLogUtils.e(TAG, "Unsupported background type");
        }
    }
}
