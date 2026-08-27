package com.taobao.weex.ui.component.list;

import androidx.recyclerview.widget.RecyclerView;
import com.taobao.weex.ui.component.WXComponent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
interface DragHelper {
    boolean isDragExcluded(RecyclerView.ViewHolder viewHolder);

    boolean isDraggable();

    boolean isLongPressDragEnabled();

    void onDragEnd(WXComponent wXComponent, int i, int i2);

    void onDragStart(WXComponent wXComponent, int i);

    void onDragging(int i, int i2);

    void setDragExcluded(RecyclerView.ViewHolder viewHolder, boolean z);

    void setDraggable(boolean z);

    void setLongPressDragEnabled(boolean z);

    void startDrag(RecyclerView.ViewHolder viewHolder);
}
