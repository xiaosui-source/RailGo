package com.taobao.weex.ui.component.list;

import android.content.Context;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.PagerSnapHelper;
import com.alibaba.fastjson.JSON;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.IWXObject;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXBaseRefresh;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXLoading;
import com.taobao.weex.ui.component.WXRefresh;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.listview.WXRecyclerView;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXListComponent extends BasicListComponent<BounceRecyclerView> {
    private String TAG;
    private boolean hasSetGapItemDecoration;
    private float mPaddingLeft;
    private float mPaddingRight;
    private Float[] mSpanOffsets;
    private String mSpanOffsetsStr;
    private boolean renderReverse;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new WXListComponent(wXSDKInstance, wXVContainer, true, basicComponentData);
        }
    }

    @Deprecated
    public WXListComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    private boolean hasColumnPros() {
        if (getAttrs().containsKey(Constants.Name.COLUMN_WIDTH) && this.mColumnWidth != WXUtils.parseFloat(getAttrs().get(Constants.Name.COLUMN_WIDTH))) {
            return true;
        }
        if (!getAttrs().containsKey(Constants.Name.COLUMN_COUNT) || this.mColumnCount == WXUtils.parseInt(getAttrs().get(Constants.Name.COLUMN_COUNT))) {
            return getAttrs().containsKey(Constants.Name.COLUMN_GAP) && this.mColumnGap != WXUtils.parseFloat(getAttrs().get(Constants.Name.COLUMN_GAP));
        }
        return true;
    }

    private boolean isRecycler(WXComponent wXComponent) {
        return WXBasicComponentType.WATERFALL.equals(wXComponent.getComponentType()) || WXBasicComponentType.RECYCLE_LIST.equals(wXComponent.getComponentType()) || WXBasicComponentType.RECYCLER.equals(wXComponent.getComponentType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void removeFooterOrHeader(WXComponent wXComponent) {
        if (wXComponent instanceof WXLoading) {
            ((BounceRecyclerView) getHostView()).removeFooterView(wXComponent);
        } else if (wXComponent instanceof WXRefresh) {
            ((BounceRecyclerView) getHostView()).removeHeaderView(wXComponent);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean setRefreshOrLoading(final WXComponent wXComponent) {
        if (getHostView() == 0) {
            WXLogUtils.e(this.TAG, "setRefreshOrLoading: HostView == null !!!!!! check list attr has append =tree");
            return true;
        }
        if (wXComponent instanceof WXRefresh) {
            ((BounceRecyclerView) getHostView()).setOnRefreshListener((WXRefresh) wXComponent);
            ((BounceRecyclerView) getHostView()).postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.list.WXListComponent.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    ((BounceRecyclerView) WXListComponent.this.getHostView()).setHeaderView(wXComponent);
                }
            }), 100L);
            return true;
        }
        if (!(wXComponent instanceof WXLoading)) {
            return false;
        }
        ((BounceRecyclerView) getHostView()).setOnLoadingListener((WXLoading) wXComponent);
        ((BounceRecyclerView) getHostView()).postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.list.WXListComponent.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                ((BounceRecyclerView) WXListComponent.this.getHostView()).setFooterView(wXComponent);
            }
        }), 100L);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateRecyclerAttr() {
        int i = WXUtils.parseInt(getAttrs().get(Constants.Name.COLUMN_COUNT));
        this.mColumnCount = i;
        if (i <= 0 && this.mLayoutType != 1) {
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put("componentType", getComponentType());
            arrayMap.put("attribute", getAttrs().toString());
            arrayMap.put("stackTrace", Arrays.toString(Thread.currentThread().getStackTrace()));
            WXExceptionUtils.commitCriticalExceptionRT(getInstanceId(), WXErrorCode.WX_RENDER_ERR_LIST_INVALID_COLUMN_COUNT, Constants.Name.COLUMN_COUNT, String.format(Locale.ENGLISH, "You are trying to set the list/recycler/vlist/waterfall's column to %d, which is illegal. The column count should be a positive integer", Integer.valueOf(this.mColumnCount)), arrayMap);
            this.mColumnCount = 1;
        }
        this.mColumnGap = WXUtils.parseFloat(getAttrs().get(Constants.Name.COLUMN_GAP));
        this.mColumnWidth = WXUtils.parseFloat(getAttrs().get(Constants.Name.COLUMN_WIDTH));
        this.mPaddingLeft = WXUtils.parseFloat(getAttrs().get(Constants.Name.PADDING_LEFT));
        this.mPaddingRight = WXUtils.parseFloat(getAttrs().get(Constants.Name.PADDING_RIGHT));
        String str = (String) getAttrs().get(Constants.Name.SPAN_OFFSETS);
        this.mSpanOffsetsStr = str;
        try {
            if (TextUtils.isEmpty(str)) {
                this.mSpanOffsets = null;
            } else {
                List array = JSON.parseArray(this.mSpanOffsetsStr, Float.class);
                int size = array.size();
                Float[] fArr = this.mSpanOffsets;
                if (fArr == null || fArr.length != size) {
                    this.mSpanOffsets = new Float[size];
                }
                array.toArray(this.mSpanOffsets);
            }
        } catch (Throwable th) {
            WXLogUtils.w("Parser SpanOffsets error ", th);
        }
        if (this.hasSetGapItemDecoration || getSpanOffsets() == null || getHostView() == 0 || ((BounceRecyclerView) getHostView()).getInnerView() == null) {
            return;
        }
        this.hasSetGapItemDecoration = true;
        ((BounceRecyclerView) getHostView()).getInnerView().addItemDecoration(new GapItemDecoration(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.list.BasicListComponent, io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addChild(WXComponent wXComponent, int i) {
        super.addChild(wXComponent, i);
        if (wXComponent == null || i < -1) {
            return;
        }
        setRefreshOrLoading(wXComponent);
        if (getHostView() == 0 || !hasColumnPros()) {
            return;
        }
        updateRecyclerAttr();
        ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void createChildViewAt(int i) {
        if (i >= 0 || childCount() - 1 >= 0) {
            IWXObject listChild = getListChild(i);
            if (!(listChild instanceof WXBaseRefresh)) {
                super.createChildViewAt(i);
                return;
            }
            final WXComponent wXComponent = (WXComponent) listChild;
            wXComponent.createView();
            if (listChild instanceof WXRefresh) {
                ((BounceRecyclerView) getHostView()).setOnRefreshListener((WXRefresh) listChild);
                ((BounceRecyclerView) getHostView()).postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.list.WXListComponent.3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public void run() {
                        ((BounceRecyclerView) WXListComponent.this.getHostView()).setHeaderView(wXComponent);
                    }
                }, 100L);
            } else if (listChild instanceof WXLoading) {
                ((BounceRecyclerView) getHostView()).setOnLoadingListener((WXLoading) listChild);
                ((BounceRecyclerView) getHostView()).postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.list.WXListComponent.4
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public void run() {
                        ((BounceRecyclerView) WXListComponent.this.getHostView()).setFooterView(wXComponent);
                    }
                }, 100L);
            }
        }
    }

    public Float[] getSpanOffsets() {
        return this.mSpanOffsets;
    }

    @Override // com.taobao.weex.ui.component.list.BasicListComponent
    public boolean isRenderFromBottom() {
        return this.renderReverse;
    }

    @Override // com.taobao.weex.ui.component.list.BasicListComponent, io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void remove(WXComponent wXComponent, boolean z) {
        super.remove(wXComponent, z);
        removeFooterOrHeader(wXComponent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.COLUMN_COUNT)
    public void setColumnCount(int i) {
        if (i != this.mColumnCount) {
            markComponentUsable();
            updateRecyclerAttr();
            ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.COLUMN_GAP)
    public void setColumnGap(float f) {
        if (f != this.mColumnGap) {
            markComponentUsable();
            updateRecyclerAttr();
            ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.COLUMN_WIDTH)
    public void setColumnWidth(float f) {
        if (f != this.mColumnWidth) {
            markComponentUsable();
            updateRecyclerAttr();
            WXRecyclerView innerView = ((BounceRecyclerView) getHostView()).getInnerView();
            if (innerView.getLayoutManager() != null) {
                innerView.getAdapter().notifyDataSetChanged();
            } else {
                innerView.initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
            }
        }
    }

    @WXComponentProp(name = "renderReverse")
    public void setRenderReverse(boolean z) {
        this.renderReverse = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.list.BasicListComponent
    @WXComponentProp(name = Constants.Name.SCROLLABLE)
    public void setScrollable(boolean z) {
        ((BounceRecyclerView) getHostView()).getInnerView().setScrollable(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.SPAN_OFFSETS)
    public void setSpanOffsets(String str) {
        if (TextUtils.equals(str, this.mSpanOffsetsStr)) {
            return;
        }
        markComponentUsable();
        updateRecyclerAttr();
        ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void updateProperties(Map<String, Object> map) {
        super.updateProperties(map);
        if (isRecycler(this)) {
            if (WXBasicComponentType.WATERFALL.equals(getComponentType())) {
                this.mLayoutType = 3;
            } else {
                this.mLayoutType = getAttrs().getLayoutType();
            }
        }
        if (map.containsKey("padding") || map.containsKey(Constants.Name.PADDING_LEFT) || map.containsKey(Constants.Name.PADDING_RIGHT)) {
            if (this.mPaddingLeft == WXUtils.parseFloat(map.get(Constants.Name.PADDING_LEFT)) && this.mPaddingRight == WXUtils.parseFloat(map.get(Constants.Name.PADDING_RIGHT))) {
                return;
            }
            markComponentUsable();
            updateRecyclerAttr();
            ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    public WXListComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.TAG = "WXListComponent";
        this.hasSetGapItemDecoration = false;
        this.renderReverse = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.list.BasicListComponent
    public BounceRecyclerView generateListView(Context context, int i) {
        BounceRecyclerView bounceRecyclerView = new BounceRecyclerView(context, this.mLayoutType, this.mColumnCount, this.mColumnGap, i);
        if (bounceRecyclerView.getSwipeLayout() != null && WXUtils.getBoolean(getAttrs().get(Constants.Name.NEST_SCROLLING_ENABLED), Boolean.FALSE).booleanValue()) {
            bounceRecyclerView.getSwipeLayout().setNestedScrollingEnabled(true);
        }
        if (WXUtils.getBoolean(getAttrs().get(Constants.Name.PAGE_ENABLED), Boolean.FALSE).booleanValue()) {
            (TextUtils.isEmpty(WXUtils.getString(getAttrs().get(Constants.Name.PAGE_SIZE), null)) ? new PagerSnapHelper() : new WXPagerSnapHelper()).attachToRecyclerView(bounceRecyclerView.getInnerView());
        }
        return bounceRecyclerView;
    }
}
