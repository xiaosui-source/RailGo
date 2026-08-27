package com.taobao.weex.ui.view.listview.adapter;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.taobao.weex.ui.view.listview.adapter.ListBaseViewHolder;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class RecyclerViewBaseAdapter<T extends ListBaseViewHolder> extends RecyclerView.Adapter<T> {
    private IRecyclerAdapterListener iRecyclerAdapterListener;

    public RecyclerViewBaseAdapter(IRecyclerAdapterListener iRecyclerAdapterListener) {
        this.iRecyclerAdapterListener = iRecyclerAdapterListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        IRecyclerAdapterListener iRecyclerAdapterListener = this.iRecyclerAdapterListener;
        if (iRecyclerAdapterListener != null) {
            return iRecyclerAdapterListener.getItemCount();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return this.iRecyclerAdapterListener.getItemId(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        IRecyclerAdapterListener iRecyclerAdapterListener = this.iRecyclerAdapterListener;
        return iRecyclerAdapterListener != null ? iRecyclerAdapterListener.getItemViewType(i) : i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(T t, int i) {
        IRecyclerAdapterListener iRecyclerAdapterListener = this.iRecyclerAdapterListener;
        if (iRecyclerAdapterListener != null) {
            iRecyclerAdapterListener.onBindViewHolder(t, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public T onCreateViewHolder(ViewGroup viewGroup, int i) {
        IRecyclerAdapterListener iRecyclerAdapterListener = this.iRecyclerAdapterListener;
        if (iRecyclerAdapterListener != null) {
            return (T) iRecyclerAdapterListener.onCreateViewHolder(viewGroup, i);
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public boolean onFailedToRecycleView(T t) {
        IRecyclerAdapterListener iRecyclerAdapterListener = this.iRecyclerAdapterListener;
        return iRecyclerAdapterListener != null ? iRecyclerAdapterListener.onFailedToRecycleView(t) : super.onFailedToRecycleView((RecyclerViewBaseAdapter<T>) t);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(T t) {
        ViewGroup.LayoutParams layoutParams;
        super.onViewAttachedToWindow((RecyclerViewBaseAdapter<T>) t);
        if (t == null || !t.isFullSpan() || (layoutParams = t.itemView.getLayoutParams()) == null || !(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams)) {
            return;
        }
        ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(T t) {
        super.onViewDetachedFromWindow((RecyclerViewBaseAdapter<T>) t);
        if (t != null) {
            t.setComponentUsing(false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(T t) {
        IRecyclerAdapterListener iRecyclerAdapterListener = this.iRecyclerAdapterListener;
        if (iRecyclerAdapterListener != null) {
            iRecyclerAdapterListener.onViewRecycled(t);
        }
        super.onViewRecycled((RecyclerViewBaseAdapter<T>) t);
    }
}
