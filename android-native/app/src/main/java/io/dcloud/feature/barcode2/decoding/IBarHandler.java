package io.dcloud.feature.barcode2.decoding;

import android.graphics.Bitmap;
import android.os.Handler;
import com.dcloud.zxing2.Result;
import io.dcloud.feature.barcode2.view.ViewfinderView;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IBarHandler {
    void autoFocus();

    void drawViewfinder();

    Handler getHandler();

    ViewfinderView getViewfinderView();

    void handleDecode(Result result, Bitmap bitmap);

    boolean isRunning();
}
