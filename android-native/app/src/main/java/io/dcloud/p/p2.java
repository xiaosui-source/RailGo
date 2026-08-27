package io.dcloud.p;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface p2 {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface a {
        void a(View view);

        boolean b(View view);

        void c(View view);
    }

    void a(Canvas canvas);

    void a(a aVar);

    boolean a();

    void b(a aVar);

    boolean b();

    boolean dismiss();

    RectF getFrame();
}
