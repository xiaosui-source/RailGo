package com.dcloud.android.widget.toast;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.Toast;
import java.lang.reflect.Field;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class ToastCompat extends Toast {
    private final Toast toast;

    public ToastCompat(Context context) {
        super(context);
        this.toast = this;
    }

    public static ToastCompat makeText(Context context, CharSequence charSequence, int i) {
        Toast toastMakeText = Toast.makeText(context, charSequence, i);
        setContextCompat(toastMakeText.getView(), new SafeToastContext(context));
        return new ToastCompat(context, toastMakeText);
    }

    private static void setContextCompat(View view, Context context) {
        if (Build.VERSION.SDK_INT == 25) {
            try {
                Field declaredField = View.class.getDeclaredField("mContext");
                declaredField.setAccessible(true);
                declaredField.set(view, context);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public Toast getBaseToast() {
        return this.toast;
    }

    @Override // android.widget.Toast
    public int getDuration() {
        Toast toast = this.toast;
        return toast instanceof ToastCompat ? super.getDuration() : toast.getDuration();
    }

    @Override // android.widget.Toast
    public int getGravity() {
        Toast toast = this.toast;
        return toast instanceof ToastCompat ? super.getGravity() : toast.getGravity();
    }

    @Override // android.widget.Toast
    public float getHorizontalMargin() {
        Toast toast = this.toast;
        return toast instanceof ToastCompat ? super.getHorizontalMargin() : toast.getHorizontalMargin();
    }

    @Override // android.widget.Toast
    public float getVerticalMargin() {
        Toast toast = this.toast;
        return toast instanceof ToastCompat ? super.getVerticalMargin() : toast.getVerticalMargin();
    }

    @Override // android.widget.Toast
    public View getView() {
        Toast toast = this.toast;
        return toast instanceof ToastCompat ? super.getView() : toast.getView();
    }

    @Override // android.widget.Toast
    public int getXOffset() {
        Toast toast = this.toast;
        return toast instanceof ToastCompat ? super.getXOffset() : toast.getXOffset();
    }

    @Override // android.widget.Toast
    public int getYOffset() {
        Toast toast = this.toast;
        return toast instanceof ToastCompat ? super.getYOffset() : toast.getYOffset();
    }

    @Override // android.widget.Toast
    public void setDuration(int i) {
        Toast toast = this.toast;
        if (toast instanceof ToastCompat) {
            super.setDuration(i);
        } else {
            toast.setDuration(i);
        }
    }

    @Override // android.widget.Toast
    public void setGravity(int i, int i2, int i3) {
        Toast toast = this.toast;
        if (toast instanceof ToastCompat) {
            super.setGravity(i, i2, i3);
        } else {
            toast.setGravity(i, i2, i3);
        }
    }

    @Override // android.widget.Toast
    public void setMargin(float f, float f2) {
        Toast toast = this.toast;
        if (toast instanceof ToastCompat) {
            super.setMargin(f, f2);
        } else {
            toast.setMargin(f, f2);
        }
    }

    @Override // android.widget.Toast
    public void setText(int i) {
        Toast toast = this.toast;
        if (toast instanceof ToastCompat) {
            super.setText(i);
        } else {
            toast.setText(i);
        }
    }

    @Override // android.widget.Toast
    public void setView(View view) {
        Toast toast = this.toast;
        if (toast instanceof ToastCompat) {
            super.setView(view);
        } else {
            toast.setView(view);
        }
        setContextCompat(view, new SafeToastContext(view.getContext()));
    }

    @Override // android.widget.Toast
    public void show() {
        Toast toast = this.toast;
        if (toast instanceof ToastCompat) {
            super.show();
        } else {
            toast.show();
        }
    }

    private ToastCompat(Context context, Toast toast) {
        super(context);
        this.toast = toast;
    }

    public static ToastCompat makeText(Context context, int i, int i2) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(i), i2);
    }

    @Override // android.widget.Toast
    public void setText(CharSequence charSequence) {
        Toast toast = this.toast;
        if (toast instanceof ToastCompat) {
            super.setText(charSequence);
        } else {
            toast.setText(charSequence);
        }
    }
}
