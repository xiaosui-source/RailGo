package com.taobao.weex.ui.component.list;

import android.view.View;
import android.view.ViewGroup;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.utils.WXLogUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class StickyHeaderHelper {
    private final ViewGroup mParent;
    private Map<String, View> mHeaderViews = new HashMap();
    private Map<String, WXCell> mHeaderComps = new HashMap();
    private String mCurrentStickyRef = null;

    public StickyHeaderHelper(ViewGroup viewGroup) {
        this.mParent = viewGroup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeFrontStickyVisible() {
        if (this.mHeaderViews.size() <= 0) {
            return;
        }
        boolean z = false;
        for (int childCount = this.mParent.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mParent.getChildAt(childCount);
            if (z && (childAt.getTag() instanceof StickyHeaderHelper)) {
                if (childAt.getVisibility() != 8) {
                    childAt.setVisibility(8);
                }
            } else if (childAt.getTag() instanceof StickyHeaderHelper) {
                if (childAt.getVisibility() != 0) {
                    childAt.setVisibility(0);
                }
                z = true;
            }
        }
    }

    public void clearStickyHeaders() {
        if (this.mHeaderViews.size() <= 0) {
            return;
        }
        Iterator<Map.Entry<String, WXCell>> it = this.mHeaderComps.entrySet().iterator();
        while (it.hasNext()) {
            WXCell value = it.next().getValue();
            it.remove();
            notifyStickyRemove(value);
        }
    }

    public void notifyStickyRemove(WXCell wXCell) {
        if (wXCell == null) {
            return;
        }
        final WXCell wXCellRemove = this.mHeaderComps.containsValue(wXCell) ? this.mHeaderComps.remove(wXCell.getRef()) : wXCell;
        final View viewRemove = this.mHeaderViews.remove(wXCell.getRef());
        if (wXCellRemove == null || viewRemove == null) {
            WXEnvironment.isApkDebugable();
            return;
        }
        String str = this.mCurrentStickyRef;
        if (str != null && str.equals(wXCell.getRef())) {
            this.mCurrentStickyRef = null;
        }
        this.mParent.post(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.list.StickyHeaderHelper.1
            @Override // java.lang.Runnable
            public void run() {
                StickyHeaderHelper.this.mParent.removeView(viewRemove);
                if (viewRemove.getVisibility() != 0) {
                    viewRemove.setVisibility(0);
                }
                wXCellRemove.recoverySticky();
                StickyHeaderHelper.this.changeFrontStickyVisible();
            }
        }));
        if (wXCellRemove.getEvents().contains(Constants.Event.UNSTICKY)) {
            wXCellRemove.fireEvent(Constants.Event.UNSTICKY);
        }
    }

    public void notifyStickyShow(WXCell wXCell) {
        WXCell wXCell2;
        if (wXCell == null) {
            return;
        }
        this.mHeaderComps.put(wXCell.getRef(), wXCell);
        String str = this.mCurrentStickyRef;
        if (str == null || (wXCell2 = this.mHeaderComps.get(str)) == null || wXCell.getScrollPositon() > wXCell2.getScrollPositon()) {
            this.mCurrentStickyRef = wXCell.getRef();
        }
        String str2 = this.mCurrentStickyRef;
        if (str2 == null) {
            WXLogUtils.e("Current Sticky ref is null.");
            return;
        }
        WXCell wXCell3 = this.mHeaderComps.get(str2);
        ViewGroup realView = wXCell3.getRealView();
        if (realView == null) {
            WXLogUtils.e("Sticky header's real view is null.");
            return;
        }
        View view = this.mHeaderViews.get(wXCell3.getRef());
        if (view != null) {
            view.bringToFront();
        } else {
            this.mHeaderViews.put(wXCell3.getRef(), realView);
            float translationX = realView.getTranslationX();
            float translationY = realView.getTranslationY();
            wXCell3.removeSticky();
            ViewGroup viewGroup = (ViewGroup) realView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(realView);
            }
            realView.setTag(wXCell3.getRef());
            this.mParent.addView(realView, new ViewGroup.MarginLayoutParams(-2, -2));
            realView.setTag(this);
            if (wXCell3.getStickyOffset() > 0) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) realView.getLayoutParams();
                if (wXCell3.getStickyOffset() != marginLayoutParams.topMargin) {
                    marginLayoutParams.topMargin = wXCell3.getStickyOffset();
                }
            }
            realView.setTranslationX(translationX);
            realView.setTranslationY(translationY);
        }
        changeFrontStickyVisible();
        if (wXCell3.getEvents().contains("sticky")) {
            wXCell3.fireEvent("sticky");
        }
    }

    public void updateStickyView(int i) {
        Iterator<Map.Entry<String, WXCell>> it = this.mHeaderComps.entrySet().iterator();
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            WXCell value = it.next().getValue();
            int scrollPositon = value.getScrollPositon();
            if (scrollPositon > i) {
                arrayList.add(value);
            } else if (scrollPositon == i) {
                this.mCurrentStickyRef = value.getRef();
                View view = this.mHeaderViews.get(value.getRef());
                if (view != null) {
                    view.bringToFront();
                    changeFrontStickyVisible();
                }
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            notifyStickyRemove((WXCell) obj);
        }
    }
}
