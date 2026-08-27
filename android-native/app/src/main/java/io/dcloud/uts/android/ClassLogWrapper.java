package io.dcloud.uts.android;

import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.uts.Date;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSNumber;
import io.dcloud.uts.UTSRegExp;
import io.dcloud.uts.gson.JsonArray;
import io.dcloud.uts.gson.JsonElement;
import io.dcloud.uts.gson.JsonObject;
import io.dcloud.uts.gson.JsonPrimitive;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.jvm.ReflectLambdaKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: ClassLogWrapper.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J<\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u00012\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u00010\tj\b\u0012\u0004\u0012\u00020\u0001`\n2\b\b\u0002\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001JV\u0010\u0010\u001a\u00020\u00112\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00132\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u00010\tj\b\u0012\u0004\u0012\u00020\u0001`\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0006\u001a\u00020\u0001H\u0002J<\u0010\u0019\u001a\u00020\u00152\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001b2\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u00010\tj\b\u0012\u0004\u0012\u00020\u0001`\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\fH\u0002J:\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u00012\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u00010\tj\b\u0012\u0004\u0012\u00020\u0001`\n2\u0006\u0010\u0016\u001a\u00020\fH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001e\u001a\u00020\u0001H\u0002J\u001a\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000e2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u0002J\u0014\u0010#\u001a\u0004\u0018\u00010\u000e2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u0002J\u001c\u0010$\u001a\u00020\u000e2\b\u0010\u001e\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010%\u001a\u00020 H\u0002¨\u0006&"}, d2 = {"Lio/dcloud/uts/android/ClassLogWrapper;", "", "<init>", "()V", "wrapClass", "Lio/dcloud/uts/gson/JsonElement;", "objInstance", "parentField", "inputStock", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "limitCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "wrapNumberText", "", "logValue", "fillRootJsonWithMap", "", "customMap", "", "rootJsonObject", "Lio/dcloud/uts/gson/JsonObject;", "nextLimitCounter", "valueProperObject", "Lio/dcloud/uts/gson/JsonArray;", "wrapClassInfo", "classObj", "Ljava/lang/Class;", "doJsonConvert", "logClassOf", "input", "needSubType", "", "type", "value", "logSubTypeOf", "logTypeOf", "isReturnTag", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ClassLogWrapper {
    public static final ClassLogWrapper INSTANCE = new ClassLogWrapper();

    private ClassLogWrapper() {
    }

    public static /* synthetic */ JsonElement wrapClass$default(ClassLogWrapper classLogWrapper, Object obj, Object obj2, HashSet hashSet, AtomicInteger atomicInteger, int i, Object obj3) {
        if ((i & 8) != 0) {
            atomicInteger = new AtomicInteger(256);
        }
        return classLogWrapper.wrapClass(obj, obj2, hashSet, atomicInteger);
    }

    public final JsonElement wrapClass(Object objInstance, Object parentField, HashSet<Object> inputStock, AtomicInteger limitCounter) {
        Intrinsics.checkNotNullParameter(inputStock, "inputStock");
        Intrinsics.checkNotNullParameter(limitCounter, "limitCounter");
        if (objInstance == null) {
            if (parentField == null) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("type", new JsonPrimitive("null"));
                jsonObject.add("value", new JsonPrimitive("null"));
                return jsonObject;
            }
            return new JsonPrimitive("null");
        }
        if (inputStock.contains(objInstance)) {
            inputStock.remove(objInstance);
            return new JsonPrimitive("[Circular]");
        }
        inputStock.add(objInstance);
        JsonPrimitive jsonPrimitive = new JsonPrimitive("null");
        try {
            jsonPrimitive = doJsonConvert(objInstance, parentField, inputStock, limitCounter);
        } catch (Throwable unused) {
        }
        inputStock.remove(objInstance);
        return jsonPrimitive;
    }

    public final String wrapNumberText(Object logValue) {
        if (logValue == null) {
            return "null";
        }
        if (logValue instanceof Number) {
            boolean zAreEqual = Intrinsics.areEqual(logValue, Float.valueOf(0.0f));
            String string = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
            if (!zAreEqual && !Intrinsics.areEqual(logValue, Double.valueOf(0.0d)) && !Intrinsics.areEqual(logValue, (Object) 0L) && (!Intrinsics.areEqual(logValue, (Object) 0) || !(logValue instanceof Integer))) {
                Number number = (Number) logValue;
                if (number.intValue() != 0 || (!(logValue instanceof Short) && !(logValue instanceof Byte))) {
                    if (Intrinsics.areEqual(NumberKt.div(number, Integer.valueOf(number.intValue())), Double.valueOf(1.0d))) {
                        string = String.valueOf(number.intValue());
                    } else if ((logValue instanceof Double) || (logValue instanceof Float)) {
                        if (Intrinsics.areEqual(logValue, UTSNumber.INSTANCE.getNaN()) || Intrinsics.areEqual(logValue, UTSNumber.INSTANCE.getPOSITIVE_INFINITY()) || Intrinsics.areEqual(logValue, UTSNumber.INSTANCE.getNEGATIVE_INFINITY())) {
                            string = logValue.toString();
                        } else {
                            double dAbs = Math.abs(number.doubleValue());
                            if (dAbs >= 1.0E-6d && dAbs < 1.0E21d) {
                                string = ClassLogWrapper$$ExternalSyntheticBackportWithForwarding0.m(new BigDecimal(logValue.toString()).round(new MathContext(17, RoundingMode.HALF_UP))).toPlainString();
                            } else {
                                String lowerCase = StringKt.toLowerCase(logValue.toString());
                                if (StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) ".0e", false, 2, (Object) null)) {
                                    lowerCase = StringsKt.replace$default(lowerCase, ".0e", "e", false, 4, (Object) null);
                                }
                                String str = lowerCase;
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "e-", false, 2, (Object) null) || !StringsKt.contains$default((CharSequence) str, (CharSequence) "e", false, 2, (Object) null)) {
                                    return lowerCase;
                                }
                                List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{"e"}, false, 0, 6, (Object) null);
                                return ((String) listSplit$default.get(0)) + "e+" + ((String) listSplit$default.get(1));
                            }
                        }
                    } else {
                        string = ClassLogWrapper$$ExternalSyntheticBackportWithForwarding0.m(new BigDecimal(number.toString()).round(new MathContext(17, RoundingMode.HALF_UP))).toPlainString();
                    }
                }
            }
            Intrinsics.checkNotNull(string);
            return string;
        }
        return logValue.toString();
    }

    private final JsonObject wrapClassInfo(Class<?> classObj, HashSet<Object> inputStock, JsonObject rootJsonObject, AtomicInteger nextLimitCounter) {
        Field[] fieldArr;
        String strLogSubTypeOf;
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        Field[] declaredFields = classObj.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
        Field[] fieldArr2 = declaredFields;
        int length = fieldArr2.length;
        boolean z = false;
        int i = 0;
        while (i < length) {
            Field field = fieldArr2[i];
            Object obj = Modifier.isStatic(field.getModifiers()) ? field.get(classObj) : null;
            ClassLogWrapper classLogWrapper = INSTANCE;
            String strLogTypeOf$default = logTypeOf$default(classLogWrapper, obj, z, 2, null);
            if (Intrinsics.areEqual(field.getName(), "Companion") && obj != null) {
                Reflection.getOrCreateKotlinClass(obj.getClass()).isCompanion();
                fieldArr = fieldArr2;
            } else {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.add("type", new JsonPrimitive(strLogTypeOf$default));
                if (classLogWrapper.needSubType(strLogTypeOf$default, obj) && (strLogSubTypeOf = classLogWrapper.logSubTypeOf(obj)) != null) {
                    jsonObject2.add("subType", new JsonPrimitive(strLogSubTypeOf));
                }
                jsonObject2.add("name", new JsonPrimitive(field.getName()));
                if (obj instanceof Function) {
                    KFunction kFunctionReflect = ReflectLambdaKt.reflect((Function) obj);
                    if (kFunctionReflect != null) {
                        JsonArray jsonArray2 = new JsonArray();
                        Iterator<T> it = kFunctionReflect.getParameters().iterator();
                        while (it.hasNext()) {
                            jsonArray2.add(new JsonPrimitive(INSTANCE.logTypeOf(((KParameter) it.next()).getType().toString(), true)));
                        }
                        jsonObject2.add("parameter", jsonArray2);
                        if (!Intrinsics.areEqual("kotlin.Unit", kFunctionReflect.getReturnType().toString())) {
                            jsonObject2.add("returned", new JsonPrimitive(INSTANCE.logTypeOf(kFunctionReflect.getReturnType().toString(), true)));
                        }
                    }
                    fieldArr = fieldArr2;
                } else {
                    JsonElement jsonElementWrapClass = classLogWrapper.wrapClass(obj, classObj, inputStock, nextLimitCounter);
                    if (Intrinsics.areEqual("object", strLogTypeOf$default)) {
                        fieldArr = fieldArr2;
                        if (jsonElementWrapClass instanceof JsonObject) {
                            Set<Map.Entry<String, JsonElement>> setEntrySet = ((JsonObject) jsonElementWrapClass).entrySet();
                            Intrinsics.checkNotNullExpressionValue(setEntrySet, "entrySet(...)");
                            Iterator<T> it2 = setEntrySet.iterator();
                            while (it2.hasNext()) {
                                Map.Entry entry = (Map.Entry) it2.next();
                                jsonObject2.add((String) entry.getKey(), (JsonElement) entry.getValue());
                            }
                        }
                    } else {
                        fieldArr = fieldArr2;
                    }
                    if (Intrinsics.areEqual("object", strLogTypeOf$default) && Intrinsics.areEqual("\"[Circular]\"", jsonElementWrapClass.toString())) {
                        jsonObject2.add("value", jsonElementWrapClass);
                        Intrinsics.checkNotNull(obj);
                        jsonObject2.add("className", new JsonPrimitive(classLogWrapper.logClassOf(obj)));
                    } else {
                        jsonObject2.add("value", jsonElementWrapClass);
                    }
                }
                jsonArray.add(jsonObject2);
            }
            i++;
            fieldArr2 = fieldArr;
            z = false;
        }
        if (UTSJSONObject.class.isAssignableFrom(classObj) && !rootJsonObject.has("__$originalPosition")) {
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.add("name", new JsonPrimitive("UTSJSONObject"));
            rootJsonObject.add("__$originalPosition", jsonObject3);
        }
        jsonObject.add("properties", jsonArray);
        rootJsonObject.add("value", jsonObject);
        return rootJsonObject;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0449  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0459  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x067e  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0992  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x099d  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x0a98  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final io.dcloud.uts.gson.JsonElement doJsonConvert(java.lang.Object r33, java.lang.Object r34, java.util.HashSet<java.lang.Object> r35, java.util.concurrent.atomic.AtomicInteger r36) {
        /*
            Method dump skipped, instructions count: 3007
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.uts.android.ClassLogWrapper.doJsonConvert(java.lang.Object, java.lang.Object, java.util.HashSet, java.util.concurrent.atomic.AtomicInteger):io.dcloud.uts.gson.JsonElement");
    }

    private final String logClassOf(Object input) {
        if (input instanceof Class) {
            return ((Class) input).getName();
        }
        if (input instanceof KClass) {
            return JvmClassMappingKt.getJavaClass((KClass) input).getName();
        }
        String qualifiedName = Reflection.getOrCreateKotlinClass(input.getClass()).getQualifiedName();
        return qualifiedName != null ? qualifiedName : input.getClass().getName();
    }

    private final boolean needSubType(String type, Object value) {
        return Intrinsics.areEqual(type, "object") || (value instanceof Number);
    }

    private final String logSubTypeOf(Object input) {
        if ((input instanceof UTSArray) || (input instanceof List) || (input instanceof Object[]) || (input instanceof byte[]) || (input instanceof char[]) || (input instanceof short[]) || (input instanceof float[]) || (input instanceof double[]) || (input instanceof int[]) || (input instanceof boolean[]) || (input instanceof long[])) {
            return "array";
        }
        if ((input instanceof UTSRegExp) || (input instanceof Regex)) {
            return "regexp";
        }
        if ((input instanceof Date) || (input instanceof java.util.Date)) {
            return "date";
        }
        if (input instanceof Number) {
            return "number";
        }
        if (input instanceof String) {
            return "string";
        }
        if (input instanceof Throwable) {
            return "error";
        }
        if (input instanceof Set) {
            return "set";
        }
        if (input instanceof Map) {
            return "map";
        }
        if (input instanceof Function) {
            return "function";
        }
        if ((input instanceof Class) || (input instanceof KClass)) {
            return "class";
        }
        return "object";
    }

    static /* synthetic */ String logTypeOf$default(ClassLogWrapper classLogWrapper, Object obj, boolean z, int i, Object obj2) {
        if ((i & 2) != 0) {
            z = false;
        }
        return classLogWrapper.logTypeOf(obj, z);
    }

    private final String logTypeOf(Object input, boolean isReturnTag) {
        if (input == null) {
            return "null";
        }
        if (isReturnTag && (input instanceof String)) {
            if (Intrinsics.areEqual("kotlin.Number", input)) {
                return "number";
            }
            if (Intrinsics.areEqual("kotlin.String", input)) {
                return "string";
            }
            return (String) input;
        }
        return UTSAndroid.INSTANCE.typeof(input);
    }

    private final void fillRootJsonWithMap(Map<String, ? extends Object> customMap, HashSet<Object> inputStock, JsonObject rootJsonObject, AtomicInteger nextLimitCounter, JsonArray valueProperObject, Object objInstance) {
        String strLogTypeOf$default;
        String strLogSubTypeOf;
        for (Map.Entry<String, ? extends Object> entry : customMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            JsonObject jsonObject = new JsonObject();
            if (Intrinsics.areEqual("__$originalPosition", key) && (value instanceof JsonObject)) {
                rootJsonObject.add("__$originalPosition", (JsonElement) value);
            } else {
                if (value instanceof UTSLogInfo) {
                    UTSLogInfo uTSLogInfo = (UTSLogInfo) value;
                    strLogTypeOf$default = INSTANCE.logTypeOf(uTSLogInfo.getType(), true);
                    value = uTSLogInfo.getValue();
                } else {
                    strLogTypeOf$default = logTypeOf$default(INSTANCE, value, false, 2, null);
                }
                jsonObject.add("type", new JsonPrimitive(strLogTypeOf$default));
                ClassLogWrapper classLogWrapper = INSTANCE;
                if (classLogWrapper.needSubType(strLogTypeOf$default, value) && (strLogSubTypeOf = classLogWrapper.logSubTypeOf(value)) != null) {
                    jsonObject.add("subType", new JsonPrimitive(strLogSubTypeOf));
                }
                jsonObject.add("name", new JsonPrimitive(key));
                if (value instanceof Function) {
                    KFunction kFunctionReflect = ReflectLambdaKt.reflect((Function) value);
                    if (kFunctionReflect != null) {
                        JsonArray jsonArray = new JsonArray();
                        Iterator<T> it = kFunctionReflect.getParameters().iterator();
                        while (it.hasNext()) {
                            jsonArray.add(new JsonPrimitive(INSTANCE.logTypeOf(((KParameter) it.next()).getType().toString(), true)));
                        }
                        jsonObject.add("parameter", jsonArray);
                        if (!Intrinsics.areEqual("kotlin.Unit", kFunctionReflect.getReturnType().toString())) {
                            jsonObject.add("returned", new JsonPrimitive(logTypeOf$default(INSTANCE, kFunctionReflect.getReturnType().toString(), false, 2, null)));
                        }
                    }
                } else {
                    JsonElement jsonElementWrapClass = classLogWrapper.wrapClass(value, objInstance, inputStock, nextLimitCounter);
                    if (Intrinsics.areEqual("object", strLogTypeOf$default) && (jsonElementWrapClass instanceof JsonObject)) {
                        Set<Map.Entry<String, JsonElement>> setEntrySet = ((JsonObject) jsonElementWrapClass).entrySet();
                        Intrinsics.checkNotNullExpressionValue(setEntrySet, "entrySet(...)");
                        Iterator<T> it2 = setEntrySet.iterator();
                        while (it2.hasNext()) {
                            Map.Entry entry2 = (Map.Entry) it2.next();
                            jsonObject.add((String) entry2.getKey(), (JsonElement) entry2.getValue());
                        }
                    } else if (Intrinsics.areEqual("object", strLogTypeOf$default) && Intrinsics.areEqual("\"[Circular]\"", jsonElementWrapClass.toString())) {
                        jsonObject.add("value", jsonElementWrapClass);
                        Intrinsics.checkNotNull(value);
                        jsonObject.add("className", new JsonPrimitive(classLogWrapper.logClassOf(value)));
                    } else {
                        jsonObject.add("value", jsonElementWrapClass);
                    }
                }
                valueProperObject.add(jsonObject);
            }
        }
        if (objInstance instanceof UTSJSONObject) {
            if (!rootJsonObject.has("__$originalPosition")) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.add("name", new JsonPrimitive("UTSJSONObject"));
                rootJsonObject.add("__$originalPosition", jsonObject2);
            } else {
                JsonElement jsonElement = rootJsonObject.get("__$originalPosition");
                if (jsonElement instanceof JsonObject) {
                    ((JsonObject) jsonElement).add("name", new JsonPrimitive("UTSJSONObject"));
                }
                rootJsonObject.add("__$originalPosition", jsonElement);
            }
        }
    }
}
