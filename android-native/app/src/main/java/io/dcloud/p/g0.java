package io.dcloud.p;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import io.dcloud.common.DHInterface.ICallBack;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class g0 extends Toast implements ICallBack {
    private static ArrayList f = new ArrayList();
    View a;
    TextView b;
    WindowManager.LayoutParams c;
    WindowManager d;
    String e;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            g0.this.a();
        }
    }

    public g0(Activity activity, String str) {
        super(activity);
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = str;
        this.d = activity.getWindowManager();
        TextView textView = new TextView(activity);
        this.b = textView;
        this.a = textView;
        textView.setPadding(20, 20, 20, 20);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2, 8, -2);
        this.c = layoutParams;
        layoutParams.gravity = 17;
    }

    @Override // io.dcloud.common.DHInterface.ICallBack
    public Object onCallBack(int i, Object obj) {
        return null;
    }

    @Override // android.widget.Toast
    public void setDuration(int i) {
        if (i == 1) {
            i = 3500;
        } else if (i == 0) {
            i = 2000;
        }
        super.setDuration(i);
    }

    @Override // android.widget.Toast
    public void setGravity(int i, int i2, int i3) {
        WindowManager.LayoutParams layoutParams = this.c;
        layoutParams.gravity = i;
        layoutParams.x = i2;
        layoutParams.y = i3;
        super.setGravity(i, i2, i3);
    }

    @Override // android.widget.Toast
    public void setText(CharSequence charSequence) {
        this.b.setText(charSequence);
        super.setText(charSequence);
    }

    @Override // android.widget.Toast
    public synchronized void show() {
        f.add(this);
        this.d.addView(this.a, this.c);
        this.a.postDelayed(new a(), getDuration());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a() {
        View view = this.a;
        if (view != null) {
            try {
                this.d.removeViewImmediate(view);
            } catch (Exception unused) {
            }
            f.remove(this);
            this.a = null;
        }
    }

    public void a(View view, TextView textView) {
        this.a = view;
        this.b = textView;
    }

    public static synchronized void a(String str) {
        if (f.isEmpty()) {
            return;
        }
        for (int size = f.size() - 1; size >= 0; size--) {
            ((g0) f.get(size)).a();
        }
        f.clear();
    }
}
