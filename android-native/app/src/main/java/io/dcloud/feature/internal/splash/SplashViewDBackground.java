package io.dcloud.feature.internal.splash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import io.dcloud.base.R;
import io.dcloud.common.util.AppRuntime;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class SplashViewDBackground extends RelativeLayout implements ISplash {
    String a;
    a b;
    TextView c;
    boolean d;
    private boolean e;

    public SplashViewDBackground(Context context, Bitmap bitmap, String str, boolean z) {
        super(context);
        this.b = null;
        this.c = null;
        this.e = false;
        this.a = str;
        this.d = z;
        if (AppRuntime.getAppDarkMode(context)) {
            setBackgroundColor(context.getResources().getColor(R.color.nightBG));
        } else {
            setBackgroundColor(-1);
        }
        a(bitmap);
    }

    private void a(Bitmap bitmap) {
        float fA;
        if (this.e) {
            return;
        }
        this.b = new a(getContext(), this.d);
        int i = getResources().getDisplayMetrics().heightPixels;
        int iA = (int) this.b.a(6.0f);
        int iA2 = (int) this.b.a(65.0f);
        int iA3 = (int) this.b.a(8.0f);
        if (getResources().getDisplayMetrics().widthPixels >= 1080) {
            fA = this.b.a(6.0f);
        } else {
            if (getResources().getDisplayMetrics().widthPixels < 720) {
                if (getResources().getDisplayMetrics().widthPixels >= 540) {
                    fA = this.b.a(10.0f);
                }
                a aVar = this.b;
                aVar.a(bitmap, iA2, iA2, (int) aVar.a(1.0f), 12962246, -5592406);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iA2, iA2);
                layoutParams.addRule(14);
                layoutParams.topMargin = ((i / 2) - ((iA2 + iA) + iA3)) / 2;
                this.b.setId(android.R.id.button1);
                addView(this.b, layoutParams);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.addRule(3, this.b.getId());
                layoutParams2.addRule(13);
                layoutParams2.topMargin = iA;
                TextView textView = new TextView(getContext());
                this.c = textView;
                textView.setSingleLine();
                this.c.setTextSize(iA3);
                this.c.setTextColor(-10855846);
                this.c.setId(android.R.id.button2);
                setNameText(this.a);
                this.c.setTypeface(Typeface.create("宋体", 0));
                addView(this.c, layoutParams2);
            }
            fA = this.b.a(8.0f);
        }
        iA3 = (int) fA;
        a aVar2 = this.b;
        aVar2.a(bitmap, iA2, iA2, (int) aVar2.a(1.0f), 12962246, -5592406);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(iA2, iA2);
        layoutParams3.addRule(14);
        layoutParams3.topMargin = ((i / 2) - ((iA2 + iA) + iA3)) / 2;
        this.b.setId(android.R.id.button1);
        addView(this.b, layoutParams3);
        RelativeLayout.LayoutParams layoutParams22 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams22.addRule(3, this.b.getId());
        layoutParams22.addRule(13);
        layoutParams22.topMargin = iA;
        TextView textView2 = new TextView(getContext());
        this.c = textView2;
        textView2.setSingleLine();
        this.c.setTextSize(iA3);
        this.c.setTextColor(-10855846);
        this.c.setId(android.R.id.button2);
        setNameText(this.a);
        this.c.setTypeface(Typeface.create("宋体", 0));
        addView(this.c, layoutParams22);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof ISplash) {
            this.e = true;
        }
        super.addView(view, i, layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.e) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override // io.dcloud.feature.internal.splash.ISplash
    public void setImageBitmap(Bitmap bitmap) {
        this.b.setBitmap(bitmap);
    }

    @Override // io.dcloud.feature.internal.splash.ISplash
    public void setNameText(String str) {
        TextView textView = this.c;
        if (textView == null || !TextUtils.isEmpty(textView.getText())) {
            return;
        }
        this.c.setText(str);
    }
}
