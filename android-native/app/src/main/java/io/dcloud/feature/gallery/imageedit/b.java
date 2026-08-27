package io.dcloud.feature.gallery.imageedit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import io.dcloud.base.R;
import io.dcloud.feature.gallery.imageedit.view.IMGColorGroup;
import io.dcloud.p.q2;
import java.lang.reflect.Field;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class b extends Dialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private EditText a;
    private a b;
    private q2 c;
    private IMGColorGroup d;
    private int e;
    private TextView f;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface a {
        void a(q2 q2Var);
    }

    public b(Context context, a aVar) {
        super(context, R.style.ImageTextDialog);
        this.e = -1;
        setContentView(R.layout.image_text_dialog);
        this.b = aVar;
        Window window = getWindow();
        if (window != null) {
            window.setLayout(-1, -1);
        }
    }

    private void b() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField.setAccessible(true);
            declaredField.set(this.a, Integer.valueOf(R.drawable.image_edit_cursor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(q2 q2Var) {
        this.c = q2Var;
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        this.e = this.d.getCheckColor();
        if (!this.f.isSelected()) {
            this.a.setTextColor(this.e);
            this.a.setBackgroundColor(0);
        } else {
            if (this.e == -1) {
                this.a.setTextColor(-16777216);
            } else {
                this.a.setTextColor(-1);
            }
            this.a.setBackgroundColor(this.e);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_done) {
            a();
            this.e = -1;
            this.a.setBackgroundColor(0);
            return;
        }
        if (id == R.id.tv_cancel) {
            dismiss();
            this.e = -1;
            this.a.setBackgroundColor(0);
        } else if (id == R.id.textview_1) {
            view.setSelected(!view.isSelected());
            if (!view.isSelected()) {
                this.a.setTextColor(this.e);
                this.a.setBackgroundColor(0);
            } else {
                if (this.e == -1) {
                    this.a.setTextColor(-16777216);
                } else {
                    this.a.setTextColor(-1);
                }
                this.a.setBackgroundColor(this.e);
            }
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IMGColorGroup iMGColorGroup = (IMGColorGroup) findViewById(R.id.cg_colors);
        this.d = iMGColorGroup;
        iMGColorGroup.setOnCheckedChangeListener(this);
        this.a = (EditText) findViewById(R.id.et_text);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_done).setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.textview_1);
        this.f = textView;
        textView.setOnClickListener(this);
        this.f.setSelected(false);
    }

    @Override // android.app.Dialog
    protected void onStart() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        super.onStart();
        this.a.setPadding(30, 30, 30, 30);
        q2 q2Var = this.c;
        if (q2Var != null) {
            this.a.setText(q2Var.c());
            int iB = this.c.b();
            this.e = iB;
            this.a.setTextColor(iB);
            b();
            int iA = this.c.a();
            if (iA == 0) {
                this.f.setSelected(false);
                this.a.setBackgroundColor(0);
            } else {
                this.e = iA;
                this.a.setBackgroundColor(iA);
                this.f.setSelected(true);
            }
            if (!this.c.d()) {
                EditText editText = this.a;
                editText.setSelection(editText.length());
            }
            this.c = null;
        } else {
            this.a.setText("");
            this.f.setSelected(false);
        }
        getCurrentFocus();
        this.d.setCheckColor(this.e);
        this.a.requestFocus();
    }

    private void a() {
        int i;
        int i2;
        String string = this.a.getText().toString();
        if (!TextUtils.isEmpty(string) && this.b != null) {
            if (this.f.isSelected()) {
                i2 = this.e;
                i = -1;
                if (i2 == -1) {
                    i = -16777216;
                }
            } else {
                i = this.e;
                i2 = 0;
            }
            this.b.a(new q2(string, i, i2));
        }
        dismiss();
    }
}
