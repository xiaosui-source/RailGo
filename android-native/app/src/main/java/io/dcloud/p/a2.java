package io.dcloud.p;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class a2 extends d2 {
    private Context b;

    public a2(Context context, Uri uri) {
        super(uri);
        this.b = context;
    }

    @Override // io.dcloud.p.d2
    public Bitmap a(BitmapFactory.Options options) throws IOException {
        Uri uriA = a();
        if (uriA == null) {
            return null;
        }
        String path = uriA.getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            InputStream inputStreamOpen = this.b.getAssets().open(path.substring(1));
            return options.inJustDecodeBounds ? BitmapFactory.decodeStream(inputStreamOpen, null, options) : z.a(inputStreamOpen, options);
        } catch (IOException unused) {
            return null;
        }
    }
}
