package com.alibaba.android.bindingx.core.internal;

import android.text.TextUtils;

/* loaded from: classes.dex */
class Euler {
    private static final String DEFAULT_ORDER = "XYZ";
    String order;
    boolean isEuler = true;
    double x = 0.0d;
    double y = 0.0d;
    double z = 0.0d;

    Euler() {
    }

    void setValue(double d, double d2, double d3, String str) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        if (TextUtils.isEmpty(str)) {
            str = DEFAULT_ORDER;
        }
        this.order = str;
    }
}
