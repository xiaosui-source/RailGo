package com.taobao.weex.ui.view.refresh.wrapper;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import com.taobao.weex.R;
import com.taobao.weex.ui.component.list.ListComponentView;
import com.taobao.weex.ui.component.list.StickyHeaderHelper;
import com.taobao.weex.ui.component.list.WXCell;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.ui.view.listview.WXRecyclerView;
import com.taobao.weex.ui.view.listview.adapter.RecyclerViewBaseAdapter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class BounceRecyclerView extends BaseBounceView<WXRecyclerView> implements ListComponentView, WXGestureObservable {
    public static final int DEFAULT_COLUMN_COUNT = 1;
    public static final int DEFAULT_COLUMN_GAP = 1;
    private RecyclerViewBaseAdapter adapter;
    private int mColumnCount;
    private float mColumnGap;
    private int mLayoutType;
    private int mOrientation;
    private StickyHeaderHelper mStickyHeaderHelper;

    public BounceRecyclerView(Context context, int i, int i2, float f, int i3) {
        super(context, i3);
        this.adapter = null;
        this.mLayoutType = i;
        this.mColumnCount = i2;
        this.mColumnGap = f;
        this.mOrientation = i3;
        init(context);
        this.mStickyHeaderHelper = new StickyHeaderHelper(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return getInnerView().getGestureListener();
    }

    @Override // com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView, com.taobao.weex.ui.component.list.ListComponentView
    public /* bridge */ /* synthetic */ WXRecyclerView getInnerView() {
        return (WXRecyclerView) super.getInnerView();
    }

    @Override // com.taobao.weex.ui.component.list.ListComponentView
    public RecyclerViewBaseAdapter getRecyclerViewBaseAdapter() {
        return this.adapter;
    }

    public StickyHeaderHelper getStickyHeaderHelper() {
        return this.mStickyHeaderHelper;
    }

    @Override // com.taobao.weex.ui.component.list.ListComponentView
    public void notifyStickyRemove(WXCell wXCell) {
        this.mStickyHeaderHelper.notifyStickyRemove(wXCell);
    }

    @Override // com.taobao.weex.ui.component.list.ListComponentView
    public void notifyStickyShow(WXCell wXCell) {
        this.mStickyHeaderHelper.notifyStickyShow(wXCell);
    }

    @Override // com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView
    public void onLoadmoreComplete() {
        RecyclerViewBaseAdapter recyclerViewBaseAdapter = this.adapter;
        if (recyclerViewBaseAdapter != null) {
            recyclerViewBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView
    public void onRefreshingComplete() {
        RecyclerViewBaseAdapter recyclerViewBaseAdapter = this.adapter;
        if (recyclerViewBaseAdapter != null) {
            recyclerViewBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        getInnerView().registerGestureListener(wXGesture);
    }

    @Override // com.taobao.weex.ui.component.list.ListComponentView
    public void setRecyclerViewBaseAdapter(RecyclerViewBaseAdapter recyclerViewBaseAdapter) {
        this.adapter = recyclerViewBaseAdapter;
        if (getInnerView() != null) {
            getInnerView().setAdapter(recyclerViewBaseAdapter);
        }
    }

    @Override // com.taobao.weex.ui.component.list.ListComponentView
    public void updateStickyView(int i) {
        this.mStickyHeaderHelper.updateStickyView(i);
    }

    @Override // com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView
    public WXRecyclerView setInnerView(Context context) throws XmlPullParserException, IllegalAccessException, Resources.NotFoundException, NoSuchMethodException, IOException, SecurityException, IllegalArgumentException, InvocationTargetException {
        WXRecyclerView wXRecyclerView;
        int next;
        if (R.layout.weex_recycler_layout != 0) {
            XmlResourceParser xml = getResources().getXml(R.layout.weex_recycler_layout);
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                try {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (next != 1);
            wXRecyclerView = new WXRecyclerView(context, attributeSetAsAttributeSet);
            wXRecyclerView.setScrollBarStyle(33554432);
            try {
                Method declaredMethod = View.class.getDeclaredMethod("initializeScrollbars", TypedArray.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(wXRecyclerView, null);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.mOrientation == 1) {
                wXRecyclerView.setVerticalScrollBarEnabled(true);
            } else {
                wXRecyclerView.setHorizontalScrollBarEnabled(true);
            }
        } else {
            wXRecyclerView = new WXRecyclerView(context);
        }
        WXRecyclerView wXRecyclerView2 = wXRecyclerView;
        wXRecyclerView2.setOverScrollMode(0);
        wXRecyclerView2.initView(context, this.mLayoutType, this.mColumnCount, this.mColumnGap, getOrientation());
        return wXRecyclerView2;
    }

    public BounceRecyclerView(Context context, int i, int i2) {
        this(context, i, 1, 1.0f, i2);
    }
}
