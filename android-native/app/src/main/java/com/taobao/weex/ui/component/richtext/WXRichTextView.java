package com.taobao.weex.ui.component.richtext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import com.taobao.weex.ui.component.richtext.span.ImgSpan;
import com.taobao.weex.ui.view.WXTextView;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXRichTextView extends WXTextView {
    public WXRichTextView(Context context) {
        super(context);
    }

    private boolean updateSelection(MotionEvent motionEvent, Spannable spannable) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int paddingLeft = x - getPaddingLeft();
            int paddingTop = y - getPaddingTop();
            int scrollX = paddingLeft + getScrollX();
            int scrollY = paddingTop + getScrollY();
            Layout textLayout = getTextLayout();
            int offsetForHorizontal = textLayout.getOffsetForHorizontal(textLayout.getLineForVertical(scrollY), scrollX);
            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
            if (clickableSpanArr.length != 0) {
                if (actionMasked == 1) {
                    clickableSpanArr[0].onClick(this);
                } else {
                    Selection.setSelection(spannable, spannable.getSpanStart(clickableSpanArr[0]), spannable.getSpanEnd(clickableSpanArr[0]));
                }
                return true;
            }
            Selection.removeSelection(spannable);
        }
        return false;
    }

    @Override // com.taobao.weex.ui.view.WXTextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return ((!isEnabled() || getTextLayout() == null || !(getText() instanceof Spannable)) ? false : updateSelection(motionEvent, (Spannable) getText())) || super.onTouchEvent(motionEvent);
    }

    @Override // com.taobao.weex.ui.view.WXTextView
    public void setTextLayout(Layout layout) {
        super.setTextLayout(layout);
        if (layout.getText() instanceof Spanned) {
            Spanned spanned = (Spanned) layout.getText();
            ImgSpan[] imgSpanArr = (ImgSpan[]) spanned.getSpans(0, spanned.length(), ImgSpan.class);
            if (imgSpanArr != null) {
                for (ImgSpan imgSpan : imgSpanArr) {
                    imgSpan.setView(this);
                }
            }
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        super.verifyDrawable(drawable);
        return true;
    }
}
