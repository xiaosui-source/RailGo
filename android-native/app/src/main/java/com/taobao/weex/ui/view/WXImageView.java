package com.taobao.weex.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.utils.ImageDrawable;
import com.taobao.weex.utils.WXLogUtils;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXImageView extends ImageView implements WXGestureObservable, IRenderStatus<WXImage>, IRenderResult<WXImage>, WXImage.Measurable {
    private float[] borderRadius;
    private boolean enableBitmapAutoManage;
    private boolean gif;
    private boolean isBitmapReleased;
    private boolean mOutWindowVisibilityChangedReally;
    private WeakReference<WXImage> mWeakReference;
    private WXGesture wxGesture;

    public WXImageView(Context context) {
        super(context);
        this.isBitmapReleased = false;
        this.enableBitmapAutoManage = true;
    }

    public void autoRecoverImage() {
        if (this.enableBitmapAutoManage && this.isBitmapReleased) {
            WXImage component = getComponent();
            if (component != null) {
                component.autoRecoverImage();
            }
            this.isBitmapReleased = false;
        }
    }

    public void autoReleaseImage() {
        if (!this.enableBitmapAutoManage || this.isBitmapReleased) {
            return;
        }
        this.isBitmapReleased = true;
        WXImage component = getComponent();
        if (component != null) {
            component.autoReleaseImage();
        }
    }

    @Override // android.view.View
    public void dispatchWindowVisibilityChanged(int i) {
        this.mOutWindowVisibilityChangedReally = true;
        super.dispatchWindowVisibilityChanged(i);
        this.mOutWindowVisibilityChangedReally = false;
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.wxGesture;
    }

    @Override // com.taobao.weex.ui.component.WXImage.Measurable
    public int getNaturalHeight() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return -1;
        }
        if (drawable instanceof ImageDrawable) {
            return ((ImageDrawable) drawable).getBitmapHeight();
        }
        if (!(drawable instanceof BitmapDrawable)) {
            WXLogUtils.w("WXImageView", "Not supported drawable type: " + drawable.getClass().getSimpleName());
            return -1;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        WXLogUtils.w("WXImageView", "Bitmap on " + drawable.toString() + " is null");
        return -1;
    }

    @Override // com.taobao.weex.ui.component.WXImage.Measurable
    public int getNaturalWidth() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return -1;
        }
        if (drawable instanceof ImageDrawable) {
            return ((ImageDrawable) drawable).getBitmapWidth();
        }
        if (!(drawable instanceof BitmapDrawable)) {
            WXLogUtils.w("WXImageView", "Not supported drawable type: " + drawable.getClass().getSimpleName());
            return -1;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        WXLogUtils.w("WXImageView", "Bitmap on " + drawable.toString() + " is null");
        return -1;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        autoRecoverImage();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        autoReleaseImage();
    }

    @Override // android.view.View
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        autoRecoverImage();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            setImageDrawable(getDrawable(), this.gif);
        }
    }

    @Override // android.view.View
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        autoReleaseImage();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
        WXGesture wXGesture = this.wxGesture;
        return wXGesture != null ? wXGesture.onTouch(this, motionEvent) | zOnTouchEvent : zOnTouchEvent;
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (this.mOutWindowVisibilityChangedReally) {
            if (i == 0) {
                autoRecoverImage();
            } else {
                autoReleaseImage();
            }
        }
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.wxGesture = wXGesture;
    }

    public void setBorderRadius(float[] fArr) {
        this.borderRadius = fArr;
    }

    public void setEnableBitmapAutoManage(boolean z) {
        this.enableBitmapAutoManage = z;
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        setImageDrawable(bitmap == null ? null : new BitmapDrawable(getResources(), bitmap));
    }

    public void setImageDrawable(Drawable drawable, boolean z) {
        WXImage wXImage;
        this.gif = z;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            Drawable drawableCreateImageDrawable = ImageDrawable.createImageDrawable(drawable, getScaleType(), this.borderRadius, (layoutParams.width - getPaddingLeft()) - getPaddingRight(), (layoutParams.height - getPaddingTop()) - getPaddingBottom(), z);
            if (drawableCreateImageDrawable instanceof ImageDrawable) {
                ImageDrawable imageDrawable = (ImageDrawable) drawableCreateImageDrawable;
                if (!Arrays.equals(imageDrawable.getCornerRadii(), this.borderRadius)) {
                    imageDrawable.setCornerRadii(this.borderRadius);
                }
            }
            super.setImageDrawable(drawableCreateImageDrawable);
            WeakReference<WXImage> weakReference = this.mWeakReference;
            if (weakReference == null || (wXImage = weakReference.get()) == null) {
                return;
            }
            wXImage.readyToRender();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        setImageDrawable(getResources().getDrawable(i));
    }

    @Override // com.taobao.weex.ui.view.IRenderResult
    public WXImage getComponent() {
        WeakReference<WXImage> weakReference = this.mWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // com.taobao.weex.ui.view.IRenderStatus
    public void holdComponent(WXImage wXImage) {
        this.mWeakReference = new WeakReference<>(wXImage);
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        setImageDrawable(drawable, this.gif);
    }
}
