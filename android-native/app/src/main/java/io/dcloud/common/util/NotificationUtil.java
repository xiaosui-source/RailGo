package io.dcloud.common.util;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import dc.squareup.okio.Okio$$ExternalSyntheticApiModelOutline0;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NotificationUtil {
    private static boolean isNotificationChannel = false;
    static String sChannelId = "DC_LOCAL_NEWS";
    static String sGroupId = "DC_LOCAL_GROUP";

    public static void cancelNotification(Context context, int i) {
        ((NotificationManager) context.getSystemService("notification")).cancel(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void createCustomNotification(android.content.Context r5, java.lang.String r6, android.graphics.Bitmap r7, java.lang.String r8, java.lang.String r9, int r10, android.app.PendingIntent r11) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.NotificationUtil.createCustomNotification(android.content.Context, java.lang.String, android.graphics.Bitmap, java.lang.String, java.lang.String, int, android.app.PendingIntent):void");
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT < 26 || isNotificationChannel) {
            return;
        }
        isNotificationChannel = true;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        Okio$$ExternalSyntheticApiModelOutline0.m407m();
        notificationManager.createNotificationChannelGroup(Okio$$ExternalSyntheticApiModelOutline0.m(sGroupId, "local_badge"));
        Okio$$ExternalSyntheticApiModelOutline0.m416m$1();
        NotificationChannel notificationChannelM = Okio$$ExternalSyntheticApiModelOutline0.m(sChannelId, "Information notice", 3);
        notificationChannelM.enableLights(true);
        notificationChannelM.setShowBadge(true);
        notificationManager.createNotificationChannel(notificationChannelM);
    }

    private static boolean isMiuiRom(Context context) {
        String property = System.getProperty("http.agent");
        return !TextUtils.isEmpty(property) && property.toLowerCase(Locale.ENGLISH).contains("miui");
    }

    public static void showNotification(Context context, String str, String str2, PendingIntent pendingIntent, Bitmap bitmap, int i) {
        Notification.Builder builder;
        createNotificationChannel(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            Okio$$ExternalSyntheticApiModelOutline0.m418m$2();
            builder = Okio$$ExternalSyntheticApiModelOutline0.m(context, sChannelId);
        } else {
            builder = new Notification.Builder(context);
        }
        try {
            builder.setSmallIcon(context.createPackageContext(context.getPackageName(), 2).getResources().getIdentifier(AbsoluteConst.JSON_KEY_ICON, R.drawable.class.getSimpleName(), context.getPackageName()));
        } catch (Exception unused) {
        }
        builder.setLargeIcon(bitmap);
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setDefaults(1);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        try {
            notificationManager.notify(i, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showNotification(Context context, String str, String str2, Intent intent, int i, int i2, int i3, boolean z) {
        PendingIntent broadcast;
        Notification.Builder builder;
        createNotificationChannel(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int i4 = Build.VERSION.SDK_INT;
        int i5 = i4 >= 23 ? 1140850688 : 1073741824;
        if (z) {
            broadcast = PendingIntent.getActivity(context, i3, intent, i5);
        } else {
            broadcast = PendingIntent.getBroadcast(context, i3, intent, i5);
        }
        if (i4 >= 26) {
            Okio$$ExternalSyntheticApiModelOutline0.m418m$2();
            builder = Okio$$ExternalSyntheticApiModelOutline0.m(context, sChannelId);
        } else {
            builder = new Notification.Builder(context);
        }
        if (-1 != i) {
            builder.setSmallIcon(i);
        } else {
            builder.setSmallIcon(context.getApplicationInfo().icon);
        }
        if (-1 != i2) {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), i2));
        } else {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), context.getApplicationInfo().icon));
        }
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setDefaults(1);
        builder.setAutoCancel(true);
        builder.setContentIntent(broadcast);
        try {
            notificationManager.notify(i3, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
