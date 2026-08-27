package io.dcloud.uts;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.android.UTSLogInfo;
import io.dcloud.uts.gson.Gson;
import io.dcloud.uts.gson.JsonObject;
import io.dcloud.uts.gson.internal.LinkedTreeMap;
import io.dcloud.uts.gson.reflect.TypeToken;
import io.dcloud.uts.json.IJsonStringify;
import io.dcloud.uts.log.LogSelfV2;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KAnnotatedElements;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.KCallablesJvm;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: UTSJSONObject.kt */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\t\n\u0002\u0010\u0004\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\b\u0016\u0018\u0000 a2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u00042\u00020\u0005:\u0001aB\u0007¢\u0006\u0004\b\u0006\u0010\u0007B\u001b\b\u0016\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0006\u0010\fB\u0013\b\u0016\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\u0006\u0010\rJ\n\u0010\u0014\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010(\u001a\u00020)H\u0002J)\u0010*\u001a\u00020)2!\u0010+\u001a\u001d\u0012\u0013\u0012\u00110\t¢\u0006\f\b-\u0012\b\b.\u0012\u0004\b\b(/\u0012\u0004\u0012\u00020)0,J\u0018\u00100\u001a\u0004\u0018\u0001H1\"\u0006\b\u0000\u00101\u0018\u0001H\u0086\b¢\u0006\u0002\u00102J\b\u00103\u001a\u000204H\u0007J\u000e\u00105\u001a\u00020)2\u0006\u00106\u001a\u00020\tJ\u0013\u00107\u001a\u0004\u0018\u00010\t2\u0006\u0010.\u001a\u00020\tH\u0096\u0002J\u001b\u00108\u001a\u00020)2\u0006\u0010.\u001a\u00020\t2\b\u0010/\u001a\u0004\u0018\u00010\tH\u0096\u0002J\u0016\u00109\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t0\"H\u0016J\u0016\u0010:\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t0;H\u0016J\u000e\u0010<\u001a\u0002042\u0006\u0010=\u001a\u00020\u0002J\u0014\u0010>\u001a\u00020\u00162\n\u0010?\u001a\u0006\u0012\u0002\b\u00030@H\u0002J\u0019\u0010>\u001a\u00020\u00162\n\u0010A\u001a\u0006\u0012\u0002\b\u00030BH\u0002¢\u0006\u0002\u0010CJ\u0014\u0010D\u001a\u0004\u0018\u00010\t2\b\u0010E\u001a\u0004\u0018\u00010\tH\u0002J\b\u0010F\u001a\u00020GH\u0016J\u000e\u0010H\u001a\u00020)2\u0006\u0010I\u001a\u00020\u0000J\u000e\u0010H\u001a\u00020)2\u0006\u0010I\u001a\u00020\u0003J\u0012\u0010H\u001a\u00020)2\n\u0010I\u001a\u0006\u0012\u0002\b\u00030JJ\u0012\u0010H\u001a\u00020)2\n\u0010I\u001a\u0006\u0012\u0002\b\u00030KJ\u000f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00020MH\u0096\u0002J\b\u0010N\u001a\u00020\u0002H\u0016J\b\u0010O\u001a\u00020\u0002H\u0016J\u0016\u0010P\u001a\b\u0012\u0002\b\u0003\u0018\u00010K2\u0006\u0010=\u001a\u00020\u0002H\u0016J \u0010P\u001a\u0006\u0012\u0002\b\u00030K2\u0006\u0010=\u001a\u00020\u00022\n\u0010Q\u001a\u0006\u0012\u0002\b\u00030KH\u0016J&\u0010P\u001a\n\u0012\u0004\u0012\u0002H1\u0018\u00010K\"\u0006\b\u0000\u00101\u0018\u00012\u0006\u0010=\u001a\u00020\u0002H\u0087\b¢\u0006\u0002\bRJ2\u0010P\u001a\b\u0012\u0004\u0012\u0002H10K\"\u0006\b\u0000\u00101\u0018\u00012\u0006\u0010=\u001a\u00020\u00022\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H10KH\u0087\b¢\u0006\u0002\bSJ\u0012\u0010T\u001a\u0004\u0018\u00010\u00002\u0006\u0010=\u001a\u00020\u0002H\u0016J\u0018\u0010T\u001a\u00020\u00002\u0006\u0010=\u001a\u00020\u00022\u0006\u0010Q\u001a\u00020\u0000H\u0016J\u0012\u0010U\u001a\u0004\u0018\u00010\u00022\u0006\u0010=\u001a\u00020\u0002H\u0016J\u0018\u0010U\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u00022\u0006\u0010Q\u001a\u00020\u0002H\u0016J\u0012\u0010V\u001a\u0004\u0018\u00010W2\u0006\u0010=\u001a\u00020\u0002H\u0016J\u0018\u0010V\u001a\u00020W2\u0006\u0010=\u001a\u00020\u00022\u0006\u0010Q\u001a\u00020WH\u0016J\u0017\u0010X\u001a\u0004\u0018\u0001042\u0006\u0010=\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010YJ\u0018\u0010X\u001a\u0002042\u0006\u0010=\u001a\u00020\u00022\u0006\u0010Q\u001a\u000204H\u0016J\u0012\u0010Z\u001a\u0004\u0018\u00010\t2\u0006\u0010=\u001a\u00020\u0002H\u0016J\u0018\u0010Z\u001a\u00020\t2\u0006\u0010=\u001a\u00020\u00022\u0006\u0010Q\u001a\u00020\tH\u0016J\u001a\u0010[\u001a\u0004\u0018\u00010\t2\b\u0010\\\u001a\u0004\u0018\u00010\t2\u0006\u0010]\u001a\u00020\u0002J\u001a\u0010[\u001a\u0004\u0018\u00010\t2\b\u0010\\\u001a\u0004\u0018\u00010\t2\u0006\u0010^\u001a\u00020_J\u0012\u0010`\u001a\u0004\u0018\u00010\t2\u0006\u0010=\u001a\u00020\u0002H\u0016R&\u0010\u000e\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000f\u0010\u0007\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R&\u0010\u0015\u001a\u0004\u0018\u00010\u00168\u0016@\u0016X\u0097\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0017\u0010\u0007\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR*\u0010\u001c\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t0\u001d8\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001e\u0010\u0007\u001a\u0004\b\u001f\u0010 R4\u0010!\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\"8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b#\u0010\u0007\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'¨\u0006b"}, d2 = {"Lio/dcloud/uts/UTSJSONObject;", "", "", "Lio/dcloud/uts/IUTSObject;", "Lio/dcloud/uts/log/LogSelfV2;", "Lio/dcloud/uts/IUTSSourceMap;", "<init>", "()V", "anyObj", "", "sourceMap", "Lio/dcloud/uts/UTSSourceMapPosition;", "(Ljava/lang/Object;Lio/dcloud/uts/UTSSourceMapPosition;)V", "(Ljava/lang/Object;)V", "__$hostSourceMap", "get__$hostSourceMap$annotations", "get__$hostSourceMap", "()Lio/dcloud/uts/UTSSourceMapPosition;", "set__$hostSourceMap", "(Lio/dcloud/uts/UTSSourceMapPosition;)V", "__$getOriginalPosition", "__$arrayContent", "Lcom/alibaba/fastjson/JSONArray;", "get__$arrayContent$annotations", "get__$arrayContent", "()Lcom/alibaba/fastjson/JSONArray;", "set__$arrayContent", "(Lcom/alibaba/fastjson/JSONArray;)V", "dynamicJSONFields", "Lio/dcloud/uts/gson/internal/LinkedTreeMap;", "getDynamicJSONFields$annotations", "getDynamicJSONFields", "()Lio/dcloud/uts/gson/internal/LinkedTreeMap;", "__$cacheCustomFieldMap", "Lio/dcloud/uts/Map;", "get__$cacheCustomFieldMap$annotations", "get__$cacheCustomFieldMap", "()Lio/dcloud/uts/Map;", "set__$cacheCustomFieldMap", "(Lio/dcloud/uts/Map;)V", "initCacheField", "", "forEach", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "parse", "T", "()Ljava/lang/Object;", "isJSONArray", "", "fillJSON", "json", "get", "set", "toMap", "toLogMap", "", "hasOwnProperty", IApp.ConfigProperty.CONFIG_KEY, "wrapToJSONArray", WXBasicComponentType.LIST, "", "array", "", "([Ljava/lang/Object;)Lcom/alibaba/fastjson/JSONArray;", "wrapArrayItem", AbsoluteConst.XML_ITEM, "toJSONObject", "Lcom/alibaba/fastjson/JSON;", "mergeOther", "other", "", "Lio/dcloud/uts/UTSArray;", "iterator", "", "toString", "toJSONString", "getArray", "def", "getArray_withType", "getArray_withType_def", "getJSON", "getString", "getNumber", "", "getBoolean", "(Ljava/lang/String;)Ljava/lang/Boolean;", "getAny", "getCanInnerAttribute", "host", "attribute", "index", "", "resolveKeyPath", "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UTSJSONObject implements Iterable<String>, IUTSObject, LogSelfV2, IUTSSourceMap, KMappedMarker {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @GsonTransparent
    private JSONArray __$arrayContent;

    @GsonTransparent
    private Map<String, java.lang.Object> __$cacheCustomFieldMap;

    @GsonTransparent
    private UTSSourceMapPosition __$hostSourceMap;

    @GsonTransparent
    private final LinkedTreeMap<String, java.lang.Object> dynamicJSONFields;

    @UTSJsonTransparent
    public static /* synthetic */ void getDynamicJSONFields$annotations() {
    }

    @UTSJsonTransparent
    public static /* synthetic */ void get__$arrayContent$annotations() {
    }

    @UTSJsonTransparent
    public static /* synthetic */ void get__$cacheCustomFieldMap$annotations() {
    }

    @UTSJsonTransparent
    public static /* synthetic */ void get__$hostSourceMap$annotations() {
    }

    public UTSJSONObject() {
        this.dynamicJSONFields = new LinkedTreeMap<>();
    }

    /* compiled from: UTSJSONObject.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u001f\u0010\t\u001a\u00020\b2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b\"\u00020\f¢\u0006\u0002\u0010\rJ,\u0010\t\u001a\u0004\u0018\u0001H\u000e\"\u0006\b\u0000\u0010\u000e\u0018\u00012\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b\"\u00020\fH\u0086\b¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lio/dcloud/uts/UTSJSONObject$Companion;", "", "<init>", "()V", "keys", "Lio/dcloud/uts/UTSArray;", "", "utsObj", "Lio/dcloud/uts/UTSJSONObject;", "assign", "input", "", "Lio/dcloud/uts/IUTSObject;", "([Lio/dcloud/uts/IUTSObject;)Lio/dcloud/uts/UTSJSONObject;", "T", "([Lio/dcloud/uts/IUTSObject;)Ljava/lang/Object;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UTSArray<String> keys(UTSJSONObject utsObj) {
            Intrinsics.checkNotNullParameter(utsObj, "utsObj");
            Set<String> setKeySet = utsObj.toMap().keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
            return new UTSArray<>(CollectionsKt.toList(setKeySet));
        }

        public final UTSJSONObject assign(IUTSObject... input) {
            Intrinsics.checkNotNullParameter(input, "input");
            return Object.INSTANCE.assign((IUTSObject[]) Arrays.copyOf(input, input.length));
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: assign, reason: collision with other method in class */
        public final /* synthetic */ <T> T m511assign(IUTSObject... input) {
            java.lang.Object objFromJson;
            Intrinsics.checkNotNullParameter(input, "input");
            String jSONString = Object.INSTANCE.assign((IUTSObject[]) Arrays.copyOf(input, input.length)).toJSONString();
            JSON json = JSON.INSTANCE;
            Intrinsics.reifiedOperationMarker(4, "T");
            if (Intrinsics.areEqual("String", java.lang.Object.class.getSimpleName())) {
                Intrinsics.reifiedOperationMarker(1, "T");
                objFromJson = jSONString;
            } else {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
                try {
                    JSON json2 = JSON.INSTANCE;
                    Gson cacheParseGson = JSON.INSTANCE.getCacheParseGson();
                    Intrinsics.needClassReification();
                    objFromJson = cacheParseGson.fromJson(jSONString, new TypeToken<T>() { // from class: io.dcloud.uts.UTSJSONObject$Companion$assign$$inlined$parseObjectType$1
                    }.getType());
                } catch (Exception e) {
                    ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
                    objFromJson = null;
                }
            }
            if (objFromJson == null) {
                return null;
            }
            return objFromJson;
        }
    }

    public final UTSSourceMapPosition get__$hostSourceMap() {
        return this.__$hostSourceMap;
    }

    public final void set__$hostSourceMap(UTSSourceMapPosition uTSSourceMapPosition) {
        this.__$hostSourceMap = uTSSourceMapPosition;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UTSJSONObject(java.lang.Object obj, UTSSourceMapPosition sourceMap) {
        this(obj);
        Intrinsics.checkNotNullParameter(sourceMap, "sourceMap");
        this.__$hostSourceMap = sourceMap;
    }

    @Override // io.dcloud.uts.IUTSSourceMap
    /* renamed from: __$getOriginalPosition, reason: from getter */
    public UTSSourceMapPosition get__$hostSourceMap() {
        return this.__$hostSourceMap;
    }

    public JSONArray get__$arrayContent() {
        return this.__$arrayContent;
    }

    public void set__$arrayContent(JSONArray jSONArray) {
        this.__$arrayContent = jSONArray;
    }

    public LinkedTreeMap<String, java.lang.Object> getDynamicJSONFields() {
        return this.dynamicJSONFields;
    }

    public final Map<String, java.lang.Object> get__$cacheCustomFieldMap() {
        return this.__$cacheCustomFieldMap;
    }

    public final void set__$cacheCustomFieldMap(Map<String, java.lang.Object> map) {
        this.__$cacheCustomFieldMap = map;
    }

    private final void initCacheField() throws IllegalAccessException, IllegalArgumentException {
        if (Intrinsics.areEqual(getClass().getName(), "io.dcloud.uts.UTSJSONObject")) {
            this.__$cacheCustomFieldMap = null;
            return;
        }
        if (this.__$cacheCustomFieldMap == null) {
            this.__$cacheCustomFieldMap = new Map<>();
            Field[] declaredFields = getClass().getDeclaredFields();
            Intrinsics.checkNotNull(declaredFields);
            for (Field field : declaredFields) {
                if (field.getAnnotation(GsonTransparent.class) == null && (!Intrinsics.areEqual(field.getName(), "Companion") || !(field.get(this) instanceof Companion))) {
                    field.setAccessible(true);
                    java.lang.Object obj = field.get(this);
                    Map<String, java.lang.Object> map = this.__$cacheCustomFieldMap;
                    Intrinsics.checkNotNull(map);
                    String name = field.getName();
                    Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                    map.set(name, obj);
                }
            }
        }
    }

    public UTSJSONObject(java.lang.Object obj) {
        this();
        if (obj == null) {
            return;
        }
        if (obj instanceof UTSSourceMapPosition) {
            this.__$hostSourceMap = (UTSSourceMapPosition) obj;
            return;
        }
        if (!(obj instanceof JSONObject)) {
            if (!(obj instanceof UTSJSONObject)) {
                if (obj instanceof java.util.Map) {
                    for (Map.Entry entry : ((java.util.Map) obj).entrySet()) {
                        if (entry.getKey() != null) {
                            if (Intrinsics.areEqual("__$originalPosition", entry.getKey()) && (entry.getValue() instanceof UTSSourceMapPosition)) {
                                java.lang.Object value = entry.getValue();
                                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type io.dcloud.uts.UTSSourceMapPosition");
                                this.__$hostSourceMap = (UTSSourceMapPosition) value;
                            } else {
                                getDynamicJSONFields().put(NumberKt.toString_number_nullable$default(entry.getKey(), (Number) null, 1, (java.lang.Object) null), entry.getValue());
                            }
                        }
                    }
                    return;
                }
                return;
            }
            for (Map.Entry<String, java.lang.Object> entry2 : ((UTSJSONObject) obj).toMap().entrySet()) {
                getDynamicJSONFields().put(entry2.getKey(), entry2.getValue());
            }
            return;
        }
        for (Map.Entry entry3 : ((java.util.Map) obj).entrySet()) {
            getDynamicJSONFields().put(entry3.getKey(), entry3.getValue());
        }
    }

    public final void forEach(Function1<java.lang.Object, Unit> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        Iterator<Map.Entry<String, java.lang.Object>> it = toMap().entrySet().iterator();
        while (it.hasNext()) {
            java.lang.Object value = it.next().getValue();
            if (value != null) {
                action.invoke2(value);
            }
        }
    }

    public final /* synthetic */ <T> T parse() {
        String jSONString = toJSONObject().toJSONString();
        Intrinsics.checkNotNullExpressionValue(jSONString, "toJSONString(...)");
        Intrinsics.reifiedOperationMarker(4, "T");
        if (Intrinsics.areEqual("String", java.lang.Object.class.getSimpleName())) {
            Intrinsics.reifiedOperationMarker(1, "T");
            return (T) jSONString;
        }
        ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
        try {
            JSON json = JSON.INSTANCE;
            Gson cacheParseGson = JSON.INSTANCE.getCacheParseGson();
            Intrinsics.needClassReification();
            return (T) cacheParseGson.fromJson(jSONString, new TypeToken<T>() { // from class: io.dcloud.uts.UTSJSONObject$parse$$inlined$parseType$default$1
            }.getType());
        } catch (Exception e) {
            ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
            return null;
        }
    }

    @Deprecated(message = "UTSJSONObject never be a array", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public final boolean isJSONArray() {
        return get__$arrayContent() != null;
    }

    public final void fillJSON(java.lang.Object json) {
        Intrinsics.checkNotNullParameter(json, "json");
        if (json instanceof JSONArray) {
            set__$arrayContent((JSONArray) json);
            return;
        }
        if (json instanceof JSONObject) {
            set__$arrayContent(null);
            for (Map.Entry<String, java.lang.Object> entry : ((JSONObject) json).entrySet()) {
                Intrinsics.checkNotNullExpressionValue(entry, "next(...)");
                Map.Entry<String, java.lang.Object> entry2 = entry;
                java.lang.Object value = entry2.getValue();
                if (value instanceof JSONObject) {
                    LinkedTreeMap<String, java.lang.Object> dynamicJSONFields = getDynamicJSONFields();
                    String key = entry2.getKey();
                    String jSONString = ((JSONObject) value).toJSONString();
                    Intrinsics.checkNotNullExpressionValue(jSONString, "toJSONString(...)");
                    dynamicJSONFields.put(key, JSON.parse(jSONString));
                } else if (value instanceof JSONArray) {
                    LinkedTreeMap<String, java.lang.Object> dynamicJSONFields2 = getDynamicJSONFields();
                    String key2 = entry2.getKey();
                    String jSONString2 = ((JSONArray) value).toJSONString();
                    Intrinsics.checkNotNullExpressionValue(jSONString2, "toJSONString(...)");
                    dynamicJSONFields2.put(key2, JSON.parse(jSONString2));
                } else {
                    getDynamicJSONFields().put(entry2.getKey(), value);
                }
            }
        }
    }

    @Override // io.dcloud.uts.IUTSObject
    public java.lang.Object get(java.lang.Object name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (get__$arrayContent() != null) {
            if (!(name instanceof Integer)) {
                return null;
            }
            Number number = (Number) name;
            int iIntValue = number.intValue();
            JSONArray jSONArray = get__$arrayContent();
            Intrinsics.checkNotNull(jSONArray);
            if (iIntValue >= jSONArray.size()) {
                return null;
            }
            JSONArray jSONArray2 = get__$arrayContent();
            Intrinsics.checkNotNull(jSONArray2);
            return jSONArray2.get(number.intValue());
        }
        java.lang.Object obj = getDynamicJSONFields().get(name);
        return obj != null ? obj : toMap().get(name);
    }

    @Override // io.dcloud.uts.IUTSObject
    public void set(java.lang.Object name, java.lang.Object value) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (get__$arrayContent() != null) {
            if (name instanceof Integer) {
                Number number = (Number) name;
                int iIntValue = number.intValue();
                JSONArray jSONArray = get__$arrayContent();
                Intrinsics.checkNotNull(jSONArray);
                if (iIntValue < jSONArray.size()) {
                    JSONArray jSONArray2 = get__$arrayContent();
                    Intrinsics.checkNotNull(jSONArray2);
                    jSONArray2.set(number.intValue(), value);
                    return;
                }
                return;
            }
            return;
        }
        getDynamicJSONFields().put(name.toString(), value);
    }

    public Map<String, java.lang.Object> toMap() throws IllegalAccessException, IllegalArgumentException {
        Map<String, java.lang.Object> map = new Map<>();
        initCacheField();
        Map<String, java.lang.Object> map2 = this.__$cacheCustomFieldMap;
        if (map2 != null) {
            Intrinsics.checkNotNull(map2);
            map.putAll(map2);
        }
        map.putAll(getDynamicJSONFields());
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.uts.log.LogSelfV2
    public java.util.Map<String, java.lang.Object> toLogMap() {
        Map map = new Map();
        if (get__$arrayContent() == null) {
            for (KProperty1 kProperty1 : KClasses.getMemberProperties(Reflection.getOrCreateKotlinClass(getClass()))) {
                KCallablesJvm.setAccessible(kProperty1, true);
                V vCall = kProperty1.call(this);
                if (Intrinsics.areEqual(kProperty1.getName(), "__$hostSourceMap")) {
                    if (vCall instanceof UTSSourceMapPosition) {
                        JsonObject jsonObject = new JsonObject();
                        UTSSourceMapPosition uTSSourceMapPosition = (UTSSourceMapPosition) vCall;
                        jsonObject.addProperty("name", uTSSourceMapPosition.getName());
                        jsonObject.addProperty("file", uTSSourceMapPosition.getFile());
                        jsonObject.addProperty("column", Integer.valueOf(uTSSourceMapPosition.getColumn()));
                        jsonObject.addProperty("line", Integer.valueOf(uTSSourceMapPosition.getLine()));
                        map.put("__$originalPosition", jsonObject);
                    }
                } else if (KAnnotatedElements.findAnnotations(kProperty1, Reflection.getOrCreateKotlinClass(UTSJsonTransparent.class)).isEmpty()) {
                    if (Intrinsics.areEqual("kotlin.Number", kProperty1.getReturnType().toString())) {
                        map.put(kProperty1.getName(), new UTSLogInfo(vCall, "kotlin.Number", ""));
                    } else {
                        map.put(kProperty1.getName(), vCall);
                    }
                }
            }
            for (Map.Entry<String, java.lang.Object> entry : getDynamicJSONFields().entrySet()) {
                map.put(entry.getKey().toString(), entry.getValue());
            }
        }
        return map;
    }

    public final boolean hasOwnProperty(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return toMap().containsKey(key);
    }

    private final JSONArray wrapToJSONArray(List<?> list) {
        JSONArray jSONArray = new JSONArray();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.add(wrapArrayItem(it.next()));
        }
        return jSONArray;
    }

    private final JSONArray wrapToJSONArray(java.lang.Object[] array) {
        JSONArray jSONArray = new JSONArray();
        for (java.lang.Object obj : array) {
            jSONArray.add(wrapArrayItem(obj));
        }
        return jSONArray;
    }

    private final java.lang.Object wrapArrayItem(java.lang.Object item) throws IllegalAccessException, IllegalArgumentException {
        if (item instanceof UTSJSONObject) {
            return ((UTSJSONObject) item).toJSONObject();
        }
        if (item instanceof List) {
            return wrapToJSONArray((List<?>) item);
        }
        if (item instanceof java.lang.Object[]) {
            return wrapToJSONArray((java.lang.Object[]) item);
        }
        if (!(item instanceof JSONObject) && !(item instanceof String) && !(item instanceof Number) && !(item instanceof Boolean) && !(item instanceof Character) && item != null) {
            if (item instanceof IJsonStringify) {
                return wrapArrayItem(((IJsonStringify) item).toJSON());
            }
            if (item instanceof UTSObject) {
                JSONObject jSONObject = new JSONObject();
                UTSObject uTSObject = (UTSObject) item;
                Iterator<String> it = uTSObject.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    jSONObject.put((JSONObject) next, (String) wrapArrayItem(uTSObject.get(next)));
                }
                return jSONObject;
            }
        }
        return item;
    }

    public com.alibaba.fastjson.JSON toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, java.lang.Object> entry : toMap().entrySet()) {
            jSONObject.put((JSONObject) entry.getKey(), (String) wrapArrayItem(entry.getValue()));
        }
        return jSONObject;
    }

    public final void mergeOther(UTSJSONObject other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (other.isJSONArray()) {
            com.alibaba.fastjson.JSON jSONObject = other.toJSONObject();
            Intrinsics.checkNotNull(jSONObject, "null cannot be cast to non-null type com.alibaba.fastjson.JSONArray");
            JSONArray jSONArray = (JSONArray) jSONObject;
            int length = jSONArray.toArray().length;
            for (int i = 0; i < length; i++) {
                getDynamicJSONFields().put(String.valueOf(i), jSONArray.get(i));
            }
            return;
        }
        for (Map.Entry<String, java.lang.Object> entry : other.toMap().entrySet()) {
            getDynamicJSONFields().put(entry.getKey(), entry.getValue());
        }
    }

    public final void mergeOther(IUTSObject other) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (String str : other) {
            getDynamicJSONFields().put(str, other.get(str));
        }
    }

    public final void mergeOther(List<?> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int size = other.size();
        for (int i = 0; i < size; i++) {
            getDynamicJSONFields().put(String.valueOf(i), other.get(i));
        }
    }

    public final void mergeOther(UTSArray<?> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int size = other.size();
        for (int i = 0; i < size; i++) {
            getDynamicJSONFields().put(String.valueOf(i), other.get(i));
        }
    }

    @Override // java.lang.Iterable, io.dcloud.uts.IUTSObject
    public Iterator<String> iterator() {
        return toMap().keySet().iterator();
    }

    public String toString() {
        return "[object Object]";
    }

    public String toJSONString() {
        String jSONString = toJSONObject().toJSONString();
        Intrinsics.checkNotNullExpressionValue(jSONString, "toJSONString(...)");
        return jSONString;
    }

    public UTSArray<?> getArray(String key) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        java.lang.Object objResolveKeyPath = resolveKeyPath(key);
        if (objResolveKeyPath instanceof UTSArray) {
            return (UTSArray) objResolveKeyPath;
        }
        return null;
    }

    public UTSArray<?> getArray(String key, UTSArray<?> def) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        UTSArray<?> array = getArray(key);
        return array != null ? array : def;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ <T> UTSArray<T> getArray_withType(String key) throws IllegalAccessException, IllegalArgumentException {
        String strStringify;
        java.lang.Object objFromJson;
        Intrinsics.checkNotNullParameter(key, "key");
        java.lang.Object objResolveKeyPath = resolveKeyPath(key);
        if (!(objResolveKeyPath instanceof UTSArray)) {
            return null;
        }
        UTSArray<T> uTSArray = (UTSArray) objResolveKeyPath;
        Intrinsics.needClassReification();
        if (uTSArray.find(new Function1<?, Boolean>() { // from class: io.dcloud.uts.UTSJSONObject$getArray$someEleNoT$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Boolean invoke2(java.lang.Object obj) {
                Intrinsics.reifiedOperationMarker(3, "T");
                return Boolean.valueOf(!(obj instanceof java.lang.Object));
            }
        }) == null) {
            return uTSArray;
        }
        if (uTSArray.find(UTSJSONObject$getArray$someEleNoUTS$1.INSTANCE) != null || (strStringify = JSON.stringify(objResolveKeyPath)) == 0) {
            return null;
        }
        JSON json = JSON.INSTANCE;
        if (Intrinsics.areEqual("String", "UTSArray")) {
            objFromJson = (UTSArray) strStringify;
        } else {
            ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
            try {
                JSON json2 = JSON.INSTANCE;
                Gson cacheParseGson = JSON.INSTANCE.getCacheParseGson();
                Intrinsics.needClassReification();
                objFromJson = cacheParseGson.fromJson(strStringify, new TypeToken<UTSArray<T>>() { // from class: io.dcloud.uts.UTSJSONObject$getArray$$inlined$parseArrayType$1
                }.getType());
            } catch (Exception e) {
                ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
                objFromJson = null;
            }
        }
        UTSArray<T> uTSArray2 = (UTSArray) objFromJson;
        if (uTSArray2 != null) {
            return uTSArray2;
        }
        return null;
    }

    public UTSJSONObject getJSON(String key) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        java.lang.Object objResolveKeyPath = resolveKeyPath(key);
        if (objResolveKeyPath instanceof UTSJSONObject) {
            return (UTSJSONObject) objResolveKeyPath;
        }
        if (!(objResolveKeyPath instanceof JSONObject)) {
            return null;
        }
        UTSJSONObject uTSJSONObject = new UTSJSONObject();
        uTSJSONObject.fillJSON(objResolveKeyPath);
        return uTSJSONObject;
    }

    public UTSJSONObject getJSON(String key, UTSJSONObject def) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        UTSJSONObject json = getJSON(key);
        return json != null ? json : def;
    }

    public String getString(String key) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        java.lang.Object objResolveKeyPath = resolveKeyPath(key);
        if (objResolveKeyPath instanceof String) {
            return (String) objResolveKeyPath;
        }
        return null;
    }

    public String getString(String key, String def) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        String string = getString(key);
        return string != null ? string : def;
    }

    public Number getNumber(String key) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        java.lang.Object objResolveKeyPath = resolveKeyPath(key);
        if (objResolveKeyPath instanceof Number) {
            return (Number) objResolveKeyPath;
        }
        return null;
    }

    public Number getNumber(String key, Number def) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        Number number = getNumber(key);
        return number != null ? number : def;
    }

    public Boolean getBoolean(String key) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        java.lang.Object objResolveKeyPath = resolveKeyPath(key);
        if (objResolveKeyPath instanceof Boolean) {
            return (Boolean) objResolveKeyPath;
        }
        return null;
    }

    public boolean getBoolean(String key, boolean def) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean bool = getBoolean(key);
        return bool != null ? bool.booleanValue() : def;
    }

    public java.lang.Object getAny(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return resolveKeyPath(key);
    }

    public java.lang.Object getAny(String key, java.lang.Object def) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        java.lang.Object any = getAny(key);
        return any != null ? any : def;
    }

    public final java.lang.Object getCanInnerAttribute(java.lang.Object host, String attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        if (host instanceof UTSJSONObject) {
            return ((UTSJSONObject) host).get(attribute);
        }
        if (host instanceof IUTSObject) {
            return ((IUTSObject) host).get(attribute);
        }
        if (host instanceof java.util.Map) {
            return ((java.util.Map) host).get(attribute);
        }
        return null;
    }

    public final java.lang.Object getCanInnerAttribute(java.lang.Object host, int index) {
        if (host instanceof List) {
            return ((List) host).get(index);
        }
        if (host instanceof java.lang.Object[]) {
            return ((java.lang.Object[]) host)[index];
        }
        return null;
    }

    public java.lang.Object resolveKeyPath(String key) throws IllegalAccessException, IllegalArgumentException {
        java.lang.Object map;
        String value;
        Intrinsics.checkNotNullParameter(key, "key");
        if (Intrinsics.areEqual(getClass().getName(), "io.dcloud.uts.UTSJSONObject")) {
            map = getDynamicJSONFields();
        } else {
            map = toMap();
        }
        AbstractMap abstractMap = (AbstractMap) map;
        if (abstractMap.containsKey(key)) {
            return abstractMap.get(key);
        }
        try {
            for (String str : StringsKt.split$default((CharSequence) key, new char[]{Operators.DOT}, false, 0, 6, (java.lang.Object) null)) {
                MatchResult matchResultFind$default = Regex.find$default(new Regex("(\\w+)((\\[\\d+])+)"), str, 0, 2, null);
                if (matchResultFind$default != null) {
                    kotlin.text.MatchGroup matchGroup = matchResultFind$default.getGroups().get(1);
                    if (matchGroup == null) {
                        value = "";
                    } else {
                        value = matchGroup.getValue();
                    }
                    ArrayList arrayList = new ArrayList();
                    Intrinsics.checkNotNull(matchGroup);
                    for (String str2 : StringKt.split(StringKt.replaceAll(StringKt.substring$default(str, Integer.valueOf(matchGroup.getRange().getLast() + 1), null, 2, null), Operators.ARRAY_START_STR, ""), Operators.ARRAY_END_STR)) {
                        if (str2.length() > 0) {
                            arrayList.add(Integer.valueOf(Integer.parseInt(str2)));
                        }
                    }
                    java.lang.Object canInnerAttribute = getCanInnerAttribute(map, value);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        canInnerAttribute = getCanInnerAttribute(canInnerAttribute, ((Number) it.next()).intValue());
                        map = canInnerAttribute;
                    }
                } else {
                    map = getCanInnerAttribute(map, str);
                }
            }
            return map;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ <T> UTSArray<T> getArray_withType_def(String key, UTSArray<T> def) throws IllegalAccessException, IllegalArgumentException {
        String strStringify;
        java.lang.Object objFromJson;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        java.lang.Object objResolveKeyPath = resolveKeyPath(key);
        UTSArray<T> uTSArray = null;
        if (objResolveKeyPath instanceof UTSArray) {
            UTSArray<T> uTSArray2 = (UTSArray) objResolveKeyPath;
            Intrinsics.needClassReification();
            if (uTSArray2.find(new Function1<?, Boolean>() { // from class: io.dcloud.uts.UTSJSONObject$getArray$$inlined$getArray_withType$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Boolean invoke2(java.lang.Object obj) {
                    Intrinsics.reifiedOperationMarker(3, "T");
                    return Boolean.valueOf(!(obj instanceof java.lang.Object));
                }
            }) == null) {
                uTSArray = uTSArray2;
            } else if (uTSArray2.find(UTSJSONObject$getArray$someEleNoUTS$1.INSTANCE) == null && (strStringify = JSON.stringify(objResolveKeyPath)) != 0) {
                JSON json = JSON.INSTANCE;
                if (Intrinsics.areEqual("String", "UTSArray")) {
                    objFromJson = (UTSArray) strStringify;
                } else {
                    ObjectKt.getGlobalError().put(Thread.currentThread().getName(), null);
                    try {
                        JSON json2 = JSON.INSTANCE;
                        Gson cacheParseGson = JSON.INSTANCE.getCacheParseGson();
                        Intrinsics.needClassReification();
                        objFromJson = cacheParseGson.fromJson(strStringify, new TypeToken<UTSArray<T>>() { // from class: io.dcloud.uts.UTSJSONObject$getArray$$inlined$getArray_withType$2
                        }.getType());
                    } catch (Exception e) {
                        ObjectKt.getGlobalError().put(Thread.currentThread().getName(), e);
                        objFromJson = null;
                    }
                }
                UTSArray<T> uTSArray3 = (UTSArray) objFromJson;
                if (uTSArray3 != null) {
                    uTSArray = uTSArray3;
                }
            }
        }
        return uTSArray != null ? uTSArray : def;
    }
}
