package io.dcloud.feature.gallery.imageedit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import io.dcloud.feature.gallery.imageedit.b;
import io.dcloud.p.q2;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class IMGStickerTextView extends IMGStickerView implements b.a {
    private static float p = -1.0f;
    private TextView m;
    private q2 n;
    private b o;

    public IMGStickerTextView(Context context) {
        this(context, null, 0);
    }

    private b getDialog() {
        if (this.o == null) {
            this.o = new b(getContext(), this);
        }
        return this.o;
    }

    @Override // io.dcloud.feature.gallery.imageedit.view.IMGStickerView
    public View a(Context context) {
        TextView textView = new TextView(context);
        this.m = textView;
        textView.setTextSize(p);
        this.m.setPadding(26, 26, 26, 26);
        this.m.setTextColor(-1);
        return this.m;
    }

    @Override // io.dcloud.feature.gallery.imageedit.view.IMGStickerView
    public void b(Context context) {
        if (p <= 0.0f) {
            p = TypedValue.applyDimension(2, 24.0f, context.getResources().getDisplayMetrics());
        }
        super.b(context);
    }

    @Override // io.dcloud.feature.gallery.imageedit.view.IMGStickerView
    public void c() {
        b dialog = getDialog();
        dialog.a(this.n);
        dialog.show();
    }

    public q2 getText() {
        return this.n;
    }

    public void setText(q2 q2Var) {
        TextView textView;
        this.n = q2Var;
        if (q2Var == null || (textView = this.m) == null) {
            return;
        }
        textView.setText(q2Var.c());
        this.m.setTextColor(this.n.b());
        this.m.setBackgroundColor(this.n.a());
    }

    public IMGStickerTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IMGStickerTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // io.dcloud.feature.gallery.imageedit.b.a
    public void a(q2 q2Var) {
        TextView textView;
        this.n = q2Var;
        if (q2Var == null || (textView = this.m) == null) {
            return;
        }
        textView.setText(q2Var.c());
        this.m.setTextColor(this.n.b());
        this.m.setBackgroundColor(this.n.a());
    }
}
