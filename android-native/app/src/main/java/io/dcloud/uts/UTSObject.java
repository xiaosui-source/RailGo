package io.dcloud.uts;

import io.dcloud.common.DHInterface.IApp;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSObject.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010(\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0016\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\nH\u0002J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0096\u0002J\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0096\u0002J\u001b\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0096\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lio/dcloud/uts/UTSObject;", "Lio/dcloud/uts/IUTSObject;", "<init>", "()V", IApp.ConfigProperty.CONFIG_TARGET, "", "propertyFields", "", "Ljava/lang/reflect/Field;", "init", "", "iterator", "", "", "get", "propertyName", "set", "value", "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UTSObject implements IUTSObject {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private transient List<Field> propertyFields;
    private transient java.lang.Object target;

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean init$lambda$0(Field it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Modifier.isPublic(it.getModifiers()) && Modifier.isStatic(it.getModifiers()) && Modifier.isFinal(it.getModifiers()) && it.getName().equals("$stable");
    }

    private final void init() throws IllegalAccessException, IllegalArgumentException {
        Field field;
        Function1 function1 = new Function1() { // from class: io.dcloud.uts.UTSObject$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final java.lang.Object invoke(java.lang.Object obj) {
                return Boolean.valueOf(UTSObject.init$lambda$0((Field) obj));
            }
        };
        Field[] declaredFields = getClass().getDeclaredFields();
        Intrinsics.checkNotNull(declaredFields);
        int length = declaredFields.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                field = null;
                break;
            }
            field = declaredFields[i];
            if (Intrinsics.areEqual(field.getName(), "__v_raw")) {
                break;
            } else {
                i++;
            }
        }
        if (field != null) {
            field.setAccessible(true);
            java.lang.Object obj = field.get(this);
            this.target = obj;
            Intrinsics.checkNotNull(obj);
            Field[] declaredFields2 = obj.getClass().getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields2, "getDeclaredFields(...)");
            List list = ArraysKt.toList(declaredFields2);
            ArrayList arrayList = new ArrayList();
            for (java.lang.Object obj2 : list) {
                if (!((Boolean) function1.invoke(obj2)).booleanValue()) {
                    arrayList.add(obj2);
                }
            }
            this.propertyFields = arrayList;
            return;
        }
        this.target = this;
        List list2 = ArraysKt.toList(declaredFields);
        ArrayList arrayList2 = new ArrayList();
        for (java.lang.Object obj3 : list2) {
            if (!((Boolean) function1.invoke(obj3)).booleanValue()) {
                arrayList2.add(obj3);
            }
        }
        this.propertyFields = arrayList2;
    }

    @Override // io.dcloud.uts.IUTSObject
    public Iterator<String> iterator() throws IllegalAccessException, IllegalArgumentException {
        if (this.target == null) {
            init();
        }
        List<Field> list = this.propertyFields;
        Intrinsics.checkNotNull(list);
        List<Field> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((Field) it.next()).getName());
        }
        return arrayList.iterator();
    }

    @Override // io.dcloud.uts.IUTSObject
    public java.lang.Object get(java.lang.Object propertyName) throws IllegalAccessException, IllegalArgumentException {
        java.lang.Object next;
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        if (this.target == null) {
            init();
        }
        List<Field> list = this.propertyFields;
        Intrinsics.checkNotNull(list);
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((Field) next).getName(), propertyName)) {
                break;
            }
        }
        Field field = (Field) next;
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        return field.get(this.target);
    }

    @Override // io.dcloud.uts.IUTSObject
    public void set(java.lang.Object propertyName, java.lang.Object value) throws IllegalAccessException, IllegalArgumentException {
        java.lang.Object next;
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        if (this.target == null) {
            init();
        }
        List<Field> list = this.propertyFields;
        Intrinsics.checkNotNull(list);
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual(((Field) next).getName(), propertyName)) {
                    break;
                }
            }
        }
        Field field = (Field) next;
        if (field == null) {
            return;
        }
        field.setAccessible(true);
        field.set(this.target, value);
    }

    /* compiled from: UTSObject.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ#\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u00012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lio/dcloud/uts/UTSObject$Companion;", "", "<init>", "()V", "hasOwn", "", "obj", "Lio/dcloud/uts/UTSObject;", IApp.ConfigProperty.CONFIG_KEY, "", "toGenericType", "T", "value", "type", "Ljava/lang/reflect/Type;", "(Ljava/lang/Object;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean hasOwn(UTSObject obj, String key) throws IllegalAccessException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(obj, "obj");
            Intrinsics.checkNotNullParameter(key, "key");
            Iterator<String> it = obj.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(it.next(), key)) {
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final <T> T toGenericType(java.lang.Object value, Type type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (!(type instanceof Class)) {
                return value;
            }
            Class cls = (Class) type;
            if (!UTSObject.class.isAssignableFrom(cls)) {
                return value;
            }
            if (value == 0) {
                return null;
            }
            if (cls.isInstance(value)) {
                return value;
            }
            T t = (T) JSON.parse(JSON.stringify(value), type);
            Intrinsics.checkNotNull(t);
            return t;
        }
    }
}
