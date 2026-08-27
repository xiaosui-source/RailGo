package com.taobao.weex.ui.view;

import android.R;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.PopupMenu;
import com.taobao.weex.ui.component.WXText;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXTextView extends View implements WXGestureObservable, IWXTextView, IRenderStatus<WXText>, IRenderResult<WXText> {
    private boolean mIsLabelSet;
    private WeakReference<WXText> mWeakReference;
    private Layout textLayout;
    private WXGesture wxGesture;

    public WXTextView(Context context) {
        super(context);
        this.mIsLabelSet = false;
    }

    public void enableCopy(boolean z) {
        if (z) {
            setOnLongClickListener(new View.OnLongClickListener() { // from class: com.taobao.weex.ui.view.WXTextView.1
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    final String string;
                    PopupMenu popupMenu = new PopupMenu(WXTextView.this.getContext(), WXTextView.this);
                    try {
                        string = WXTextView.this.getContext().getResources().getString(R.string.copy);
                    } catch (Throwable unused) {
                        string = "Copy";
                    }
                    popupMenu.getMenu().add(string);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: com.taobao.weex.ui.view.WXTextView.1.1
                        @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (!string.equals(menuItem.getTitle())) {
                                return false;
                            }
                            String string2 = WXTextView.this.getText().toString();
                            ClipboardManager clipboardManager = (ClipboardManager) WXTextView.this.getContext().getSystemService("clipboard");
                            if (clipboardManager == null) {
                                return true;
                            }
                            clipboardManager.setPrimaryClip(ClipData.newPlainText(string2, string2));
                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
        } else {
            setOnLongClickListener(null);
        }
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.wxGesture;
    }

    @Override // com.taobao.weex.ui.view.IWXTextView
    public CharSequence getText() {
        Layout layout = this.textLayout;
        if (layout != null) {
            return layout.getText();
        }
        return null;
    }

    public Layout getTextLayout() {
        return this.textLayout;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Layout textLayout = getTextLayout();
        if (textLayout != null) {
            canvas.translate(getPaddingLeft(), getPaddingTop());
            textLayout.draw(canvas);
        }
        canvas.restore();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
        WXGesture wXGesture = this.wxGesture;
        return wXGesture != null ? wXGesture.onTouch(this, motionEvent) | zOnTouchEvent : zOnTouchEvent;
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.wxGesture = wXGesture;
    }

    public void setAriaLabel(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mIsLabelSet = true;
            setContentDescription(str);
            return;
        }
        this.mIsLabelSet = false;
        Layout layout = this.textLayout;
        if (layout != null) {
            setContentDescription(layout.getText());
        }
    }

    public void setTextColor(int i) {
        Layout textLayout = getTextLayout();
        if (textLayout != null) {
            textLayout.getPaint().setColor(i);
        }
    }

    public void setTextLayout(Layout layout) {
        WXText wXText;
        this.textLayout = layout;
        if (layout != null && !this.mIsLabelSet) {
            setContentDescription(layout.getText());
        }
        WeakReference<WXText> weakReference = this.mWeakReference;
        if (weakReference == null || (wXText = weakReference.get()) == null) {
            return;
        }
        wXText.readyToRender();
    }

    @Override // com.taobao.weex.ui.view.IRenderResult
    public WXText getComponent() {
        WeakReference<WXText> weakReference = this.mWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // com.taobao.weex.ui.view.IRenderStatus
    public void holdComponent(WXText wXText) {
        this.mWeakReference = new WeakReference<>(wXText);
    }
}
