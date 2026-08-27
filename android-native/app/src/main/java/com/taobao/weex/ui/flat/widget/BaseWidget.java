package com.taobao.weex.ui.flat.widget;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.taobao.weex.ui.flat.FlatGUIContext;
import com.taobao.weex.ui.view.border.BorderDrawable;
import com.taobao.weex.utils.WXViewUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
abstract class BaseWidget implements Widget {
    private BorderDrawable backgroundBorder;
    private int bottomOffset;
    private final FlatGUIContext context;
    private int leftOffset;
    private int rightOffset;
    private int topOffset;
    private Rect borderBox = new Rect();
    private Point offsetOfContainer = new Point();

    BaseWidget(FlatGUIContext flatGUIContext) {
        this.context = flatGUIContext;
    }

    @Override // com.taobao.weex.ui.flat.widget.Widget
    public final void draw(Canvas canvas) {
        canvas.save();
        WXViewUtils.clipCanvasWithinBorderBox(this, canvas);
        Rect rect = this.borderBox;
        canvas.translate(rect.left, rect.top);
        BorderDrawable borderDrawable = this.backgroundBorder;
        if (borderDrawable != null) {
            borderDrawable.draw(canvas);
        }
        canvas.clipRect(this.leftOffset, this.topOffset, this.borderBox.width() - this.rightOffset, this.borderBox.height() - this.bottomOffset);
        canvas.translate(this.leftOffset, this.topOffset);
        onDraw(canvas);
        canvas.restore();
    }

    @Override // com.taobao.weex.ui.flat.widget.Widget
    public final BorderDrawable getBackgroundAndBorder() {
        return this.backgroundBorder;
    }

    @Override // com.taobao.weex.ui.flat.widget.Widget
    public final Rect getBorderBox() {
        return this.borderBox;
    }

    @Override // com.taobao.weex.ui.flat.widget.Widget
    public final Point getLocInFlatContainer() {
        return this.offsetOfContainer;
    }

    protected void invalidate() {
        View widgetContainerView;
        Rect rect = new Rect(this.borderBox);
        Point point = this.offsetOfContainer;
        rect.offset(point.x, point.y);
        FlatGUIContext flatGUIContext = this.context;
        if (flatGUIContext == null || (widgetContainerView = flatGUIContext.getWidgetContainerView(this)) == null) {
            return;
        }
        widgetContainerView.invalidate(rect);
    }

    @Override // com.taobao.weex.ui.flat.widget.Widget
    public void setBackgroundAndBorder(BorderDrawable borderDrawable) {
        this.backgroundBorder = borderDrawable;
        Rect rect = new Rect(this.borderBox);
        Rect rect2 = this.borderBox;
        rect.offset(-rect2.left, -rect2.top);
        borderDrawable.setBounds(rect);
        setCallback(borderDrawable);
        invalidate();
    }

    protected void setCallback(Drawable drawable) {
        View widgetContainerView;
        FlatGUIContext flatGUIContext = this.context;
        if (flatGUIContext == null || (widgetContainerView = flatGUIContext.getWidgetContainerView(this)) == null) {
            return;
        }
        drawable.setCallback(widgetContainerView);
    }

    @Override // com.taobao.weex.ui.flat.widget.Widget
    public void setContentBox(int i, int i2, int i3, int i4) {
        this.leftOffset = i;
        this.topOffset = i2;
        this.rightOffset = i3;
        this.bottomOffset = i4;
        invalidate();
    }

    @Override // com.taobao.weex.ui.flat.widget.Widget
    public void setLayout(int i, int i2, int i3, int i4, int i5, int i6, Point point) {
        this.offsetOfContainer = point;
        this.borderBox.set(i3, i5, i + i3, i2 + i5);
        BorderDrawable borderDrawable = this.backgroundBorder;
        if (borderDrawable != null) {
            setBackgroundAndBorder(borderDrawable);
        }
        invalidate();
    }
}
