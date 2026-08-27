package io.dcloud.p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.view.View;
import androidx.core.app.ActivityOptionsCompat;
import io.dcloud.WebviewActivity;
import io.dcloud.base.R;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class z3 {
    private static z3 g;
    Context e;
    int a = -16777216;
    int b = -16777216;
    int c = -16777216;
    int d = 17;
    String f = "isSelected";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends ClickableSpan {
        final /* synthetic */ URLSpan a;

        a(URLSpan uRLSpan) {
            this.a = uRLSpan;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            try {
                String url = this.a.getURL();
                Intent intent = new Intent();
                intent.setClass(z3.this.e, WebviewActivity.class);
                intent.putExtra("url", url);
                intent.setData(Uri.parse(url));
                intent.setAction("android.intent.action.VIEW");
                intent.setFlags(268435456);
                intent.putExtra("ANIM", "POP");
                z3.this.e.startActivity(intent, ActivityOptionsCompat.makeCustomAnimation(z3.this.e, R.anim.dcloud_pop_in, R.anim.dcloud_pop_in_out).toBundle());
            } catch (Exception unused) {
            }
        }
    }

    public z3(Context context) {
        this.e = context;
    }

    public static z3 a(Context context) {
        if (g == null) {
            g = new z3(context);
        }
        return g;
    }

    public CharSequence a(String str) {
        Spanned spannedFromHtml = Html.fromHtml(str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannedFromHtml);
        for (URLSpan uRLSpan : (URLSpan[]) spannableStringBuilder.getSpans(0, spannedFromHtml.length(), URLSpan.class)) {
            a(spannableStringBuilder, uRLSpan);
        }
        return spannableStringBuilder;
    }

    private void a(SpannableStringBuilder spannableStringBuilder, URLSpan uRLSpan) {
        int spanStart = spannableStringBuilder.getSpanStart(uRLSpan);
        int spanEnd = spannableStringBuilder.getSpanEnd(uRLSpan);
        spannableStringBuilder.setSpan(new a(uRLSpan), spanStart, spanEnd, spannableStringBuilder.getSpanFlags(uRLSpan));
        spannableStringBuilder.setSpan(new TextAppearanceSpan(this.e, R.style.textAppearance), spanStart, spanEnd, 33);
    }
}
