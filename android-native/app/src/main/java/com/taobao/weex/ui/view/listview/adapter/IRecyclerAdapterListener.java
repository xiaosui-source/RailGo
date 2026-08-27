package com.taobao.weex.ui.view.listview.adapter;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface IRecyclerAdapterListener<T extends RecyclerView.ViewHolder> {
    int getItemCount();

    long getItemId(int i);

    int getItemViewType(int i);

    void onBindViewHolder(T t, int i);

    T onCreateViewHolder(ViewGroup viewGroup, int i);

    boolean onFailedToRecycleView(T t);

    void onViewRecycled(T t);
}
