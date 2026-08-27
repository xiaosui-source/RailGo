package com.taobao.weex.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewpager.widget.ViewPager;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.view.BaseFrameLayout;
import com.taobao.weex.ui.view.WXCircleIndicator;
import com.taobao.weex.ui.view.WXCirclePageAdapter;
import com.taobao.weex.ui.view.WXCircleViewPager;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXSliderNeighbor extends WXSlider {
    public static final String CURRENT_ITEM_SCALE = "currentItemScale";
    private static final float DEFAULT_CURRENT_ITEM_SCALE = 0.9f;
    private static final float DEFAULT_NEIGHBOR_ALPHA = 0.6f;
    private static final float DEFAULT_NEIGHBOR_SCALE = 0.8f;
    private static final int DEFAULT_NEIGHBOR_SPACE = 25;
    public static final String NEIGHBOR_ALPHA = "neighborAlpha";
    public static final String NEIGHBOR_SCALE = "neighborScale";
    public static final String NEIGHBOR_SPACE = "neighborSpace";
    private ZoomTransformer mCachedTransformer;
    private float mCurrentItemScale;
    private float mNeighborAlpha;
    private float mNeighborScale;
    private float mNeighborSpace;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new WXSliderNeighbor(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    class ZoomTransformer implements ViewPager.PageTransformer {
        ZoomTransformer() {
        }

        @Override // androidx.viewpager.widget.ViewPager.PageTransformer
        public void transformPage(View view, float f) {
            View childAt;
            int pagePosition = WXSliderNeighbor.this.mAdapter.getPagePosition(view);
            int currentItem = WXSliderNeighbor.this.mViewPager.getCurrentItem();
            int realCount = WXSliderNeighbor.this.mAdapter.getRealCount();
            boolean z = (currentItem == 0 || currentItem == realCount + (-1) || Math.abs(pagePosition - currentItem) <= 1) ? false : true;
            if (currentItem == 0 && pagePosition < realCount - 1 && pagePosition > 1) {
                z = true;
            }
            int i = realCount - 1;
            if (currentItem == i && pagePosition < realCount - 2 && pagePosition > 0) {
                z = true;
            }
            if (z || (childAt = ((ViewGroup) view).getChildAt(0)) == null) {
                return;
            }
            if (f <= (-realCount) + 1) {
                f += realCount;
            }
            if (f >= i) {
                f -= realCount;
            }
            if (f < -1.0f || f > 1.0f) {
                return;
            }
            float fAbs = Math.abs(Math.abs(f) - 1.0f);
            float f2 = WXSliderNeighbor.this.mNeighborScale + ((WXSliderNeighbor.this.mCurrentItemScale - WXSliderNeighbor.this.mNeighborScale) * fAbs);
            float f3 = ((1.0f - WXSliderNeighbor.this.mNeighborAlpha) * fAbs) + WXSliderNeighbor.this.mNeighborAlpha;
            float fCalculateTranslation = WXSliderNeighbor.this.calculateTranslation(view);
            if (f > 0.0f) {
                float f4 = -(f * fCalculateTranslation);
                childAt.setTranslationX(f4);
                view.setTranslationX(f4);
            } else if (f == 0.0f) {
                view.setTranslationX(0.0f);
                childAt.setTranslationX(0.0f);
                WXSliderNeighbor wXSliderNeighbor = WXSliderNeighbor.this;
                wXSliderNeighbor.updateAdapterScaleAndAlpha(wXSliderNeighbor.mNeighborAlpha, WXSliderNeighbor.this.mNeighborScale);
            } else {
                if (realCount == 2 && Math.abs(f) == 1.0f) {
                    return;
                }
                float f5 = (-f) * fCalculateTranslation;
                childAt.setTranslationX(f5);
                view.setTranslationX(f5);
            }
            childAt.setScaleX(f2);
            childAt.setScaleY(f2);
            childAt.setAlpha(f3);
        }
    }

    public WXSliderNeighbor(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.mNeighborScale = DEFAULT_NEIGHBOR_SCALE;
        this.mNeighborAlpha = DEFAULT_NEIGHBOR_ALPHA;
        this.mNeighborSpace = 25.0f;
        this.mCurrentItemScale = DEFAULT_CURRENT_ITEM_SCALE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateTranslation(View view) {
        if (!(view instanceof ViewGroup)) {
            return 0.0f;
        }
        View childAt = ((ViewGroup) view).getChildAt(0);
        return ((view.getMeasuredWidth() - (childAt.getMeasuredWidth() * this.mNeighborScale)) / 4.0f) + ((((view.getMeasuredWidth() - (childAt.getMeasuredWidth() * this.mCurrentItemScale)) / 2.0f) - WXViewUtils.getRealPxByWidth(this.mNeighborSpace, getInstance().getInstanceViewPortWidthWithFloat())) / 2.0f);
    }

    private void moveLeft(View view, float f, float f2, float f3) {
        ViewGroup viewGroup = (ViewGroup) view;
        updateScaleAndAlpha(viewGroup.getChildAt(0), f2, f3);
        view.setTranslationX(f);
        viewGroup.getChildAt(0).setTranslationX(f);
    }

    private void moveRight(View view, float f, float f2, float f3) {
        moveLeft(view, -f, f2, f3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAdapterScaleAndAlpha(final float f, final float f2) {
        List<View> views = this.mAdapter.getViews();
        int currentItem = this.mViewPager.getCurrentItem();
        if (views.size() > 0) {
            final View view = views.get(currentItem);
            updateScaleAndAlpha(((ViewGroup) view).getChildAt(0), 1.0f, this.mCurrentItemScale);
            if (views.size() < 2) {
                return;
            }
            view.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.WXSliderNeighbor.2
                @Override // java.lang.Runnable
                public void run() {
                    WXSliderNeighbor.this.updateNeighbor(view, f, f2);
                }
            }), 17L);
            int size = currentItem == 0 ? views.size() - 1 : currentItem - 1;
            int i = currentItem == views.size() + (-1) ? 0 : currentItem + 1;
            for (int i2 = 0; i2 < this.mAdapter.getRealCount(); i2++) {
                if (i2 != size && i2 != currentItem && i2 != i) {
                    ((ViewGroup) views.get(i2)).getChildAt(0).setAlpha(0.0f);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNeighbor(View view, float f, float f2) {
        List<View> views = this.mAdapter.getViews();
        int currentItem = this.mViewPager.getCurrentItem();
        float fCalculateTranslation = calculateTranslation(view);
        View view2 = views.get(currentItem == 0 ? views.size() - 1 : currentItem - 1);
        View view3 = views.get(currentItem == views.size() - 1 ? 0 : currentItem + 1);
        if (views.size() != 2) {
            moveLeft(view2, fCalculateTranslation, f, f2);
            moveRight(view3, fCalculateTranslation, f, f2);
        } else if (currentItem == 0) {
            moveRight(view3, fCalculateTranslation, f, f2);
        } else if (currentItem == 1) {
            moveLeft(view2, fCalculateTranslation, f, f2);
        }
    }

    private void updateScaleAndAlpha(View view, float f, float f2) {
        if (view == null) {
            return;
        }
        if (f >= 0.0f && view.getAlpha() != f) {
            view.setAlpha(f);
        }
        if (f2 < 0.0f || view.getScaleX() == f2) {
            return;
        }
        view.setScaleX(f2);
        view.setScaleY(f2);
    }

    @Override // com.taobao.weex.ui.component.WXSlider, io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addSubView(View view, final int i) throws Resources.NotFoundException {
        if (view == null || this.mAdapter == null || (view instanceof WXCircleIndicator)) {
            return;
        }
        FrameLayout frameLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
        frameLayout.addView(view);
        super.addSubView(frameLayout, i);
        updateAdapterScaleAndAlpha(this.mNeighborAlpha, this.mNeighborScale);
        this.mViewPager.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.WXSliderNeighbor.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    try {
                        if (WXSliderNeighbor.this.mViewPager.getRealCount() > 0 && i > 2) {
                            WXSliderNeighbor.this.mViewPager.beginFakeDrag();
                            WXSliderNeighbor.this.mViewPager.fakeDragBy(1.0f);
                        }
                        WXSliderNeighbor.this.mViewPager.endFakeDrag();
                    } catch (IndexOutOfBoundsException unused) {
                        WXSliderNeighbor.this.mViewPager.endFakeDrag();
                    } catch (Throwable th) {
                        try {
                            WXSliderNeighbor.this.mViewPager.endFakeDrag();
                        } catch (Exception unused2) {
                        }
                        throw th;
                    }
                } catch (Exception unused3) {
                }
            }
        }), 50L);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void bindData(WXComponent wXComponent) {
        super.bindData(wXComponent);
    }

    ZoomTransformer createTransformer() {
        if (this.mCachedTransformer == null) {
            this.mCachedTransformer = new ZoomTransformer();
        }
        return this.mCachedTransformer;
    }

    @WXComponentProp(name = CURRENT_ITEM_SCALE)
    public void setCurrentItemScale(String str) throws NumberFormatException {
        float f;
        if (TextUtils.isEmpty(str)) {
            f = DEFAULT_CURRENT_ITEM_SCALE;
        } else {
            try {
                f = Float.parseFloat(str);
            } catch (NumberFormatException unused) {
            }
        }
        if (this.mCurrentItemScale != f) {
            this.mCurrentItemScale = f;
            updateAdapterScaleAndAlpha(-1.0f, -1.0f);
        }
    }

    @WXComponentProp(name = NEIGHBOR_ALPHA)
    public void setNeighborAlpha(String str) throws NumberFormatException {
        float f;
        if (TextUtils.isEmpty(str)) {
            f = DEFAULT_NEIGHBOR_ALPHA;
        } else {
            try {
                f = Float.parseFloat(str);
            } catch (NumberFormatException unused) {
            }
        }
        if (this.mNeighborAlpha != f) {
            this.mNeighborAlpha = f;
            updateAdapterScaleAndAlpha(f, -1.0f);
        }
    }

    @WXComponentProp(name = NEIGHBOR_SCALE)
    public void setNeighborScale(String str) throws NumberFormatException {
        float f;
        if (TextUtils.isEmpty(str)) {
            f = DEFAULT_NEIGHBOR_SCALE;
        } else {
            try {
                f = Float.parseFloat(str);
            } catch (NumberFormatException unused) {
            }
        }
        if (this.mNeighborScale != f) {
            this.mNeighborScale = f;
            updateAdapterScaleAndAlpha(-1.0f, f);
        }
    }

    @WXComponentProp(name = NEIGHBOR_SPACE)
    public void setNeighborSpace(String str) throws NumberFormatException {
        float f;
        if (TextUtils.isEmpty(str)) {
            f = 25.0f;
        } else {
            try {
                f = Float.parseFloat(str);
            } catch (NumberFormatException unused) {
            }
        }
        if (this.mNeighborSpace != f) {
            this.mNeighborSpace = f;
        }
    }

    @Override // com.taobao.weex.ui.component.WXSlider, com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) throws NumberFormatException {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "neighborAlpha":
                String string = WXUtils.getString(obj, null);
                if (string != null) {
                    setNeighborAlpha(string);
                }
                return true;
            case "neighborScale":
                String string2 = WXUtils.getString(obj, null);
                if (string2 != null) {
                    setNeighborScale(string2);
                }
                return true;
            case "neighborSpace":
                String string3 = WXUtils.getString(obj, null);
                if (string3 != null) {
                    setNeighborSpace(string3);
                }
                return true;
            case "currentItemScale":
                String string4 = WXUtils.getString(obj, null);
                if (string4 != null) {
                    setCurrentItemScale(string4);
                }
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXSlider, com.taobao.weex.ui.component.WXComponent
    public BaseFrameLayout initComponentHostView(Context context) throws Resources.NotFoundException {
        BaseFrameLayout baseFrameLayout = new BaseFrameLayout(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        WXCircleViewPager wXCircleViewPager = new WXCircleViewPager(getContext());
        this.mViewPager = wXCircleViewPager;
        wXCircleViewPager.setLayoutParams(layoutParams);
        WXCirclePageAdapter wXCirclePageAdapter = new WXCirclePageAdapter();
        this.mAdapter = wXCirclePageAdapter;
        this.mViewPager.setAdapter(wXCirclePageAdapter);
        baseFrameLayout.addView(this.mViewPager);
        this.mViewPager.addOnPageChangeListener(this.mPageChangeListener);
        this.mViewPager.setOverScrollMode(2);
        registerActivityStateListener();
        this.mViewPager.setPageTransformer(false, createTransformer());
        return baseFrameLayout;
    }
}
