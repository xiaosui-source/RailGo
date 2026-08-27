package uts.sdk.modules.DCloudUniNetwork;

import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.http.WXStreamModule;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.net.NetWork;
import io.dcloud.uniapp.SourceError;
import io.dcloud.uts.ArrayBuffer;
import io.dcloud.uts.JSON;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.TextEncoder;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSIteratorKt;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import io.dcloud.uts.gson.Gson;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import uts.sdk.modules.DCloudUniNetwork.StatusCode;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0002\u0010%\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B'\b\u0016\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J*\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00150\u0014H\u0016J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0013H\u0016J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\fH\u0016J\u0018\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0005\u001a\u00020\bH\u0002J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\bH\u0002J\u0018\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0!2\b\u0010\"\u001a\u0004\u0018\u00010\fH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/RequestNetworkListener;", "T", "Luts/sdk/modules/DCloudUniNetwork/NetworkRequestListener;", "param", "Luts/sdk/modules/DCloudUniNetwork/RequestOptions;", "type", "Ljava/lang/reflect/Type;", "clzName", "", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/RequestOptions;Ljava/lang/reflect/Type;Ljava/lang/String;)V", "headers", "Lio/dcloud/uts/UTSJSONObject;", "looper", "Landroid/os/Looper;", "onStart", "", "onHeadersReceived", "statusCode", "", "", "", "onProgress", "progress", "onComplete", AbsoluteConst.JSON_KEY_OPTION, "readAsString", "byteArray", "", "parseData", "", "data", "parseCookie", "Lio/dcloud/uts/UTSArray;", WXBasicComponentType.HEADER, "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RequestNetworkListener<T> extends NetworkRequestListener {
    private String clzName;
    private UTSJSONObject headers;
    private Looper looper;
    private RequestOptions<T> param;
    private Type type;

    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkRequestListener
    public void onProgress(Number progress) {
        Intrinsics.checkNotNullParameter(progress, "progress");
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkRequestListener
    public void onStart() {
    }

    public RequestNetworkListener(RequestOptions<T> param, Type type, String clzName) {
        Intrinsics.checkNotNullParameter(param, "param");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(clzName, "clzName");
        this.headers = UTSJSONObjectKt._uO(new Pair[0]);
        this.param = param;
        this.type = type;
        this.clzName = clzName;
        this.looper = Looper.myLooper();
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkRequestListener
    public void onHeadersReceived(Number statusCode, Map<String, List<String>> headers) {
        Intrinsics.checkNotNullParameter(statusCode, "statusCode");
        Intrinsics.checkNotNullParameter(headers, "headers");
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (key == null) {
                key = "_";
            }
            if (value.size() != 0) {
                if (value.size() == 1) {
                    uTSJSONObject_uO.set(key, value.get(0));
                } else {
                    uTSJSONObject_uO.set(key, NumberKt.toString(value, (Number) 10));
                }
            }
        }
        this.headers = uTSJSONObject_uO;
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.NetworkRequestListener
    public void onComplete(UTSJSONObject option) throws NumberFormatException {
        String dataType;
        Object data;
        Intrinsics.checkNotNullParameter(option, "option");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = this.param;
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
        if (objectRef.element != null) {
            if (Intrinsics.areEqual("-1", option.get("statusCode"))) {
                UTSJSONObject uTSJSONObject = this.headers;
                if (uTSJSONObject != null) {
                    uTSJSONObject_uO.set(WXBasicComponentType.HEADER, uTSJSONObject);
                }
                Object obj = option.get("cause");
                Intrinsics.checkNotNull(obj);
                Exception exc = (Exception) obj;
                Object obj2 = option.get("errorMsg");
                Intrinsics.checkNotNull(obj2);
                String str = (String) obj2;
                String string_number_nullable$default = NumberKt.toString_number_nullable$default(exc.getCause(), (Number) null, 1, (Object) null);
                if (exc.getCause() == null) {
                    string_number_nullable$default = str;
                }
                Object obj3 = option.get("errorCode");
                Intrinsics.checkNotNull(obj3);
                int i = Integer.parseInt((String) obj3);
                if (NumberKt.numberEquals(StringKt.indexOf$default(str, "timeout", null, 2, null), -1)) {
                    String str2 = string_number_nullable$default;
                    if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "Connection refused", false, 2, (Object) null)) {
                        i = 1000;
                    } else if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "Network is unreachable", false, 2, (Object) null)) {
                        i = 600003;
                    } else if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "invalid URL", false, 2, (Object) null)) {
                        i = 600009;
                    }
                } else {
                    i = 5;
                }
                final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                objectRef2.element = (T) new RequestFailImpl(IndexKt.getErrcode(Integer.valueOf(i)));
                ((RequestFailImpl) objectRef2.element).setCause(new SourceError(string_number_nullable$default));
                new RunnableTask__1(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.RequestNetworkListener$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return RequestNetworkListener.onComplete$lambda$0(objectRef, objectRef2);
                    }
                }).execute();
                return;
            }
            uTSJSONObject_uO.set("statusCode", option.get("statusCode"));
            if (option.get("originalData") == null) {
                if (StringsKt.equals("java.lang.Object", this.clzName, true)) {
                    Object obj4 = option.get("errorMsg");
                    if (obj4 != null) {
                        Object obj5 = option.get("errorMsg");
                        Intrinsics.checkNotNull(obj5);
                        Object obj6 = JSON.parse((String) obj5);
                        if (obj6 != null) {
                            uTSJSONObject_uO.set("data", obj6);
                        } else {
                            uTSJSONObject_uO.set("data", obj4);
                        }
                    } else {
                        uTSJSONObject_uO.set("data", "error");
                    }
                } else if (StringsKt.equals("java.lang.String", this.clzName, true)) {
                    Object obj7 = option.get("errorMsg");
                    uTSJSONObject_uO.set("data", obj7 != null ? obj7 : "error");
                } else if (StringsKt.equals("io.dcloud.uts.ArrayBuffer", this.clzName, true)) {
                    TextEncoder textEncoder = new TextEncoder();
                    Object obj8 = option.get("errorMsg");
                    uTSJSONObject_uO.set("data", textEncoder.encode((String) (obj8 != null ? obj8 : "error")).getBuffer());
                } else {
                    Object obj9 = option.get("errorMsg");
                    if (obj9 != null) {
                        Object obj10 = JSON.parse((String) obj9, this.type);
                        if (obj10 != null) {
                            uTSJSONObject_uO.set("data", obj10);
                        } else {
                            final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
                            objectRef3.element = (T) new RequestFailImpl(IndexKt.getErrcode((Number) 100002));
                            ((RequestFailImpl) objectRef3.element).setData(obj9);
                            ((RequestFailImpl) objectRef3.element).setCause(new SourceError("Error message parsing failed"));
                            new RunnableTask__1(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.RequestNetworkListener$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    return RequestNetworkListener.onComplete$lambda$1(objectRef, objectRef3);
                                }
                            }).execute();
                            return;
                        }
                    }
                }
            } else {
                com.alibaba.fastjson.JSON jSONObject = this.headers.toJSONObject();
                Intrinsics.checkNotNull(jSONObject, "null cannot be cast to non-null type com.alibaba.fastjson.JSONObject");
                JSONObject jSONObject2 = (JSONObject) jSONObject;
                Iterator it = ((Set) UTSIteratorKt.resolveUTSKeyIterator(jSONObject2.keySet())).iterator();
                String str3 = "";
                String str4 = "";
                while (it.hasNext()) {
                    Iterator it2 = it;
                    String str5 = (String) it.next();
                    String str6 = str3;
                    if (StringsKt.equals(str5, NetWork.CONTENT_TYPE, true)) {
                        Object obj11 = jSONObject2.get(str5);
                        Intrinsics.checkNotNull(obj11, "null cannot be cast to non-null type kotlin.String");
                        str4 = (String) obj11;
                    }
                    str3 = str6;
                    it = it2;
                }
                String str7 = str3;
                if (StringsKt.equals("io.dcloud.uts.ArrayBuffer", this.clzName, true)) {
                    Object obj12 = option.get("originalData");
                    Intrinsics.checkNotNull(obj12, "null cannot be cast to non-null type kotlin.ByteArray");
                    ArrayBuffer.Companion companion = ArrayBuffer.INSTANCE;
                    ByteBuffer byteBufferWrap = ByteBuffer.wrap((byte[]) obj12);
                    Intrinsics.checkNotNullExpressionValue(byteBufferWrap, "wrap(...)");
                    data = companion.fromByteBuffer(byteBufferWrap);
                } else {
                    Object obj13 = option.get("originalData");
                    Intrinsics.checkNotNull(obj13, "null cannot be cast to non-null type kotlin.ByteArray");
                    String str8 = str4;
                    String asString = readAsString((byte[]) obj13, str8);
                    if (((RequestOptions) objectRef.element).getResponseType() != null) {
                        dataType = ((RequestOptions) objectRef.element).getResponseType();
                        Intrinsics.checkNotNull(dataType);
                    } else {
                        dataType = ((RequestOptions) objectRef.element).getDataType();
                    }
                    String str9 = dataType;
                    if (str9 == null) {
                        str9 = str8;
                    }
                    data = parseData(asString, Intrinsics.areEqual(((RequestOptions) objectRef.element).getMethod(), "HEAD") ? str7 : str9);
                    if (data == null) {
                        final Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
                        objectRef4.element = (T) new RequestFailImpl(IndexKt.getErrcode((Number) 100001));
                        if (!StringsKt.equals("java.lang.Object", this.clzName, true) && !StringsKt.equals("java.lang.String", this.clzName, true)) {
                            objectRef4.element = (T) new RequestFailImpl(IndexKt.getErrcode((Number) 100003));
                        }
                        new RunnableTask__1(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.RequestNetworkListener$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return RequestNetworkListener.onComplete$lambda$2(objectRef, objectRef4);
                            }
                        }).execute();
                        return;
                    }
                }
                uTSJSONObject_uO.set("data", data);
            }
            StatusCode.Companion companion2 = StatusCode.INSTANCE;
            Object obj14 = option.get("statusCode");
            Intrinsics.checkNotNull(obj14, "null cannot be cast to non-null type kotlin.String");
            uTSJSONObject_uO.set(WXStreamModule.STATUS_TEXT, companion2.getStatus((String) obj14));
            UTSJSONObject uTSJSONObject2 = this.headers;
            if (uTSJSONObject2 != null) {
                uTSJSONObject_uO.set(WXBasicComponentType.HEADER, uTSJSONObject2);
            }
            final Ref.ObjectRef objectRef5 = new Ref.ObjectRef();
            Object obj15 = uTSJSONObject_uO.get("data");
            Object obj16 = uTSJSONObject_uO.get("statusCode");
            Intrinsics.checkNotNull(obj16, "null cannot be cast to non-null type kotlin.String");
            Integer numValueOf = Integer.valueOf(Integer.parseInt((String) obj16));
            Object obj17 = uTSJSONObject_uO.get(WXBasicComponentType.HEADER);
            Intrinsics.checkNotNull(obj17);
            objectRef5.element = (T) new RequestSuccess(obj15, numValueOf, obj17, NetworkUtil.INSTANCE.parseCookie(this.headers));
            new RunnableTask__1(this.looper, new Function0() { // from class: uts.sdk.modules.DCloudUniNetwork.RequestNetworkListener$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return RequestNetworkListener.onComplete$lambda$3(objectRef, objectRef5);
                }
            }).execute();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Unit onComplete$lambda$0(Ref.ObjectRef<RequestOptions<T>> objectRef, Ref.ObjectRef<RequestFailImpl> objectRef2) {
        if (objectRef.element != null) {
            Function1<RequestFail, Unit> fail = objectRef.element.getFail();
            if (fail != null) {
                fail.invoke2(objectRef2.element);
            }
            Function1<Object, Unit> complete = objectRef.element.getComplete();
            if (complete != null) {
                complete.invoke2(objectRef2.element);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Unit onComplete$lambda$1(Ref.ObjectRef<RequestOptions<T>> objectRef, Ref.ObjectRef<RequestFailImpl> objectRef2) {
        if (objectRef.element != null) {
            Function1<RequestFail, Unit> fail = objectRef.element.getFail();
            if (fail != null) {
                fail.invoke2(objectRef2.element);
            }
            Function1<Object, Unit> complete = objectRef.element.getComplete();
            if (complete != null) {
                complete.invoke2(objectRef2.element);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Unit onComplete$lambda$2(Ref.ObjectRef<RequestOptions<T>> objectRef, Ref.ObjectRef<RequestFailImpl> objectRef2) {
        if (objectRef.element != null) {
            Function1<RequestFail, Unit> fail = objectRef.element.getFail();
            if (fail != null) {
                fail.invoke2(objectRef2.element);
            }
            Function1<Object, Unit> complete = objectRef.element.getComplete();
            if (complete != null) {
                complete.invoke2(objectRef2.element);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Unit onComplete$lambda$3(Ref.ObjectRef<RequestOptions<T>> objectRef, Ref.ObjectRef<RequestSuccess<T>> objectRef2) {
        if (objectRef.element != null) {
            Function1<RequestSuccess<T>, Unit> success = objectRef.element.getSuccess();
            if (success != null) {
                success.invoke2(objectRef2.element);
            }
            Function1<Object, Unit> complete = objectRef.element.getComplete();
            if (complete != null) {
                complete.invoke2(objectRef2.element);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.String readAsString(byte[] r4, java.lang.String r5) {
        /*
            r3 = this;
            if (r5 == 0) goto L2d
            java.util.regex.Pattern r0 = uts.sdk.modules.DCloudUniNetwork.IndexKt.getCharsetPattern()
            java.util.Locale r1 = java.util.Locale.ENGLISH
            java.lang.String r2 = "ENGLISH"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            java.lang.String r5 = r5.toLowerCase(r1)
            java.lang.String r1 = "this as java.lang.String).toLowerCase(locale)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r1)
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5
            java.util.regex.Matcher r5 = r0.matcher(r5)
            boolean r0 = r5.find()
            if (r0 == 0) goto L2d
            r0 = 1
            java.lang.String r5 = r5.group(r0)
            java.lang.String r0 = "group(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r0)
            goto L2f
        L2d:
            java.lang.String r5 = "utf-8"
        L2f:
            java.lang.String r0 = new java.lang.String     // Catch: java.lang.Exception -> L3e
            java.nio.charset.Charset r5 = java.nio.charset.Charset.forName(r5)     // Catch: java.lang.Exception -> L3e
            java.lang.String r1 = "forName(charsetName)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r1)     // Catch: java.lang.Exception -> L3e
            r0.<init>(r4, r5)     // Catch: java.lang.Exception -> L3e
            return r0
        L3e:
            java.lang.String r5 = new java.lang.String
            java.nio.charset.Charset r0 = kotlin.text.Charsets.UTF_8
            r5.<init>(r4, r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: uts.sdk.modules.DCloudUniNetwork.RequestNetworkListener.readAsString(byte[], java.lang.String):java.lang.String");
    }

    private final Object parseData(String data, String type) {
        if (!StringsKt.equals("java.lang.Object", this.clzName, true)) {
            return StringsKt.equals("java.lang.String", this.clzName, true) ? data : JSON.parse(data, this.type);
        }
        Object objFromJson = null;
        if (!NumberKt.numberEquals(StringKt.indexOf$default(type, "json", null, 2, null), -1)) {
            return JSON.parse(data);
        }
        if (Intrinsics.areEqual(type, "jsonp")) {
            if (TextUtils.isEmpty(data)) {
                return UTSJSONObjectKt._uO(new Pair[0]);
            }
            Number numberPlus = NumberKt.plus(StringKt.indexOf$default(data, Operators.BRACKET_START_STR, null, 2, null), (Number) 1);
            Number numberIndexOf$default = StringKt.indexOf$default(data, Operators.BRACKET_END_STR, null, 2, null);
            if (NumberKt.numberEquals(numberPlus, 0) || NumberKt.compareTo(numberPlus, numberIndexOf$default) >= 0) {
                return UTSJSONObjectKt._uO(new Pair[0]);
            }
            return JSON.parse(StringKt.substring(data, numberPlus, numberIndexOf$default));
        }
        try {
            objFromJson = new Gson().fromJson(data, this.type);
        } catch (Throwable unused) {
        }
        return objFromJson == null ? data : objFromJson;
    }

    private final UTSArray<String> parseCookie(UTSJSONObject header) throws IllegalAccessException, IllegalArgumentException {
        if (header != null) {
            String string = header.getString(IWebview.SET_COOKIE);
            if (string == null) {
                string = header.getString("set-cookie");
            }
            if (string != null) {
                UTSArray uTSArray = new UTSArray();
                if (Intrinsics.areEqual(StringKt.charAt(string, (Number) 0), Operators.ARRAY_START_STR) && Intrinsics.areEqual(StringKt.charAt(string, Integer.valueOf(string.length() - 1)), Operators.ARRAY_END_STR)) {
                    string = StringKt.slice(string, (Number) 1, (Number) (-1));
                }
                UTSArray<String> uTSArraySplit = StringKt.split(string, ";");
                for (Number numberInc = (Number) 0; NumberKt.compareTo(numberInc, uTSArraySplit.getLength()) < 0; numberInc = NumberKt.inc(numberInc)) {
                    if (!NumberKt.numberEquals(StringKt.indexOf$default(uTSArraySplit.get(numberInc), "Expires=", null, 2, null), -1) || !NumberKt.numberEquals(StringKt.indexOf$default(uTSArraySplit.get(numberInc), "expires=", null, 2, null), -1)) {
                        uTSArray.push(StringKt.replace(uTSArraySplit.get(numberInc), ",", ""));
                    } else {
                        uTSArray.push(uTSArraySplit.get(numberInc));
                    }
                }
                return StringKt.split(uTSArray.join(";"), ",");
            }
            return new UTSArray<>();
        }
        return new UTSArray<>();
    }
}
