package com.taobao.weex.ui.component.list.template;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.ICheckBindingScroller;
import com.taobao.weex.common.OnWXScrollListener;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.el.parse.ArrayStack;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.AppearanceHelper;
import com.taobao.weex.ui.component.Scrollable;
import com.taobao.weex.ui.component.WXBaseRefresh;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXLoading;
import com.taobao.weex.ui.component.WXRefresh;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.binding.Layouts;
import com.taobao.weex.ui.component.binding.Statements;
import com.taobao.weex.ui.component.helper.ScrollStartEndHelper;
import com.taobao.weex.ui.component.list.RecyclerTransform;
import com.taobao.weex.ui.component.list.WXCell;
import com.taobao.weex.ui.view.listview.WXRecyclerView;
import com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener;
import com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener;
import com.taobao.weex.ui.view.listview.adapter.RecyclerViewBaseAdapter;
import com.taobao.weex.ui.view.listview.adapter.WXRecyclerViewOnScrollListener;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXRecyclerTemplateList extends WXVContainer<BounceRecyclerView> implements IRecyclerAdapterListener<TemplateViewHolder>, IOnLoadMoreListener, Scrollable {
    private static final long APPEAR_CHANGE_RUNNABLE_DELAY = 50;
    private static final String EMPTY_HOLDER_TEMPLATE_KEY = "";
    public static final boolean ENABLE_TRACE_LOG = false;
    private static final String NAME_HAS_FIXED_SIZE = "hasFixedSize";
    private static final String NAME_ITEM_VIEW_CACHE_SIZE = "itemViewCacheSize";
    private static final String NAME_TEMPLATE_CACHE_SIZE = "templateCacheSize";
    public static final String TAG = "WXRecyclerTemplateList";
    private CellDataManager cellDataManager;
    private CellRenderContext cellRenderContext;
    private WXCell defaultTemplateCell;
    private String defaultTemplateKey;
    private boolean hasAppendTreeDone;
    private boolean hasLayoutDone;
    private boolean isScrollable;
    private String listDataIndexKey;
    private String listDataItemKey;
    private String listDataKey;
    private String listDataTemplateKey;
    private Runnable listUpdateRunnable;
    private Runnable mAppearChangeRunnable;
    private ArrayMap<Integer, List<AppearanceHelper>> mAppearHelpers;
    protected int mColumnCount;
    protected float mColumnGap;
    protected float mColumnWidth;
    private ArrayMap<Integer, Map<String, Map<Integer, List<Object>>>> mDisAppearWatchList;
    private boolean mForceLoadmoreNextTime;
    private boolean mHasAddScrollEvent;
    private RecyclerView.ItemAnimator mItemAnimator;
    private Point mLastReport;
    protected int mLayoutType;
    private int mListCellCount;
    private int mOffsetAccuracy;
    private float mPaddingLeft;
    private float mPaddingRight;
    private ScrollStartEndHelper mScrollStartEndHelper;
    private TemplateStickyHelper mStickyHelper;
    private Map<String, WXCell> mTemplateSources;
    private ArrayMap<String, Integer> mTemplateViewTypes;
    private ConcurrentHashMap<String, TemplateCache> mTemplatesCache;
    private WXRecyclerViewOnScrollListener mViewOnScrollListener;
    private int orientation;
    private int templateCacheSize;

    public WXRecyclerTemplateList(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) throws NumberFormatException {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.mLayoutType = 1;
        this.mColumnCount = 1;
        this.mColumnGap = 0.0f;
        this.mColumnWidth = 0.0f;
        this.mViewOnScrollListener = new WXRecyclerViewOnScrollListener(this);
        this.mListCellCount = 0;
        this.mForceLoadmoreNextTime = false;
        this.orientation = 1;
        this.isScrollable = true;
        this.mOffsetAccuracy = 10;
        this.mLastReport = new Point(-1, -1);
        this.mHasAddScrollEvent = false;
        this.listDataKey = Constants.Name.Recycler.LIST_DATA;
        this.listDataItemKey = null;
        this.listDataIndexKey = null;
        this.listDataTemplateKey = Constants.Name.Recycler.SLOT_TEMPLATE_CASE;
        this.templateCacheSize = 2;
        this.defaultTemplateKey = "@default_template_cell";
        this.mAppearHelpers = new ArrayMap<>();
        this.mDisAppearWatchList = new ArrayMap<>();
        this.cellRenderContext = new CellRenderContext();
        this.mAppearChangeRunnable = null;
        this.hasAppendTreeDone = false;
        this.hasLayoutDone = false;
        initRecyclerTemplateList(wXSDKInstance, basicComponentData, wXVContainer);
    }

    private int calcContentSize() {
        if (this.cellDataManager.listData == null) {
            return 0;
        }
        int layoutHeight = 0;
        for (int i = 0; i < this.cellDataManager.listData.size(); i++) {
            WXCell sourceTemplate = getSourceTemplate(i);
            if (sourceTemplate != null) {
                layoutHeight = (int) (layoutHeight + sourceTemplate.getLayoutHeight());
            }
        }
        return layoutHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAppendDone(boolean z) {
        if (this.mTemplateSources.size() == 0) {
            return;
        }
        Iterator<Map.Entry<String, WXCell>> it = this.mTemplateSources.entrySet().iterator();
        while (it.hasNext()) {
            if (!it.next().getValue().isAppendTreeDone()) {
                return;
            }
        }
        this.hasAppendTreeDone = true;
        if (this.hasLayoutDone) {
            notifyUpdateList();
        }
    }

    public static void doCreateCellViewBindData(WXCell wXCell, String str, boolean z) {
        if (wXCell.isLazy() || wXCell.getHostView() == 0) {
            System.currentTimeMillis();
            Statements.initLazyComponent(wXCell, null);
            WXEnvironment.isOpenDebugLog();
        }
    }

    private List<WXComponent> doRenderTemplate(WXCell wXCell, int i) {
        this.cellRenderContext.clear();
        Object obj = this.cellDataManager.listData.get(i);
        CellRenderState renderState = this.cellDataManager.getRenderState(i);
        CellRenderContext cellRenderContext = this.cellRenderContext;
        cellRenderContext.renderState = renderState;
        cellRenderContext.templateList = this;
        cellRenderContext.position = i;
        ArrayStack arrayStack = cellRenderContext.stack;
        Map map = cellRenderContext.map;
        if (this.cellDataManager.listData != null) {
            arrayStack.push(map);
            map.put(this.listDataKey, this.cellDataManager.listData);
            if (!TextUtils.isEmpty(this.listDataIndexKey)) {
                map.put(this.listDataIndexKey, new PositionRef(renderState));
            }
            if (TextUtils.isEmpty(this.listDataItemKey)) {
                arrayStack.push(obj);
            } else {
                map.put(this.listDataItemKey, obj);
            }
        }
        if (renderState.itemId <= 0) {
            getItemId(i);
        }
        List<WXComponent> listDoRender = Statements.doRender(wXCell, this.cellRenderContext);
        if (renderState.isDirty()) {
            renderState.resetDirty();
        }
        return listDoRender;
    }

    private WXCell findCell(WXComponent wXComponent) {
        WXVContainer parent;
        if (wXComponent instanceof WXCell) {
            return (WXCell) wXComponent;
        }
        if (wXComponent == null || (parent = wXComponent.getParent()) == null) {
            return null;
        }
        return findCell(parent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireScrollEvent(RecyclerView recyclerView, int i, int i2) {
        fireEvent("scroll", getScrollEvent(recyclerView, i, i2));
    }

    private WXCell getCellTemplateFromCache(String str) {
        ConcurrentLinkedQueue<WXCell> concurrentLinkedQueue;
        TemplateCache templateCache = this.mTemplatesCache.get(str);
        WXCell wXCellPoll = (templateCache == null || (concurrentLinkedQueue = templateCache.cells) == null || concurrentLinkedQueue.size() <= 0) ? null : templateCache.cells.poll();
        if (templateCache == null || !templateCache.isLoadIng) {
            if (templateCache == null) {
                templateCache = new TemplateCache();
                this.mTemplatesCache.put(str, templateCache);
            }
            templateCache.isLoadIng = true;
            WXCell wXCell = this.mTemplateSources.get(str);
            if (wXCell != null && WXUtils.getBoolean(wXCell.getAttrs().get("preload"), Boolean.TRUE).booleanValue()) {
                new AsyncCellLoadTask(str, wXCell, this).startTask();
            }
        }
        return wXCellPoll;
    }

    private int getCellTemplateItemType(WXCell wXCell) {
        if (wXCell == null) {
            return -1;
        }
        if (wXCell.getAttrs() == null) {
            return 0;
        }
        String string = WXUtils.getString(wXCell.getAttrs().get(Constants.Name.Recycler.SLOT_TEMPLATE_CASE), null);
        if (wXCell == this.defaultTemplateCell) {
            string = this.defaultTemplateKey;
        }
        int iIndexOfKey = this.mTemplateViewTypes.indexOfKey(string);
        if (iIndexOfKey < 0) {
            return -1;
        }
        return iIndexOfKey;
    }

    private void initRecyclerTemplateList(WXSDKInstance wXSDKInstance, BasicComponentData basicComponentData, WXVContainer wXVContainer) throws NumberFormatException {
        updateRecyclerAttr();
        ArrayMap<String, Integer> arrayMap = new ArrayMap<>();
        this.mTemplateViewTypes = arrayMap;
        arrayMap.put("", 0);
        this.mTemplateSources = new HashMap();
        this.mTemplatesCache = new ConcurrentHashMap<>();
        this.mStickyHelper = new TemplateStickyHelper(this);
        this.orientation = basicComponentData.getAttrs().getOrientation();
        this.listDataTemplateKey = WXUtils.getString(getAttrs().get(Constants.Name.Recycler.LIST_DATA_TEMPLATE_SWITCH_KEY), Constants.Name.Recycler.SLOT_TEMPLATE_CASE);
        this.listDataItemKey = WXUtils.getString(getAttrs().get(Constants.Name.Recycler.LIST_DATA_ITEM), this.listDataItemKey);
        this.listDataIndexKey = WXUtils.getString(getAttrs().get("index"), this.listDataIndexKey);
        CellDataManager cellDataManager = new CellDataManager(this);
        this.cellDataManager = cellDataManager;
        cellDataManager.listData = parseListDataToJSONArray(getAttrs().get(Constants.Name.Recycler.LIST_DATA));
    }

    private JSONArray parseListDataToJSONArray(Object obj) {
        try {
        } catch (Exception e) {
            WXLogUtils.e(TAG, "parseListDataException" + e.getMessage());
        }
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        if (obj instanceof String) {
            return JSONArray.parseArray(getAttrs().get(Constants.Name.Recycler.LIST_DATA).toString());
        }
        return new JSONArray();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void removeFooterOrHeader(WXComponent wXComponent) {
        if (wXComponent instanceof WXLoading) {
            ((BounceRecyclerView) getHostView()).removeFooterView(wXComponent);
        } else if (wXComponent instanceof WXRefresh) {
            ((BounceRecyclerView) getHostView()).removeHeaderView(wXComponent);
        }
    }

    private synchronized void renderTemplateCellWithData(WXCell wXCell) {
        JSONArray jSONArray;
        if (wXCell.getRenderData() != null || (jSONArray = this.cellDataManager.listData) == null || jSONArray.size() <= 0) {
            return;
        }
        synchronized (this) {
            if (wXCell.getRenderData() == null) {
                Statements.parseStatementsToken(wXCell);
                int i = 0;
                while (true) {
                    if (i >= this.cellDataManager.listData.size()) {
                        break;
                    }
                    if (wXCell == getSourceTemplate(i)) {
                        Object obj = this.cellDataManager.listData.get(i);
                        doRenderTemplate(wXCell, i);
                        Layouts.doLayoutSync(wXCell, getLayoutWidth(), getLayoutHeight());
                        wXCell.setRenderData(obj);
                        break;
                    }
                    i++;
                }
            }
        }
    }

    private Object safeGetListData(int i) {
        try {
            return this.cellDataManager.listData.get(i);
        } catch (Exception unused) {
            return JSONObject.parseObject("{}");
        }
    }

    private void setAppearanceWatch(WXComponent wXComponent, int i, boolean z) {
        int cellTemplateItemType;
        AppearanceHelper next;
        if (this.cellDataManager.listData == null || this.mAppearHelpers == null || TextUtils.isEmpty(wXComponent.getRef()) || (cellTemplateItemType = getCellTemplateItemType(findCell(wXComponent))) < 0) {
            return;
        }
        List<AppearanceHelper> arrayList = this.mAppearHelpers.get(Integer.valueOf(cellTemplateItemType));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.mAppearHelpers.put(Integer.valueOf(cellTemplateItemType), arrayList);
        }
        Iterator<AppearanceHelper> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (wXComponent.getRef().equals(next.getAwareChild().getRef())) {
                    break;
                }
            }
        }
        if (next != null) {
            next.setWatchEvent(i, z);
            return;
        }
        AppearanceHelper appearanceHelper = new AppearanceHelper(wXComponent, cellTemplateItemType);
        appearanceHelper.setWatchEvent(i, z);
        arrayList.add(appearanceHelper);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean setRefreshOrLoading(final WXComponent wXComponent) {
        if ((wXComponent instanceof WXRefresh) && getHostView() != 0) {
            ((BounceRecyclerView) getHostView()).setOnRefreshListener((WXRefresh) wXComponent);
            ((BounceRecyclerView) getHostView()).postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.8
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    ((BounceRecyclerView) WXRecyclerTemplateList.this.getHostView()).setHeaderView(wXComponent);
                }
            }), 100L);
            return true;
        }
        if (!(wXComponent instanceof WXLoading) || getHostView() == 0) {
            return false;
        }
        ((BounceRecyclerView) getHostView()).setOnLoadingListener((WXLoading) wXComponent);
        ((BounceRecyclerView) getHostView()).postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.9
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                ((BounceRecyclerView) WXRecyclerTemplateList.this.getHostView()).setFooterView(wXComponent);
            }
        }), 100L);
        return true;
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

    private void updateRecyclerAttr() throws NumberFormatException {
        this.mLayoutType = getAttrs().getLayoutType();
        int columnCount = getAttrs().getColumnCount();
        this.mColumnCount = columnCount;
        if (columnCount <= 0 && this.mLayoutType != 1) {
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put("componentType", getComponentType());
            arrayMap.put("attribute", getAttrs().toString());
            arrayMap.put("stackTrace", Arrays.toString(Thread.currentThread().getStackTrace()));
            WXExceptionUtils.commitCriticalExceptionRT(getInstanceId(), WXErrorCode.WX_RENDER_ERR_LIST_INVALID_COLUMN_COUNT, Constants.Name.COLUMN_COUNT, String.format(Locale.ENGLISH, "You are trying to set the list/recycler/vlist/waterfall's column to %d, which is illegal. The column count should be a positive integer", Integer.valueOf(this.mColumnCount)), arrayMap);
            this.mColumnCount = 1;
        }
        this.mColumnGap = getAttrs().getColumnGap();
        this.mColumnWidth = getAttrs().getColumnWidth();
        this.mPaddingLeft = getPadding().get(CSSShorthand.EDGE.LEFT);
        this.mPaddingRight = getPadding().get(CSSShorthand.EDGE.RIGHT);
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addChild(WXComponent wXComponent) {
        addChild(wXComponent, -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        super.addEvent(str);
        if (!ScrollStartEndHelper.isScrollEvent(str) || getHostView() == 0 || ((BounceRecyclerView) getHostView()).getInnerView() == null || this.mHasAddScrollEvent) {
            return;
        }
        this.mHasAddScrollEvent = true;
        ((BounceRecyclerView) getHostView()).getInnerView().addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.7
            private boolean mFirstEvent = true;
            private int offsetXCorrection;
            private int offsetYCorrection;

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int i3;
                int i4;
                super.onScrolled(recyclerView, i, i2);
                if (recyclerView.getLayoutManager().canScrollVertically()) {
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
                    WXRecyclerTemplateList.this.getScrollStartEndHelper().onScrolled(i3, i4);
                    if (WXRecyclerTemplateList.this.getEvents().contains("scroll")) {
                        if (this.mFirstEvent) {
                            this.mFirstEvent = false;
                        } else if (WXRecyclerTemplateList.this.shouldReport(i3, i4)) {
                            WXRecyclerTemplateList.this.fireScrollEvent(recyclerView, i3, i4);
                        }
                    }
                }
            }
        });
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addSubView(View view, int i) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod
    public void appendData(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.size() == 0) {
            return;
        }
        CellDataManager cellDataManager = this.cellDataManager;
        if (cellDataManager.listData == null) {
            cellDataManager.listData = new JSONArray();
        }
        int size = this.cellDataManager.listData.size();
        if (size < 0) {
            size = 0;
        }
        this.cellDataManager.listData.addAll(jSONArray);
        ((BounceRecyclerView) getHostView()).getRecyclerViewBaseAdapter().notifyItemRangeInserted(size, jSONArray.size());
    }

    @JSMethod
    public void appendRange(int i, JSONArray jSONArray) {
        insertRange(i, jSONArray);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.Scrollable
    public void bindAppearEvent(WXComponent wXComponent) {
        setAppearanceWatch(wXComponent, 0, true);
        if (this.mAppearChangeRunnable == null) {
            this.mAppearChangeRunnable = new Runnable() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.4
                @Override // java.lang.Runnable
                public void run() {
                    if (WXRecyclerTemplateList.this.mAppearChangeRunnable != null) {
                        WXRecyclerTemplateList.this.notifyAppearStateChange(0, 0, 0, 0);
                    }
                }
            };
        }
        if (getHostView() != 0) {
            ((BounceRecyclerView) getHostView()).removeCallbacks(this.mAppearChangeRunnable);
            ((BounceRecyclerView) getHostView()).postDelayed(this.mAppearChangeRunnable, APPEAR_CHANGE_RUNNABLE_DELAY);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.Scrollable
    public void bindDisappearEvent(WXComponent wXComponent) {
        setAppearanceWatch(wXComponent, 1, true);
        if (this.mAppearChangeRunnable == null) {
            this.mAppearChangeRunnable = new Runnable() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.5
                @Override // java.lang.Runnable
                public void run() {
                    if (WXRecyclerTemplateList.this.mAppearChangeRunnable != null) {
                        WXRecyclerTemplateList.this.notifyAppearStateChange(0, 0, 0, 0);
                    }
                }
            };
        }
        if (getHostView() != 0) {
            ((BounceRecyclerView) getHostView()).removeCallbacks(this.mAppearChangeRunnable);
            ((BounceRecyclerView) getHostView()).postDelayed(this.mAppearChangeRunnable, APPEAR_CHANGE_RUNNABLE_DELAY);
        }
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public void bindStickStyle(WXComponent wXComponent) {
        TemplateStickyHelper templateStickyHelper;
        WXComponent wXComponentFindParentType = findParentType(wXComponent, WXCell.class);
        if (wXComponentFindParentType == null || (templateStickyHelper = this.mStickyHelper) == null || templateStickyHelper.getStickyTypes().contains(wXComponentFindParentType.getRef())) {
            return;
        }
        this.mStickyHelper.getStickyTypes().add(wXComponentFindParentType.getRef());
        notifyUpdateList();
    }

    public int calcContentOffset(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int i = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            int iFindFirstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int spanCount = 0;
            while (i < iFindFirstVisibleItemPosition) {
                WXCell sourceTemplate = getSourceTemplate(i);
                if (sourceTemplate != null) {
                    spanCount = (int) (spanCount - sourceTemplate.getLayoutHeight());
                }
                i++;
            }
            if (layoutManager instanceof GridLayoutManager) {
                spanCount /= ((GridLayoutManager) layoutManager).getSpanCount();
            }
            View viewFindViewByPosition = layoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
            return viewFindViewByPosition != null ? spanCount + viewFindViewByPosition.getTop() : spanCount;
        }
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return -1;
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int spanCount2 = staggeredGridLayoutManager.getSpanCount();
        int i2 = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];
        int layoutHeight = 0;
        while (i < i2) {
            WXCell sourceTemplate2 = getSourceTemplate(i);
            if (sourceTemplate2 != null) {
                layoutHeight = (int) (layoutHeight - sourceTemplate2.getLayoutHeight());
            }
            i++;
        }
        int i3 = layoutHeight / spanCount2;
        View viewFindViewByPosition2 = layoutManager.findViewByPosition(i2);
        return viewFindViewByPosition2 != null ? i3 + viewFindViewByPosition2.getTop() : i3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod(uiThread = true)
    public void closest(String str, String str2, JSCallback jSCallback) throws NumberFormatException {
        try {
            String[] strArrSplit = str.split("@");
            String str3 = strArrSplit[0];
            int i = Integer.parseInt(strArrSplit[1]);
            WXComponent wXComponentFindVirtualComponentByVRef = TemplateDom.findVirtualComponentByVRef(getInstanceId(), str);
            if (wXComponentFindVirtualComponentByVRef != null && getHostView() != 0 && ((BounceRecyclerView) getHostView()).getInnerView() != null) {
                ArrayList arrayList = new ArrayList(4);
                Selector.closest(wXComponentFindVirtualComponentByVRef, str2, arrayList);
                if (arrayList.size() > 0) {
                    jSCallback.invoke(TemplateDom.toMap(str3, i, (WXComponent) arrayList.get(0)));
                } else {
                    jSCallback.invoke(new HashMap(4));
                }
            }
        } catch (Exception e) {
            jSCallback.invoke(new HashMap(4));
            WXLogUtils.e(TAG, e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void computeVisiblePointInViewCoordinate(PointF pointF) {
        WXRecyclerView innerView = ((BounceRecyclerView) getHostView()).getInnerView();
        pointF.set(innerView.computeHorizontalScrollOffset(), innerView.computeVerticalScrollOffset());
    }

    public WXComponent copyComponentFromSourceCell(WXCell wXCell) {
        renderTemplateCellWithData(wXCell);
        return (WXCell) Statements.copyComponentTree(wXCell);
    }

    public ArrayStack copyStack(CellRenderContext cellRenderContext, ArrayStack arrayStack) {
        ArrayStack arrayStack2 = new ArrayStack();
        for (int i = 0; i < arrayStack.size(); i++) {
            Object map = arrayStack.get(i);
            if (map instanceof Map) {
                map = new HashMap((Map) map);
            }
            arrayStack2.push(map);
        }
        return arrayStack2;
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void createChildViewAt(int i) {
        if (i >= 0 || childCount() - 1 >= 0) {
            WXComponent child = getChild(i);
            if (child instanceof WXBaseRefresh) {
                child.createView();
                setRefreshOrLoading(child);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public void destroy() {
        synchronized (this) {
            if (getHostView() != 0) {
                if (this.mAppearChangeRunnable != null) {
                    ((BounceRecyclerView) getHostView()).removeCallbacks(this.mAppearChangeRunnable);
                    this.mAppearChangeRunnable = null;
                }
                ((BounceRecyclerView) getHostView()).removeCallbacks(this.listUpdateRunnable);
                if (((BounceRecyclerView) getHostView()).getInnerView() != null) {
                    ((BounceRecyclerView) getHostView()).getInnerView().setAdapter(null);
                }
            }
            CellDataManager cellDataManager = this.cellDataManager;
            if (cellDataManager.listData != null) {
                cellDataManager.setListData(null);
            }
            if (this.mStickyHelper != null) {
                this.mStickyHelper = null;
            }
            ArrayMap<String, Integer> arrayMap = this.mTemplateViewTypes;
            if (arrayMap != null) {
                arrayMap.clear();
            }
            Map<String, WXCell> map = this.mTemplateSources;
            if (map != null) {
                map.clear();
            }
            ArrayMap<Integer, List<AppearanceHelper>> arrayMap2 = this.mAppearHelpers;
            if (arrayMap2 != null) {
                arrayMap2.clear();
            }
            ArrayMap<Integer, Map<String, Map<Integer, List<Object>>>> arrayMap3 = this.mDisAppearWatchList;
            if (arrayMap3 != null) {
                arrayMap3.clear();
            }
            super.destroy();
        }
    }

    public WXComponent findChildByAttrsRef(WXComponent wXComponent, String str) {
        if (wXComponent.getAttrs() != null && str.equals(wXComponent.getAttrs().get("ref"))) {
            return wXComponent;
        }
        if (!(wXComponent instanceof WXVContainer)) {
            return null;
        }
        WXVContainer wXVContainer = (WXVContainer) wXComponent;
        for (int i = 0; i < wXVContainer.getChildCount(); i++) {
            WXComponent wXComponentFindChildByAttrsRef = findChildByAttrsRef(wXVContainer.getChild(i), str);
            if (wXComponentFindChildByAttrsRef != null) {
                return wXComponentFindChildByAttrsRef;
            }
        }
        return null;
    }

    public WXComponent findChildByRef(WXComponent wXComponent, String str) {
        if (str.equals(wXComponent.getRef())) {
            return wXComponent;
        }
        if (!(wXComponent instanceof WXVContainer)) {
            return null;
        }
        WXVContainer wXVContainer = (WXVContainer) wXComponent;
        for (int i = 0; i < wXVContainer.getChildCount(); i++) {
            WXComponent wXComponentFindChildByRef = findChildByRef(wXVContainer.getChild(i), str);
            if (wXComponentFindChildByRef != null) {
                return wXComponentFindChildByRef;
            }
        }
        return null;
    }

    public List<WXComponent> findChildListByRef(WXComponent wXComponent, String str) {
        WXComponent wXComponentFindChildByRef = findChildByRef(wXComponent, str);
        if (wXComponentFindChildByRef == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        WXVContainer parent = wXComponentFindChildByRef.getParent();
        if (parent == null || (parent instanceof WXRecyclerTemplateList)) {
            arrayList.add(wXComponentFindChildByRef);
            return arrayList;
        }
        for (int i = 0; i < parent.getChildCount(); i++) {
            WXComponent child = parent.getChild(i);
            if (str.equals(child.getRef())) {
                arrayList.add(child);
            }
        }
        return arrayList;
    }

    public WXComponent findParentType(WXComponent wXComponent, Class cls) {
        if (cls.isAssignableFrom(wXComponent.getClass())) {
            return wXComponent;
        }
        if (wXComponent.getParent() == null) {
            return null;
        }
        findTypeParent(wXComponent.getParent(), cls);
        return null;
    }

    public CellDataManager getCellDataManager() {
        return this.cellDataManager;
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
        ArrayMap<String, Integer> arrayMap;
        Map<String, WXCell> map;
        if (!this.hasLayoutDone || !this.hasAppendTreeDone || this.cellDataManager.listData == null || (arrayMap = this.mTemplateViewTypes) == null || arrayMap.size() <= 1 || (map = this.mTemplateSources) == null || map.size() == 0) {
            return 0;
        }
        return this.cellDataManager.listData.size();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035  */
    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public long getItemId(int r7) {
        /*
            r6 = this;
            com.taobao.weex.ui.component.list.template.CellDataManager r0 = r6.cellDataManager
            com.taobao.weex.ui.component.list.template.CellRenderState r0 = r0.getRenderState(r7)
            long r1 = r0.itemId
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L45
            java.lang.String r1 = r6.getTemplateKey(r7)
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L1b
            r0 = -1
            return r0
        L1b:
            java.lang.Object r1 = r6.safeGetListData(r7)
            boolean r2 = r1 instanceof com.alibaba.fastjson.JSONObject
            if (r2 == 0) goto L35
            r2 = r1
            com.alibaba.fastjson.JSONObject r2 = (com.alibaba.fastjson.JSONObject) r2
            java.lang.String r3 = "keyItemId"
            boolean r4 = r2.containsKey(r3)
            if (r4 == 0) goto L35
            long r1 = r2.getLongValue(r3)
            r0.itemId = r1
            goto L45
        L35:
            int r1 = r1.hashCode()
            int r1 = java.lang.Math.abs(r1)
            long r1 = (long) r1
            r3 = 24
            long r1 = r1 << r3
            long r3 = (long) r7
            long r1 = r1 + r3
            r0.itemId = r1
        L45:
            long r0 = r0.itemId
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.getItemId(int):long");
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public int getItemViewType(int i) {
        int iIndexOfKey = this.mTemplateViewTypes.indexOfKey(getTemplateKey(i));
        return iIndexOfKey < 0 ? this.mTemplateViewTypes.indexOfKey("") : iIndexOfKey;
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public int getOrientation() {
        return this.orientation;
    }

    public Map<String, Object> getScrollEvent(RecyclerView recyclerView, int i, int i2) {
        int i3 = -calcContentOffset(recyclerView);
        int measuredWidth = recyclerView.getMeasuredWidth() + recyclerView.computeHorizontalScrollRange();
        int iCalcContentSize = calcContentSize();
        HashMap map = new HashMap(3);
        HashMap map2 = new HashMap(3);
        HashMap map3 = new HashMap(3);
        map2.put("width", Float.valueOf(WXViewUtils.getWebPxByWidth(measuredWidth, getInstance().getInstanceViewPortWidthWithFloat())));
        map2.put("height", Float.valueOf(WXViewUtils.getWebPxByWidth(iCalcContentSize, getInstance().getInstanceViewPortWidthWithFloat())));
        map3.put(Constants.Name.X, Float.valueOf(-WXViewUtils.getWebPxByWidth(i, getInstance().getInstanceViewPortWidthWithFloat())));
        map3.put(Constants.Name.Y, Float.valueOf(-WXViewUtils.getWebPxByWidth(i3, getInstance().getInstanceViewPortWidthWithFloat())));
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.Scrollable
    public int getScrollX() {
        BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) getHostView();
        if (bounceRecyclerView == null) {
            return 0;
        }
        return bounceRecyclerView.getInnerView().getScrollX();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.Scrollable
    public int getScrollY() {
        BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) getHostView();
        if (bounceRecyclerView == null) {
            return 0;
        }
        return bounceRecyclerView.getInnerView().getScrollY();
    }

    public WXCell getSourceTemplate(int i) {
        return this.mTemplateSources.get(getTemplateKey(i));
    }

    public int getTemplateCacheSize() {
        return this.templateCacheSize;
    }

    public String getTemplateKey(int i) {
        return getTemplateKey(safeGetListData(i));
    }

    public ConcurrentHashMap<String, TemplateCache> getTemplatesCache() {
        if (this.mTemplatesCache == null) {
            this.mTemplatesCache = new ConcurrentHashMap<>();
        }
        return this.mTemplatesCache;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod
    public void insertData(int i, Object obj) {
        JSONArray jSONArray;
        if (obj == null || (jSONArray = this.cellDataManager.listData) == null || i > jSONArray.size()) {
            return;
        }
        if (this.cellDataManager.insertData(i, obj)) {
            notifyUpdateList();
        } else {
            ((BounceRecyclerView) getHostView()).getRecyclerViewBaseAdapter().notifyItemInserted(i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod
    public void insertRange(int i, JSONArray jSONArray) {
        JSONArray jSONArray2;
        if (jSONArray == null || jSONArray.size() == 0 || (jSONArray2 = this.cellDataManager.listData) == null || i > jSONArray2.size()) {
            return;
        }
        if (this.cellDataManager.insertRange(i, jSONArray)) {
            notifyUpdateList();
        } else {
            ((BounceRecyclerView) getHostView()).getRecyclerViewBaseAdapter().notifyItemRangeInserted(i, jSONArray.size());
        }
    }

    @Override // com.taobao.weex.ui.component.Scrollable
    public boolean isScrollable() {
        return this.isScrollable;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected WXComponent.MeasureOutput measure(int i, int i2) {
        int screenHeight = WXViewUtils.getScreenHeight(WXEnvironment.sApplication);
        int weexHeight = WXViewUtils.getWeexHeight(getInstanceId());
        if (weexHeight < screenHeight) {
            screenHeight = weexHeight;
        }
        if (i2 > screenHeight) {
            i2 = weexHeight - getAbsoluteY();
        }
        return super.measure(i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener
    public void notifyAppearStateChange(int i, int i2, int i3, int i4) {
        int i5;
        Map<Integer, List<Object>> value;
        List<WXComponent> listFindChildListByRef;
        ArrayMap<Integer, List<AppearanceHelper>> arrayMap = this.mAppearHelpers;
        if (arrayMap == null || arrayMap.size() <= 0) {
            return;
        }
        if (this.mAppearChangeRunnable != null) {
            ((BounceRecyclerView) getHostView()).removeCallbacks(this.mAppearChangeRunnable);
            this.mAppearChangeRunnable = null;
        }
        String str = i4 > 0 ? "up" : i4 < 0 ? "down" : null;
        if (getOrientation() == 0 && i3 != 0) {
            str = i3 > 0 ? "left" : "right";
        }
        WXRecyclerView innerView = ((BounceRecyclerView) getHostView()).getInnerView();
        int i6 = i;
        while (true) {
            i5 = 0;
            if (i6 > i2) {
                break;
            }
            List<AppearanceHelper> list = this.mAppearHelpers.get(Integer.valueOf(getItemViewType(i6)));
            if (list != null) {
                for (AppearanceHelper appearanceHelper : list) {
                    if (appearanceHelper.isWatch()) {
                        TemplateViewHolder templateViewHolder = (TemplateViewHolder) innerView.findViewHolderForAdapterPosition(i6);
                        if (templateViewHolder == null || templateViewHolder.getComponent() == null || (listFindChildListByRef = findChildListByRef(templateViewHolder.getComponent(), appearanceHelper.getAwareChild().getRef())) == null || listFindChildListByRef.size() == 0) {
                            break;
                        }
                        Map<String, Map<Integer, List<Object>>> arrayMap2 = this.mDisAppearWatchList.get(Integer.valueOf(i6));
                        if (arrayMap2 == null) {
                            arrayMap2 = new ArrayMap<>();
                            this.mDisAppearWatchList.put(Integer.valueOf(i6), arrayMap2);
                        }
                        Map<Integer, List<Object>> arrayMap3 = arrayMap2.get(appearanceHelper.getAwareChild().getRef());
                        if (arrayMap3 == null) {
                            arrayMap3 = new ArrayMap<>();
                            arrayMap2.put(appearanceHelper.getAwareChild().getRef(), arrayMap3);
                        }
                        for (int i7 = 0; i7 < listFindChildListByRef.size(); i7++) {
                            WXComponent wXComponent = listFindChildListByRef.get(i7);
                            if (wXComponent.getHostView() != null) {
                                boolean zIsViewVisible = appearanceHelper.isViewVisible(wXComponent.getHostView());
                                int iHashCode = wXComponent.getHostView().hashCode();
                                if (zIsViewVisible) {
                                    if (!arrayMap3.containsKey(Integer.valueOf(iHashCode))) {
                                        wXComponent.notifyAppearStateChange(Constants.Event.APPEAR, str);
                                        arrayMap3.put(Integer.valueOf(iHashCode), (wXComponent.getEvents() == null || wXComponent.getEvents().getEventBindingArgsValues() == null || wXComponent.getEvents().getEventBindingArgsValues().get(Constants.Event.DISAPPEAR) == null) ? null : wXComponent.getEvents().getEventBindingArgsValues().get(Constants.Event.DISAPPEAR));
                                    }
                                } else if (arrayMap3.containsKey(Integer.valueOf(iHashCode))) {
                                    wXComponent.notifyAppearStateChange(Constants.Event.DISAPPEAR, str);
                                    arrayMap3.remove(Integer.valueOf(iHashCode));
                                }
                            }
                        }
                    }
                }
            }
            i6++;
        }
        int itemCount = getItemCount();
        while (i5 < itemCount) {
            if (i5 < i || i5 > i2) {
                Map<String, Map<Integer, List<Object>>> map = this.mDisAppearWatchList.get(Integer.valueOf(i5));
                if (map != null) {
                    WXCell wXCell = this.mTemplateSources.get(getTemplateKey(i5));
                    if (wXCell == null) {
                        return;
                    }
                    for (Map.Entry<String, Map<Integer, List<Object>>> entry : map.entrySet()) {
                        WXComponent wXComponentFindChildByRef = findChildByRef(wXCell, entry.getKey());
                        if (wXComponentFindChildByRef != null && (value = entry.getValue()) != null && value.size() != 0) {
                            WXEvent events = wXComponentFindChildByRef.getEvents();
                            Iterator<Map.Entry<Integer, List<Object>>> it = value.entrySet().iterator();
                            while (it.hasNext()) {
                                events.putEventBindingArgsValue(Constants.Event.DISAPPEAR, it.next().getValue());
                                wXComponentFindChildByRef.notifyAppearStateChange(Constants.Event.DISAPPEAR, str);
                            }
                            value.clear();
                        }
                    }
                    this.mDisAppearWatchList.remove(Integer.valueOf(i5));
                } else {
                    continue;
                }
            } else {
                i5 = i2 + 1;
            }
            i5++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void notifyUpdateList() {
        if (getHostView() == 0 || ((BounceRecyclerView) getHostView()).getInnerView() == null || this.listUpdateRunnable == null) {
            return;
        }
        if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
            this.listUpdateRunnable.run();
        } else {
            ((BounceRecyclerView) getHostView()).removeCallbacks(this.listUpdateRunnable);
            ((BounceRecyclerView) getHostView()).post(this.listUpdateRunnable);
        }
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener
    public void onBeforeScroll(int i, int i2) {
        TemplateStickyHelper templateStickyHelper = this.mStickyHelper;
        if (templateStickyHelper != null) {
            templateStickyHelper.onBeforeScroll(i, i2);
        }
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public boolean onFailedToRecycleView(TemplateViewHolder templateViewHolder) {
        return false;
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IOnLoadMoreListener
    public void onLoadMore(int i) {
        JSONArray jSONArray;
        try {
            String loadMoreOffset = getAttrs().getLoadMoreOffset();
            if (TextUtils.isEmpty(loadMoreOffset)) {
                loadMoreOffset = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
            }
            if (i > WXViewUtils.getRealPxByWidth(Integer.parseInt(loadMoreOffset), getInstance().getInstanceViewPortWidthWithFloat()) || (jSONArray = this.cellDataManager.listData) == null) {
                return;
            }
            if (this.mListCellCount != jSONArray.size() || this.mForceLoadmoreNextTime) {
                fireEvent(Constants.Event.LOADMORE);
                this.mListCellCount = this.cellDataManager.listData.size();
                this.mForceLoadmoreNextTime = false;
            }
        } catch (Exception e) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("WXRecyclerTemplateList onLoadMore : ", e);
            }
        }
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public void onViewRecycled(TemplateViewHolder templateViewHolder) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod(uiThread = true)
    public void queryElement(String str, String str2, JSCallback jSCallback) throws NumberFormatException {
        try {
            String[] strArrSplit = str.split("@");
            String str3 = strArrSplit[0];
            int i = Integer.parseInt(strArrSplit[1]);
            WXComponent wXComponentFindVirtualComponentByVRef = TemplateDom.findVirtualComponentByVRef(getInstanceId(), str);
            if (wXComponentFindVirtualComponentByVRef != null && getHostView() != 0 && ((BounceRecyclerView) getHostView()).getInnerView() != null) {
                ArrayList arrayList = new ArrayList(4);
                Selector.queryElementAll(wXComponentFindVirtualComponentByVRef, str2, arrayList);
                if (arrayList.size() > 0) {
                    jSCallback.invoke(TemplateDom.toMap(str3, i, (WXComponent) arrayList.get(0)));
                } else {
                    jSCallback.invoke(new HashMap(4));
                }
            }
        } catch (Exception e) {
            jSCallback.invoke(new HashMap(4));
            WXLogUtils.e(TAG, e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod(uiThread = true)
    public void queryElementAll(String str, String str2, JSCallback jSCallback) throws NumberFormatException {
        ArrayList arrayList = new ArrayList();
        try {
            String[] strArrSplit = str.split("@");
            int i = 0;
            String str3 = strArrSplit[0];
            int i2 = Integer.parseInt(strArrSplit[1]);
            WXComponent wXComponentFindVirtualComponentByVRef = TemplateDom.findVirtualComponentByVRef(getInstanceId(), str);
            if (wXComponentFindVirtualComponentByVRef != null && getHostView() != 0 && ((BounceRecyclerView) getHostView()).getInnerView() != null) {
                ArrayList arrayList2 = new ArrayList(4);
                Selector.queryElementAll(wXComponentFindVirtualComponentByVRef, str2, arrayList2);
                int size = arrayList2.size();
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    arrayList.add(TemplateDom.toMap(str3, i2, (WXComponent) obj));
                }
                jSCallback.invoke(arrayList);
            }
        } catch (Exception e) {
            jSCallback.invoke(arrayList);
            WXLogUtils.e(TAG, e);
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void remove(WXComponent wXComponent, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        removeFooterOrHeader(wXComponent);
        super.remove(wXComponent, z);
    }

    @JSMethod
    public void removeData(int i, int i2) {
        JSONArray jSONArray = this.cellDataManager.listData;
        if (jSONArray == null || i >= jSONArray.size()) {
            return;
        }
        if (i2 <= 0) {
            i2 = 1;
        }
        int i3 = 0;
        while (i2 > 0 && i < this.cellDataManager.listData.size()) {
            this.cellDataManager.removeData(Integer.valueOf(i));
            i2--;
            i3++;
        }
        if (i3 > 0) {
            notifyUpdateList();
        }
    }

    @JSMethod
    public void resetLoadmore() {
        this.mForceLoadmoreNextTime = true;
        this.mListCellCount = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod(uiThread = true)
    public void scrollTo(String str, Map<String, Object> map) {
        boolean zBooleanValue;
        try {
            int i = str.indexOf(64) > 0 ? Integer.parseInt(str.split("@")[0]) : (int) Float.parseFloat(str);
            if (i >= 0) {
                float realPxByWidth = 0.0f;
                if (map != null) {
                    Object obj = map.get(Constants.Name.ANIMATED);
                    Boolean bool = Boolean.TRUE;
                    WXUtils.getBoolean(obj, bool).getClass();
                    String string = map.get("offset") == null ? WXInstanceApm.VALUE_ERROR_CODE_DEFAULT : map.get("offset").toString();
                    zBooleanValue = WXUtils.getBoolean(map.get(Constants.Name.ANIMATED), bool).booleanValue();
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
                int i2 = (int) realPxByWidth;
                BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) getHostView();
                if (bounceRecyclerView == null) {
                    return;
                }
                bounceRecyclerView.getInnerView().scrollTo(zBooleanValue, i, i2, getOrientation());
            }
        } catch (Exception e2) {
            WXLogUtils.e(TAG, e2);
        }
    }

    @JSMethod(uiThread = true)
    public void scrollToElement(String str, Map<String, Object> map) {
        scrollTo(str, map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.COLUMN_COUNT)
    public void setColumnCount(int i) throws NumberFormatException {
        if (getAttrs().getColumnCount() != this.mColumnCount) {
            updateRecyclerAttr();
            ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.COLUMN_GAP)
    public void setColumnGap(float f) throws NumberFormatException {
        if (getAttrs().getColumnGap() != this.mColumnGap) {
            updateRecyclerAttr();
            ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.COLUMN_WIDTH)
    public void setColumnWidth(int i) throws NumberFormatException {
        if (getAttrs().getColumnWidth() != this.mColumnWidth) {
            updateRecyclerAttr();
            ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public void setIsLayoutRTL(boolean z) {
        super.setIsLayoutRTL(z);
        this.mViewOnScrollListener.setLayoutRTL(isLayoutRTL());
    }

    @JSMethod
    public void setListData(Object obj) {
        JSONArray listDataToJSONArray = parseListDataToJSONArray(obj);
        CellDataManager cellDataManager = this.cellDataManager;
        boolean z = cellDataManager.listData != listDataToJSONArray;
        if ((listDataToJSONArray instanceof JSONArray) && z) {
            cellDataManager.setListData(listDataToJSONArray);
            notifyUpdateList();
        }
    }

    @WXComponentProp(name = Constants.Name.OFFSET_ACCURACY)
    public void setOffsetAccuracy(int i) {
        this.mOffsetAccuracy = (int) WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidthWithFloat());
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) throws NumberFormatException {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "switch":
            case "case":
                this.listDataTemplateKey = WXUtils.getString(obj, Constants.Name.Recycler.SLOT_TEMPLATE_CASE);
                return true;
            case "showScrollbar":
                Boolean bool = WXUtils.getBoolean(obj, null);
                if (bool != null) {
                    setShowScrollbar(bool.booleanValue());
                }
            case "itemViewCacheSize":
            case "hasFixedSize":
                return true;
            case "offsetAccuracy":
                setOffsetAccuracy(WXUtils.getInteger(obj, 10).intValue());
            case "loadmoreoffset":
                return true;
            case "scrollable":
                setScrollable(WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue());
                return true;
            case "alias":
                this.listDataItemKey = WXUtils.getString(obj, this.listDataItemKey);
                return true;
            case "index":
                this.listDataIndexKey = WXUtils.getString(obj, this.listDataIndexKey);
                return true;
            case "listData":
                setListData(obj);
                return true;
            case "scrollDirection":
                if (obj != null) {
                    setScrollDirection(obj.toString());
                }
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.SCROLL_DIRECTION)
    public void setScrollDirection(String str) throws NumberFormatException {
        if (this.orientation != getAttrs().getOrientation()) {
            this.orientation = getAttrs().getOrientation();
            updateRecyclerAttr();
            WXRecyclerView innerView = ((BounceRecyclerView) getHostView()).getInnerView();
            if (this.orientation == 0) {
                innerView.setHorizontalScrollBarEnabled(true);
            } else {
                innerView.setHorizontalScrollBarEnabled(false);
            }
            innerView.initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.SCROLL_LEFT)
    public void setScrollLeft(String str) {
        BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) getHostView();
        if (bounceRecyclerView == null) {
            return;
        }
        bounceRecyclerView.getInnerView().scrollTo((int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(str), getInstance().getInstanceViewPortWidthWithFloat()), 0, getAttrs().getOrientation());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.SCROLL_TOP)
    public void setScrollTop(String str) {
        BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) getHostView();
        if (bounceRecyclerView == null) {
            return;
        }
        bounceRecyclerView.getInnerView().scrollTo(0, (int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(str), getInstance().getInstanceViewPortWidthWithFloat()), getAttrs().getOrientation());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.SCROLLABLE)
    public void setScrollable(boolean z) {
        ((BounceRecyclerView) getHostView()).getInnerView().setScrollable(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = Constants.Name.SHOW_SCROLLBAR)
    public void setShowScrollbar(boolean z) {
        if (getHostView() == 0 || ((BounceRecyclerView) getHostView()).getInnerView() == null) {
            return;
        }
        if (getOrientation() == 1) {
            ((BounceRecyclerView) getHostView()).getInnerView().setVerticalScrollBarEnabled(z);
        } else {
            ((BounceRecyclerView) getHostView()).getInnerView().setHorizontalScrollBarEnabled(z);
        }
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
        TemplateStickyHelper templateStickyHelper;
        WXComponent wXComponentFindParentType = findParentType(wXComponent, WXCell.class);
        if (wXComponentFindParentType == null || (templateStickyHelper = this.mStickyHelper) == null || !templateStickyHelper.getStickyTypes().contains(wXComponentFindParentType.getRef())) {
            return;
        }
        this.mStickyHelper.getStickyTypes().remove(wXComponentFindParentType.getRef());
        notifyUpdateList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JSMethod
    public void updateData(int i, Object obj) {
        JSONArray jSONArray;
        if (obj == null || (jSONArray = this.cellDataManager.listData) == null || i >= jSONArray.size()) {
            return;
        }
        if (this.cellDataManager.updateData(obj, i)) {
            ((BounceRecyclerView) getHostView()).getRecyclerViewBaseAdapter().notifyItemChanged(i, obj);
        } else {
            notifyUpdateList();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void updateProperties(Map<String, Object> map) throws NumberFormatException {
        super.updateProperties(map);
        if (map.containsKey("padding") || map.containsKey(Constants.Name.PADDING_LEFT) || map.containsKey(Constants.Name.PADDING_RIGHT)) {
            if (this.mPaddingLeft == getPadding().get(CSSShorthand.EDGE.LEFT) && this.mPaddingRight == getPadding().get(CSSShorthand.EDGE.RIGHT)) {
                return;
            }
            updateRecyclerAttr();
            ((BounceRecyclerView) getHostView()).getInnerView().initView(getContext(), this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addChild(WXComponent wXComponent, int i) {
        boolean z = wXComponent instanceof WXCell;
        if (!z) {
            super.addChild(wXComponent, i);
        }
        if (!(wXComponent instanceof WXBaseRefresh) && z) {
            if (wXComponent.getAttrs() != null) {
                String string = WXUtils.getString(wXComponent.getAttrs().get(Constants.Name.Recycler.SLOT_TEMPLATE_CASE), null);
                if (getAttrs().containsKey(Constants.Name.Recycler.LIST_DATA_TEMPLATE_SWITCH_KEY)) {
                    if (this.defaultTemplateCell == null) {
                        this.defaultTemplateCell = (WXCell) wXComponent;
                        if (TextUtils.isEmpty(string)) {
                            string = this.defaultTemplateKey;
                            wXComponent.getAttrs().put(Constants.Name.Recycler.SLOT_TEMPLATE_CASE, (Object) string);
                        } else {
                            this.defaultTemplateKey = string;
                        }
                    }
                } else if (this.defaultTemplateCell == null || wXComponent.getAttrs().containsKey("default")) {
                    this.defaultTemplateCell = (WXCell) wXComponent;
                    if (TextUtils.isEmpty(string)) {
                        string = this.defaultTemplateKey;
                        wXComponent.getAttrs().put(Constants.Name.Recycler.SLOT_TEMPLATE_CASE, (Object) string);
                    } else {
                        this.defaultTemplateKey = string;
                    }
                }
                if (string != null) {
                    this.mTemplateSources.put(string, (WXCell) wXComponent);
                    if (this.mTemplateViewTypes.get(string) == null) {
                        ArrayMap<String, Integer> arrayMap = this.mTemplateViewTypes;
                        arrayMap.put(string, Integer.valueOf(arrayMap.size()));
                    }
                }
            }
            ((WXCell) wXComponent).setCellAppendTreeListener(new WXCell.CellAppendTreeListener() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.6
                @Override // com.taobao.weex.ui.component.list.WXCell.CellAppendTreeListener
                public void onAppendTreeDone() {
                    WXRecyclerTemplateList.this.checkAppendDone(false);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public BounceRecyclerView initComponentHostView(Context context) {
        BounceRecyclerView bounceRecyclerView = new BounceRecyclerView(context, this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        WXAttr attrs = getAttrs();
        String str = (String) attrs.get("transform");
        if (str != null) {
            bounceRecyclerView.getInnerView().addItemDecoration(RecyclerTransform.parseTransforms(getOrientation(), str));
        }
        this.mItemAnimator = bounceRecyclerView.getInnerView().getItemAnimator();
        if (attrs.get(NAME_TEMPLATE_CACHE_SIZE) != null) {
            this.templateCacheSize = WXUtils.getInteger(attrs.get(NAME_TEMPLATE_CACHE_SIZE), Integer.valueOf(this.templateCacheSize)).intValue();
        }
        int numberInt = attrs.get(NAME_ITEM_VIEW_CACHE_SIZE) != null ? WXUtils.getNumberInt(getAttrs().get(NAME_ITEM_VIEW_CACHE_SIZE), 2) : 2;
        boolean zBooleanValue = attrs.get("hasFixedSize") != null ? WXUtils.getBoolean(attrs.get("hasFixedSize"), Boolean.FALSE).booleanValue() : false;
        RecyclerViewBaseAdapter recyclerViewBaseAdapter = new RecyclerViewBaseAdapter(this);
        recyclerViewBaseAdapter.setHasStableIds(true);
        bounceRecyclerView.getInnerView().setItemAnimator(null);
        if (numberInt != 2) {
            bounceRecyclerView.getInnerView().setItemViewCacheSize(numberInt);
        }
        if (bounceRecyclerView.getSwipeLayout() != null && WXUtils.getBoolean(getAttrs().get(Constants.Name.NEST_SCROLLING_ENABLED), Boolean.FALSE).booleanValue()) {
            bounceRecyclerView.getSwipeLayout().setNestedScrollingEnabled(true);
        }
        bounceRecyclerView.getInnerView().setHasFixedSize(zBooleanValue);
        bounceRecyclerView.setRecyclerViewBaseAdapter(recyclerViewBaseAdapter);
        bounceRecyclerView.setOverScrollMode(2);
        bounceRecyclerView.getInnerView().clearOnScrollListeners();
        bounceRecyclerView.getInnerView().addOnScrollListener(this.mViewOnScrollListener);
        bounceRecyclerView.getInnerView().addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                View childAt;
                super.onScrollStateChanged(recyclerView, i);
                WXRecyclerTemplateList.this.getScrollStartEndHelper().onScrollStateChanged(i);
                List<OnWXScrollListener> wXScrollListeners = WXRecyclerTemplateList.this.getInstance().getWXScrollListeners();
                if (wXScrollListeners == null || wXScrollListeners.size() <= 0) {
                    return;
                }
                for (OnWXScrollListener onWXScrollListener : wXScrollListeners) {
                    if (onWXScrollListener != null && (childAt = recyclerView.getChildAt(0)) != null) {
                        onWXScrollListener.onScrollStateChanged(recyclerView, 0, childAt.getTop(), i);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                List<OnWXScrollListener> wXScrollListeners = WXRecyclerTemplateList.this.getInstance().getWXScrollListeners();
                if (wXScrollListeners == null || wXScrollListeners.size() <= 0) {
                    return;
                }
                try {
                    for (OnWXScrollListener onWXScrollListener : wXScrollListeners) {
                        if (onWXScrollListener != null) {
                            if (!(onWXScrollListener instanceof ICheckBindingScroller)) {
                                onWXScrollListener.onScrolled(recyclerView, i, i2);
                            } else if (((ICheckBindingScroller) onWXScrollListener).isNeedScroller(WXRecyclerTemplateList.this.getRef(), null)) {
                                onWXScrollListener.onScrolled(recyclerView, i, i2);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bounceRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                BounceRecyclerView bounceRecyclerView2 = (BounceRecyclerView) WXRecyclerTemplateList.this.getHostView();
                if (bounceRecyclerView2 == null) {
                    return;
                }
                WXRecyclerTemplateList.this.mViewOnScrollListener.onScrolled(bounceRecyclerView2.getInnerView(), 0, 0);
                bounceRecyclerView2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        this.listUpdateRunnable = new Runnable() { // from class: com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                if (WXRecyclerTemplateList.this.mStickyHelper != null && WXRecyclerTemplateList.this.mStickyHelper.getStickyTypes().size() > 0) {
                    WXRecyclerTemplateList.this.mStickyHelper.getStickyPositions().clear();
                    if (WXRecyclerTemplateList.this.cellDataManager.listData != null) {
                        for (int i = 0; i < WXRecyclerTemplateList.this.cellDataManager.listData.size(); i++) {
                            WXCell sourceTemplate = WXRecyclerTemplateList.this.getSourceTemplate(i);
                            if (sourceTemplate != null && sourceTemplate.isSticky()) {
                                WXRecyclerTemplateList.this.mStickyHelper.getStickyPositions().add(Integer.valueOf(i));
                            }
                        }
                    }
                }
                if (WXRecyclerTemplateList.this.getHostView() != 0 && ((BounceRecyclerView) WXRecyclerTemplateList.this.getHostView()).getRecyclerViewBaseAdapter() != null) {
                    ((BounceRecyclerView) WXRecyclerTemplateList.this.getHostView()).getRecyclerViewBaseAdapter().notifyDataSetChanged();
                }
                WXEnvironment.isOpenDebugLog();
            }
        };
        return bounceRecyclerView;
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public void onBindViewHolder(TemplateViewHolder templateViewHolder, int i) {
        WXCell template;
        if (templateViewHolder == null || (template = templateViewHolder.getTemplate()) == null) {
            return;
        }
        if (templateViewHolder.getHolderPosition() >= 0) {
            fireEvent(TemplateDom.DETACH_CELL_SLOT, TemplateDom.findAllComponentRefs(getRef(), i, template));
        }
        System.currentTimeMillis();
        templateViewHolder.setHolderPosition(i);
        Object obj = this.cellDataManager.listData.get(i);
        CellRenderState renderState = this.cellDataManager.getRenderState(i);
        if (template.getRenderData() == obj && (renderState == null || !renderState.isDirty())) {
            WXEnvironment.isOpenDebugLog();
            fireEvent(TemplateDom.ATTACH_CELL_SLOT, TemplateDom.findAllComponentRefs(getRef(), i, template));
        } else {
            Statements.doInitCompontent(doRenderTemplate(template, i));
            template.setRenderData(obj);
            Layouts.doLayoutAsync(templateViewHolder, true);
            WXEnvironment.isOpenDebugLog();
        }
    }

    @Override // com.taobao.weex.ui.view.listview.adapter.IRecyclerAdapterListener
    public TemplateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        String strKeyAt = this.mTemplateViewTypes.keyAt(i);
        WXCell wXCell = this.mTemplateSources.get(strKeyAt);
        if (wXCell == null) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
            return new TemplateViewHolder(this, frameLayout, i);
        }
        WXCell cellTemplateFromCache = getCellTemplateFromCache(strKeyAt);
        if (cellTemplateFromCache == null && !wXCell.isSourceUsed()) {
            wXCell.setSourceUsed(true);
            renderTemplateCellWithData(wXCell);
            WXEnvironment.isOpenDebugLog();
            cellTemplateFromCache = wXCell;
        }
        if (cellTemplateFromCache == null) {
            System.currentTimeMillis();
            cellTemplateFromCache = (WXCell) copyComponentFromSourceCell(wXCell);
            WXEnvironment.isOpenDebugLog();
        }
        if (cellTemplateFromCache.isLazy() || cellTemplateFromCache.getHostView() == 0) {
            doCreateCellViewBindData(cellTemplateFromCache, strKeyAt, false);
            WXEnvironment.isOpenDebugLog();
        } else {
            WXEnvironment.isOpenDebugLog();
        }
        return new TemplateViewHolder(this, cellTemplateFromCache, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void onHostViewInitialized(BounceRecyclerView bounceRecyclerView) {
        super.onHostViewInitialized((WXRecyclerTemplateList) bounceRecyclerView);
        WXRecyclerView innerView = bounceRecyclerView.getInnerView();
        if (innerView == null || innerView.getAdapter() == null) {
            WXLogUtils.e(TAG, "RecyclerView is not found or Adapter is not bound");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void setHostLayoutParams(BounceRecyclerView bounceRecyclerView, int i, int i2, int i3, int i4, int i5, int i6) {
        super.setHostLayoutParams((WXRecyclerTemplateList) bounceRecyclerView, i, i2, i3, i4, i5, i6);
        if (this.hasLayoutDone) {
            return;
        }
        this.hasLayoutDone = true;
        this.hasAppendTreeDone = true;
        notifyUpdateList();
    }

    public String getTemplateKey(Object obj) {
        String string = obj instanceof JSONObject ? ((JSONObject) obj).getString(this.listDataTemplateKey) : null;
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        if (this.defaultTemplateCell != null) {
            return this.defaultTemplateKey;
        }
        return "";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.Scrollable
    public void scrollTo(WXComponent wXComponent, Map<String, Object> map) {
        int size;
        boolean zBooleanValue;
        float realPxByWidth = 0.0f;
        int numberInt = -1;
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
            size = WXUtils.getNumberInt(map.get(Constants.Name.Recycler.CELL_INDEX), -1);
            numberInt = WXUtils.getNumberInt(map.get(Constants.Name.Recycler.TYPE_INDEX), -1);
        } else {
            size = -1;
            zBooleanValue = true;
        }
        WXCell wXCellFindCell = findCell(wXComponent);
        if (numberInt >= 0 && this.cellDataManager.listData != null && wXComponent.getRef() != null) {
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i >= this.cellDataManager.listData.size()) {
                    break;
                }
                WXCell sourceTemplate = getSourceTemplate(i);
                if (sourceTemplate != null) {
                    if (wXCellFindCell.getRef().equals(sourceTemplate.getRef())) {
                        i2++;
                    }
                    if (i2 > numberInt) {
                        size = i;
                        break;
                    }
                }
                i++;
            }
            if (size < 0) {
                size = this.cellDataManager.listData.size() - 1;
            }
        }
        int i3 = (int) realPxByWidth;
        BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) getHostView();
        if (bounceRecyclerView != null && size >= 0) {
            bounceRecyclerView.getInnerView().scrollTo(zBooleanValue, size, i3, getOrientation());
        }
    }
}
