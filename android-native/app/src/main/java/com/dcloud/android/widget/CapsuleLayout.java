package com.dcloud.android.widget;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class CapsuleLayout extends LinearLayout {
    public static final int STYLE_DARK = 2;
    public static final int STYLE_LIGHT = 1;
    public boolean isDiy;
    public float mAngle;
    public int mBackgroundColor;
    CapsuleDrawable mDrawable;
    private List<View> mIntervals;
    public Paint mPaint;
    private int mSelectColor;
    public int mStrokeColor;
    public int mStrokeWidth;
    private int mStyle;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: com.dcloud.android.widget.CapsuleLayout$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dcloud$android$widget$CapsuleLayout$ButtonType;

        static {
            int[] iArr = new int[ButtonType.values().length];
            $SwitchMap$com$dcloud$android$widget$CapsuleLayout$ButtonType = iArr;
            try {
                iArr[ButtonType.LIFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$dcloud$android$widget$CapsuleLayout$ButtonType[ButtonType.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$dcloud$android$widget$CapsuleLayout$ButtonType[ButtonType.MIDDLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public enum ButtonType {
        LIFT,
        MIDDLE,
        RIGHT
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class CapsuleDrawable extends GradientDrawable {
        private CapsuleDrawable() {
        }

        /* synthetic */ CapsuleDrawable(CapsuleLayout capsuleLayout, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public CapsuleLayout(Context context, float f) {
        super(context);
        this.mStyle = 1;
        this.isDiy = false;
        this.mAngle = f;
        this.mIntervals = new ArrayList();
        setRoundColor(Color.parseColor("#ffffffff"), Color.parseColor("#ffe5e5e5"), 1);
        this.mSelectColor = Color.parseColor("#CBCCCD");
    }

    private void initButtonBackground(View view, ButtonType buttonType) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        AnonymousClass1 anonymousClass1 = null;
        CapsuleDrawable capsuleDrawable = new CapsuleDrawable(this, anonymousClass1);
        CapsuleDrawable capsuleDrawable2 = new CapsuleDrawable(this, anonymousClass1);
        float[] fArr = new float[0];
        int i = AnonymousClass1.$SwitchMap$com$dcloud$android$widget$CapsuleLayout$ButtonType[buttonType.ordinal()];
        if (i == 1) {
            float f = this.mAngle;
            fArr = new float[]{f, f, 0.0f, 0.0f, 0.0f, 0.0f, f, f};
            capsuleDrawable2.setStroke(this.mStrokeWidth, 0);
        } else if (i == 2) {
            float f2 = this.mAngle;
            fArr = new float[]{0.0f, 0.0f, f2, f2, f2, f2, 0.0f, 0.0f};
            capsuleDrawable2.setStroke(this.mStrokeWidth, 0);
        } else if (i == 3) {
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        }
        capsuleDrawable2.setCornerRadii(fArr);
        capsuleDrawable.setCornerRadii(fArr);
        capsuleDrawable.setColor(0);
        capsuleDrawable2.setColor(this.mSelectColor);
        stateListDrawable.addState(new int[]{R.attr.state_pressed, R.attr.state_enabled}, capsuleDrawable2);
        stateListDrawable.addState(new int[]{R.attr.state_enabled, R.attr.state_focused}, capsuleDrawable2);
        stateListDrawable.addState(new int[]{R.attr.state_enabled}, capsuleDrawable);
        stateListDrawable.addState(new int[]{R.attr.state_focused}, capsuleDrawable2);
        stateListDrawable.addState(new int[]{R.attr.state_window_focused}, capsuleDrawable2);
        stateListDrawable.addState(new int[0], capsuleDrawable);
        view.setBackground(stateListDrawable);
    }

    private void updateBackground() {
        if (this.mDrawable == null) {
            CapsuleDrawable capsuleDrawable = new CapsuleDrawable(this, null);
            this.mDrawable = capsuleDrawable;
            setBackground(capsuleDrawable);
        }
        this.mDrawable.setCornerRadius(this.mAngle);
        this.mDrawable.setStroke(this.mStrokeWidth, this.mStrokeColor);
        this.mDrawable.setColor(this.mBackgroundColor);
        this.mDrawable.invalidateSelf();
    }

    private void updateIntervalColor() {
        Iterator<View> it = this.mIntervals.iterator();
        while (it.hasNext()) {
            it.next().setBackgroundColor(this.mStrokeColor);
        }
    }

    public void addButtonView(View view, LinearLayout.LayoutParams layoutParams, ButtonType buttonType, View.OnClickListener onClickListener) {
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1);
        layoutParams2.weight = 1.0f;
        layoutParams2.gravity = 17;
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.addView(view, layoutParams);
        addView(linearLayout, layoutParams2);
        linearLayout.setOnClickListener(onClickListener);
        initButtonBackground(linearLayout, buttonType);
    }

    public void addIntervalView(float f) {
        View view = new View(getContext());
        view.setBackgroundColor(this.mStrokeColor);
        addView(view, new LinearLayout.LayoutParams(this.mStrokeWidth, (int) (f * 18.0f)));
        this.mIntervals.add(view);
    }

    public int checkColorToStyle(int i) {
        if (this.isDiy) {
            return 1;
        }
        int i2 = (i >> 16) & 255;
        int i3 = (i >> 8) & 255;
        int i4 = i & 255;
        if (i2 <= 235 || i3 <= 235 || i4 <= 235) {
            if (this.mStyle == 1) {
                this.mStyle = 2;
                setRoundColor(Color.parseColor("#1a000000"), Color.parseColor("#4de5e5e5"), 1);
                updateIntervalColor();
            }
        } else if (this.mStyle == 2) {
            this.mStyle = 1;
            setRoundColor(Color.parseColor("#ffffffff"), Color.parseColor("#ffe5e5e5"), 1);
            updateIntervalColor();
        }
        return this.mStyle;
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        this.mIntervals.clear();
    }

    public void setAngle(float f) {
        this.mAngle = f;
        updateBackground();
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        if (drawable instanceof CapsuleDrawable) {
            super.setBackground(drawable);
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        updateBackground();
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
    }

    public void setButtonSelectColor(View view, ButtonType buttonType, int i) {
        if (view == null || view.getParent() == null) {
            return;
        }
        this.mSelectColor = i;
        initButtonBackground((View) view.getParent(), buttonType);
    }

    public void setRoundColor(int i, int i2, int i3) {
        this.mBackgroundColor = i;
        this.mStrokeColor = i2;
        this.mStrokeWidth = i3;
        updateBackground();
    }

    public void setRoundColor(int i) {
        this.mStrokeColor = i;
        updateBackground();
    }
}
