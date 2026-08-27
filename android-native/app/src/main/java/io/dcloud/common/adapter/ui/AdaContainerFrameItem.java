package io.dcloud.common.adapter.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import io.dcloud.common.DHInterface.IContainerView;
import io.dcloud.common.DHInterface.INativeView;
import io.dcloud.common.DHInterface.ITypeofAble;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AdaContainerFrameItem extends AdaFrameItem implements IContainerView {
    private boolean isITypeofAble;
    public ArrayList<AdaFrameItem> mChildArrayList;
    public ArrayList<INativeView> mChildNativeViewList;

    protected AdaContainerFrameItem(Context context) {
        super(context);
        this.isITypeofAble = false;
        this.mChildArrayList = null;
        this.mChildNativeViewList = null;
        this.mChildArrayList = new ArrayList<>(1);
    }

    @Override // io.dcloud.common.DHInterface.IContainerView
    public void addFrameItem(AdaFrameItem adaFrameItem, ViewGroup.LayoutParams layoutParams) {
        addFrameItem(adaFrameItem, -1, layoutParams);
    }

    public void addNativeViewChild(INativeView iNativeView) {
        if (this.mChildNativeViewList == null) {
            this.mChildNativeViewList = new ArrayList<>();
        }
        if (iNativeView == null || this.mChildNativeViewList.contains(iNativeView)) {
            return;
        }
        this.mChildNativeViewList.add(iNativeView);
    }

    public boolean checkITypeofAble() {
        boolean z = this.isITypeofAble;
        if (z) {
            return z;
        }
        ArrayList<AdaFrameItem> arrayList = this.mChildArrayList;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            AdaFrameItem adaFrameItem = arrayList.get(i);
            i++;
            AdaFrameItem adaFrameItem2 = adaFrameItem;
            if (adaFrameItem2 instanceof AdaContainerFrameItem) {
                return ((AdaContainerFrameItem) adaFrameItem2).checkITypeofAble();
            }
        }
        return false;
    }

    public void clearView() {
        ArrayList<AdaFrameItem> arrayList = this.mChildArrayList;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                AdaFrameItem adaFrameItem = arrayList.get(i);
                i++;
                adaFrameItem.dispose();
            }
            this.mChildArrayList.clear();
        }
        View view = this.mViewImpl;
        if ((view instanceof ViewGroup) && view != null) {
            ((ViewGroup) view).removeAllViews();
        }
        this.isITypeofAble = false;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        clearView();
        super.dispose();
    }

    public ArrayList<AdaFrameItem> getChilds() {
        return this.mChildArrayList;
    }

    public ViewGroup obtainMainViewGroup() {
        return (ViewGroup) this.mViewImpl;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public boolean onDispose() {
        boolean zOnDispose = super.onDispose();
        ArrayList<AdaFrameItem> arrayList = this.mChildArrayList;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                AdaFrameItem adaFrameItem = arrayList.get(i);
                i++;
                zOnDispose |= adaFrameItem.onDispose();
            }
        }
        return zOnDispose;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public void onPopFromStack(boolean z) {
        super.onPopFromStack(z);
        ArrayList<AdaFrameItem> arrayList = this.mChildArrayList;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                AdaFrameItem adaFrameItem = arrayList.get(i);
                i++;
                adaFrameItem.onPopFromStack(z);
            }
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public void onPushToStack(boolean z) {
        super.onPushToStack(z);
        ArrayList<AdaFrameItem> arrayList = this.mChildArrayList;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                AdaFrameItem adaFrameItem = arrayList.get(i);
                i++;
                adaFrameItem.onPushToStack(z);
            }
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    protected void onResize() {
        super.onResize();
        ArrayList<AdaFrameItem> arrayList = this.mChildArrayList;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            AdaFrameItem adaFrameItem = arrayList.get(i);
            i++;
            adaFrameItem.onResize();
        }
    }

    @Override // io.dcloud.common.DHInterface.IContainerView
    public void removeAllFrameItem() {
        if (this.mViewImpl != null) {
            clearView();
            ((ViewGroup) this.mViewImpl).removeAllViews();
        }
        this.isITypeofAble = false;
    }

    @Override // io.dcloud.common.DHInterface.IContainerView
    public void removeFrameItem(AdaFrameItem adaFrameItem) {
        if (adaFrameItem instanceof ITypeofAble) {
            this.isITypeofAble = false;
        }
        View view = this.mViewImpl;
        if (view != null) {
            ((ViewGroup) view).removeView(adaFrameItem.obtainMainView());
            this.mChildArrayList.remove(adaFrameItem);
        }
    }

    public void removeNativeViewChild(INativeView iNativeView) {
        ArrayList<INativeView> arrayList = this.mChildNativeViewList;
        if (arrayList == null || !arrayList.contains(iNativeView)) {
            return;
        }
        this.mChildNativeViewList.remove(iNativeView);
    }

    public void sortNativeViewBringToFront() {
        ArrayList<INativeView> arrayList = this.mChildNativeViewList;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                INativeView iNativeView = arrayList.get(i);
                i++;
                INativeView iNativeView2 = iNativeView;
                if (iNativeView2.obtanMainView() != null && iNativeView2.obtanMainView().getParent() != null) {
                    iNativeView2.obtanMainView().bringToFront();
                }
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IContainerView
    public void addFrameItem(AdaFrameItem adaFrameItem) {
        addFrameItem(adaFrameItem, -1);
    }

    public void addFrameItem(AdaFrameItem adaFrameItem, int i, ViewGroup.LayoutParams layoutParams) {
        if (adaFrameItem instanceof ITypeofAble) {
            this.isITypeofAble = true;
        }
        if (this.mViewImpl instanceof ViewGroup) {
            View viewObtainMainView = adaFrameItem.obtainMainView();
            ViewParent parent = viewObtainMainView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(viewObtainMainView);
                this.mChildArrayList.remove(adaFrameItem);
            }
            if (((ViewGroup) this.mViewImpl).getChildCount() < i) {
                i = ((ViewGroup) this.mViewImpl).getChildCount();
            }
            ((ViewGroup) this.mViewImpl).addView(viewObtainMainView, i, layoutParams);
            if (i < 0 || i > this.mChildArrayList.size()) {
                i = this.mChildArrayList.size();
            }
            this.mChildArrayList.add(i, adaFrameItem);
        }
    }

    public void removeAllFrameItem(boolean z) {
        if (z) {
            View view = this.mViewImpl;
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int i = 0;
                for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                    View childAt = viewGroup.getChildAt(i2);
                    if (!(childAt instanceof INativeView)) {
                        viewGroup.removeView(childAt);
                    }
                }
                ArrayList<AdaFrameItem> arrayList = this.mChildArrayList;
                if (arrayList != null) {
                    int size = arrayList.size();
                    while (i < size) {
                        AdaFrameItem adaFrameItem = arrayList.get(i);
                        i++;
                        adaFrameItem.dispose();
                    }
                    this.mChildArrayList.clear();
                    return;
                }
                return;
            }
            return;
        }
        removeAllFrameItem();
    }

    public void addFrameItem(AdaFrameItem adaFrameItem, int i) {
        ViewGroup.LayoutParams layoutParams = adaFrameItem.obtainMainView().getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -1);
        }
        addFrameItem(adaFrameItem, i, layoutParams);
    }
}
