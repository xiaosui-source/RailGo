package com.taobao.weex.ui.component.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXHeader;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.flat.WidgetContainer;
import com.taobao.weex.ui.view.WXFrameLayout;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.LinkedList;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXCell extends WidgetContainer<WXFrameLayout> {
    private CellAppendTreeListener cellAppendTreeListener;
    private boolean isAppendTreeDone;
    private boolean isSourceUsed;
    private boolean mFlatUIEnabled;
    private View mHeadView;
    private int mLastLocationY;
    private ViewGroup mRealView;
    private int mScrollPosition;
    private View mTempStickyView;
    private Object renderData;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface CellAppendTreeListener {
        void onAppendTreeDone();
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new WXCell(wXSDKInstance, wXVContainer, true, basicComponentData);
        }
    }

    @Deprecated
    public WXCell(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.mLastLocationY = 0;
        this.mScrollPosition = -1;
        this.mFlatUIEnabled = false;
        this.isSourceUsed = false;
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void appendTreeCreateFinish() {
        super.appendTreeCreateFinish();
        this.isAppendTreeDone = true;
        CellAppendTreeListener cellAppendTreeListener = this.cellAppendTreeListener;
        if (cellAppendTreeListener != null) {
            cellAppendTreeListener.onAppendTreeDone();
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public void createViewImpl() {
        if (getRealView() == null || getRealView().getParent() == null) {
            super.createViewImpl();
        }
    }

    public int getLocationFromStart() {
        return this.mLastLocationY;
    }

    public Object getRenderData() {
        return this.renderData;
    }

    public int getScrollPositon() {
        return this.mScrollPosition;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public int getStickyOffset() {
        if (getAttrs().get(Constants.Name.STICKY_OFFSET) == null) {
            return 0;
        }
        return (int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(getAttrs().get(Constants.Name.STICKY_OFFSET)), getViewPortWidthForFloat());
    }

    @Override // com.taobao.weex.ui.flat.WidgetContainer
    public boolean intendToBeFlatContainer() {
        return getInstance().getFlatUIContext().isFlatUIEnabled(this) && WXCell.class.equals(getClass()) && !isSticky();
    }

    public boolean isAppendTreeDone() {
        return this.isAppendTreeDone;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public boolean isFlatUIEnabled() {
        return this.mFlatUIEnabled;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public boolean isLazy() {
        return super.isLazy() && !isFixed();
    }

    public boolean isSourceUsed() {
        return this.isSourceUsed;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.flat.WidgetContainer
    protected void mountFlatGUI() {
        if (getHostView() != 0) {
            if (this.widgets == null) {
                this.widgets = new LinkedList();
            }
            ((WXFrameLayout) getHostView()).mountFlatGUI(this.widgets);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void recoverySticky() {
        View view = this.mHeadView;
        if (view != null) {
            if (view.getLayoutParams() != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mHeadView.getLayoutParams();
                if (marginLayoutParams.topMargin > 0) {
                    marginLayoutParams.topMargin = 0;
                }
            }
            if (this.mHeadView.getVisibility() != 0) {
                this.mHeadView.setVisibility(0);
            }
            if (this.mHeadView.getParent() != null) {
                ((ViewGroup) this.mHeadView.getParent()).removeView(this.mHeadView);
            }
            ((WXFrameLayout) getHostView()).removeView(this.mTempStickyView);
            ((WXFrameLayout) getHostView()).addView(this.mHeadView);
            this.mHeadView.setTranslationX(0.0f);
            this.mHeadView.setTranslationY(0.0f);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [android.view.View] */
    public void removeSticky() {
        if (((WXFrameLayout) getHostView()).getChildCount() > 0) {
            this.mHeadView = ((WXFrameLayout) getHostView()).getChildAt(0);
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            ((WXFrameLayout) getHostView()).getLocationOnScreen(iArr);
            getParentScroller().getView().getLocationOnScreen(iArr2);
            int i = iArr[0] - iArr2[0];
            int top = getParent().getHostView().getTop();
            ((WXFrameLayout) getHostView()).removeView(this.mHeadView);
            this.mRealView = (ViewGroup) this.mHeadView;
            this.mTempStickyView = new FrameLayout(getContext());
            ((WXFrameLayout) getHostView()).addView(this.mTempStickyView, new FrameLayout.LayoutParams((int) getLayoutWidth(), (int) getLayoutHeight()));
            this.mHeadView.setTranslationX(i);
            this.mHeadView.setTranslationY(top);
        }
    }

    public void setCellAppendTreeListener(CellAppendTreeListener cellAppendTreeListener) {
        this.cellAppendTreeListener = cellAppendTreeListener;
        if (this.isAppendTreeDone) {
            cellAppendTreeListener.onAppendTreeDone();
        }
    }

    public void setLocationFromStart(int i) {
        this.mLastLocationY = i;
    }

    public void setRenderData(Object obj) {
        this.renderData = obj;
    }

    void setScrollPositon(int i) {
        this.mScrollPosition = i;
    }

    public void setSourceUsed(boolean z) {
        this.isSourceUsed = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.flat.WidgetContainer
    public void unmountFlatGUI() {
        if (getHostView() != 0) {
            ((WXFrameLayout) getHostView()).unmountFlatGUI();
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public ViewGroup getRealView() {
        return this.mRealView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public WXFrameLayout initComponentHostView(Context context) {
        if (!isSticky() && !(this instanceof WXHeader)) {
            WXFrameLayout wXFrameLayout = new WXFrameLayout(context);
            this.mRealView = wXFrameLayout;
            if (isFlatUIEnabled()) {
                wXFrameLayout.setLayerType(2, null);
            }
            return wXFrameLayout;
        }
        WXFrameLayout wXFrameLayout2 = new WXFrameLayout(context);
        WXFrameLayout wXFrameLayout3 = new WXFrameLayout(context);
        this.mRealView = wXFrameLayout3;
        wXFrameLayout2.addView(wXFrameLayout3);
        if (isFlatUIEnabled()) {
            wXFrameLayout2.setLayerType(2, null);
        }
        return wXFrameLayout2;
    }

    public WXCell(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.mLastLocationY = 0;
        this.mScrollPosition = -1;
        this.mFlatUIEnabled = false;
        this.isSourceUsed = false;
        lazy(true);
        setContentBoxMeasurement(new ContentBoxMeasurement() { // from class: com.taobao.weex.ui.component.list.WXCell.1
            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutAfter(float f, float f2) {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutBefore() {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void measureInternal(float f, float f2, int i, int i2) {
                if (i2 == 0) {
                    this.mMeasureHeight = 1.0f;
                }
            }
        });
        if (!canRecycled()) {
            wXSDKInstance.getApmForInstance().updateDiffStats(WXInstanceApm.KEY_PAGE_STATS_CELL_DATA_UN_RECYCLE_NUM, 1.0d);
        }
        if (TextUtils.isEmpty(getAttrs().getScope())) {
            wXSDKInstance.getApmForInstance().updateDiffStats(WXInstanceApm.KEY_PAGE_STATS_CELL_UN_RE_USE_NUM, 1.0d);
        }
    }
}
