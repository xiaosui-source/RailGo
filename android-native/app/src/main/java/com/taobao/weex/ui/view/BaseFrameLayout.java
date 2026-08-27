package com.taobao.weex.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import com.taobao.weex.ui.flat.widget.Widget;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class BaseFrameLayout extends FrameLayout {
    private List<Widget> mWidgets;

    public BaseFrameLayout(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        try {
            dispatchDrawInterval(canvas);
        } catch (Throwable th) {
            WXLogUtils.e(WXLogUtils.getStackTrace(th));
        }
    }

    protected void dispatchDrawInterval(Canvas canvas) {
        if (this.mWidgets == null) {
            WXViewUtils.clipCanvasWithinBorderBox(this, canvas);
            super.dispatchDraw(canvas);
            return;
        }
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        Iterator<Widget> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            it.next().draw(canvas);
        }
        canvas.restore();
    }

    public void mountFlatGUI(List<Widget> list) {
        this.mWidgets = list;
        if (list != null) {
            setWillNotDraw(true);
        }
        invalidate();
    }

    public void unmountFlatGUI() {
        this.mWidgets = null;
        setWillNotDraw(false);
        invalidate();
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return this.mWidgets != null || super.verifyDrawable(drawable);
    }
}
