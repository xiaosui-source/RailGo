package io.dcloud.feature.utsplugin;

import com.taobao.weex.bridge.JSCallback;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ParamConvertHelper.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0016\u0018\u0000 :2\u00020\u0001:\u0001:B5\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\u0012\u00108\u001a\u0004\u0018\u00010\u00012\b\u00109\u001a\u0004\u0018\u00010\u0001R\u0014\u0010\r\u001a\u00020\u0003X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u0003X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\u0003X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u001a\u0010\u0014\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001a\u0010\u001a\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017R\u001a\u0010\u001c\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017R\u001a\u0010\u001e\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0015\"\u0004\b\u001f\u0010\u0017R\u001a\u0010 \u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0015\"\u0004\b!\u0010\u0017R\u001a\u0010\"\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017R\u001c\u0010$\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R \u0010)\u001a\b\u0012\u0002\b\u0003\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R$\u0010/\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u0010\n\u0002\u00104\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00105\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0015\"\u0004\b7\u0010\u0017¨\u0006;"}, d2 = {"Lio/dcloud/feature/utsplugin/ParamConvertHelper;", "", "classTypeName", "", "actualTypeArguments", "", "Ljava/lang/reflect/Type;", "hostCallback", "Lcom/taobao/weex/bridge/JSCallback;", "isKeepAlive", "", "<init>", "(Ljava/lang/String;[Ljava/lang/reflect/Type;Lcom/taobao/weex/bridge/JSCallback;Z)V", "UTSCallbackType", "getUTSCallbackType", "()Ljava/lang/String;", "UTSObjectType", "getUTSObjectType", "StringClassFlag", "getStringClassFlag", "isUTSCallback", "()Z", "setUTSCallback", "(Z)V", "isUTSObject", "setUTSObject", "isPrimitive", "setPrimitive", "isListType", "setListType", "isUTSArray", "setUTSArray", "isMapType", "setMapType", "isAnyType", "setAnyType", "mHostCallback", "getMHostCallback", "()Lcom/taobao/weex/bridge/JSCallback;", "setMHostCallback", "(Lcom/taobao/weex/bridge/JSCallback;)V", "tClass", "Ljava/lang/Class;", "getTClass", "()Ljava/lang/Class;", "setTClass", "(Ljava/lang/Class;)V", "hostTypeArguments", "getHostTypeArguments", "()[Ljava/lang/reflect/Type;", "setHostTypeArguments", "([Ljava/lang/reflect/Type;)V", "[Ljava/lang/reflect/Type;", "mIsKeepAlive", "getMIsKeepAlive", "setMIsKeepAlive", "getInstance", "value", "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes.dex */
public class ParamConvertHelper {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String StringClassFlag;
    private final String UTSCallbackType;
    private final String UTSObjectType;
    private Type[] hostTypeArguments;
    private boolean isAnyType;
    private boolean isListType;
    private boolean isMapType;
    private boolean isPrimitive;
    private boolean isUTSArray;
    private boolean isUTSCallback;
    private boolean isUTSObject;
    private JSCallback mHostCallback;
    private boolean mIsKeepAlive;
    private Class<?> tClass;

    /* compiled from: ParamConvertHelper.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\f"}, d2 = {"Lio/dcloud/feature/utsplugin/ParamConvertHelper$Companion;", "", "<init>", "()V", "isPrimitiveType", "", "classTypeName", "", "isListType", "isUTSArray", "isMapType", "isAnyType", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isPrimitiveType(String classTypeName) {
            Intrinsics.checkNotNullParameter(classTypeName, "classTypeName");
            return Intrinsics.areEqual("int", classTypeName) || Intrinsics.areEqual("byte", classTypeName) || Intrinsics.areEqual("float", classTypeName) || Intrinsics.areEqual("double", classTypeName) || Intrinsics.areEqual("short", classTypeName) || Intrinsics.areEqual("kotlin.Int", classTypeName) || Intrinsics.areEqual("kotlin.Long", classTypeName) || Intrinsics.areEqual("kotlin.Double", classTypeName) || Intrinsics.areEqual("kotlin.Float", classTypeName) || Intrinsics.areEqual("kotlin.Byte", classTypeName) || Intrinsics.areEqual("kotlin.String", classTypeName) || Intrinsics.areEqual("java.lang.Number", classTypeName) || Intrinsics.areEqual("java.lang.String", classTypeName) || Intrinsics.areEqual("java.lang.Boolean", classTypeName) || Intrinsics.areEqual("java.lang.Byte", classTypeName) || Intrinsics.areEqual("boolean", classTypeName) || Intrinsics.areEqual("long", classTypeName);
        }

        public final boolean isListType(String classTypeName) {
            Intrinsics.checkNotNullParameter(classTypeName, "classTypeName");
            return StringsKt.startsWith$default(classTypeName, "java.util.List", false, 2, (Object) null);
        }

        public final boolean isUTSArray(String classTypeName) {
            Intrinsics.checkNotNullParameter(classTypeName, "classTypeName");
            return StringsKt.startsWith$default(classTypeName, "io.dcloud.uts.UTSArray", false, 2, (Object) null);
        }

        public final boolean isMapType(String classTypeName) {
            Intrinsics.checkNotNullParameter(classTypeName, "classTypeName");
            return Intrinsics.areEqual(classTypeName, "java.util.LinkedHashMap") || Intrinsics.areEqual(classTypeName, "io.dcloud.uts.Map");
        }

        public final boolean isAnyType(String classTypeName) {
            Intrinsics.checkNotNullParameter(classTypeName, "classTypeName");
            return Intrinsics.areEqual(classTypeName, "java.lang.Object") || Intrinsics.areEqual(classTypeName, "kotlin.Any");
        }
    }

    public final String getUTSCallbackType() {
        return this.UTSCallbackType;
    }

    public final String getUTSObjectType() {
        return this.UTSObjectType;
    }

    public final String getStringClassFlag() {
        return this.StringClassFlag;
    }

    /* renamed from: isUTSCallback, reason: from getter */
    public final boolean getIsUTSCallback() {
        return this.isUTSCallback;
    }

    public final void setUTSCallback(boolean z) {
        this.isUTSCallback = z;
    }

    /* renamed from: isUTSObject, reason: from getter */
    public final boolean getIsUTSObject() {
        return this.isUTSObject;
    }

    public final void setUTSObject(boolean z) {
        this.isUTSObject = z;
    }

    /* renamed from: isPrimitive, reason: from getter */
    public final boolean getIsPrimitive() {
        return this.isPrimitive;
    }

    public final void setPrimitive(boolean z) {
        this.isPrimitive = z;
    }

    /* renamed from: isListType, reason: from getter */
    public final boolean getIsListType() {
        return this.isListType;
    }

    public final void setListType(boolean z) {
        this.isListType = z;
    }

    /* renamed from: isUTSArray, reason: from getter */
    public final boolean getIsUTSArray() {
        return this.isUTSArray;
    }

    public final void setUTSArray(boolean z) {
        this.isUTSArray = z;
    }

    /* renamed from: isMapType, reason: from getter */
    public final boolean getIsMapType() {
        return this.isMapType;
    }

    public final void setMapType(boolean z) {
        this.isMapType = z;
    }

    /* renamed from: isAnyType, reason: from getter */
    public final boolean getIsAnyType() {
        return this.isAnyType;
    }

    public final void setAnyType(boolean z) {
        this.isAnyType = z;
    }

    public final JSCallback getMHostCallback() {
        return this.mHostCallback;
    }

    public final void setMHostCallback(JSCallback jSCallback) {
        this.mHostCallback = jSCallback;
    }

    public final Class<?> getTClass() {
        return this.tClass;
    }

    public final void setTClass(Class<?> cls) {
        this.tClass = cls;
    }

    public final Type[] getHostTypeArguments() {
        return this.hostTypeArguments;
    }

    public final void setHostTypeArguments(Type[] typeArr) {
        this.hostTypeArguments = typeArr;
    }

    public final boolean getMIsKeepAlive() {
        return this.mIsKeepAlive;
    }

    public final void setMIsKeepAlive(boolean z) {
        this.mIsKeepAlive = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public ParamConvertHelper(java.lang.String r4, java.lang.reflect.Type[] r5, com.taobao.weex.bridge.JSCallback r6, boolean r7) throws java.lang.ClassNotFoundException {
        /*
            r3 = this;
            java.lang.String r0 = "classTypeName"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            r3.<init>()
            java.lang.String r0 = "io.dcloud.uts.UTSCallback"
            r3.UTSCallbackType = r0
            java.lang.String r1 = "io.dcloud.uts.UTSJSONObject"
            r3.UTSObjectType = r1
            java.lang.String r2 = "java.lang.String"
            r3.StringClassFlag = r2
            r3.mIsKeepAlive = r7
            r3.hostTypeArguments = r5
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r4)
            r7 = 1
            if (r5 == 0) goto L22
            r3.isUTSObject = r7
            return
        L22:
            io.dcloud.feature.utsplugin.ParamConvertHelper$Companion r5 = io.dcloud.feature.utsplugin.ParamConvertHelper.INSTANCE
            boolean r1 = r5.isPrimitiveType(r4)
            if (r1 == 0) goto L2d
            r3.isPrimitive = r7
            return
        L2d:
            boolean r1 = r5.isListType(r4)
            if (r1 == 0) goto L36
            r3.isListType = r7
            return
        L36:
            boolean r1 = r5.isUTSArray(r4)
            if (r1 == 0) goto L3f
            r3.isUTSArray = r7
            return
        L3f:
            boolean r1 = r5.isAnyType(r4)
            if (r1 == 0) goto L48
            r3.isAnyType = r7
            return
        L48:
            boolean r5 = r5.isMapType(r4)
            if (r5 == 0) goto L51
            r3.isMapType = r7
            return
        L51:
            java.lang.Class r4 = java.lang.Class.forName(r4)
            r3.tClass = r4
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.String r4 = r4.getName()
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r4)
            if (r4 == 0) goto L67
            r3.isUTSCallback = r7
            goto L8a
        L67:
            java.lang.Class<?> r4 = r3.tClass
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            boolean r4 = r4.isPrimitive()
            if (r4 != 0) goto L88
            java.lang.Class<?> r4 = r3.tClass
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.String r4 = r4.getName()
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
            if (r4 == 0) goto L82
            goto L88
        L82:
            r4 = 0
            r3.isUTSCallback = r4
            r3.isPrimitive = r4
            goto L8a
        L88:
            r3.isPrimitive = r7
        L8a:
            r3.mHostCallback = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ParamConvertHelper.<init>(java.lang.String, java.lang.reflect.Type[], com.taobao.weex.bridge.JSCallback, boolean):void");
    }

    public /* synthetic */ ParamConvertHelper(String str, Type[] typeArr, JSCallback jSCallback, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, typeArr, jSCallback, (i & 8) != 0 ? false : z);
    }

    /* JADX WARN: Removed duplicated region for block: B:149:0x0315 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0414 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getInstance(java.lang.Object r25) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 1060
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ParamConvertHelper.getInstance(java.lang.Object):java.lang.Object");
    }
}
