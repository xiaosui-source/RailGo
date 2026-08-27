package io.dcloud.p;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class l {
    public static int a(Context context) {
        return (int) (context.getResources().getDisplayMetrics().heightPixels * 0.8f);
    }

    public static int b(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            Matcher matcher = Pattern.compile("[\\w\\.]+[\\.](avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc)").matcher(str);
            if (matcher.find()) {
                return matcher.group();
            }
            if (TextUtils.isEmpty(str3)) {
                return String.valueOf(System.currentTimeMillis()) + ".dat";
            }
        } else {
            String lastPathSegment = Uri.parse(str).getLastPathSegment();
            if (lastPathSegment != null && lastPathSegment.contains(str2)) {
                return lastPathSegment;
            }
            if (TextUtils.isEmpty(str3)) {
                return String.valueOf(System.currentTimeMillis()) + str2;
            }
        }
        return str3;
    }
}
