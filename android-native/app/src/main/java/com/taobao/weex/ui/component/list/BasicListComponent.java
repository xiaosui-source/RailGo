package com.taobao.weex.ui.component.list;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.collection.ArrayMap;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.ICheckBindingScroller;
import com.taobao.weex.common.IWXObject;
import com.taobao.weex.common.OnWXScrollListener;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.AppearanceHelper;
import com.taobao.weex.ui.component.Scrollable;
import com.taobao.weex.ui.component.WXBaseRefresh;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXHeader;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.ui.component.WXLoading;
import com.taobao.weex.ui.component.WXRefresh;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.helper.ScrollStartEndHelper;
import com.taobao.weex.ui.component.helper.WXStickyHelper;
import com.taobao.weex.ui.component.list.ListComponentView;
import com.taobao.weex.ui.view.listview.WXRecyclerView;
import com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener;
import com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener;
import com.taobao.weex.ui.view.listview.adapter.ListBaseViewHolder;
import com.taobao.weex.ui.view.listview.adapter.RecyclerViewBaseAdapter;
import com.taobao.weex.ui.view.listview.adapter.WXRecyclerViewOnScrollListener;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.constant.AbsoluteConst;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class BasicListComponent<T extends ViewGroup & ListComponentView> extends WXVContainer<T> implements IRecyclerAdapterListener<ListBaseViewHolder>, IOnLoadMoreListener, Scrollable {
    private static final boolean DEFAULT_EXCLUDED = false;
    private static final String DEFAULT_TRIGGER_TYPE = "longpress";
    private static final String DRAG_ANCHOR = "dragAnchor";
    private static final String DRAG_TRIGGER_TYPE = "dragTriggerType";
    private static final String EXCLUDED = "dragExcluded";
    public static final String LOADMOREOFFSET = "loadmoreoffset";
    private static final int MAX_VIEWTYPE_ALLOW_CACHE = 9;
    public static final String TRANSFORM = "transform";
    private String TAG;
    private boolean isScrollable;
    boolean isThisGroupFinished;
    private WXComponent keepPositionCell;
    private Runnable keepPositionCellRunnable;
    private long keepPositionLayoutDelay;
    ListStanceCell listStanceObject;
    private Runnable mAppearChangeRunnable;
    private long mAppearChangeRunnableDelay;
    private Map<String, AppearanceHelper> mAppearComponents;
    protected int mColumnCount;
    protected float mColumnGap;
    protected float mColumnWidth;
    private DragHelper mDragHelper;
    private boolean mForceLoadmoreNextTime;
    private boolean mHasAddScrollEvent;
    private RecyclerView.ItemAnimator mItemAnimator;
    private Point mLastReport;
    protected int mLayoutType;
    protected float mLeftGap;
    private int mListCellCount;
    private int mOffsetAccuracy;
    private ArrayMap<String, Long> mRefToViewType;
    protected float mRightGap;
    private ScrollStartEndHelper mScrollStartEndHelper;
    private Map<String, Map<String, WXComponent>> mStickyMap;
    private String mTriggerType;
    private WXRecyclerViewOnScrollListener mViewOnScrollListener;
    private SparseArray<ArrayList<WXComponent>> mViewTypes;
    private WXStickyHelper stickyHelper;
    private static final Pattern transformPattern = Pattern.compile("([a-z]+)\\(([0-9\\.]+),?([0-9\\.]+)?\\)");
    private static boolean mAllowCacheViewHolder = true;
    private static boolean mDownForBidCacheViewHolder = false;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    interface DragTriggerType {
        public static final String LONG_PRESS = "longpress";
        public static final String PAN = "pan";
    }

    public BasicListComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.TAG = "BasicListComponent";
        this.mListCellCount = 0;
        this.mForceLoadmoreNextTime = false;
        this.mAppearComponents = new HashMap();
        this.mAppearChangeRunnable = null;
        this.mAppearChangeRunnableDelay = 50L;
        this.isScrollable = true;
        this.mViewOnScrollListener = new WXRecyclerViewOnScrollListener(this);
        this.mLayoutType = 1;
        this.mColumnCount = 1;
        this.mColumnGap = 0.0f;
        this.mColumnWidth = 0.0f;
        this.mLeftGap = 0.0f;
        this.mRightGap = 0.0f;
        this.mOffsetAccuracy = 10;
        this.mLastReport = new Point(-1, -1);
        this.mHasAddScrollEvent = false;
        this.mStickyMap = new HashMap();
        this.keepPositionCell = null;
        this.keepPositionCellRunnable = null;
        this.keepPositionLayoutDelay = 150L;
        this.isThisGroupFinished = false;
        this.stickyHelper = new WXStickyHelper(this);
    }

    private void bindViewType(WXComponent wXComponent) {
        int iGenerateViewType = generateViewType(wXComponent);
        if (this.mViewTypes == null) {
            this.mViewTypes = new SparseArray<>();
        }
        ArrayList<WXComponent> arrayList = this.mViewTypes.get(iGenerateViewType);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.mViewTypes.put(iGenerateViewType, arrayList);
        }
        arrayList.add(wXComponent);
    }

    private void checkRecycledViewPool(int i) {
        try {
            if (this.mViewTypes.size() > 9) {
                mAllowCacheViewHolder = false;
            }
            if (mDownForBidCacheViewHolder && getHostView() != null && getHostView().getInnerView() != null) {
                getHostView().getInnerView().getRecycledViewPool().setMaxRecycledViews(i, 0);
            }
            if (mDownForBidCacheViewHolder || mAllowCacheViewHolder || getHostView() == null || getHostView().getInnerView() == null) {
                return;
            }
            for (int i2 = 0; i2 < this.mViewTypes.size(); i2++) {
                getHostView().getInnerView().getRecycledViewPool().setMaxRecycledViews(this.mViewTypes.keyAt(i2), 0);
            }
            mDownForBidCacheViewHolder = true;
        } catch (Exception unused) {
            WXLogUtils.e(this.TAG, "Clear recycledViewPool error!");
        }
    }

    private ListBaseViewHolder createVHForFakeComponent(int i) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setBackgroundColor(-1);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
        return new ListBaseViewHolder(frameLayout, i);
    }

    private ListBaseViewHolder createVHForRefreshComponent(int i) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, 1));
        return new ListBaseViewHolder(frameLayout, i);
    }

    private WXComponent findComponentByAnchorName(WXComponent wXComponent, String str) {
        String string;
        long jCurrentTimeMillis = WXEnvironment.isApkDebugable() ? System.currentTimeMillis() : 0L;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(wXComponent);
        while (!arrayDeque.isEmpty()) {
            WXComponent wXComponent2 = (WXComponent) arrayDeque.removeFirst();
            if (wXComponent2 != null && (string = WXUtils.getString(wXComponent2.getAttrs().get(str), null)) != null && string.equals(AbsoluteConst.TRUE)) {
                if (WXEnvironment.isApkDebugable()) {
                    WXLogUtils.d("dragPerf", "findComponentByAnchorName time: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
                }
                return wXComponent2;
            }
            if (wXComponent2 instanceof WXVContainer) {
                WXVContainer wXVContainer = (WXVContainer) wXComponent2;
                int iChildCount = wXVContainer.childCount();
                for (int i = 0; i < iChildCount; i++) {
                    arrayDeque.add(wXVContainer.getChild(i));
                }
            }
        }
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d("dragPerf", "findComponentByAnchorName elapsed time: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
        }
        return null;
    }

    private WXComponent findDirectListChild(WXComponent wXComponent) {
        WXVContainer parent;
        if (wXComponent == null || (parent = wXComponent.getParent()) == null) {
            return null;
        }
        return parent instanceof BasicListComponent ? wXComponent : findDirectListChild(parent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireScrollEvent(RecyclerView recyclerView, int i, int i2) {
        if (getInstance() != null) {
            fireEvent("scroll", getScrollEvent(recyclerView, i, i2));
        }
    }

    private int generateViewType(IWXObject iWXObject) {
        long jLongValue = -1;
        try {
            if (!(iWXObject instanceof ListStanceCell) && (iWXObject instanceof WXComponent)) {
                WXComponent wXComponent = (WXComponent) iWXObject;
                long j = Integer.parseInt(wXComponent.getRef());
                String scope = wXComponent.getAttrs().getScope();
                if (TextUtils.isEmpty(scope)) {
                    jLongValue = j;
                } else {
                    if (this.mRefToViewType == null) {
                        this.mRefToViewType = new ArrayMap<>();
                    }
                    if (!this.mRefToViewType.containsKey(scope)) {
                        this.mRefToViewType.put(scope, Long.valueOf(j));
                    }
                    jLongValue = this.mRefToViewType.get(scope).longValue();
                }
            }
        } catch (RuntimeException e) {
            WXLogUtils.eTag(this.TAG, e);
            WXLogUtils.e(this.TAG, "getItemViewType: NO ID, this will crash the whole render system of WXListRecyclerView");
        }
        return (int) jLongValue;
    }

    private float getListChildLayoutHeight(int i) {
        IWXObject listChild = getListChild(i);
        if (listChild == null) {
            return 0.0f;
        }
        if (listChild instanceof ListStanceCell) {
            return 1.0f;
        }
        if (listChild instanceof WXComponent) {
            return ((WXComponent) listChild).getLayoutHeight();
        }
        return 0.0f;
    }

    private IWXObject getListStanceCell(String str) {
        if (this.listStanceObject == null) {
            this.listStanceObject = new ListStanceCell(str);
        }
        return this.listStanceObject;
    }

    private String getTriggerType(WXComponent wXComponent) {
        if (wXComponent == null) {
            return "longpress";
        }
        String string = WXUtils.getString(wXComponent.getAttrs().get(DRAG_TRIGGER_TYPE), "longpress");
        String str = ("longpress".equals(string) || "pan".equals(string)) ? string : "longpress";
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d(this.TAG, "trigger type is " + str);
        }
        return str;
    }

    private void relocateAppearanceHelper() {
        Iterator<Map.Entry<String, AppearanceHelper>> it = this.mAppearComponents.entrySet().iterator();
        while (it.hasNext()) {
            AppearanceHelper value = it.next().getValue();
            value.setCellPosition(this.mChildren.indexOf(findDirectListChild(value.getAwareChild())));
        }
    }

    private void setAppearanceWatch(WXComponent wXComponent, int i, boolean z) {
        AppearanceHelper appearanceHelper = this.mAppearComponents.get(wXComponent.getRef());
        if (appearanceHelper != null) {
            appearanceHelper.setWatchEvent(i, z);
            return;
        }
        if (z) {
            int iIndexOf = this.mChildren.indexOf(findDirectListChild(wXComponent));
            if (iIndexOf != -1) {
                AppearanceHelper appearanceHelper2 = new AppearanceHelper(wXComponent, iIndexOf);
                appearanceHelper2.setWatchEvent(i, true);
                this.mAppearComponents.put(wXComponent.getRef(), appearanceHelper2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldReport(int i, int i2) {
        Point point = this.mLastReport;
        int i3 = point.x;
        if (i3 == -1 && point.y == -1) {
            point.x = i;
            point.y = i2;
            return true;
        }
        int iAbs = Math.abs(i3 - i);
        int iAbs2 = Math.abs(this.mLastReport.y - i2);
        int i4 = this.mOffsetAccuracy;
        if (iAbs < i4 && iAbs2 < i4) {
            return false;
        }
        Point point2 = this.mLastReport;
        point2.x = i;
        point2.y = i2;
        return true;
    }

    private void unBindViewType(WXComponent wXComponent) {
        ArrayList<WXComponent> arrayList;
        int iGenerateViewType = generateViewType(wXComponent);
        SparseArray<ArrayList<WXComponent>> sparseArray = this.mViewTypes;
        if (sparseArray == null || (arrayList = sparseArray.get(iGenerateViewType)) == null) {
            return;
        }
        arrayList.remove(wXComponent);
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addChild(WXComponent wXComponent) {
        addChild(wXComponent, -1);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        super.addEvent(str);
        if (!ScrollStartEndHelper.isScrollEvent(str) || getHostView() == null || getHostView().getInnerView() == null || this.mHasAddScrollEvent) {
            return;
        }
        this.mHasAddScrollEvent = true;
        getHostView().getInnerView().addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.taobao.weex.ui.component.list.BasicListComponent.7
            private boolean mFirstEvent = true;
            private int offsetXCorrection;
            private int offsetYCorrection;

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int i3;
                int i4;
                super.onScrolled(recyclerView, i, i2);
                int iComputeHorizontalScrollOffset = recyclerView.computeHorizontalScrollOffset();
                int iComputeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset();
                if (i == 0 && i2 == 0) {
                    this.offsetXCorrection = iComputeHorizontalScrollOffset;
                    this.offsetYCorrection = iComputeVerticalScrollOffset;
                    i3 = 0;
                    i4 = 0;
                } else {
                    i3 = iComputeHorizontalScrollOffset - this.offsetXCorrection;
                    i4 = iComputeVerticalScrollOffset - this.offsetYCorrection;
                }
                BasicListComponent.this.getScrollStartEndHelper().onScrolled(i3, i4);
                if (BasicListComponent.this.getEvents().contains("scroll")) {
                    if (this.mFirstEvent) {
                        this.mFirstEvent = false;
                        return;
                    }
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (BasicListComponent.this.getOrientation() == 1) {
                        if (!layoutManager.canScrollVertically()) {
                            return;
                        }
                    } else if (BasicListComponent.this.getOrientation() == 0 && !layoutManager.canScrollHorizontally()) {
                        return;
                    }
                    if (BasicListComponent.this.shouldReport(i3, i4)) {
                        BasicListComponent.this.fireScrollEvent(recyclerView, i3, i4);
                    }
                }
            }
        });
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addSubView(View view, int i) {
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void bindAppearEvent(WXComponent wXComponent) {
        setAppearanceWatch(wXComponent, 0, true);
        if (this.mAppearChangeRunnable == null) {
            this.mAppearChangeRunnable = new Runnable() { // from class: com.taobao.weex.ui.component.list.BasicListComponent.4
                @Override // java.lang.Runnable
                public void run() {
                    if (BasicListComponent.this.mAppearChangeRunnable != null) {
                        BasicListComponent.this.notifyAppearStateChange(0, 0, 0, 0);
                    }
                }
            };
        }
        if (getHostView() != null) {
            getHostView().removeCallbacks(this.mAppearChangeRunnable);
            getHostView().postDelayed(this.mAppearChangeRunnable, this.mAppearChangeRunnableDelay);
        }
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void bindDisappearEvent(WXComponent wXComponent) {
        setAppearanceWatch(wXComponent, 1, true);
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void bindStickStyle(WXComponent wXComponent) {
        this.stickyHelper.bindStickStyle(wXComponent, this.mStickyMap);
    }

    public int calcContentOffset(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int i = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            int iFindFirstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            if (iFindFirstVisibleItemPosition == -1) {
                return 0;
            }
            View viewFindViewByPosition = layoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
            int top = viewFindViewByPosition != null ? viewFindViewByPosition.getTop() : 0;
            int spanCount = 0;
            while (i < iFindFirstVisibleItemPosition) {
                spanCount = (int) (spanCount - getListChildLayoutHeight(i));
                i++;
            }
            if (layoutManager instanceof GridLayoutManager) {
                spanCount /= ((GridLayoutManager) layoutManager).getSpanCount();
            }
            return spanCount + top;
        }
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return -1;
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int spanCount2 = staggeredGridLayoutManager.getSpanCount();
        int i2 = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];
        if (i2 == -1) {
            return 0;
        }
        View viewFindViewByPosition2 = layoutManager.findViewByPosition(i2);
        int top2 = viewFindViewByPosition2 != null ? viewFindViewByPosition2.getTop() : 0;
        int listChildLayoutHeight = 0;
        while (i < i2) {
            listChildLayoutHeight = (int) (listChildLayoutHeight - getListChildLayoutHeight(i));
            i++;
        }
        return (listChildLayoutHeight / spanCount2) + top2;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void computeVisiblePointInViewCoordinate(PointF pointF) {
        WXRecyclerView innerView = getHostView().getInnerView();
        pointF.set(innerView.computeHorizontalScrollOffset(), innerView.computeVerticalScrollOffset());
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mAppearChangeRunnable != null && getHostView() != null) {
            getHostView().removeCallbacks(this.mAppearChangeRunnable);
            this.mAppearChangeRunnable = null;
        }
        if (getHostView() != null && getHostView().getInnerView() != null) {
            getHostView().getInnerView().clearOnScrollListeners();
        }
        super.destroy();
        Map<String, Map<String, WXComponent>> map = this.mStickyMap;
        if (map != null) {
            map.clear();
        }
        SparseArray<ArrayList<WXComponent>> sparseArray = this.mViewTypes;
        if (sparseArray != null) {
            sparseArray.clear();
        }
        ArrayMap<String, Long> arrayMap = this.mRefToViewType;
        if (arrayMap != null) {
            arrayMap.clear();
        }
    }

    abstract T generateListView(Context context, int i);

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public int getChildCount() {
        WXComponent child = super.getChild(0);
        return (child == null || !(child instanceof WXRefresh) || this.mColumnCount <= 1) ? super.getChildCount() : super.getChildCount() + (this.mColumnCount - 1);
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public ViewGroup.LayoutParams getChildLayoutParams(WXComponent wXComponent, View view, int i, int i2, int i3, int i4, int i5, int i6) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if ((wXComponent instanceof WXBaseRefresh) && marginLayoutParams == null) {
            return new LinearLayout.LayoutParams(i, i2);
        }
        if (marginLayoutParams == null) {
            return new RecyclerView.LayoutParams(i, i2);
        }
        marginLayoutParams.width = i;
        marginLayoutParams.height = i2;
        setMarginsSupportRTL(marginLayoutParams, i3, 0, i4, 0);
        return marginLayoutParams;
    }

    @Override // com.taobao.weex.ui.component.WXVContainer, io.dcloud.feature.uniapp.ui.component.AbsVContainer
    protected int getChildrenLayoutTopOffset() {
        return 0;
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public int getItemCount() {
        if (this.isThisGroupFinished) {
            return getChildCount();
        }
        return 0;
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public long getItemId(int i) {
        try {
            IWXObject listChild = getListChild(i);
            if (!(listChild instanceof ListStanceCell) && (listChild instanceof WXComponent)) {
                return Long.parseLong(((WXComponent) listChild).getRef());
            }
            return -1L;
        } catch (RuntimeException e) {
            WXLogUtils.e(this.TAG, WXLogUtils.getStackTrace(e));
            return -1L;
        }
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public int getItemViewType(int i) {
        return generateViewType(getListChild(i));
    }

    public IWXObject getListChild(int i) {
        int i2;
        WXComponent child = super.getChild(0);
        return i == 0 ? child : (child == null || !(child instanceof WXRefresh) || (i2 = this.mColumnCount) <= 1) ? super.getChild(i) : i - i2 < 0 ? getListStanceCell(child.getStyles().getBackgroundColor()) : super.getChild(i - (i2 - 1));
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public int getOrientation() {
        return 1;
    }

    public Map<String, Object> getScrollEvent(RecyclerView recyclerView, int i, int i2) {
        if (getOrientation() == 1) {
            i2 = -calcContentOffset(recyclerView);
        }
        int measuredWidth = recyclerView.getMeasuredWidth() + recyclerView.computeHorizontalScrollRange();
        int listChildLayoutHeight = 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            listChildLayoutHeight = (int) (listChildLayoutHeight + getListChildLayoutHeight(i3));
        }
        HashMap map = new HashMap(3);
        HashMap map2 = new HashMap(3);
        HashMap map3 = new HashMap(3);
        map2.put("width", Float.valueOf(WXViewUtils.getWebPxByWidth(measuredWidth, getInstance().getInstanceViewPortWidthWithFloat())));
        map2.put("height", Float.valueOf(WXViewUtils.getWebPxByWidth(listChildLayoutHeight, getInstance().getInstanceViewPortWidthWithFloat())));
        map3.put(Constants.Name.X, Float.valueOf(-WXViewUtils.getWebPxByWidth(i, getInstance().getInstanceViewPortWidthWithFloat())));
        map3.put(Constants.Name.Y, Float.valueOf(-WXViewUtils.getWebPxByWidth(i2, getInstance().getInstanceViewPortWidthWithFloat())));
        map.put(Constants.Name.CONTENT_SIZE, map2);
        map.put(Constants.Name.CONTENT_OFFSET, map3);
        map.put(Constants.Name.ISDRAGGING, Boolean.valueOf(recyclerView.getScrollState() == 1));
        return map;
    }

    public ScrollStartEndHelper getScrollStartEndHelper() {
        if (this.mScrollStartEndHelper == null) {
            this.mScrollStartEndHelper = new ScrollStartEndHelper(this);
        }
        return this.mScrollStartEndHelper;
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public int getScrollX() {
        T hostView = getHostView();
        if (hostView == null) {
            return 0;
        }
        return hostView.getInnerView().getScrollX();
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public int getScrollY() {
        T hostView = getHostView();
        if (hostView == null) {
            return 0;
        }
        return hostView.getInnerView().getScrollY();
    }

    protected boolean isRenderFromBottom() {
        return false;
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public boolean isScrollable() {
        return this.isScrollable;
    }

    protected void markComponentUsable() {
        ArrayList<WXComponent> arrayList = this.mChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            WXComponent wXComponent = arrayList.get(i);
            i++;
            wXComponent.setUsing(false);
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected WXComponent.MeasureOutput measure(int i, int i2) {
        return super.measure(i, i2);
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener
    public void notifyAppearStateChange(int i, int i2, int i3, int i4) {
        View hostView;
        String str = null;
        if (this.mAppearChangeRunnable != null) {
            getHostView().removeCallbacks(this.mAppearChangeRunnable);
            this.mAppearChangeRunnable = null;
        }
        if (i4 > 0) {
            str = "up";
        } else if (i4 < 0) {
            str = "down";
        }
        if (getOrientation() == 0 && i3 != 0) {
            str = i3 > 0 ? "left" : "right";
        }
        for (AppearanceHelper appearanceHelper : this.mAppearComponents.values()) {
            WXComponent awareChild = appearanceHelper.getAwareChild();
            if (appearanceHelper.isWatch() && (hostView = awareChild.getHostView()) != null) {
                int appearStatus = appearanceHelper.setAppearStatus(ViewCompat.isAttachedToWindow(hostView) && appearanceHelper.isViewVisible(true));
                if (appearStatus != 0) {
                    boolean zIsApkDebugable = WXEnvironment.isApkDebugable();
                    String str2 = Constants.Event.APPEAR;
                    if (zIsApkDebugable) {
                        WXLogUtils.d(Constants.Event.APPEAR, "item " + appearanceHelper.getCellPositionINScollable() + " result " + appearStatus);
                    }
                    if (appearStatus != 1) {
                        str2 = Constants.Event.DISAPPEAR;
                    }
                    awareChild.notifyAppearStateChange(str2, str);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0110  */
    @Override // com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onBeforeScroll(int r11, int r12) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.list.BasicListComponent.onBeforeScroll(int, int):void");
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener
    public void onLoadMore(int i) {
        try {
            String loadMoreOffset = getAttrs().getLoadMoreOffset();
            if (TextUtils.isEmpty(loadMoreOffset)) {
                loadMoreOffset = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
            }
            if (i > WXViewUtils.getRealPxByWidth(WXUtils.getInt(loadMoreOffset), getInstance().getInstanceViewPortWidthWithFloat()) || !getEvents().contains(Constants.Event.LOADMORE)) {
                return;
            }
            if (this.mListCellCount != this.mChildren.size() || this.mForceLoadmoreNextTime) {
                fireEvent(Constants.Event.LOADMORE);
                this.mListCellCount = this.mChildren.size();
                this.mForceLoadmoreNextTime = false;
            }
        } catch (Exception e) {
            WXLogUtils.d(this.TAG + "onLoadMore :", e);
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void remove(WXComponent wXComponent, boolean z) {
        int iIndexOf = this.mChildren.indexOf(wXComponent);
        if (z) {
            wXComponent.detachViewAndClearPreInfo();
        }
        unBindViewType(wXComponent);
        T hostView = getHostView();
        if (hostView == null) {
            return;
        }
        if ("default".equals(wXComponent.getAttrs().get(Constants.Name.DELETE_CELL_ANIMATION))) {
            hostView.getInnerView().setItemAnimator(this.mItemAnimator);
        } else {
            hostView.getInnerView().setItemAnimator(null);
        }
        hostView.getRecyclerViewBaseAdapter().notifyItemRemoved(iIndexOf);
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d(this.TAG, "removeChild child at " + iIndexOf);
        }
        super.remove(wXComponent, z);
    }

    @JSMethod
    public void resetLoadmore() {
        this.mForceLoadmoreNextTime = true;
        this.mListCellCount = 0;
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void scrollTo(WXComponent wXComponent, Map<String, Object> map) {
        boolean zBooleanValue;
        WXCell wXCell;
        int iIndexOf;
        float realPxByWidth = 0.0f;
        if (map != null) {
            String string = map.get("offset") == null ? WXInstanceApm.VALUE_ERROR_CODE_DEFAULT : map.get("offset").toString();
            zBooleanValue = WXUtils.getBoolean(map.get(Constants.Name.ANIMATED), Boolean.TRUE).booleanValue();
            if (string != null) {
                try {
                    realPxByWidth = WXViewUtils.getRealPxByWidth(Float.parseFloat(string), getInstance().getInstanceViewPortWidthWithFloat());
                } catch (Exception e) {
                    WXLogUtils.e("Float parseFloat error :" + e.getMessage());
                }
            }
        } else {
            zBooleanValue = true;
        }
        int i = (int) realPxByWidth;
        T hostView = getHostView();
        if (hostView == null) {
            return;
        }
        while (true) {
            if (wXComponent == null) {
                wXCell = null;
                break;
            } else {
                if (wXComponent instanceof WXCell) {
                    wXCell = (WXCell) wXComponent;
                    break;
                }
                wXComponent = wXComponent.getParent();
            }
        }
        if (wXCell == null || (iIndexOf = this.mChildren.indexOf(wXCell)) == -1) {
            return;
        }
        hostView.getInnerView().scrollTo(zBooleanValue, iIndexOf, i, getOrientation());
    }

    @WXComponentProp(name = "bounce")
    public void setBounce(String str) {
        WXRecyclerView innerView = getHostView().getInnerView();
        if (Boolean.valueOf(str).booleanValue()) {
            innerView.setOverScrollMode(0);
        } else {
            innerView.setOverScrollMode(2);
        }
    }

    @WXComponentProp(name = Constants.Name.DRAGGABLE)
    public void setDraggable(boolean z) {
        DragHelper dragHelper = this.mDragHelper;
        if (dragHelper != null) {
            dragHelper.setDraggable(z);
        }
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d("set draggable : " + z);
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public void setIsLayoutRTL(boolean z) {
        super.setIsLayoutRTL(z);
        this.mViewOnScrollListener.setLayoutRTL(z);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void setLayout(WXComponent wXComponent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (wXComponent.getHostView() != null) {
            ViewCompat.setLayoutDirection(wXComponent.getHostView(), wXComponent.isLayoutRTL() ? 1 : 0);
        }
        super.setLayout(wXComponent);
    }

    @WXComponentProp(name = Constants.Name.OFFSET_ACCURACY)
    public void setOffsetAccuracy(int i) {
        this.mOffsetAccuracy = (int) WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidthWithFloat());
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "draggable":
                setDraggable(WXUtils.getBoolean(obj, Boolean.FALSE).booleanValue());
                return true;
            case "showScrollbar":
                Boolean bool = WXUtils.getBoolean(obj, null);
                if (bool != null) {
                    setShowScrollbar(bool.booleanValue());
                }
                return true;
            case "offsetAccuracy":
                setOffsetAccuracy(WXUtils.getInteger(obj, 10).intValue());
            case "loadmoreoffset":
                return true;
            case "scrollable":
                setScrollable(WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue());
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    @WXComponentProp(name = Constants.Name.SCROLL_LEFT)
    public void setScrollLeft(String str) {
        T hostView = getHostView();
        if (hostView == null) {
            return;
        }
        hostView.getInnerView().scrollTo((int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(str), getInstance().getInstanceViewPortWidthWithFloat()), 0, getOrientation());
    }

    @WXComponentProp(name = Constants.Name.SCROLL_TOP)
    public void setScrollTop(String str) {
        T hostView = getHostView();
        if (hostView == null) {
            return;
        }
        hostView.getInnerView().scrollTo(0, (int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(str), getInstance().getInstanceViewPortWidthWithFloat()), getOrientation());
    }

    @WXComponentProp(name = Constants.Name.SCROLLABLE)
    public void setScrollable(boolean z) {
        this.isScrollable = z;
        WXRecyclerView innerView = getHostView().getInnerView();
        if (innerView != null) {
            innerView.setScrollable(z);
        }
    }

    @WXComponentProp(name = Constants.Name.SHOW_SCROLLBAR)
    public void setShowScrollbar(boolean z) {
        if (getHostView() == null || getHostView().getInnerView() == null) {
            return;
        }
        if (getOrientation() == 1) {
            getHostView().getInnerView().setVerticalScrollBarEnabled(z);
        } else {
            getHostView().getInnerView().setHorizontalScrollBarEnabled(z);
        }
    }

    @JSMethod
    public void setSpecialEffects(JSONObject jSONObject) {
        if (getHostView() == null || !(getHostView() instanceof BounceRecyclerView)) {
            return;
        }
        if (jSONObject == null || !jSONObject.containsKey("id")) {
            getHostView().getInnerView().setNestInfo(null);
            return;
        }
        String string = jSONObject.getString("id");
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("isNestParent", (Object) Boolean.FALSE);
        jSONObject2.put("instanceId", (Object) getInstance().getInstanceId());
        jSONObject2.put("listParentId", (Object) string);
        getHostView().getInnerView().setNestInfo(jSONObject2);
        getHostView().getInnerView().callBackNestParent(getRef(), getInstance().getInstanceId(), WXViewUtils.getRealPxByWidth(WXUtils.getFloat(jSONObject.getString("headerHeight")), getInstance().getInstanceViewPortWidthWithFloat()));
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void unbindAppearEvent(WXComponent wXComponent) {
        setAppearanceWatch(wXComponent, 0, false);
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void unbindDisappearEvent(WXComponent wXComponent) {
        setAppearanceWatch(wXComponent, 1, false);
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void unbindStickStyle(WXComponent wXComponent) {
        this.stickyHelper.unbindStickStyle(wXComponent, this.mStickyMap);
        WXHeader wXHeader = (WXHeader) findTypeParent(wXComponent, WXHeader.class);
        if (wXHeader == null || getHostView() == null) {
            return;
        }
        getHostView().notifyStickyRemove(wXHeader);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addChild(WXComponent wXComponent, int i) {
        boolean z;
        super.addChild(wXComponent, i);
        if (wXComponent == null || i < -1) {
            return;
        }
        if (i >= this.mChildren.size()) {
            i = -1;
        }
        bindViewType(wXComponent);
        boolean z2 = false;
        if (this.isThisGroupFinished || !isRenderFromBottom()) {
            z = false;
        } else if (!wXComponent.getAttrs().containsKey("renderReversePosition")) {
            return;
        } else {
            z = true;
        }
        this.isThisGroupFinished = true;
        int size = i == -1 ? this.mChildren.size() - 1 : i;
        final T hostView = getHostView();
        if (hostView != 0) {
            if (getBasicComponentData() == null || !"default".equals(getAttrs().get(Constants.Name.INSERT_CELL_ANIMATION))) {
                hostView.getInnerView().setItemAnimator(null);
            } else {
                hostView.getInnerView().setItemAnimator(this.mItemAnimator);
            }
            if (wXComponent.getBasicComponentData() != null && WXUtils.getBoolean(wXComponent.getAttrs().get(Constants.Name.KEEP_SCROLL_POSITION), Boolean.FALSE).booleanValue() && i <= getChildCount() && i > -1) {
                z2 = true;
            }
            if (!z2 && z) {
                z2 = true;
            }
            if (z2) {
                T t = hostView;
                if (t.getInnerView().getLayoutManager() instanceof LinearLayoutManager) {
                    if (this.keepPositionCell == null) {
                        ListBaseViewHolder listBaseViewHolder = (ListBaseViewHolder) t.getInnerView().findViewHolderForAdapterPosition(((LinearLayoutManager) t.getInnerView().getLayoutManager()).findLastCompletelyVisibleItemPosition());
                        if (listBaseViewHolder != null) {
                            this.keepPositionCell = listBaseViewHolder.getComponent();
                        }
                        if (this.keepPositionCell != null) {
                            if (!t.getInnerView().isLayoutFrozen()) {
                                t.getInnerView().setLayoutFrozen(true);
                            }
                            Runnable runnable = this.keepPositionCellRunnable;
                            if (runnable != null) {
                                hostView.removeCallbacks(runnable);
                            }
                            this.keepPositionCellRunnable = new Runnable() { // from class: com.taobao.weex.ui.component.list.BasicListComponent.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (BasicListComponent.this.keepPositionCell != null) {
                                        BasicListComponent basicListComponent = BasicListComponent.this;
                                        int iIndexOf = basicListComponent.indexOf(basicListComponent.keepPositionCell);
                                        int top = BasicListComponent.this.keepPositionCell.getHostView() != null ? BasicListComponent.this.keepPositionCell.getHostView().getTop() : 0;
                                        if (top > 0) {
                                            ((LinearLayoutManager) ((ListComponentView) hostView).getInnerView().getLayoutManager()).scrollToPositionWithOffset(iIndexOf, top);
                                        } else {
                                            ((ListComponentView) hostView).getInnerView().getLayoutManager().scrollToPosition(iIndexOf);
                                        }
                                        ((ListComponentView) hostView).getInnerView().setLayoutFrozen(false);
                                        BasicListComponent.this.keepPositionCell = null;
                                        BasicListComponent.this.keepPositionCellRunnable = null;
                                    }
                                }
                            };
                        }
                    }
                    if (this.keepPositionCellRunnable == null) {
                        int iFindLastVisibleItemPosition = ((LinearLayoutManager) t.getInnerView().getLayoutManager()).findLastVisibleItemPosition();
                        if (!z) {
                            i = iFindLastVisibleItemPosition;
                        }
                        t.getInnerView().scrollToPosition(i);
                    }
                }
                t.getRecyclerViewBaseAdapter().notifyItemRangeInserted(size, 20);
                Runnable runnable2 = this.keepPositionCellRunnable;
                if (runnable2 != null) {
                    hostView.removeCallbacks(runnable2);
                    hostView.postDelayed(this.keepPositionCellRunnable, this.keepPositionLayoutDelay);
                }
            } else {
                hostView.getRecyclerViewBaseAdapter().notifyDataSetChanged();
            }
        }
        relocateAppearanceHelper();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.WXComponent
    public T initComponentHostView(Context context) {
        AdapterView adapterView = (T) generateListView(context, getOrientation());
        String attrByKey = getAttrByKey("transform");
        if (attrByKey != null) {
            ((ListComponentView) adapterView).getInnerView().addItemDecoration(RecyclerTransform.parseTransforms(getOrientation(), attrByKey));
        }
        if (getAttrs().get(Constants.Name.KEEP_POSITION_LAYOUT_DELAY) != null) {
            this.keepPositionLayoutDelay = WXUtils.getNumberInt(getAttrs().get(Constants.Name.KEEP_POSITION_LAYOUT_DELAY), (int) this.keepPositionLayoutDelay);
        }
        if (getAttrs().get("appearActionDelay") != null) {
            this.mAppearChangeRunnableDelay = WXUtils.getNumberInt(getAttrs().get("appearActionDelay"), (int) this.mAppearChangeRunnableDelay);
        }
        ListComponentView listComponentView = (ListComponentView) adapterView;
        this.mItemAnimator = listComponentView.getInnerView().getItemAnimator();
        RecyclerViewBaseAdapter recyclerViewBaseAdapter = new RecyclerViewBaseAdapter(this);
        recyclerViewBaseAdapter.setHasStableIds(true);
        listComponentView.setRecyclerViewBaseAdapter(recyclerViewBaseAdapter);
        listComponentView.getInnerView().addOnScrollListener(this.mViewOnScrollListener);
        if (getAttrs().get(Constants.Name.HAS_FIXED_SIZE) != null) {
            listComponentView.getInnerView().setHasFixedSize(WXUtils.getBoolean(getAttrs().get(Constants.Name.HAS_FIXED_SIZE), Boolean.FALSE).booleanValue());
        }
        listComponentView.getInnerView().addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.taobao.weex.ui.component.list.BasicListComponent.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                int size;
                View childAt;
                super.onScrollStateChanged(recyclerView, i);
                BasicListComponent.this.getScrollStartEndHelper().onScrollStateChanged(i);
                List<OnWXScrollListener> wXScrollListeners = BasicListComponent.this.getInstance().getWXScrollListeners();
                if (wXScrollListeners == null || (size = wXScrollListeners.size()) <= 0) {
                    return;
                }
                for (int i2 = 0; i2 < size && i2 < wXScrollListeners.size(); i2++) {
                    OnWXScrollListener onWXScrollListener = wXScrollListeners.get(i2);
                    if (onWXScrollListener != null && (childAt = recyclerView.getChildAt(0)) != null) {
                        onWXScrollListener.onScrollStateChanged(recyclerView, 0, childAt.getTop(), i);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                List<OnWXScrollListener> wXScrollListeners = BasicListComponent.this.getInstance().getWXScrollListeners();
                if (wXScrollListeners == null || wXScrollListeners.size() <= 0) {
                    return;
                }
                try {
                    for (OnWXScrollListener onWXScrollListener : wXScrollListeners) {
                        if (onWXScrollListener != null) {
                            if (!(onWXScrollListener instanceof ICheckBindingScroller)) {
                                onWXScrollListener.onScrolled(recyclerView, i, i2);
                            } else if (((ICheckBindingScroller) onWXScrollListener).isNeedScroller(BasicListComponent.this.getRef(), null)) {
                                onWXScrollListener.onScrolled(recyclerView, i, i2);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        adapterView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.list.BasicListComponent.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ViewGroup viewGroup = (ViewGroup) BasicListComponent.this.getHostView();
                if (viewGroup == 0) {
                    return;
                }
                BasicListComponent.this.mViewOnScrollListener.onScrolled(((ListComponentView) viewGroup).getInnerView(), 0, 0);
                viewGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        return adapterView;
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public void onBindViewHolder(final ListBaseViewHolder listBaseViewHolder, int i) {
        if (listBaseViewHolder == null) {
            return;
        }
        listBaseViewHolder.setComponentUsing(true);
        IWXObject listChild = getListChild(i);
        if (listChild instanceof ListStanceCell) {
            listBaseViewHolder.getView().setBackgroundColor(WXResourceUtils.getColor(((ListStanceCell) listChild).getBackgroundColor(), -1));
            listBaseViewHolder.getView().setVisibility(0);
            listBaseViewHolder.getView().postInvalidate();
            return;
        }
        if (listChild instanceof WXComponent) {
            WXComponent wXComponent = (WXComponent) listChild;
            if ((wXComponent instanceof WXRefresh) || (wXComponent instanceof WXLoading) || wXComponent.isFixed()) {
                if (WXEnvironment.isApkDebugable()) {
                    WXLogUtils.d(this.TAG, "Bind WXRefresh & WXLoading " + listBaseViewHolder);
                }
                if (!(wXComponent instanceof WXBaseRefresh) || listBaseViewHolder.getView() == null) {
                    return;
                }
                if (wXComponent.getAttrs().get("holderBackground") != null || wXComponent.getStyles().containsKey("backgroundColor")) {
                    Object obj = wXComponent.getAttrs().get("holderBackground");
                    listBaseViewHolder.getView().setBackgroundColor(obj != null ? WXResourceUtils.getColor(obj.toString(), -1) : WXResourceUtils.getColor(wXComponent.getStyles().getBackgroundColor(), -1));
                    listBaseViewHolder.getView().setVisibility(0);
                    listBaseViewHolder.getView().postInvalidate();
                    return;
                }
                return;
            }
            if (listBaseViewHolder.getComponent() == null || !(listBaseViewHolder.getComponent() instanceof WXCell)) {
                return;
            }
            if (listBaseViewHolder.isRecycled()) {
                listBaseViewHolder.bindData(wXComponent);
                wXComponent.onRenderFinish(1);
            }
            DragHelper dragHelper = this.mDragHelper;
            if (dragHelper == null || !dragHelper.isDraggable()) {
                return;
            }
            String str = this.mTriggerType;
            if (str == null) {
                str = "longpress";
            }
            this.mTriggerType = str;
            WXCell wXCell = (WXCell) listBaseViewHolder.getComponent();
            boolean zBooleanValue = WXUtils.getBoolean(wXCell.getAttrs().get(EXCLUDED), Boolean.FALSE).booleanValue();
            this.mDragHelper.setDragExcluded(listBaseViewHolder, zBooleanValue);
            if (!"pan".equals(this.mTriggerType)) {
                if ("longpress".equals(this.mTriggerType)) {
                    this.mDragHelper.setLongPressDragEnabled(true);
                    return;
                }
                return;
            }
            this.mDragHelper.setLongPressDragEnabled(false);
            WXComponent wXComponentFindComponentByAnchorName = findComponentByAnchorName(wXCell, DRAG_ANCHOR);
            if (wXComponentFindComponentByAnchorName != null && wXComponentFindComponentByAnchorName.getHostView() != null && !zBooleanValue) {
                wXComponentFindComponentByAnchorName.getHostView().setOnTouchListener(new View.OnTouchListener() { // from class: com.taobao.weex.ui.component.list.BasicListComponent.6
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (MotionEventCompat.getActionMasked(motionEvent) != 0) {
                            return true;
                        }
                        BasicListComponent.this.mDragHelper.startDrag(listBaseViewHolder);
                        return true;
                    }
                });
                return;
            }
            if (WXEnvironment.isApkDebugable()) {
                if (!zBooleanValue) {
                    WXLogUtils.e(this.TAG, "[error] onBindViewHolder: the anchor component or view is not found");
                    return;
                }
                WXLogUtils.d(this.TAG, "onBindViewHolder: position " + i + " is drag excluded");
            }
        }
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public ListBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (i == -1) {
            return createVHForRefreshComponent(i);
        }
        if (this.mChildren != null) {
            SparseArray<ArrayList<WXComponent>> sparseArray = this.mViewTypes;
            if (sparseArray == null) {
                return createVHForFakeComponent(i);
            }
            ArrayList<WXComponent> arrayList = sparseArray.get(i);
            checkRecycledViewPool(i);
            if (arrayList == null) {
                return createVHForFakeComponent(i);
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                WXComponent wXComponent = arrayList.get(i2);
                if (wXComponent != null && !wXComponent.isUsing()) {
                    if (wXComponent.isFixed()) {
                        return createVHForFakeComponent(i);
                    }
                    if (!(wXComponent instanceof WXCell)) {
                        if (wXComponent instanceof WXBaseRefresh) {
                            return createVHForRefreshComponent(i);
                        }
                        WXLogUtils.e(this.TAG, "List cannot include element except cell、header、fixed、refresh and loading");
                        return createVHForFakeComponent(i);
                    }
                    if (wXComponent.getRealView() != null) {
                        return new ListBaseViewHolder(wXComponent, i);
                    }
                    wXComponent.lazy(false);
                    wXComponent.createView();
                    wXComponent.applyLayoutAndEvent(wXComponent);
                    return new ListBaseViewHolder(wXComponent, i, true);
                }
            }
        }
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.e(this.TAG, "Cannot find request viewType: " + i);
        }
        return createVHForFakeComponent(i);
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public boolean onFailedToRecycleView(ListBaseViewHolder listBaseViewHolder) {
        if (!WXEnvironment.isApkDebugable()) {
            return false;
        }
        WXLogUtils.d(this.TAG, "Failed to recycle " + listBaseViewHolder);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void onHostViewInitialized(T t) {
        super.onHostViewInitialized((BasicListComponent<T>) t);
        WXRecyclerView innerView = t.getInnerView();
        if (innerView == null || innerView.getAdapter() == null) {
            WXLogUtils.e(this.TAG, "RecyclerView is not found or Adapter is not bound");
            return;
        }
        if (WXUtils.getBoolean(getAttrs().get("prefetchGapDisable"), Boolean.FALSE).booleanValue() && innerView.getLayoutManager() != null) {
            innerView.getLayoutManager().setItemPrefetchEnabled(false);
        }
        ArrayList<WXComponent> arrayList = this.mChildren;
        if (arrayList == null) {
            WXLogUtils.e(this.TAG, "children is null");
        } else {
            this.mDragHelper = new DefaultDragHelper(arrayList, innerView, new EventTrigger() { // from class: com.taobao.weex.ui.component.list.BasicListComponent.1
                @Override // com.taobao.weex.ui.component.list.EventTrigger
                public void triggerEvent(String str, Map<String, Object> map) {
                    BasicListComponent.this.fireEvent(str, map);
                }
            });
            this.mTriggerType = getTriggerType(this);
        }
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public void onViewRecycled(ListBaseViewHolder listBaseViewHolder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        listBaseViewHolder.setComponentUsing(false);
        if (!listBaseViewHolder.canRecycled() || listBaseViewHolder.getComponent() == null || listBaseViewHolder.getComponent().isUsing()) {
            WXLogUtils.w(this.TAG, "this holder can not be allowed to  recycled");
        } else {
            listBaseViewHolder.recycled();
        }
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d(this.TAG, "Recycle holder " + (System.currentTimeMillis() - jCurrentTimeMillis) + "  Thread:" + Thread.currentThread().getName());
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void setMarginsSupportRTL(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2, int i3, int i4) {
        marginLayoutParams.setMargins(i, i2, i3, i4);
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.setMarginEnd(i3);
    }

    @JSMethod
    public void scrollTo(String str, JSCallback jSCallback) {
        float realPxByWidth = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(JSON.parseObject(str).getString(Constants.Name.SCROLL_TOP)), getInstance().getInstanceViewPortWidthWithFloat());
        T hostView = getHostView();
        if (hostView == null) {
            return;
        }
        WXRecyclerView innerView = hostView.getInnerView();
        if (getOrientation() == 1) {
            innerView.scrollTo(0, (int) realPxByWidth, getOrientation());
        } else {
            innerView.scrollTo((int) realPxByWidth, 0, getOrientation());
        }
        if (jSCallback != null) {
            HashMap map = new HashMap();
            map.put("type", WXImage.SUCCEED);
            jSCallback.invoke(map);
        }
    }
}
