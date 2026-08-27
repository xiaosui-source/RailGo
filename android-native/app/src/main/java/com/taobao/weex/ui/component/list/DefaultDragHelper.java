package com.taobao.weex.ui.component.list;

import android.view.View;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.WXLogUtils;
import io.dcloud.common.DHInterface.IApp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class DefaultDragHelper implements DragHelper {
    private static final String EVENT_END_DRAG = "dragend";
    private static final String EVENT_START_DRAG = "dragstart";
    private static final String TAG = "WXListExComponent";
    private static final String TAG_EXCLUDED = "drag_excluded";
    private boolean isDraggable = false;
    private final List<WXComponent> mDataSource;
    private final EventTrigger mEventTrigger;
    private ItemTouchHelper mItemTouchHelper;
    private boolean mLongPressEnabled;
    private final RecyclerView mRecyclerView;

    DefaultDragHelper(List<WXComponent> list, RecyclerView recyclerView, EventTrigger eventTrigger) {
        this.mDataSource = list;
        this.mEventTrigger = eventTrigger;
        this.mRecyclerView = recyclerView;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragSupportCallback(this, true));
        this.mItemTouchHelper = itemTouchHelper;
        try {
            itemTouchHelper.attachToRecyclerView(recyclerView);
        } catch (Throwable unused) {
        }
    }

    private Map<String, Object> buildEvent(String str, int i, int i2) {
        HashMap map = new HashMap(4);
        if (str == null) {
            str = "unknown";
        }
        map.put(IApp.ConfigProperty.CONFIG_TARGET, str);
        map.put("fromIndex", Integer.valueOf(i));
        map.put("toIndex", Integer.valueOf(i2));
        map.put("timestamp", Long.valueOf(System.currentTimeMillis()));
        return map;
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public boolean isDragExcluded(RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        if (view != null) {
            return view.getTag() != null && TAG_EXCLUDED.equals(viewHolder.itemView.getTag());
        }
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.e(TAG, "[error] viewHolder.itemView is null");
        }
        return false;
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public boolean isDraggable() {
        return this.isDraggable;
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public boolean isLongPressDragEnabled() {
        return this.mLongPressEnabled;
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public void onDragEnd(WXComponent wXComponent, int i, int i2) {
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d(TAG, "list on drag end : from index " + i + " to index " + i2);
        }
        this.mEventTrigger.triggerEvent(EVENT_END_DRAG, buildEvent(wXComponent.getRef(), i, i2));
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public void onDragStart(WXComponent wXComponent, int i) {
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d(TAG, "list on drag start : from index " + i);
        }
        this.mEventTrigger.triggerEvent(EVENT_START_DRAG, buildEvent(wXComponent.getRef(), i, -1));
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public void onDragging(int i, int i2) {
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d(TAG, "list on dragging : from index " + i + " to index " + i2);
        }
        RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
        if (adapter == null) {
            WXLogUtils.e(TAG, "drag failed because of RecyclerView#Adapter is not bound");
            return;
        }
        if (i < 0 || i > this.mDataSource.size() - 1 || i2 < 0 || i2 > this.mDataSource.size() - 1) {
            return;
        }
        Collections.swap(this.mDataSource, i, i2);
        adapter.notifyItemMoved(i, i2);
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public void setDragExcluded(RecyclerView.ViewHolder viewHolder, boolean z) {
        View view = viewHolder.itemView;
        if (view == null) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.e(TAG, "[error] viewHolder.itemView is null");
            }
        } else if (z) {
            view.setTag(TAG_EXCLUDED);
        } else {
            view.setTag(null);
        }
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public void setDraggable(boolean z) {
        this.isDraggable = z;
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public void setLongPressDragEnabled(boolean z) {
        this.mLongPressEnabled = z;
    }

    @Override // com.taobao.weex.ui.component.list.DragHelper
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        if (this.isDraggable) {
            this.mItemTouchHelper.startDrag(viewHolder);
        }
    }
}
