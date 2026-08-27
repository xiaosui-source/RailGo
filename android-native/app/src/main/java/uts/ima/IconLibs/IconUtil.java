package uts.ima.IconLibs;

import android.content.pm.ActivityInfo;
import com.taobao.weex.el.parse.Operators;
import kotlin.Metadata;
import kotlin.text.StringsKt;

/* compiled from: IconUtil.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"Luts/ima/IconLibs/IconUtil;", "", "<init>", "()V", "getsAliasNames", "Lio/dcloud/uts/UTSArray;", "", "context", "Landroid/content/Context;", "extractSimpleName", "fullName", "isActivityAlias", "", "info", "Landroid/content/pm/ActivityInfo;", "ima-icons_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IconUtil {
    public static final IconUtil INSTANCE = new IconUtil();

    private IconUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0081 A[Catch: Exception -> 0x008e, TryCatch #0 {Exception -> 0x008e, blocks: (B:3:0x0005, B:5:0x0018, B:7:0x0023, B:9:0x0032, B:10:0x0036, B:11:0x004b, B:13:0x0051, B:14:0x0068, B:16:0x0074, B:22:0x0083, B:19:0x0079, B:20:0x0080, B:21:0x0081), top: B:26:0x0005 }] */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final io.dcloud.uts.UTSArray<java.lang.String> getsAliasNames(android.content.Context r6) {
        /*
            java.lang.String r0 = "context"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            android.content.pm.PackageManager r0 = r6.getPackageManager()     // Catch: java.lang.Exception -> L8e
            java.lang.String r6 = r6.getPackageName()     // Catch: java.lang.Exception -> L8e
            r1 = 513(0x201, float:7.19E-43)
            android.content.pm.PackageInfo r6 = r0.getPackageInfo(r6, r1)     // Catch: java.lang.Exception -> L8e
            android.content.pm.ActivityInfo[] r6 = r6.activities     // Catch: java.lang.Exception -> L8e
            r0 = 0
            if (r6 == 0) goto L81
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Exception -> L8e
            r1.<init>()     // Catch: java.lang.Exception -> L8e
            java.util.Collection r1 = (java.util.Collection) r1     // Catch: java.lang.Exception -> L8e
            int r2 = r6.length     // Catch: java.lang.Exception -> L8e
            r3 = 0
        L21:
            if (r3 >= r2) goto L36
            r4 = r6[r3]     // Catch: java.lang.Exception -> L8e
            int r3 = r3 + 1
            uts.ima.IconLibs.IconUtil r5 = uts.ima.IconLibs.IconUtil.INSTANCE     // Catch: java.lang.Exception -> L8e
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)     // Catch: java.lang.Exception -> L8e
            boolean r5 = r5.isActivityAlias(r4)     // Catch: java.lang.Exception -> L8e
            if (r5 == 0) goto L21
            r1.add(r4)     // Catch: java.lang.Exception -> L8e
            goto L21
        L36:
            java.util.List r1 = (java.util.List) r1     // Catch: java.lang.Exception -> L8e
            java.lang.Iterable r1 = (java.lang.Iterable) r1     // Catch: java.lang.Exception -> L8e
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch: java.lang.Exception -> L8e
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r1, r2)     // Catch: java.lang.Exception -> L8e
            r6.<init>(r2)     // Catch: java.lang.Exception -> L8e
            java.util.Collection r6 = (java.util.Collection) r6     // Catch: java.lang.Exception -> L8e
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Exception -> L8e
        L4b:
            boolean r2 = r1.hasNext()     // Catch: java.lang.Exception -> L8e
            if (r2 == 0) goto L68
            java.lang.Object r2 = r1.next()     // Catch: java.lang.Exception -> L8e
            android.content.pm.ActivityInfo r2 = (android.content.pm.ActivityInfo) r2     // Catch: java.lang.Exception -> L8e
            uts.ima.IconLibs.IconUtil r3 = uts.ima.IconLibs.IconUtil.INSTANCE     // Catch: java.lang.Exception -> L8e
            java.lang.String r2 = r2.name     // Catch: java.lang.Exception -> L8e
            java.lang.String r4 = "name"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)     // Catch: java.lang.Exception -> L8e
            java.lang.String r2 = r3.extractSimpleName(r2)     // Catch: java.lang.Exception -> L8e
            r6.add(r2)     // Catch: java.lang.Exception -> L8e
            goto L4b
        L68:
            java.util.List r6 = (java.util.List) r6     // Catch: java.lang.Exception -> L8e
            java.util.Collection r6 = (java.util.Collection) r6     // Catch: java.lang.Exception -> L8e
            java.lang.String[] r1 = new java.lang.String[r0]     // Catch: java.lang.Exception -> L8e
            java.lang.Object[] r6 = r6.toArray(r1)     // Catch: java.lang.Exception -> L8e
            if (r6 == 0) goto L79
            java.lang.String[] r6 = (java.lang.String[]) r6     // Catch: java.lang.Exception -> L8e
            if (r6 == 0) goto L81
            goto L83
        L79:
            java.lang.NullPointerException r6 = new java.lang.NullPointerException     // Catch: java.lang.Exception -> L8e
            java.lang.String r0 = "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>"
            r6.<init>(r0)     // Catch: java.lang.Exception -> L8e
            throw r6     // Catch: java.lang.Exception -> L8e
        L81:
            java.lang.String[] r6 = new java.lang.String[r0]     // Catch: java.lang.Exception -> L8e
        L83:
            io.dcloud.uts.UTSArray r0 = new io.dcloud.uts.UTSArray     // Catch: java.lang.Exception -> L8e
            int r1 = r6.length     // Catch: java.lang.Exception -> L8e
            java.lang.Object[] r6 = java.util.Arrays.copyOf(r6, r1)     // Catch: java.lang.Exception -> L8e
            r0.<init>(r6)     // Catch: java.lang.Exception -> L8e
            return r0
        L8e:
            io.dcloud.uts.UTSArray r6 = new io.dcloud.uts.UTSArray
            r6.<init>()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: uts.ima.IconLibs.IconUtil.getsAliasNames(android.content.Context):io.dcloud.uts.UTSArray");
    }

    private final String extractSimpleName(String fullName) {
        return StringsKt.substringAfterLast$default(fullName, Operators.DOT, (String) null, 2, (Object) null);
    }

    private final boolean isActivityAlias(ActivityInfo info) {
        return info.targetActivity != null;
    }
}
