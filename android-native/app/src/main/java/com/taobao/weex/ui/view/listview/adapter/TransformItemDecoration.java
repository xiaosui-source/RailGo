package com.taobao.weex.ui.view.listview.adapter;

import android.graphics.Canvas;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TransformItemDecoration extends RecyclerView.ItemDecoration {
    float mAlpha;
    boolean mIsVertical;
    int mRotation;
    float mScaleX;
    float mScaleY;
    int mXTranslate;
    int mYTranslate;

    public TransformItemDecoration(boolean z, float f, int i, int i2, int i3, float f2, float f3) {
        this.mIsVertical = z;
        this.mAlpha = f;
        this.mXTranslate = i;
        this.mYTranslate = i2;
        this.mRotation = i3;
        this.mScaleX = f2;
        this.mScaleY = f3;
    }

    private void updateItem(View view, int i, int i2) {
        int width;
        int left;
        if (this.mIsVertical) {
            int height = view.getHeight();
            left = view.getTop() + (height / 2);
            width = height;
            i = i2;
        } else {
            width = view.getWidth();
            left = view.getLeft() + (width / 2);
        }
        float fMin = Math.min(1.0f, Math.max(-1.0f, (1.0f / ((width + i) / 2)) * (left - (i / 2))));
        float f = this.mAlpha;
        if (f > 0.0f) {
            view.setAlpha(1.0f - (f * Math.abs(fMin)));
        }
        float f2 = this.mScaleX;
        if (f2 > 0.0f || this.mScaleY > 0.0f) {
            view.setScaleX(1.0f - (f2 * Math.abs(fMin)));
            view.setScaleY(1.0f - (this.mScaleY * Math.abs(fMin)));
        }
        int i3 = this.mRotation;
        if (i3 != 0) {
            view.setRotation(i3 * fMin);
        }
        int i4 = this.mXTranslate;
        if (i4 != 0) {
            view.setTranslationX(i4 * Math.abs(fMin));
        }
        int i5 = this.mYTranslate;
        if (i5 != 0) {
            view.setTranslationY(i5 * Math.abs(fMin));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);
        int width = recyclerView.getWidth();
        int height = recyclerView.getHeight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            updateItem(recyclerView.getChildAt(i), width, height);
        }
    }
}
