package io.dcloud.p;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import io.dcloud.base.R;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class e4 extends Dialog {
    private final Context a;
    private TextView b;
    private TextView c;
    private Button d;
    private Button e;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements View.OnClickListener {
        final /* synthetic */ View.OnClickListener a;

        a(View.OnClickListener onClickListener) {
            this.a = onClickListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            View.OnClickListener onClickListener = this.a;
            if (onClickListener != null) {
                onClickListener.onClick(view);
                e4.this.dismiss();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements View.OnClickListener {
        final /* synthetic */ View.OnClickListener a;

        b(View.OnClickListener onClickListener) {
            this.a = onClickListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            View.OnClickListener onClickListener = this.a;
            if (onClickListener != null) {
                onClickListener.onClick(view);
                e4.this.cancel();
            }
        }
    }

    public e4(Context context) {
        super(context);
        this.a = context;
        a();
    }

    private void a() {
        requestWindowFeature(1);
        setContentView(R.layout.dcloud_sample_dialog);
        b();
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        c();
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    private void b() {
        this.b = (TextView) findViewById(R.id.tv_sample_dialog_title);
        this.c = (TextView) findViewById(R.id.tv_sample_dialog_content);
        this.d = (Button) findViewById(R.id.btn_sample_dialog_sure);
        this.e = (Button) findViewById(R.id.btn_sample_dialog_cancel);
    }

    private void c() {
        Display defaultDisplay = ((Activity) this.a).getWindowManager().getDefaultDisplay();
        if (this.a.getResources().getConfiguration().orientation == 1) {
            this.c.setMaxHeight((int) (defaultDisplay.getHeight() * 0.6d));
        } else {
            this.c.setMaxHeight((int) (defaultDisplay.getHeight() * 0.5d));
        }
    }

    public void b(int i) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        window.setLayout(i, attributes.height);
    }

    public void a(int i) {
        this.c.setGravity(i);
    }

    public void a(String str) {
        this.c.setText(str);
    }

    public void a(String str, View.OnClickListener onClickListener) {
        this.e.setVisibility(0);
        this.e.setText(str);
        this.e.setOnClickListener(new b(onClickListener));
    }

    public void b(String str) {
        this.b.setText(str);
    }

    public void b(String str, View.OnClickListener onClickListener) {
        this.d.setVisibility(0);
        this.d.setText(str);
        this.d.setOnClickListener(new a(onClickListener));
    }
}
