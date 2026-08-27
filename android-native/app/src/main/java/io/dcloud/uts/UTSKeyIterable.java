package io.dcloud.uts;

import io.dcloud.uts.UTSKeyIterable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;

/* compiled from: UTSIterator.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\tH\u0002J\u0013\u0010\n\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0096\u0002J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\u00012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010H\u0016¨\u0006\u0011À\u0006\u0003"}, d2 = {"Lio/dcloud/uts/UTSKeyIterable;", "", "ignoredKeys", "Lio/dcloud/uts/UTSArray;", "", "getAllFields", "", "Ljava/lang/reflect/Field;", "clazz", "Ljava/lang/Class;", "get", "propertyName", "set", "", "value", "keyIterator", "Lio/dcloud/uts/UTSIterator;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface UTSKeyIterable {
    java.lang.Object get(java.lang.Object propertyName);

    UTSArray<String> ignoredKeys();

    UTSIterator<String> keyIterator();

    void set(java.lang.Object propertyName, java.lang.Object value);

    /* compiled from: UTSIterator.kt */
    /* renamed from: io.dcloud.uts.UTSKeyIterable$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static List $private$getAllFields(UTSKeyIterable _this, Class cls) {
            ArrayList arrayList = new ArrayList();
            while (cls != null) {
                Iterator it = ArrayIteratorKt.iterator(cls.getDeclaredFields());
                while (it.hasNext()) {
                    Field field = (Field) it.next();
                    Intrinsics.checkNotNull(field);
                    arrayList.add(field);
                }
                cls = cls.getSuperclass();
            }
            return arrayList;
        }

        public static java.lang.Object $default$get(UTSKeyIterable _this, java.lang.Object propertyName) {
            java.lang.Object next;
            Intrinsics.checkNotNullParameter(propertyName, "propertyName");
            try {
                Iterator it = $private$getAllFields(_this, _this.getClass()).iterator();
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
                return field.get(_this);
            } catch (Exception e) {
                e.printStackTrace();
                console.INSTANCE.errorV1(e);
                return null;
            }
        }

        public static void $default$set(UTSKeyIterable _this, java.lang.Object propertyName, java.lang.Object obj) throws IllegalAccessException, IllegalArgumentException {
            java.lang.Object next;
            Intrinsics.checkNotNullParameter(propertyName, "propertyName");
            try {
                Iterator it = $private$getAllFields(_this, _this.getClass()).iterator();
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
                if (field != null) {
                    field.setAccessible(true);
                    field.set(_this, obj);
                    return;
                }
                console.INSTANCE.errorV1("not found field " + propertyName);
            } catch (Exception e) {
                e.printStackTrace();
                console.INSTANCE.errorV1(e);
            }
        }

        public static UTSIterator $default$keyIterator(UTSKeyIterable _this) {
            KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(_this.getClass());
            UTSArray<String> uTSArrayIgnoredKeys = _this.ignoredKeys();
            ArrayList arrayList = new ArrayList();
            for (KProperty1 kProperty1 : KClasses.getMemberProperties(orCreateKotlinClass)) {
                if (!uTSArrayIgnoredKeys.contains(kProperty1.getName())) {
                    arrayList.add(kProperty1.getName());
                }
            }
            final Iterator it = arrayList.iterator();
            return new UTSIterator(new Function0() { // from class: io.dcloud.uts.UTSKeyIterable$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final java.lang.Object invoke() {
                    return UTSKeyIterable.CC.keyIterator$lambda$2(it);
                }
            });
        }

        public static UTSIteratorResult<String> keyIterator$lambda$2(Iterator<String> it) {
            if (it.hasNext()) {
                return new UTSIteratorResult<>(false, it.next());
            }
            return new UTSIteratorResult<>(true, "");
        }

        public static UTSArray $default$ignoredKeys(UTSKeyIterable _this) {
            return new UTSArray();
        }
    }

    /* compiled from: UTSIterator.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static UTSArray<String> ignoredKeys(UTSKeyIterable uTSKeyIterable) {
            return CC.$default$ignoredKeys(uTSKeyIterable);
        }

        @Deprecated
        public static java.lang.Object get(UTSKeyIterable uTSKeyIterable, java.lang.Object propertyName) {
            Intrinsics.checkNotNullParameter(propertyName, "propertyName");
            return CC.$default$get(uTSKeyIterable, propertyName);
        }

        @Deprecated
        public static void set(UTSKeyIterable uTSKeyIterable, java.lang.Object propertyName, java.lang.Object obj) throws IllegalAccessException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(propertyName, "propertyName");
            CC.$default$set(uTSKeyIterable, propertyName, obj);
        }

        @Deprecated
        public static UTSIterator<String> keyIterator(UTSKeyIterable uTSKeyIterable) {
            return CC.$default$keyIterator(uTSKeyIterable);
        }
    }
}
