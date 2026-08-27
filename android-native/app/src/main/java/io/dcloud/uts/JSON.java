package io.dcloud.uts;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.uts.android.ClassLogWrapper;
import io.dcloud.uts.android.UTSGsonEncoder;
import io.dcloud.uts.gson.Gson;
import io.dcloud.uts.gson.GsonBuilder;
import io.dcloud.uts.gson.JsonElement;
import io.dcloud.uts.gson.JsonNull;
import io.dcloud.uts.gson.ToNumberPolicy;
import io.dcloud.uts.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: JSON.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u00011B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\"\u0010\u0004\u001a\u0002H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\b¢\u0006\u0002\u0010\tJ,\u0010\u0004\u001a\u0004\u0018\u0001H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0087\b¢\u0006\u0004\b\f\u0010\rJ'\u0010\u0004\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007¢\u0006\u0002\u0010\u0010J1\u0010\u0004\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0012\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001f\u001a\u00020\bH\u0007J\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001f\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007J3\u0010 \u001a\u00020\b2\b\u0010!\u001a\u0004\u0018\u00010\u00012\u000e\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00172\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\b$J\u0012\u0010 \u001a\u00020\b2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u0007Jd\u0010 \u001a\u00020\b2\b\u0010!\u001a\u0004\u0018\u00010\u00012D\b\u0002\u0010%\u001a>\u0012\u0013\u0012\u00110\b¢\u0006\f\b'\u0012\b\b(\u0012\u0004\b\b()\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b'\u0012\b\b(\u0012\u0004\b\b(*\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010&j\u0004\u0018\u0001`+2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0001H\u0007J\u0012\u0010,\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001f\u001a\u00020\bH\u0007J\"\u0010,\u001a\u0004\u0018\u0001H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u00012\u0006\u0010\u001f\u001a\u00020\bH\u0087\b¢\u0006\u0004\b-\u0010.J\u0016\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u001f\u001a\u00020\bH\u0007J&\u0010/\u001a\n\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0017\"\u0006\b\u0000\u0010\u0005\u0018\u00012\u0006\u0010\u001f\u001a\u00020\bH\u0087\b¢\u0006\u0002\b0R\u0011\u0010\u001a\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\n \u001e*\u0004\u0018\u00010\u000f0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lio/dcloud/uts/JSON;", "", "<init>", "()V", "parse", "T", "Lio/dcloud/uts/gson/Gson;", "json", "", "(Lio/dcloud/uts/gson/Gson;Ljava/lang/String;)Ljava/lang/Object;", "ignoreError", "", "parseType", "(Ljava/lang/String;Z)Ljava/lang/Object;", "typeOfT", "Ljava/lang/reflect/Type;", "(Ljava/lang/String;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "(Ljava/lang/String;Ljava/lang/reflect/Type;Z)Ljava/lang/Object;", "convertJSONObject", "Lio/dcloud/uts/UTSJSONObject;", "inputObject", "Lcom/alibaba/fastjson/JSONObject;", "convertJSONArray", "Lio/dcloud/uts/UTSArray;", "jsonArray", "Lcom/alibaba/fastjson/JSONArray;", "cacheParseGson", "getCacheParseGson", "()Lio/dcloud/uts/gson/Gson;", "hostAnyType", "kotlin.jvm.PlatformType", "inputString", "stringify", "sourceInput", "replacerArray", "space", "stringify_replacerArray", "replacer", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", IApp.ConfigProperty.CONFIG_KEY, "value", "Lio/dcloud/uts/stringifyReplacer;", "parseObject", "parseObjectType", "(Ljava/lang/String;)Ljava/lang/Object;", "parseArray", "parseArrayType", "JSON_SKIP_OBJECT", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class JSON {
    public static final JSON INSTANCE = new JSON();
    private static final Gson cacheParseGson;
    private static final Type hostAnyType;

    private JSON() {
    }

    /* compiled from: JSON.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lio/dcloud/uts/JSON$JSON_SKIP_OBJECT;", "", "<init>", "()V", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class JSON_SKIP_OBJECT {
        public static final JSON_SKIP_OBJECT INSTANCE = new JSON_SKIP_OBJECT();

        private JSON_SKIP_OBJECT() {
        }
    }

    public final /* synthetic */ <T> T parse(Gson gson, String json) {
        Intrinsics.checkNotNullParameter(gson, "<this>");
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.needClassReification();
        return (T) gson.fromJson(json, new TypeToken<T>() { // from class: io.dcloud.uts.JSON.parse.1
        }.getType());
    }

    public static /* synthetic */ java.lang.Object parseType$default(String json, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.reifiedOperationMarker(4, "T");
        if (Intrinsics.areEqual("String", java.lang.Object.class.getSimpleName())) {
            Intrinsics.reifiedOperationMarker(1, "T");
            return json;
        }
        ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
        try {
            Gson cacheParseGson2 = INSTANCE.getCacheParseGson();
            Intrinsics.needClassReification();
            return cacheParseGson2.fromJson(json, new JSON$parse$$inlined$parse$1().getType());
        } catch (Exception e) {
            if (!z) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
            }
            return null;
        }
    }

    @JvmStatic
    public static final /* synthetic */ <T> T parseType(String json, boolean ignoreError) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.reifiedOperationMarker(4, "T");
        if (Intrinsics.areEqual("String", java.lang.Object.class.getSimpleName())) {
            Intrinsics.reifiedOperationMarker(1, "T");
            return (T) json;
        }
        ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
        try {
            Gson cacheParseGson2 = INSTANCE.getCacheParseGson();
            Intrinsics.needClassReification();
            return (T) cacheParseGson2.fromJson(json, new JSON$parse$$inlined$parse$1().getType());
        } catch (Exception e) {
            if (!ignoreError) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
            }
            return null;
        }
    }

    @JvmStatic
    public static final <T> T parse(String json, Type typeOfT) {
        Intrinsics.checkNotNullParameter(json, "json");
        return (T) parse(json, typeOfT, false);
    }

    public static /* synthetic */ java.lang.Object parse$default(String str, Type type, boolean z, int i, java.lang.Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return parse(str, type, z);
    }

    @JvmStatic
    public static final <T> T parse(String json, Type typeOfT, boolean ignoreError) {
        Intrinsics.checkNotNullParameter(json, "json");
        if ((typeOfT instanceof Class) && String.class.isAssignableFrom((Class) typeOfT)) {
            return (T) json;
        }
        ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
        try {
            return (T) cacheParseGson.fromJson(json, typeOfT);
        } catch (Exception e) {
            if (!ignoreError) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
            }
            return null;
        }
    }

    public final UTSJSONObject convertJSONObject(JSONObject inputObject) {
        Intrinsics.checkNotNullParameter(inputObject, "inputObject");
        UTSJSONObject uTSJSONObject = new UTSJSONObject();
        for (Map.Entry<String, java.lang.Object> entry : inputObject.entrySet()) {
            Intrinsics.checkNotNullExpressionValue(entry, "next(...)");
            Map.Entry<String, java.lang.Object> entry2 = entry;
            java.lang.Object value = entry2.getValue();
            if (value instanceof JSONObject) {
                uTSJSONObject.getDynamicJSONFields().put(entry2.getKey(), convertJSONObject((JSONObject) value));
            } else if (value instanceof JSONArray) {
                uTSJSONObject.getDynamicJSONFields().put(entry2.getKey(), convertJSONArray((JSONArray) value));
            } else {
                uTSJSONObject.getDynamicJSONFields().put(entry2.getKey(), value);
            }
        }
        return uTSJSONObject;
    }

    public final UTSArray<?> convertJSONArray(JSONArray jsonArray) {
        Intrinsics.checkNotNullParameter(jsonArray, "jsonArray");
        UTSArray<?> uTSArray = new UTSArray<>();
        for (java.lang.Object objConvertJSONArray : jsonArray) {
            if (objConvertJSONArray instanceof JSONObject) {
                objConvertJSONArray = INSTANCE.convertJSONObject((JSONObject) objConvertJSONArray);
            } else if (objConvertJSONArray instanceof JSONArray) {
                objConvertJSONArray = INSTANCE.convertJSONArray((JSONArray) objConvertJSONArray);
            }
            uTSArray.add(objConvertJSONArray);
        }
        return uTSArray;
    }

    static {
        Gson gsonCreate = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.UTS_Number).registerTypeAdapter(UTSJSONObject.class, new UTSJsonDeserializer()).create();
        Intrinsics.checkNotNullExpressionValue(gsonCreate, "create(...)");
        cacheParseGson = gsonCreate;
        hostAnyType = new TypeToken<java.lang.Object>() { // from class: io.dcloud.uts.JSON$hostAnyType$1
        }.getType();
    }

    public final Gson getCacheParseGson() {
        return cacheParseGson;
    }

    @JvmStatic
    public static final java.lang.Object parse(String inputString) {
        Intrinsics.checkNotNullParameter(inputString, "inputString");
        return parse(inputString, false);
    }

    public static /* synthetic */ java.lang.Object parse$default(String str, boolean z, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return parse(str, z);
    }

    @JvmStatic
    public static final java.lang.Object parse(String inputString, boolean ignoreError) {
        Intrinsics.checkNotNullParameter(inputString, "inputString");
        ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
        if (StringsKt.isBlank(inputString)) {
            if (!ignoreError) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), new IllegalArgumentException("JSON.parse error: input text is empty"));
            }
            return null;
        }
        try {
            return cacheParseGson.fromJson(inputString, hostAnyType);
        } catch (Exception e) {
            if (!ignoreError) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
            }
            return null;
        }
    }

    public static /* synthetic */ String stringify_replacerArray$default(java.lang.Object obj, UTSArray uTSArray, java.lang.Object obj2, int i, java.lang.Object obj3) {
        if ((i & 4) != 0) {
            obj2 = null;
        }
        return stringify_replacerArray(obj, uTSArray, obj2);
    }

    @JvmStatic
    public static final String stringify_replacerArray(java.lang.Object sourceInput, final UTSArray<java.lang.Object> replacerArray, java.lang.Object space) {
        Intrinsics.checkNotNullParameter(replacerArray, "replacerArray");
        return stringify(sourceInput, new Function2() { // from class: io.dcloud.uts.JSON$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                return JSON.stringify$lambda$1(replacerArray, obj, obj2);
            }
        }, space);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final java.lang.Object stringify$lambda$1(UTSArray<java.lang.Object> uTSArray, java.lang.Object obj, java.lang.Object obj2) {
        return (Intrinsics.areEqual(obj, "") || uTSArray.contains(obj)) ? obj2 : JSON_SKIP_OBJECT.INSTANCE;
    }

    @JvmStatic
    public static final String stringify(java.lang.Object sourceInput) {
        return stringify(sourceInput, null, null);
    }

    public static /* synthetic */ String stringify$default(java.lang.Object obj, Function2 function2, java.lang.Object obj2, int i, java.lang.Object obj3) {
        if ((i & 2) != 0) {
            function2 = null;
        }
        if ((i & 4) != 0) {
            obj2 = null;
        }
        return stringify(obj, function2, obj2);
    }

    @JvmStatic
    public static final String stringify(java.lang.Object sourceInput, Function2<? super String, java.lang.Object, ? extends java.lang.Object> replacer, java.lang.Object space) {
        if (replacer != null) {
            sourceInput = replacer.invoke("", sourceInput);
        }
        if (sourceInput == null) {
            String string = JsonNull.INSTANCE.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
        if (sourceInput instanceof Function) {
            return "";
        }
        if (sourceInput instanceof Number) {
            if (UTSNumber.INSTANCE.isNaN(sourceInput)) {
                String string2 = JsonNull.INSTANCE.toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                return string2;
            }
            if (Intrinsics.areEqual(sourceInput, Double.valueOf(Double.POSITIVE_INFINITY)) || Intrinsics.areEqual(sourceInput, Float.valueOf(Float.POSITIVE_INFINITY)) || Intrinsics.areEqual(sourceInput, Double.valueOf(Double.NEGATIVE_INFINITY)) || Intrinsics.areEqual(sourceInput, Float.valueOf(Float.NEGATIVE_INFINITY))) {
                String string3 = JsonNull.INSTANCE.toString();
                Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
                return string3;
            }
            return ClassLogWrapper.INSTANCE.wrapNumberText(sourceInput);
        }
        if (sourceInput instanceof JSONObject) {
            String jSONString = ((JSONObject) sourceInput).toJSONString();
            Intrinsics.checkNotNullExpressionValue(jSONString, "toJSONString(...)");
            return jSONString;
        }
        JsonElement jsonElementEncode = new UTSGsonEncoder().encode(sourceInput, replacer);
        if (jsonElementEncode == null) {
            String string4 = JsonNull.INSTANCE.toString();
            Intrinsics.checkNotNullExpressionValue(string4, "toString(...)");
            return string4;
        }
        if (space == null) {
            String string5 = jsonElementEncode.toString();
            Intrinsics.checkNotNullExpressionValue(string5, "toString(...)");
            return string5;
        }
        if (space instanceof Number) {
            int iIntValue = ((Number) space).intValue();
            if (iIntValue > 0) {
                String string6 = jsonElementEncode.toString(StringKt.repeat(Operators.SPACE_STR, Integer.valueOf(iIntValue <= 10 ? iIntValue : 10)));
                Intrinsics.checkNotNull(string6);
                return string6;
            }
            String string7 = jsonElementEncode.toString();
            Intrinsics.checkNotNull(string7);
            return string7;
        }
        if (space instanceof String) {
            String strSubstring = (String) space;
            if (strSubstring.length() > 10) {
                strSubstring = StringKt.substring(strSubstring, (Number) 0, (Number) 10);
            }
            String string8 = jsonElementEncode.toString(strSubstring);
            Intrinsics.checkNotNullExpressionValue(string8, "toString(...)");
            return string8;
        }
        String string9 = jsonElementEncode.toString();
        Intrinsics.checkNotNullExpressionValue(string9, "toString(...)");
        return string9;
    }

    @JvmStatic
    public static final UTSJSONObject parseObject(String inputString) {
        Intrinsics.checkNotNullParameter(inputString, "inputString");
        java.lang.Object obj = parse(inputString);
        if (obj != null && (obj instanceof UTSJSONObject)) {
            return (UTSJSONObject) obj;
        }
        return null;
    }

    @JvmStatic
    public static final /* synthetic */ <T> T parseObjectType(String inputString) {
        T t;
        Intrinsics.checkNotNullParameter(inputString, "inputString");
        JSON json = INSTANCE;
        Intrinsics.reifiedOperationMarker(4, "T");
        if (Intrinsics.areEqual("String", java.lang.Object.class.getSimpleName())) {
            Intrinsics.reifiedOperationMarker(1, "T");
            t = (T) inputString;
        } else {
            ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
            try {
                Gson cacheParseGson2 = json.getCacheParseGson();
                Intrinsics.needClassReification();
                t = (T) cacheParseGson2.fromJson(inputString, new TypeToken<T>() { // from class: io.dcloud.uts.JSON$parseObject$$inlined$parseType$default$1
                }.getType());
            } catch (Exception e) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
                t = null;
            }
        }
        if (t == null) {
            return null;
        }
        return t;
    }

    @JvmStatic
    public static final UTSArray<?> parseArray(String inputString) {
        Intrinsics.checkNotNullParameter(inputString, "inputString");
        java.lang.Object obj = parse(inputString);
        if (obj != null && (obj instanceof UTSArray)) {
            return (UTSArray) obj;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JvmStatic
    public static final /* synthetic */ <T> UTSArray<T> parseArrayType(String inputString) {
        java.lang.Object objFromJson;
        Intrinsics.checkNotNullParameter(inputString, "inputString");
        JSON json = INSTANCE;
        if (Intrinsics.areEqual("String", "UTSArray")) {
            objFromJson = (UTSArray) inputString;
        } else {
            ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
            try {
                Gson cacheParseGson2 = json.getCacheParseGson();
                Intrinsics.needClassReification();
                objFromJson = cacheParseGson2.fromJson(inputString, new TypeToken<UTSArray<T>>() { // from class: io.dcloud.uts.JSON$parseArray$$inlined$parseType$default$1
                }.getType());
            } catch (Exception e) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
                objFromJson = null;
            }
        }
        UTSArray<T> uTSArray = (UTSArray) objFromJson;
        if (uTSArray != null) {
            return uTSArray;
        }
        return null;
    }
}
