package com.taobao.weex.utils;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import java.lang.reflect.Constructor;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class StaticLayoutProxy {
    private static Constructor<StaticLayout> layoutConstructor;

    public static StaticLayout create(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, float f, float f2, boolean z, boolean z2) {
        if (!z2) {
            return new StaticLayout(charSequence, textPaint, i, alignment, f, f2, z);
        }
        StaticLayout staticLayoutCreateInternal = createInternal(charSequence, textPaint, i, alignment, TextDirectionHeuristics.RTL, f, f2, z);
        return staticLayoutCreateInternal != null ? staticLayoutCreateInternal : new StaticLayout(charSequence, textPaint, i, alignment, f, f2, z);
    }

    private static StaticLayout createInternal(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z) {
        try {
            if (layoutConstructor == null) {
                Class cls = Float.TYPE;
                layoutConstructor = StaticLayout.class.getConstructor(CharSequence.class, TextPaint.class, Integer.TYPE, Layout.Alignment.class, TextDirectionHeuristic.class, cls, cls, Boolean.TYPE);
            }
            Constructor<StaticLayout> constructor = layoutConstructor;
            if (constructor != null) {
                return constructor.newInstance(charSequence, textPaint, Integer.valueOf(i), alignment, textDirectionHeuristic, Float.valueOf(f), Float.valueOf(f2), Boolean.valueOf(z));
            }
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
