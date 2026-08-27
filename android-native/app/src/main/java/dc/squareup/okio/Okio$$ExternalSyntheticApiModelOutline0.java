package dc.squareup.okio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.media.ExifInterface;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class Okio$$ExternalSyntheticApiModelOutline0 {
    public static /* synthetic */ Notification.Builder m(Context context, String str) {
        return new Notification.Builder(context, str);
    }

    public static /* synthetic */ NotificationChannel m(String str, CharSequence charSequence, int i) {
        return new NotificationChannel(str, charSequence, i);
    }

    public static /* synthetic */ NotificationChannelGroup m(String str, CharSequence charSequence) {
        return new NotificationChannelGroup(str, charSequence);
    }

    public static /* synthetic */ ExifInterface m(FileDescriptor fileDescriptor) {
        return new ExifInterface(fileDescriptor);
    }

    public static /* synthetic */ ExifInterface m(InputStream inputStream) {
        return new ExifInterface(inputStream);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ Class m396m() {
        return OptionalLong.class;
    }

    public static /* bridge */ /* synthetic */ Optional m(Object obj) {
        return (Optional) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ OptionalInt m401m(Object obj) {
        return (OptionalInt) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ OptionalLong m404m(Object obj) {
        return (OptionalLong) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ DoubleAdder m405m(Object obj) {
        return (DoubleAdder) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ LongAdder m406m(Object obj) {
        return (LongAdder) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ void m407m() {
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ boolean m411m(Object obj) {
        return obj instanceof OptionalInt;
    }

    /* renamed from: m$1, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ Class m415m$1() {
        return OptionalDouble.class;
    }

    /* renamed from: m$1, reason: collision with other method in class */
    public static /* synthetic */ void m416m$1() {
    }

    public static /* bridge */ /* synthetic */ boolean m$1(Object obj) {
        return obj instanceof Optional;
    }

    /* renamed from: m$2, reason: collision with other method in class */
    public static /* synthetic */ void m418m$2() {
    }

    public static /* bridge */ /* synthetic */ boolean m$2(Object obj) {
        return obj instanceof OptionalLong;
    }

    public static /* bridge */ /* synthetic */ boolean m$3(Object obj) {
        return obj instanceof LongAdder;
    }

    public static /* bridge */ /* synthetic */ boolean m$4(Object obj) {
        return obj instanceof DoubleAdder;
    }
}
