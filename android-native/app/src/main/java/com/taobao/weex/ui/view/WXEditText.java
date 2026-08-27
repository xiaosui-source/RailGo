package com.taobao.weex.ui.view;

import android.content.Context;
import android.os.Build;
import android.text.method.BaseMovementMethod;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewParent;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import io.dcloud.common.core.ui.keyboard.DCEditText;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXEditText extends DCEditText implements WXGestureObservable {
    private boolean mAllowCopyPaste;
    private boolean mAllowDisableMovement;
    private int mLines;
    private WXGesture wxGesture;

    public WXEditText(Context context, String str) {
        super(context, str);
        this.mLines = 1;
        this.mAllowDisableMovement = true;
        this.mAllowCopyPaste = true;
        setBackground(null);
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.wxGesture;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (getLayout() != null) {
            int height = getLayout().getHeight();
            if (!this.mAllowDisableMovement || i2 >= height) {
                setMovementMethod(getDefaultMovementMethod());
            } else {
                setMovementMethod(new BaseMovementMethod());
            }
        }
    }

    @Override // android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        return !this.mAllowCopyPaste || super.onTextContextMenuItem(i);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
        WXGesture wXGesture = this.wxGesture;
        if (wXGesture != null) {
            zOnTouchEvent |= wXGesture.onTouch(this, motionEvent);
        }
        ViewParent parent = getParent();
        if (parent != null) {
            int action = motionEvent.getAction() & 255;
            if (action != 0) {
                if (action == 1 || action == 3) {
                    parent.requestDisallowInterceptTouchEvent(false);
                    return zOnTouchEvent;
                }
            } else if (this.mLines < getLineCount()) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
        return zOnTouchEvent;
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.wxGesture = wXGesture;
    }

    public void setAllowCopyPaste(boolean z) {
        this.mAllowCopyPaste = z;
        if (z) {
            setLongClickable(true);
            setCustomSelectionActionModeCallback(null);
            if (Build.VERSION.SDK_INT >= 23) {
                setCustomInsertionActionModeCallback(null);
                return;
            }
            return;
        }
        setLongClickable(false);
        ActionMode.Callback callback = new ActionMode.Callback() { // from class: com.taobao.weex.ui.view.WXEditText.1
            @Override // android.view.ActionMode.Callback
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override // android.view.ActionMode.Callback
            public void onDestroyActionMode(ActionMode actionMode) {
            }

            @Override // android.view.ActionMode.Callback
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }
        };
        if (Build.VERSION.SDK_INT >= 23) {
            setCustomInsertionActionModeCallback(callback);
        }
        setCustomSelectionActionModeCallback(callback);
    }

    public void setAllowDisableMovement(boolean z) {
        this.mAllowDisableMovement = z;
    }

    @Override // android.widget.TextView
    public void setLines(int i) {
        super.setLines(i);
        this.mLines = i;
    }
}
