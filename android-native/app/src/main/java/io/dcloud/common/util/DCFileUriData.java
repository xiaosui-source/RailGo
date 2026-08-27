package io.dcloud.common.util;

import android.net.Uri;
import android.text.TextUtils;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCFileUriData {
    public String filePath;
    public String fileReplacePath;
    public Uri fileUri;
    public boolean isReplace = false;

    public void clear() {
        if (this.fileUri != null) {
            this.fileUri = null;
        }
        if (!TextUtils.isEmpty(this.filePath)) {
            this.filePath = null;
        }
        if (TextUtils.isEmpty(this.fileReplacePath)) {
            return;
        }
        this.fileReplacePath = null;
    }
}
